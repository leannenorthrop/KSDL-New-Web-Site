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
 * CMS content management url handler for Event/Programme information.
 * Displays both content management pages under the Events navigation
 * menu within the CMS area and public pages under the Events navigation menu.
 * 
 * TODO: Complete refactor to increase DRY
 * TODO: Tidy this up in light of Grails lessons learned.
 * TODO: Complete internationalization.
 *
 * @author Leanne Northrop
 * @since  29th January, 2010, 17:04
 */
class EventController extends CMSController {
    def emailService
    def ADMIN_ROLES = ['EventOrganiser', 'Administrator']
    def DOMAIN_NAME = 'Event'
    
    /**
     * Although added via Bootstrap we re-add cms util methods here for
     * development purposes.
     */
    EventController() {
        CMSUtil.addFinderMethods(this)
        CMSUtil.addCMSMethods(this)
    }

    // the save and update actions only accept POST requests
    static allowedMethods = [manage: 'GET',
                             save: 'POST', 
                             update: 'POST', 
                             changeState: 'GET', 
                             delete: 'GET',                             
                             view: 'GET',                             
                             show: 'GET',                                                          
                             edit: 'GET',                                                                                       
                             pre_publish: 'GET',
                             preview: 'POST', 
                             updatePublished: 'POST',
                             updateAndPublish: 'POST',
                             onAddComment: ['POST','GET']]   
    def index = {
        forward(action:'home')
    }

    def home = {
        def now = new java.util.Date() 

        def publishedEvents = Event.findAllByPublishState('Published');
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
        def publishedEvents = Event.findAllByPublishState('Published');
        def regularEvents = publishedEvents.findAll { event ->
            def rule = event.dates[0]
            rule.isRule && rule.isUnbounded()
        };		
        render(view: 'list', model:[events:regularEvents, title:'hi'])
    }
			
    def list = {
        def model = [:]

        def allEvents = Event.findAllByPublishState('Published');
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

                def allPublishedEventsModel = this.publishedEvents(params);
                println allPublishedEventsModel
                println allPublishedEventsModel.events
                def allPublishedEvents = allPublishedEventsModel.events
                allPublishedEvents.sort()
                def events = allPublishedEvents.findAll { event ->
                    def rule = event.dates[0]
                    return !rule.isRule && event.isOnDay(start, daysUntilEndOfMonth)
                };

                def datePat = message(code: 'event.date.format')
                model << [events: events, title: start.format(datePat)]
            } catch(error) {
                log.warn "Unable to generate list of events", error
                def events = publishedEvents(params)['events'];
                model << [events: events, title: 'events.all.title']
            }
        } else {
            def events = Event.unorderedPublished().list(params);
            model << [events: events, title: 'events.all.title']
        }
        model
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
    
    def query = {
        viewEvent(params.id)
    }    

    def saveEvent(event,params,onSave,saveMsg,onError,errMsg) {
        if (!event) {
            event = new Event()
            event.author = currentUser()             
        }
        if (versionCheck(params,event)) {
            Event.withTransaction { status ->
                try {                    
                    event.properties = params
                    event.bindPrices(params)
                    event.bindDates(params)

                    if (!event.hasErrors() && event.save()) {
                        if (params.tags) {
                            def tags = article.tags
                            def newtags = params.tags.split(',')
                            if (tags) {
                                tags.each {tag ->
                                    def found = newtags.find {newtag -> newtag == tag}
                                    if (!found) {
                                        article.removeTag(tag)
                                    }
                                }
                            } else {
                                newtags = newtags as List
                                article.setTags(newtags)
                            }
                        }
                        flash.message = saveMsg
                        if (onSave == manage) {
                            redirect(action: onSave)                            
                        } else {
                            redirect(action: onSave,params:[id:params.id])
                        }
                    }
                    else {
                        def msg = "Can not save ${event} at this time"
                        flash.message = msg
                        flash.isError = true
                        flash.args = [event]
                        flash.bean = event                        
                        rollback(status,msg,event)
                        render(view: onError, model: [event: event])                        
                    }
                } catch(error) {
                    def msg = "Can not save ${event} at this time"
                    log.error msg, error                    
                    rollback(status,msg,event,error)
                    redirect(action: manage,id:params.id)
                }
            }
        } else {
            redirect(action: manage)
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

    def calendar = {
        []
    }

    def events = {
        def start = new Date(params.start.toLong()*1000);
        def end = new Date(params.end.toLong()*1000);

        def s = new DateTime(start.getTime())
        def e = new DateTime(end.getTime()) 
        int days = Days.daysBetween(s, e).getDays();

        def publishedEvents = Event.findAllByPublishState('Published');
      
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
                    publishedEvents = Event.findAllByPublishState('Published');
                }
            } else {
                publishedEvents = Event.findAllByPublishState('Published');
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
