/*******************************************************************************
 * Copyright © 2010 Leanne Northrop
 *
 * This file is part of Samye Content Management System.
 *
 * Samye Content Management System is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * Samye Content Management System is distributed in the hope that it will be
 * useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Samye Content Management System.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * BT plc, hereby disclaims all copyright interest in the program
 * “Samye Content Management System” written by Leanne Northrop.
 ******************************************************************************/

package org.samye.dzong.london.events

import org.apache.shiro.SecurityUtils
import org.joda.time.*

/**
 * Web request handler for event information.
 *
 * TODO: test
 *
 * Author: Leanne Northrop
 * Date: 29th January, 2010, 17:04
 */
class EventController {
    //flash.message = "${message(code: 'default.updated.message', args: [message(code: 'event.label', default: 'Event'), event.id])}"
    def userLookupService
    def eventService
    def twitterService

    def index = {
        redirect(action: home)
    }

    def home = {
        return [events: Event.publishState('Published').list(), title: "Current Programme"]
    }

    def list = {
        if (params.tags) {
            def tags = params.tags.toLowerCase().split(",").toList()
            def events = eventService.publishedByTags(tags)
            model: [events: events, title: message(code: 'all.events.with.tags', args: params.tags), auditLogs: auditDetails]
        } else {
            def publishedEvents = Event.findAllByPublishState("Published")
            model: [events: publishedEvents, title: message(code: 'all.events'), auditLogs: auditDetails]
        }
    }

    // the save and update actions only accept POST requests
    static allowedMethods = [save: 'POST', update: 'POST',changeState: 'GET']

    def ajaxUnpublishedEvents = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
            model = eventService.unpublished(params)
        } else {
            model = eventService.userUnpublished(params)
        }
        render(view: 'unpublished', model: model)
    }

    def ajaxPublishedEvents = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)

        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
            model = eventService.published(params)
        } else {
            model = eventService.userPublished(params)
        }
        render(view: 'published', model: model)
    }

    def ajaxArchivedEvents = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)

        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
            model = eventService.archived(params)
        } else {
            model = eventService.userArchived(params)
        }
        render(view: 'archived', model: model)
    }

    def ajaxDeletedEvents = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)

        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
            model = eventService.deleted(params)
        } else {
            model = eventService.userDeleted(params)
        }
        render(view: 'deleted', model: model)
    }

    def manage = {
        render(view: 'manage')
    }

    def view = {
        def event = Event.get(params.id)
        if (!event) {
            flash.message = "Event not found with id ${params.id}"
            redirect(action: home)
        }
        else {
            def id = params.id;
            return [event: event, id: id]
        }
    }

    def show = {
        def event = Event.get(params.id)

        if (!event) {
            flash.message = "Event not found with id ${params.id}"
            redirect(action: list)
        }
        else {
            return [event: event, id: params.id]
        }
    }

    def delete = {
        def event = Event.get(params.id)
        if (event) {
            if (params.version) {
                def version = params.version.toLong()
                if (event.version > version) {
                    event.errors.rejectValue("version", "event.optimistic.locking.failure", "Another user has updated this Event while you were editing.")
                    redirect(action: manage)
                    return
                }
            }
            event.publishState = "Unpublished"
            event.deleted = true
            if (!event.hasErrors() && event.save()) {
                flash.message = "Event ${event.title} deleted"
                redirect(action: manage)
            }
            else {
                redirect(action: manage)
            }
        }
        else {
            flash.message = "Event not found with id ${params.id}"
            redirect(action: manage)
        }
    }

    def edit = {
        def event = Event.get(params.id)

        if (!event) {
            flash.message = "Event not found with id ${params.id}"
            redirect(action: manage)
        }
        else {
            return [event: event, id: params.id]
        }
    }

    def pre_publish = {
        def event = Event.get(params.id)

        if (!event) {
            flash.message = "Event not found with id ${params.id}"
            redirect(action: manage)
        }
        else {
            return render(view: 'publish', model: [event: event], id: params.id)
        }
    }

    def publish = {
        def event = Event.get(params.id)
        if (event) {
            if (params.version) {
                def version = params.version.toLong()
                if (event.version > version) {
                    flash.message = "Event ${event.title} was being edited - please try again."
                    redirect(action: manage)
                    return
                }
            }

            def isFirstPublish = event.publishState != 'Published'
            if (isFirstPublish) {
                event.datePublished = new Date()
            }

            event.properties = params
            event.publishState = "Published"
            event.startTime = new TimeOfDay(Integer.valueOf(params.startTimeHour),Integer.valueOf(params.startTimeMin))
            event.eventDuration =  new MutablePeriod(Integer.valueOf(params.eventDurationHour), Integer.valueOf(params.eventDurationMin), 0, 0).toPeriod()
            if (params.tags) {
                event.parseTags(params.tags)
            }

            if (!event.hasErrors() && event.save()) {
                if (isFirstPublish) {
                    try {

                        twitterService.setStatus("We've just published ${event.title}.'", [username: 'lsdci', password: 'change!t']);
                    } catch (error) {

                    }
                }
                println "Published event. Publish date set to ${event.datePublished}"
                flash.message = "Event ${event.title} has been Published"
                redirect(action: manage)
            }
            else {
                flash.message = "Event ${event.title} could not be ${params.state} due to an internal error. Please try again."
                redirect(action: pre_publish, id: params.id)
            }
        }
        else {
            flash.message = "Event not found with id ${params.id}"
            redirect(action: manage)
        }
    }

    def update = {
        println "The id is ${params.id}"
        def event = Event.get(params.id)
        if (event) {
            if (params.version) {
                def version = params.version.toLong()
                if (event.version > version) {
                    flash.isError = true
                    flash.message = "event.update.error"
                    event.errors.rejectValue("version", "event.optimistic.locking.failure", "Another user has updated this Event while you were editing.")
                    render(view: 'edit', model: [event: event, id: params.id])
                    return
                }
            }
            event.properties = params
            event.startTime = new TimeOfDay(Integer.valueOf(params.startTimeHour),Integer.valueOf(params.startTimeMin))
            event.eventDuration =  new MutablePeriod(Integer.valueOf(params.eventDurationHour), Integer.valueOf(params.eventDurationMin), 0, 0).toPeriod()
            if (params.tags) {
                event.parseTags(params.tags)
            }
            if (!event.hasErrors() && event.save()) {
                flash.message = "Event ${event.title} updated"
                redirect(action: manage)
            }
            else {
                flash.isError = true
                flash.message = "event.update.error"
                render(view: 'edit', model: [event: event, id: params.id])
            }
        }
        else {
            flash.message = "Event not found with id ${params.id}"
            redirect(action: manage)
        }
    }

    def create = {
        def event = new Event()
        event.properties = params
        return ['event': event]
    }

    def save = {
        def event = new Event(params)
        event.author = userLookupService.lookup()
        event.startTime = new TimeOfDay(Integer.valueOf(params.startTimeHour),Integer.valueOf(params.startTimeMin))
        event.eventDuration =  new MutablePeriod(Integer.valueOf(params.eventDurationHour), Integer.valueOf(params.eventDurationMin), 0, 0).toPeriod()
        if (!event.hasErrors() && event.save()) {
            flash.message = "Event ${event.title} created"
            redirect(action: manage)
        }
        else {
            flash.isError = true
            flash.message = "event.update.error"
            render(view: 'create', model: [event: event, id: params.id])
        }
    }

    def changeState = {
        def event = Event.get(params.id)
        if (event) {
            if (params.version) {
                def version = params.version.toLong()
                if (event.version > version) {
                    flash.message = "Event ${event.title} was being edited - please try again."
                    redirect(action: manage)
                    return
                }
            }
            def isFirstPublish = event.publishState != 'Published' && params.state == 'Published'
            if (isFirstPublish) {
                event.datePublished = new Date()
            }
            event.publishState = params.state
            event.deleted = false
            if (!event.hasErrors() && event.save()) {
                if (isFirstPublish) {
                    try {
                        twitterService.setStatus("We've just published ${event}.'", [username: 'lsdci', password: 'change!t']);
                    } catch (error) {

                    }
                }
                flash.message = "Event ${event.title} has been moved to ${event.publishState}"
                redirect(action: manage)
            }
            else {
                flash.message = "Event ${event.title} could not be ${params.state} due to an internal error. Please try again."
                redirect(action: manage)
            }
        }
        else {
            flash.message = "Event not found with id ${params.id}"
            redirect(action: manage)
        }
    }
}
