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
import java.text.ParsePosition
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList

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
        def todaysEvents = Event.publishedDateRange(now, tommorow, "featured", "desc").list();
        def thisWeeksEvents = Event.publishedDateRange(now, endOfWeek, "featured", "desc").list();
        def thisMonthEvents = Event.publishedDateRange(now, endOfMonth, "featured", "desc").list();
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
            def dates = event.dates
            def rule = dates.find {it != null}
            return [event: event, id: params.id, rule: rule]
        }
    }

    def pre_publish = {
        def event = Event.get(params.id)

        if (!event) {
            flash.message = "Event not found with id ${params.id}"
            redirect(action: manage)
        }
        else {
            def dates = event.dates
            def rule = dates.find {it != null}
            return render(view: 'publish', model: [event: event, rule: rule], id: params.id)
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

            def errorParams = [isError: false]
            def dates = event.dates
            def rule = dates.find {it != null}
            handleDate(rule, params, errorParams)

            if (errorParams['isError']) {
                flash.isError = true
                flash.message = errorParams['message']
                flash.args = errorParams['args']
                render(view: 'create', model: [event: event, id: params.id, rule: rule])
            }

            event.properties = params
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
            def dates = event.dates
            def rule = dates.find {it != null}
            def errorParams = [isError: false]
            handleDate(rule, params, errorParams)

            if (errorParams['isError']) {
                flash.isError = true
                flash.message = errorParams['message']
                flash.args = errorParams['args']
                render(view: 'create', model: [event: event, id: params.id, rule: rule])
            } else {
                rule.save()
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
                render(view: 'edit', model: [event: event, id: params.id, rule: rule])
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

        def EventDate date = new EventDate()
        date.startDate = new Date()
        date.startTime = new TimeOfDay(9, 0)
        date.endTime = new TimeOfDay(10, 0)
        date.endDate = new Date()
        date.isRule = false

        // Set default values
        event.prices = event.getPriceList()
        (0..3).each{
            event.prices.get(it).category = it == 0 ? 'F' : (it == 1 ? 'S' : (it == 2 ? 'M' : 'O'));
        }

        return [event: event, rule: date]
    }

    def save = {
        def event = new Event()
        event.author = userLookupService.lookup()
        def EventDate date = new EventDate()
        date.startDate = new Date()
        date.startTime = new TimeOfDay(9, 0)
        date.endTime = new TimeOfDay(10, 0)
        date.endDate = new Date()
        date.isRule = false
        date.save()
        def errorParams = [isError: false]
        handleDate(date, params, errorParams)

        if (errorParams['isError']) {
            event.hasErrors()
            flash.isError = true
            flash.message = errorParams['message']
            flash.args = [event]
            render(view: 'create', model: [event: event, id: params.id, rule: date])
        } else {
            date.save()
            event.addToDates(date)
        }

        event.properties = params

        /*def _toBeDeleted = event.prices.findAll {it._deleted}
        if (_toBeDeleted) {
            event.prices.removeAll(_toBeDeleted)
        }
        println "************** ${event.prices}"
        */

        if (!event.hasErrors() && event.save()) {
            flash.isError = false
            flash.message = "Event ${event.title} created"
            redirect(action: manage)
        }
        else {
            flash.isError = true
            flash.message = "event.update.error"
            flash.args = [event]
            render(view: 'create', model: [event: event, id: params.id, rule: date])
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

    def handleDate(rule, params, errorParams) {
        def ruleParam = params.rule;
        if (ruleParam.type) {
            println "***************************${ruleParam}"

            try {
                rule.startTime = new TimeOfDay(Integer.valueOf(params.startTimeHour), Integer.valueOf(params.startTimeMin))
            } catch (error) {
                log.info("Start time could not be set.", error)
                errorParams['isError'] = true
                errorParams['message'] = "event.starttime.update.error"
            }
            try {
                rule.endTime = new TimeOfDay(Integer.valueOf(params.endTimeHour), Integer.valueOf(params.endTimeMin))
            } catch (error) {
                log.info("End time could not be set.", error)
                errorParams['isError'] = true
                errorParams['message'] = "event.endtime.update.error"
            }
            rule.isRule = ruleParam.type == '1';
            if (!rule.isRule) {
                try {
                    rule.startDate = new SimpleDateFormat("dd-MM-yyyy").parse(params.eventDate, new ParsePosition(0))
                    rule.endDate = rule.startDate
                } catch (error) {
                    log.info("Event date could not be set.", error)
                    errorParams['isError'] = true
                    errorParams['message'] = "event.update.error"
                }
            } else {
                rule.ruleType = ruleParam.ruleType1;
                if ('always' == rule.ruleType) {
                    try {
                        rule.startDate = new SimpleDateFormat("dd-MM-yyyy").parse(ruleParam.from[0], new ParsePosition(0))
                        rule.endDate = rule.startDate
                    } catch (error) {
                        log.info("Event date could not be set.", error)
                        errorParams['isError'] = true
                        errorParams['message'] = "event.update.error"
                    }
                } else {
                    try {
                        rule.startDate = new SimpleDateFormat("dd-MM-yyyy").parse(ruleParam.from[1], new ParsePosition(0))
                        rule.endDate = new SimpleDateFormat("dd-MM-yyyy").parse(ruleParam.until, new ParsePosition(0))
                    } catch (error) {
                        log.info("Event date could not be set.", error)
                        errorParams['isError'] = true
                        errorParams['message'] = "event.update.error"
                    }
                }

                def ruleType = ruleParam.ruleType2;
                if ('daily' == ruleType) {
                    rule.interval = Integer.valueOf(ruleParam.dailyinterval);
                    rule.modifierType = 'D';
                } else if ('weekly' == ruleType) {
                    rule.interval = Integer.valueOf(ruleParam.weeklyinterval);
                    rule.modifierType = 'W';
                    def modifier = ""
                    ruleParam.weekly.each {
                        modifier += it.key + " "
                    }
                    rule.modifier = modifier.trim()
                } else if ('monthly' == ruleType) {
                    rule.interval = Integer.valueOf(ruleParam.monthlyinterval);
                    rule.modifierType = 'MD';
                    def modifier = ""
                    ['one','two'].each { instance ->
                        def interval = ruleParam.monthly[instance].interval;
                        if ('5' == interval) {
                            interval = '1-'
                        } else {
                            interval += '+'
                        }
                        ruleParam.monthly[instance].each {
                            if ('interval' != it.key) {
                                modifier += interval + " " + it.key + " "
                            }
                        }
                    }
                    rule.modifier = modifier.trim()
                }
            }

            try {
                rule.duration = new MutablePeriod(rule.startTime.toDateTimeToday(), rule.endTime.toDateTimeToday()).toPeriod()
            } catch (error) {
                log.info("Event duration could not be set.", error)
                errorParams['isError'] = true
                errorParams['message'] = "event.update.error"
            }

            println "***************************${rule}"
        }
    }
}
