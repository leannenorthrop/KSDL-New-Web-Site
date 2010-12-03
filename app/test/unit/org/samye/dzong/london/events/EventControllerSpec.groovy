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

import grails.test.*
import grails.plugin.spock.*
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import java.text.SimpleDateFormat
import org.samye.dzong.london.media.Image
import org.samye.dzong.london.ShiroUser
import org.samye.dzong.london.community.Teacher
import org.samye.dzong.london.venue.*
import org.joda.time.*
import groovy.time.*


/**
 *  Unit test for Events controller
 */
class EventControllerSpec extends ControllerSpec {
    def 'Index redirects to home'() {
        when:
        controller.index()

        then:
        redirectArgs == [action: controller.home]
    }

    def 'Home generates page with events separated into 4 lists'() {
        setup: "When events are present for today, this week and this month"
        def today = new Date()
        today.clearTime()
        def regularEvents = (0..3).collect { validRegularEvent(today+it) }
        def todayEvents = (0..3).collect { validEvent(today) }
        def weekEvents = thisWeekEvents() 
        def monthEvents = thisMonthEvents()
        def othermonthEvents = (1..4).collect { validEvent(today+(31*it)) }
        Event.metaClass.static.published = { 
            return new Expando(list: { regularEvents+todayEvents+weekEvents+monthEvents+othermonthEvents })  
        }

        and: "article service is present"
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})

        when:
        def model = controller.home()

        then:
        def weekEventsOnToday = weekEvents.findAll { it.dates[0].startDate == today }.size()
        def monthEventsOnToday = monthEvents.findAll {it.dates[0].startDate == today}.size()
        def regularEventsOnToday = regularEvents.findAll{it.dates[0].startDate == today}.size() 
        def expectedTodayEvents = todayEvents.size() + weekEventsOnToday + monthEventsOnToday + regularEventsOnToday 
        model.todaysEvents.size() == expectedTodayEvents

        def weekEventsAfterToday = weekEvents.findAll { it.dates[0].startDate.after(today) }.size()
        def monthEventsAfterToday = monthEvents.findAll {it.dates[0].startDate.after(today)}.size()
        def expectedWeekEvents = weekEventsAfterToday + monthEventsAfterToday 
        model.thisWeeksEvents.size() == expectedWeekEvents 

        def cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK))
        def endOfWeek = cal.time
        model.thisMonthEvents.size() == monthEvents.findAll{it.dates[0].startDate.after(endOfWeek)}.size()  

        model.followingMonths.size() == othermonthEvents.size() 

        model.regularEvents == regularEvents 
    }

    def 'Current redirects to list with start and end days of current month'() {
        when:
        controller.current()

        then:
        def now = Calendar.getInstance()
        now.set(Calendar.DAY_OF_MONTH, now.getActualMinimum(Calendar.DAY_OF_MONTH))
        def start = now.format("yyyy-MM-dd")
        now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH))
        def end = now.format("yyyy-MM-dd")
        redirectArgs == [action: controller.list, params:['start': start,'end':end]]
    }

    def 'Future redirects to list with start and end day from current month to end of month next year'() {
        when:
        controller.future()

        then:
        def now = Calendar.getInstance()
        now.set(Calendar.DAY_OF_MONTH, now.getActualMinimum(Calendar.DAY_OF_MONTH))
        def start = now.format("yyyy-MM-dd")
        now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH))
        now.roll(Calendar.YEAR,1)
        def end = now.format("yyyy-MM-dd")
        redirectArgs == [action: controller.list, params:['start': start,'end':end]]
    }

    def 'Regular renders list with all regular events'() {
        setup:
        def regularEvents = (0..3).collect { validRegularEvent(new Date()) }
        Event.metaClass.static.published = { 
            return new Expando(list: { regularEvents })  
        }

        when:
        controller.regular()

        then:
        controller.modelAndView.viewName == 'list'
        controller.modelAndView.model.linkedHashMap.events == regularEvents
    }

    def 'Manager renders management page'() {
        when:
        controller.manage()

        then:
        controller.modelAndView.viewName == 'manage'
    }
    
    def validEvent() {
        def event = new Event(title: "Meditation", 
                              summary: "summary", 
                              content: "content",
                              publishState: 'Published',
                              category: 'E',
                              isRepeatable: false,
                              organizer: new ShiroUser(username:"leanne.northrop@abc.com"),
                              leader: new Teacher(name:"AKA"),
                              venue: new Venue(name:"Spa Road"),
                              deleted:false,
                              featured:false,
                              home:false)
        return event
    }

    def validEvent(date) {
        def event = validEvent() 
        event.title=date.format("dd MM yyyy")
        addValidDate(event,date)
        return event
    }

    def validRegularEvent(date) {
        def event = validEvent() 
        event.title=date.format("dd MM yyyy")
        addValidRegularDate(event,date)
        return event
    }

    def addValidDate(event,date) {
        def eventDate = new EventDate()
        eventDate.event = event
        eventDate.startDate = date
        eventDate.endDate = date
        eventDate.isRule = false 
        event.dates = [eventDate]
    }

    def addValidRegularDate(event,date) {
        def eventDate = new EventDate()
        eventDate.event = event
        eventDate.startDate = date
        eventDate.endDate = date
        eventDate.ruleType = "always"
        eventDate.modifierType = "D"
        eventDate.interval = 1
        eventDate.isRule = true
        event.dates = [eventDate]
    }

    def thisWeekEvents() {
        def cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK))
        def now = cal.time
        now.clearTime()
        def weekEvents = (0..6).collect { validEvent(now+it) }
        weekEvents
    }

    def thisMonthEvents() {
        def cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH))
        def now = cal.time
        now.clearTime()
        def monthEvents = (0..20).collect { validEvent(now+it) }
        monthEvents
    }
}

