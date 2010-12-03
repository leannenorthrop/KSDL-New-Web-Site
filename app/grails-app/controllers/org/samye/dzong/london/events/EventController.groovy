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
import org.joda.time.format.*
import java.text.ParsePosition
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap
import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList
import net.fortuna.ical4j.model.property.ProdId
import net.fortuna.ical4j.model.property.Version
import net.fortuna.ical4j.model.property.CalScale
import net.fortuna.ical4j.data.CalendarOutputter
import net.fortuna.ical4j.model.property.Attach

/**
 * Web request handler for event information.
 *
 * @author Leanne Northrop
 * @since 29th January, 2010, 17:04
 */
class EventController {
    //flash.message = "${message(code: 'default.updated.message', args: [message(code: 'event.label', default: 'Event'), event.id])}"
    def userLookupService
    def eventService
    def emailService
	def articleService
    private static int MIN = 30
    private static int MAX = 200

    def index = {
        redirect(action: home)
    }

    def home = {
        def now = new java.util.Date() 

        def publishedEvents = Event.published().list();
        def todaysEvents = publishedEvents.findAll { it.isOnDay(now) }
		
        now = now + 1
        def dt = new DateTime(now.getTime())
        DateTime endOfWeek = dt.dayOfWeek().withMaximumValue();
        int weekdays = Days.daysBetween(dt, endOfWeek).getDays();
        def thisWeeksEvents = publishedEvents.findAll { event ->
            def rule = event.dates[0]
            def isRegular = rule.isUnbounded()
            return !isRegular && event.isOnDay(now, weekdays)
        };

        DateTime endOfMonth = dt.dayOfMonth().withMaximumValue();
        int monthdays = Days.daysBetween(endOfWeek, endOfMonth).getDays();
        def thisMonthEvents = publishedEvents.findAll { event ->
            def rule = event.dates[0]
            def isRegular = rule.isUnbounded()
            return !isRegular && event.isOnDay(endOfWeek.toDate(), monthdays)
        };

        def regularEvents = publishedEvents.findAll { event ->
            def rule = event.dates[0]
            rule.isRule && rule.isUnbounded()
        };

        def startOfThisMonth = dt.dayOfMonth().withMinimumValue();
        def followingMonths = []
		(1..12).each { month ->
			def followingMonth = startOfThisMonth.monthOfYear().addToCopy(month)
			boolean foundAnEvent = false;
			for (event in publishedEvents) {
				if (!event.dates[0].isUnbounded()) {
					foundAnEvent = event.isOnDay(followingMonth.toDate(), followingMonth.dayOfMonth().withMaximumValue().getDayOfMonth())
					if (foundAnEvent) {
						break;
					}
				}
			}
            if (foundAnEvent) {
	            followingMonths << [followingMonth.toDate(), followingMonth.dayOfMonth().withMaximumValue().toDate()]
			}
        }
        def model = [events: publishedEvents, todaysEvents: todaysEvents, thisWeeksEvents: thisWeeksEvents, thisMonthEvents: thisMonthEvents,regularEvents:regularEvents, followingMonths:followingMonths, title: "Current Programme"]
		articleService.addHeadersAndKeywords(model,request,response)
		model
    }

	def current = {
        DateTime dt = new DateTime();
        dt = dt.withTime(0,0,0,0)
        def now = dt.toDate()
		def startOfThisMonth = dt.dayOfMonth().withMinimumValue();
		def endOfThisMonth = dt.dayOfMonth().withMaximumValue();
		def format = DateTimeFormat.forPattern("yyyy-MM-dd");
		
		redirect(action: list, params: ["start": format.print(startOfThisMonth),"end":format.print(endOfThisMonth)])
	}
	
	def future = {
        DateTime dt = new DateTime();
        dt = dt.withTime(0,0,0,0)
        def now = dt.toDate()
		def startOfThisMonth = dt.dayOfMonth().withMinimumValue();
		def endOfThisMonth = dt.dayOfMonth().withMaximumValue();
		def nextYear = endOfThisMonth.plusYears(1)
		def format = DateTimeFormat.forPattern("yyyy-MM-dd");
		
		redirect(action: list, params: ["start": format.print(startOfThisMonth),"end":format.print(nextYear)])
	}
	
	def regular = {
		def publishedEvents = Event.published().list();
		def regularEvents = publishedEvents.findAll { event ->
            def rule = event.dates[0]
            rule.isRule && rule.isUnbounded()
        };		
		render(view: 'list', model:[events:regularEvents])
	}
			
    def list = {
        def model = []
        if (params) {
            try {
                def dateParser = new SimpleDateFormat("yyyy-MM-dd")
                def start = new DateTime();

                if (params?.start) {
                    start = new DateTime(dateParser.parse(params.start).getTime())
                } 
                start = start.withTime(0, 0, 0, 0);

                def lastDayOfMonth = start.dayOfMonth().withMaximumValue()
                int daysUntilEndOfMonth = Days.daysBetween(start, lastDayOfMonth).getDays();
                start = start.toDate()

                def publishedEvents = Event.unorderedPublished().list(params);
                def events = publishedEvents.findAll { event ->
                    def rule = event.dates[0]
                    return event.isOnDay(start, daysUntilEndOfMonth)
                };

                def datePat = message(code: 'event.date.format')
                def args = [start.format(datePat)]
                model = [events: events, title: message(code: 'event.between', args: args)]
            } catch(error) {
                log.warn "Unable to generate list of events", error
                def events = Event.unorderedPublished().list(params);
                model = [events: events, title: 'events.all.title']
            }
        } else {
            def events = Event.unorderedPublished().list(params);
            model = [events: events, title: 'events.all.title']
        }
        articleService.addHeadersAndKeywords(model,request,response)
        model
    }

    // the save and update actions only accept POST requests
    static allowedMethods = [save: 'POST', update: 'POST', changeState: 'GET']

    def ajaxUnpublished = {
        render(view: 'unpublished',model:getEventsForView('unpublished',params))
    }

    def ajaxPublished = {
        render(view: 'published',model:getEventsForView('published',params))
    }

    def ajaxArchived = {
        render(view: 'archived',model:getEventsForView('archived',params))
    }

    def ajaxReady = {
        render(view: 'ready',model:getEventsForView('ready',params))
    }

    def ajaxDeleted = {
        render(view: 'deleted',model:getEventsForView('deleted',params))
    }

    def getEventsForView(viewName,params) {
        params.offset = params?.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params?.max ? params.max.toInteger() : MIN, MAX)

        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
            model = eventService."${viewName}"(params)
        } else {
            model = eventService."user${viewName.capitalize()}"(params)
        }
        model
    }

    def manage = {
        render(view: 'manage')
    }


    def handleError(message, action) {
        flash.message = message 
        flash.isError = true
        redirect(action: action)
    }

    def handleError(message, action, event) {
        flash.message = message 
        flash.isError = true
        flash.bean = event
        flash.args = [event] 
        redirect(action: action, id: event.id)
    }

    def view = {
        def event = Event.get(params.id)
        if (!event) {
            handleError("Event not found with id ${params.id}",home)
        }
        else {
            def similar = eventService.findSimilar(event)
            return [event: event, id: params.id, similar:similar]
        }
    }

    def query = {
        def event = Event.get(params.id)
        if (!event) {
            handleError("Event not found with id ${params.id}",home)
        }
        else {
            def id = params.id;
            def similar = eventService.findSimilar(event)
            return [event: event, id: id, similar:similar]
        }
    }

    // todo return to original page before request
    def send = {
        def event = Event.get(params.id)
        if (event && event.organizer && params.email && params.body && params.subject) {
            emailService.sendEventQuery(event.organizer.username, params.email, params.subject, params.body)
        }
        redirect(action: home)
    }

    def show = {
        def event = Event.get(params.id)

        if (!event) {
            handleError("Event not found with id ${params.id}",home)
        }
        else {
            return [event: event, id: params.id]
        }
    }

    def versionCheck(params,event) {
        def ok = true
        if (params.version) {
            def version = params.version.toLong()
            ok = event.version <= version
            if (!ok) {
                event.errors.rejectValue("version", "event.optimistic.locking.failure", "Another user has updated this Event while you were editing.")
                handleError("Can not perform the requested action at this time.",manage)
            }
        }
        ok
    }

    // todo ensure delete request sends version
    def delete = {
        def event = Event.get(params.id)
        if (event) {
            if (versionCheck(params,event)){
                event.publishState = "Unpublished"
                event.deleted = true
                event.title += ' (Deleted)'
                if (!event.hasErrors() && event.save()) {
                    flash.message = "Event ${event.title} has been deleted"
                    redirect(action: manage)
                }
                else {
                    handleError("Can not delete '${event}' at this time",manage,event)
                }
            }
        }
        else {
            handleError("Event not found with id ${params.id}",manage)
        }
    }

    def edit = {
        def event = Event.get(params.id)

        if (!event) {
            handleError("Event not found with id ${params.id}",manage)
        }
        else {
            if (!flash.message) {
                flash.message = 'event.help' 
            }
            [event: event, id: params.id]
        }
    }

    def pre_publish = {
        def event = Event.get(params.id)

        if (!event) {
            handleError("Event not found with id ${params.id}",manage)
        }
        else {
            render(view: 'publish', model: [event: event], id: params.id)
        }
    }

    def saveEvent(event,params,onSave,saveMsg,onError,errMsg) {
        if (versionCheck(params,event)) {
            event.properties = params
            event.bindPrices(params)
            event.bindDates(params)

            if (!event.hasErrors() && event.save()) {
                if (params.tags) {
                    def newtags = params.tags.split(',') as List
                    event.setTags(newtags)
                }
                flash.message = saveMsg
                redirect(action: onSave)
            }
            else {
                handleError(errMsg,onError,event)
            }
        }
    }

    def publish = {
        def event = Event.get(params.id)
        if (event) {
            params.publishState = 'Published'
            params.datePublished = new Date()
            def okMsg = "Event ${event.title} has been Published"
            def errMsg = "Event ${event.title} could not be ${params.state} due to errors. Please correct the errors and try again."
            saveEvent(event,params,manage,okMsg,pre_publish,errMsg)
        }
        else {
            handleError("Event not found with id ${params.id}",manage)
        }
    }

    def update = {
        def event = Event.get(params.id)
        if (event) {
            def okMsg = "Event ${event.title} has been saved"
            def errMsg = "event.update.error"
            saveEvent(event,params,manage,okMsg,pre_publish,errMsg)
        }
        else {
            handleError("Event not found with id ${params.id}",manage)
        }
    }

    def create = {
        def event = new Event()
        event.properties = params
        event.organizer = userLookupService.lookup()
        if (!flash.message) {
            flash.message = "Please ensure that a Teacher entry has been created and published under the Teachers/Therapists menu before creating a new Event. Also please ensure the selected Organizer has configured their public details by going to the Settings -> About Me menu."
        }
        return [event: event]
    }

    def save = {
        def event = new Event()
        event.author = userLookupService.lookup()

        event.properties = params
        if (!event.hasErrors() && event.save()) {
            flash.message = "Event ${event.title} has been created"
            redirect(action: manage)
        }
        else {
            handleError("event.update.error",create,event)
        }
    }

    def changeState = {
        def event = Event.get(params.id)
        if (event) {
            params.datePublished = new Date()
            params.publishState = params.state
            params.deleted = false
            def okMsg = "Event ${event.title} has been moved to ${params.publishState}"
            def errMsg = "Event ${event.title} could not be ${params.state} due to errors. Please correct the errors and try again."
            saveEvent(event,params,manage,okMsg,manage,errMsg)
        }
        else {
            handleError("Event not found with id ${params.id}",manage)
        }
    }

    def calendar = {
        try {
            def publishedEvents = null
            if (params.type) {
                if ("meditation" == params.type) {
                    publishedEvents = Event.meditation("title", "desc").list();
                } else if ("buddhism" == params.type) {
                    publishedEvents = Event.buddhism("title", "desc").list();
                } else if ("community" == params.type) {
                    publishedEvents = Event.community("title", "desc").list();
                } else if ("wellbeing" == params.type) {
                    publishedEvents = Event.wellbeing("title", "desc").list();
                } else {
                    publishedEvents = Event.published().list();
                }
            } else {
                publishedEvents = Event.published().list();
            }

            net.fortuna.ical4j.model.Calendar calendar = new net.fortuna.ical4j.model.Calendar();
            calendar.getProperties().add(new ProdId("-//" + message(code:'title')+ "//iCal4j 1.0//EN"));
            calendar.getProperties().add(Version.VERSION_2_0);
            calendar.getProperties().add(CalScale.GREGORIAN);

            publishedEvents.each { event ->
                def iCalEvent = event.toiCalVEvent()
                iCalEvent.getProperties().add(new Attach(new URI(createLink(action:'view',controller:'event',absolute:true,id:event.id).toString())));
                calendar.getComponents().add(iCalEvent)
            }

            calendar.validate(true)

            response.contentType = "text/calendar; charset=utf-8"

            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(calendar, response.outputStream);
        } catch (error) {
            log.error("Error generating a calendar ", error)
            redirect(action: 'home')
        }
    }
}
