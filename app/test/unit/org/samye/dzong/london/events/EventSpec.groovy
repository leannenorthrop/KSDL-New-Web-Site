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
import org.joda.time.*
import org.springframework.context.support.StaticMessageSource

/**
 * Unit test for Event domain class.
 */
class EventSpec extends UnitSpec {
    def messageSource
    def grailsApplication

    def setup() {
        mockLogging(Event,true)
        mockLogging(EventDate,true)
        mockLogging(EventPrice,true)
    }

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

    def "Validation does not fail when title is not unique"() {
        setup:
            def event1 = validEvent()
            def event2 = validEvent()
            mockForConstraintsTests(Event, [ event1, event2 ])

        when:
            event1.title = "a" 
            event2.title = "a" 
            event2.validate()

        then:
            assert event2.errors.isEmpty()
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
            def iCalEvent = event.toiCalVEvent()


        when:
            iCalEvent.validate(true)

        then:
            iCalEvent.startDate.date.time == now.time
            iCalEvent.endDate.date.time == now.time
            iCalEvent.location.value == "Spa Road"
            notThrown(ValidationException)
    }

    def "Converts multiple date event to iCalendar Event with rule"() {
        setup:
            def event = validEvent()
            def now = new Date()
            addValidDates(event,now)
            def iCalEvent = event.toiCalVEvent()


        when:
            iCalEvent.validate(true)

        then:
            iCalEvent.startDate.date.time == now.time
            iCalEvent.endDate.date.time == now.time
            iCalEvent.location.value == "Spa Road"
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

    def 'bindPrices deletes specified prices'() {
        setup:
            mockDomain(Event)
            mockDomain(EventPrice)
            def event = validEvent()
            (0..3).collect{ def p = new EventPrice(price: it); event.addToPrices(p); p.save() }
            event.save()

        and:
            def params = ['priceList[2]._deleted':'true','priceList[1]._deleted':'true']

        when:
            event.bindPrices(params)

        then:
            event.prices.size() == 2
            event.prices[0].price == 0d
            event.prices[1].price == 3d
    }

    def 'bindDates deletes specified prices'() {
        setup:
            mockDomain(Event)
            mockDomain(EventDate)
            def event = validEvent()
            def today = new Date()
            today.clearTime()
            (0..3).collect{ def d = new EventDate(startDate:today+it); event.addToDates(d); d.save() }
            event.save()

        and:
            def params = ['dateList[2]._deleted':'true','dateList[1]._deleted':'true',
                          'dateList[0].startTimeHour':'10', 'dateList[0].startTimeMin':'0', 'dateList[0].endTimeHour':'11', 'dateList[0].endTimeMin':'0',
                          'dateList[3].startTimeHour':'10', 'dateList[3].startTimeMin':'0', 'dateList[3].endTimeHour':'11', 'dateList[3].endTimeMin':'0' ]

        when:
            event.bindDates(params)

        then:
            event.dates.size() == 2
            event.dates[0].startDate == today 
            event.dates[1].startDate == today + 3
    }

    def 'bindDates saves times'() {
        setup:
            mockDomain(Event)
            mockDomain(EventDate)
            def event = validEvent()
            def today = new Date()
            today.clearTime()
            (0..1).collect{ def d = new EventDate(startDate:today+it); event.addToDates(d); d.save() }
            event.save()

        and:
            def params = ['dateList[0].startTimeHour':'10', 'dateList[0].startTimeMin':'0', 'dateList[0].endTimeHour':'11', 'dateList[0].endTimeMin':'0',
                          'dateList[1].startTimeHour':'8', 'dateList[1].startTimeMin':'0', 'dateList[1].endTimeHour':'9', 'dateList[1].endTimeMin':'0' ]

        when:
            event.bindDates(params)

        then:
            event.dates.size() == 2
            event.dates[0].startTime == new TimeOfDay(10,0) 
            event.dates[0].endTime == new TimeOfDay(11,0) 
            event.dates[1].startTime == new TimeOfDay(8,0) 
            event.dates[1].endTime == new TimeOfDay(9,0) 
    }

    def 'toJSON generates json markup for given day'() {
        setup:
            def today = new Date()
            today.clearTime()
            def event = validEvent()
            addValidDate(event,today)
            messageSource = new StaticMessageSource()
            def locale = Locale.UK
            messageSource.addMessage("publish.category.controller.M",locale,"meditation")
            event.messageSource = messageSource
            mockDomain(Event,[event])

        when:
            def result = event.toJSON(today)

        then:
            result == '{"id":15,"title":"Meditation","start":"2010-11-02 09:00:00","end":"2010-11-02 10:00:00","className":"M","url":"meditation/event/15","allDay":false}'
    }

    def 'toDate returns start date when not a rule or multiple'() {
        setup:
            def event = singleEvent() 
            def today = new Date()

        when:
            def result = event.toDate()

        then:
            result.format('dd MM yyyy') == today.format('dd MM yyyy')
    }

    def 'toDate returns next date when multiple'() {
        setup:
            def event = multipleEvent()
            def today = new Date()

        when:
            def result = event.toDate()

        then:
            result.format('dd MM yyyy') == today.format('dd MM yyyy')
    }

    def 'toDate returns next date when rule'() {
        setup:
            def event = ruleEvent()
            def today = new Date()

        when:
            def result = event.toDate()

        then:
            result.format('dd MM yyyy') == (today).format('dd MM yyyy')
    }

    def 'compareTo is 0 when objects are same date and time'() {
        expect:
        0 == event1.compareTo(event2)

        where:
        event1 << [singleEvent(),ruleEvent(),multipleEvent(),singleEvent(),singleEvent(),ruleEvent()]
        event2 << [singleEvent(),ruleEvent(),multipleEvent(),ruleEvent(),multipleEvent(),multipleEvent()]
    }

    def 'compareTo is -1'() {
        expect:
        -1 == event1.compareTo(event2)

        where:
        event1 << [singleEvent(10),ruleEvent(10),multipleEvent(10),singleEvent(10),singleEvent(10),ruleEvent(10)]
        event2 << [singleEvent(),ruleEvent(),multipleEvent(),ruleEvent(),multipleEvent(),multipleEvent()]
    }

    def 'compareTo is 1'() {
        expect:
        1 == event1.compareTo(event2)

        where:
        event2 << [singleEvent(10),ruleEvent(10),multipleEvent(10),singleEvent(10),singleEvent(10),ruleEvent(10)]
        event1 << [singleEvent(),ruleEvent(),multipleEvent(),ruleEvent(),multipleEvent(),multipleEvent()]
    }

    def ruleEvent() {
        def today = new Date()
        def event = validEvent()
        addValidDates(event,today-2)
        event
    }

    def multipleEvent() {
        def today = new Date()
        def event = validEvent()
        addValidDates(event,today-1,3)
        event
    }

    def singleEvent() {
        def today = new Date()
        today.clearTime()
        def event = validEvent()
        addValidDate(event,today)
        event
    }

    def ruleEvent(time) {
        def event = ruleEvent()
        event.dates[0].startTime = new org.joda.time.TimeOfDay(time)
        event
    }

    def multipleEvent(time) {
        def event = multipleEvent()
        for (d in event.dates) {
            d.startTime = new org.joda.time.TimeOfDay(time)
        }
        event
    }

    def singleEvent(time) {
        def event = singleEvent()
        event.dates[0].startTime = new org.joda.time.TimeOfDay(time)
        event
    }

    def validEvent() {
        def event = new Event(title: "Meditation", 
                              summary: "summary", 
                              content: "content",
                              publishState: 'Unpublished',
                              category: 'M',
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
