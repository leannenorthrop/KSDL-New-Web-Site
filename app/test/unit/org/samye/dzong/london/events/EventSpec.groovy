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
import org.samye.dzong.london.media.Image
import org.samye.dzong.london.ShiroUser
import org.samye.dzong.london.community.Teacher
import org.samye.dzong.london.venue.*
import grails.plugin.spock.*
import spock.lang.*
import net.fortuna.ical4j.model.ValidationException

/**
 * Unit test for Event domain class.
 */
class EventSpec extends UnitSpec {
    def "Validation fails when title is blank"() {
        setup:
            def event = validEvent()
            mockForConstraintsTests(Event, [ event ])

        when:
            event.title = "" 

        then:
            false == event.validate()
            "blank" == event.errors["title"]
    }

    def "Validation fails when title is not unique"() {
        setup:
            def event1 = validEvent()
            def event2 = validEvent()
            mockForConstraintsTests(Event, [ event1, event2 ])

        when:
            event1.title = "a" 
            event2.title = "a" 

        then:
            false == event2.validate()
            "unique" == event2.errors["title"]
    }

    def "Validation fails when title is longer than 256"() {
        setup:
            def event = validEvent()
            mockForConstraintsTests(Event, [ event ])

        when:
            event.title = "a".padLeft(256) 

        then:
            false == event.validate()
            "size" == event.errors["title"]
    }

    def "Validation fails when summary is less than 5"() {
        setup:
            def event = validEvent()
            mockForConstraintsTests(Event, [ event ])

        when:
            event.summary = "aaaa"

        then:
            false == event.validate()
            "size" == event.errors["summary"]
    }

    def "Validation fails when content is less than 5"() {
        setup:
            def event = validEvent()
            mockForConstraintsTests(Event, [ event ])

        when:
            event.content = "aaaa"

        then:
            false == event.validate()
            "size" == event.errors["content"]
    }

    def "Validation fails when isRepeatable is null"() {
        setup:
            def event = validEvent()
            mockForConstraintsTests(Event, [ event ])

        when:
            event.isRepeatable = null

        then:
            false == event.validate()
            "nullable" == event.errors["isRepeatable"]
    }

    def "Validation fails when dates is null"() {
        setup:
            def event = validEvent()
            mockForConstraintsTests(Event, [ event ])

        when:
            event.dates = null

        then:
            false == event.validate()
            "nullable" == event.errors["dates"]
    }

    def "Validation fails when organizer is null"() {
        setup:
            def event = validEvent()
            mockForConstraintsTests(Event, [ event ])

        when:
            event.organizer = null

        then:
            false == event.validate()
            "nullable" == event.errors["organizer"]
    }

    def "Validation fails when leader is null"() {
        setup:
            def event = validEvent()
            mockForConstraintsTests(Event, [ event ])

        when:
            event.leader = null

        then:
            false == event.validate()
            "nullable" == event.errors["leader"]
    }

    def "Validation fails when venue is null"() {
        setup:
            def event = validEvent()
            mockForConstraintsTests(Event, [ event ])

        when:
            event.venue = null

        then:
            false == event.validate()
            "nullable" == event.errors["venue"]
    }

    def "Price list is not null"() {
        when:
            def event = validEvent()

        then:
            null != event.prices 
    }

    def "Dates list is not null"() {
        when:
            def event = validEvent()

        then:
            null != event.dates 
    }

    def "Price list is decorated"() {
        when:
            def event = validEvent()
            def prices = event.getPriceList()

        then:
            null != prices.get(2)
            EventPrice.class == prices.get(2).class
    }

    def "Dates list is decorated"() {
        when:
            def event = validEvent()
            def dates = event.getDateList()

        then:
            null != dates.get(2)
            EventDate.class == dates.get(2).class
    }

    def "String returns title"() {
        setup:
            def event = validEvent()

        expect:
            "Meditation" == event.toString()
    }

    def "Converts single date event to iCalendar Event with no rule"() {
        setup:
            def event = validEvent()
            def now = new Date()
            addValidDate(event,now)


        when:
            def iCalEvent = event.toiCalVEvent()
            iCalEvent.validate(true)

        then:
            iCalEvent.startDate.date.time = now.time
            iCalEvent.endDate.date.time = now.time
            iCalEvent.location.value == "Spa Road"
            iCalEvent.organizer.value == "MAILTO:leanne.northrop@abc.com"
            notThrown(ValidationException)
    }

    def "Converts multiple date event to iCalendar Event with rule"() {
        setup:
            def event = validEvent()
            def now = new Date()
            addValidDates(event,now)


        when:
            def iCalEvent = event.toiCalVEvent()
            iCalEvent.validate(true)

        then:
            iCalEvent.startDate.date.time = now.time
            iCalEvent.endDate.date.time = now.time
            iCalEvent.location.value == "Spa Road"
            iCalEvent.organizer.value == "MAILTO:leanne.northrop@abc.com"
            notThrown(ValidationException)
    }

    def "isOnDay returns false if dates are not date"() {
        expect:
            false == event.isOnDay(new Date())

        where:
            event << [validEvent(),validEvent()]
    }

    def "isOnDay returns true if single date event for today occurs today"() {
        setup:
            def event = validEvent()
            def now = new Date()
            addValidDate(event,now)

        expect:
            true == event.isOnDay(now)
    }

    def "isOnDay returns true if a multiple date event occurs tomorrow"() {
        setup:
            def event = validEvent()
            def now = new Date()
            addValidDates(event,now,3)

        expect:
            true == event.isOnDay(now+1)
    }

    def "isOnDay returns true if a ruled event occurs day after tomorrow"() {
        setup:
            def event = validEvent()
            def now = new Date()
            addValidDates(event,now)

        expect:
            true == event.isOnDay(now+2)
    }

    def "isOnDay returns true if single date event for today occurs over next 10 days"() {
        setup:
            def event = validEvent()
            def now = new Date()
            addValidDate(event,now+4)

        expect:
            true == event.isOnDay(now,10)
    }

    def "isOnDay returns true if a multiple date event occurs over next 10 days"() {
        setup:
            def event = validEvent()
            def now = new Date()
            addValidDates(event,now,3)

        expect:
            true == event.isOnDay(now-1,10)
    }

    def "isOnDay returns true if a ruled event occurs over next 10 days"() {
        setup:
            def event = validEvent()
            def now = new Date()
            addValidDates(event,now+5)

        expect:
            true == event.isOnDay(now+2,10)
    }

    def "isOnDay returns false if dates are not set but checking over next 10 days"() {
        setup:
            def event = validEvent()
            def now = new Date()

        expect:
            false == event.isOnDay(now+2,10)
    }

    def validEvent() {
        def event = new Event(title: "Meditation", 
                              summary: "summary", 
                              content: "content",
                              publishState: 'Unpublished',
                              category: 'E',
                              isRepeatable: false,
                              organizer: new ShiroUser(username:"leanne.northrop@abc.com"),
                              leader: new Teacher(name:"AKA"),
                              venue: new Venue(name:"Spa Road"),
                              deleted:false,
                              featured:false,
                              home:false)
        event
    }

    def addValidDates(event,date,count) {
        def dates = (0..count).collect {
            def eventDate = new EventDate()
            eventDate.event = event
            eventDate.startDate = date + it
            eventDate.endDate = date + it
            eventDate
        }
        event.dates = dates 
    }

    def addValidDates(event,date) {
        def eventDate = new EventDate()
        eventDate.event = event
        eventDate.startDate = date
        eventDate.endDate = date
        eventDate.ruleType = "always"
        eventDate.modifierType = "D"
        eventDate.interval = 3
        eventDate.isRule = true
        event.dates = [eventDate]
    }

    def addValidDate(event,date) {
        def eventDate = new EventDate()
        eventDate.event = event
        eventDate.startDate = date
        eventDate.endDate = date
        event.dates = [eventDate]
    }
}
