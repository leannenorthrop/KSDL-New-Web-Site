/** *****************************************************************************
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
 ***************************************************************************** */

package org.samye.dzong.london.events

import org.apache.shiro.SecurityUtils
import org.joda.time.*
import java.text.SimpleDateFormat
import java.text.DateFormat
import java.text.ParsePosition

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
        def events = Event.publishState('Published').list();

        def now = new Date()
        now.setHours(0)
        now.setMinutes(0)
        now.setSeconds(0)
        def tommorow = now + 1
        def endOfWeek = now + 7
        def endOfMonth = now + 31
        def todaysEvents = Event.publishedDateRange(now, tommorow, "eventDate", "desc").list();
        def thisWeeksEvents = Event.publishedDateRange(now, endOfWeek, "eventDate", "desc").list();
        def thisMonthEvents = Event.publishedDateRange(now, endOfMonth, "eventDate", "desc").list();
        return [events: events, todaysEvents: todaysEvents, thisWeeksEvents: thisWeeksEvents, thisMonthEvents: thisMonthEvents, title: "Current Programme"]
    }

    def list = {
        def events = Event.publishState('Published').list();
        return [events: events, title: 'events.all.title']
    }

    // the save and update actions only accept POST requests
    static allowedMethods = [save: 'POST', update: 'POST', changeState: 'GET']

    def ajaxUnpublished = {
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

    def ajaxPublished = {
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

    def ajaxArchived = {
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

    def ajaxReady = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)

        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
            model = eventService.ready(params)
        } else {
            model = eventService.userReady(params)
        }
        render(view: 'ready', model: model)
    }

    def ajaxDeleted = {
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

            try {
                event.startTime = new TimeOfDay(Integer.valueOf(params.startTimeHour), Integer.valueOf(params.startTimeMin))
            } catch (error) {
                log.info("Start time could not be set.", error)
                event.hasErrors()
                flash.isError = true
                flash.message = "event.starttime.update.error"
                flash.args = [event]
                render(view: 'create', model: [event: event, id: params.id])
            }
            try {
                event.endTime = new TimeOfDay(Integer.valueOf(params.endTimeHour), Integer.valueOf(params.endTimeMin))
            } catch (error) {
                log.info("End time could not be set.", error)
                event.hasErrors()
                flash.isError = true
                flash.message = "event.endtime.update.error"
                flash.args = [event]
                render(view: 'create', model: [event: event, id: params.id])
            }
            try {
                params.eventDate = new SimpleDateFormat("dd-MM-yyyy").parse(params.eventDate, new ParsePosition(0))
            } catch (error) {
                event.hasErrors()
                flash.isError = true
                flash.message = "event.update.error"
                flash.args = [event]
                render(view: 'create', model: [event: event, id: params.id])
            }
            try {
                event.eventDuration = new MutablePeriod(event.startTime.toDateTimeToday(), event.endTime.toDateTimeToday()).toPeriod()
            } catch (error) {
                event.hasErrors()
                flash.isError = true
                flash.message = "event.update.error"
                flash.args = [event]
                render(view: 'create', model: [event: event, id: params.id])
            }
            event.properties = params
            def isFirstPublish = event.publishState != 'Published'
            if (isFirstPublish) {
                event.datePublished = new Date()
            }

            event.publishState = "Published"
            def tags = event.tags
            def newtags = params.tags.split(',')
            tags.each {tag ->
                def found = newtags.find {newtag -> newtag == tag}
                if (!found) {
                    event.removeTag(tag)
                }
            }
            event.addTags(newtags)

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


            try {
                event.startTime = new TimeOfDay(Integer.valueOf(params.startTimeHour), Integer.valueOf(params.startTimeMin))
            } catch (error) {
                log.info("Start time could not be set.", error)
                event.hasErrors()
                flash.isError = true
                flash.message = "event.starttime.update.error"
                flash.args = [event]
                render(view: 'create', model: [event: event, id: params.id])
            }
            try {
                event.endTime = new TimeOfDay(Integer.valueOf(params.endTimeHour), Integer.valueOf(params.endTimeMin))
            } catch (error) {
                log.info("End time could not be set.", error)
                event.hasErrors()
                flash.isError = true
                flash.message = "event.endtime.update.error"
                flash.args = [event]
                render(view: 'create', model: [event: event, id: params.id])
            }
            try {
                params.eventDate = new SimpleDateFormat("dd-MM-yyyy").parse(params.eventDate, new ParsePosition(0))
            } catch (error) {
                event.hasErrors()
                flash.isError = true
                flash.message = "event.update.error"
                flash.args = [event]
                render(view: 'create', model: [event: event, id: params.id])
            }
            try {
                event.eventDuration = new MutablePeriod(event.startTime.toDateTimeToday(), event.endTime.toDateTimeToday()).toPeriod()
            } catch (error) {
                event.hasErrors()
                flash.isError = true
                flash.message = "event.update.error"
                flash.args = [event]
                render(view: 'create', model: [event: event, id: params.id])
            }
            event.properties = params
            if (!event.hasErrors() && event.save()) {
                flash.message = "Event ${event.title} updated"
                redirect(action: manage)
            }
            else {
                flash.isError = true
                flash.message = "event.update.error"
                flash.args = [event]
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
        event.eventDate = new Date()
        event.properties = params
        return ['event': event]
    }

    def save = {
        def event = new Event()
        event.author = userLookupService.lookup()
        try {
            event.startTime = new TimeOfDay(Integer.valueOf(params.startTimeHour), Integer.valueOf(params.startTimeMin))
        } catch (error) {
            log.info("Start time could not be set.", error)
            event.hasErrors()
            flash.isError = true
            flash.message = "event.starttime.update.error"
            flash.args = [event]
            render(view: 'create', model: [event: event, id: params.id])
        }
        try {
            event.endTime = new TimeOfDay(Integer.valueOf(params.endTimeHour), Integer.valueOf(params.endTimeMin))
        } catch (error) {
            log.info("End time could not be set.", error)
            event.hasErrors()
            flash.isError = true
            flash.message = "event.endtime.update.error"
            flash.args = [event]
            render(view: 'create', model: [event: event, id: params.id])
        }
        try {
            params.eventDate = new SimpleDateFormat("dd-MM-yyyy").parse(params.eventDate, new ParsePosition(0))
        } catch (error) {
            event.hasErrors()
            flash.isError = true
            flash.message = "event.update.error"
            flash.args = [event]
            render(view: 'create', model: [event: event, id: params.id])
        }
        try {
            event.eventDuration = new MutablePeriod(event.startTime.toDateTimeToday(), event.endTime.toDateTimeToday()).toPeriod()
        } catch (error) {
            event.hasErrors()
            flash.isError = true
            flash.message = "event.update.error"
            flash.args = [event]
            render(view: 'create', model: [event: event, id: params.id])
        }
        event.properties = params

        if (!event.hasErrors() && event.save()) {
            flash.message = "Event ${event.title} created"
            redirect(action: manage)
        }
        else {
            flash.isError = true
            flash.message = "event.update.error"
            flash.args = [event]
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
