/*
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
 */
package org.samye.dzong.london.events

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
import java.text.SimpleDateFormat;
import grails.converters.JSON
import org.samye.dzong.london.cms.*


/*
 * Web request handler for event information.
 *
 * @author Leanne Northrop
 * @since 29th January, 2010, 17:04
 */
class EventController extends CMSController {
    //flash.message = "${message(code: 'default.updated.message', args: [message(code: 'event.label', default: 'Event'), event.id])}"
    def eventService
    def emailService
	def articleService
    def static final ADMIN_ROLES = ['Editor', 'Administrator'] 

    def index = {
        redirect(action: home)
    }

    def home = {
        def now = new java.util.Date() 

        def publishedEvents = Event.published().list();
        publishedEvents.sort();
        def todaysEvents = publishedEvents.findAll { 
            def r = it.isOnDay(now) 
            r
        }
		
        now = now + 1
        def dt = new DateTime(now.getTime())
        DateTime endOfWeek = dt.dayOfWeek().withMaximumValue();
        int weekdays = Days.daysBetween(dt, endOfWeek).getDays();
        def thisWeeksEvents = publishedEvents.findAll { event ->
            return event.isOnDay(now, weekdays)
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
		render(view: 'list', model:[events:regularEvents, title:'hi'])
	}
			
    def list = {
        def model = [:]

        def allEvents = Event.published().list();
        def now = new java.util.Date() 
        now = now + 1
        def dt = new DateTime(now.getTime())
        def startOfThisMonth = dt.dayOfMonth().withMinimumValue();
        def followingMonths = []
		(1..12).each { month ->
			def followingMonth = startOfThisMonth.monthOfYear().addToCopy(month)
			boolean foundAnEvent = false;
			for (event in allEvents) {
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
        model = [followingMonths:followingMonths]
        log.debug "Following months is ${model.followingMonths}"

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
                publishedEvents.sort()
                def events = publishedEvents.findAll { event ->
                    def rule = event.dates[0]
                    return !rule.isRule && event.isOnDay(start, daysUntilEndOfMonth)
                };

                def datePat = message(code: 'event.date.format')
                model << [events: events, title: start.format(datePat)]
            } catch(error) {
                log.warn "Unable to generate list of events", error
                def events = Event.unorderedPublished().list(params);
                model << [events: events, title: 'events.all.title']
            }
        } else {
            def events = Event.unorderedPublished().list(params);
            model << [events: events, title: 'events.all.title']
        }
        articleService.addHeadersAndKeywords(model,request,response)
        model
    }

    // the save and update actions only accept POST requests
    static allowedMethods = [save: 'POST', update: 'POST', changeState: 'GET']

    def manage = {
        render(view: 'manage')
    }

    def view = {
        viewEvent(params.id)
    }

    def query = {
        viewEvent(params.id)
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
        viewEvent(params.id)
    }

    // todo ensure delete request sends version
    def delete = {
        def event = Event.get(params.id)
        if (event) {
            if (versionCheck(params,event)){
                Event.withTransaction { status ->
                    try {
                        event.publishState = "Unpublished"
                        event.deleted = true
                        event.title += ' (Deleted)'
                        if (!event.hasErrors() && event.save()) {
                            flash.message = "Event ${event.title} has been deleted"
                            redirect(action: manage)
                        }
                        else {
                            def msg = "Can not delete ${event.title} at this time"
                            rollback(status,msg,event)
                            handleError(msg,event,manage)
                        }
                    } catch (error) {
                        def msg = "Can not delete ${event.title} at this time"
                        rollback(status,msg,event,error)
                        redirect(action: manage,id:params.id)
                    }
                }
            } else {
                redirect(action: manage)
            }
        }
        else {
            notFound(manage)
        }
    }

    def edit = {
        def event = Event.get(params.id)

        if (!event) {
            notFound(manage)
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
            notFound(manage)
        }
        else {
            render(view: 'publish', model: [event: event], id: params.id)
        }
    }

    def saveEvent(event,params,onSave,saveMsg,onError,errMsg) {
        if (versionCheck(params,event)) {
            Event.withTransaction { status ->
                try {
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
                        def msg = "Can not save ${event.title} at this time"
                        rollback(status,msg,event,error)
                        handleError(errMsg,event,onError)
                    }
                } catch(error) {
                    def msg = "Can not save ${event.title} at this time"
                    rollback(status,msg,event,error)
                    redirect(action: manage,id:params.id)
                }
            }
        } else {
            redirect(action: manage)
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
            notFound(manage)
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
            notFound(manage)
        }
    }

    def create = {
        def event = new Event()
        event.properties = params
        event.organizer = currentUser() 
        if (!flash.message) {
            flash.message = "Please ensure that a Teacher entry has been created and published under the Teachers/Therapists menu before creating a new Event. Also please ensure the selected Organizer has configured their public details by going to the Settings -> About Me menu."
        }
        return [event: event]
    }

    def save = {
        def event = new Event()
        event.author = currentUser() 

        Event.withTransaction { status ->
            try { 
                event.properties = params
                if (!event.hasErrors() && event.save()) {
                    flash.message = "Event ${event.title} has been created"
                    redirect(action: manage)
                }
                else {
                    def msg = "Can not save at this time"
                    rollback(status,msg,event)
                    handleError("event.update.error",event,create)
                }
            } catch (error) {
                def msg = "Can not save at this time"
                rollback(status,msg,event,error)
                redirect(action: create,id:params.id)
            }
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
            notFound(manage)
        }
    }

    def calendar = {
        []
    }

    def events = {
        def start = new Date(params.start.toLong()*1000);
        def end = new Date(params.end.toLong()*1000);

        def s = new DateTime(start.getTime())
        def e = new DateTime(end.getTime()) 
        int days = Days.daysBetween(s, e).getDays();

        def publishedEvents = Event.published().list();
      
        def moonData = grailsApplication.config.moonData
        response.contentType = "text/plain"
        response.outputStream << "["
        def first = true
        (0..days).each { delta ->
            def day = start + delta

            publishedEvents.eachWithIndex { event,index ->
                if (event.isOnDay(day)) {
                    def str = event.toJSON(day)
                    if (str) {
                        if (!first){ 
                            response.outputStream << ','
                        } else {
                            first = false
                        }
                        response.outputStream << str 
                    }
                }
            }
            def ss = day.format('yyyy-MM-dd')
            def name = null
            if (moonData.fullMoon.get(ss)) {
                name = "Full Moon"
            } else if (moonData.newMoon.get(ss)) {
                name = "New Moon"
            } else if (moonData.lastQuarter.get(ss)) {
                name = "Last Qtr Moon"
            } else if (moonData.firstQuarter.get(ss)) {
                name = "First Qtr Moon"
            }

            if (name) {
                if (!first){ 
                    response.outputStream << ','
                } else {
                    first = false
                }
                response.outputStream << "{"
                response.outputStream << '"title":"' + name + '",'
                response.outputStream << '"start":"' + ss + '",'
                response.outputStream << '"allDay":true' 
                response.outputStream << "}"
            }
        }
        response.outputStream << "]"
    }

    def subscribe = {
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
