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
import spock.lang.*
import org.joda.time.*
import java.text.SimpleDateFormat

/*
 * Unit test for EventDate class.
 */
class EventDateSpec extends UnitSpec {
    def "default constructor creates valid object"() {
        setup:
            def eventDate = new EventDate()
            eventDate.event = new Event()
            mockForConstraintsTests(EventDate, [ eventDate ])

        when:
            def result = eventDate.validate()

        then:
            result == true
    }

    def "default constructor creates single date rule for now"() {
        setup:
            def f = new SimpleDateFormat("dd-MM-yyyy");

        when:
            def eventDate = new EventDate()
            eventDate.event = new Event()

        then:
            f.format(eventDate.startDate) == f.format(new Date())
            eventDate.startTime == new TimeOfDay(9, 0)
            eventDate.endTime == new TimeOfDay(10, 0)
            f.format(eventDate.endDate) == f.format(new Date())
            eventDate.isRule == false
            eventDate.ruleType == "once"
            eventDate.interval == 1
            eventDate.modifierType == "D"
    }

    def "copy constructor clones all fields"() {
        setup:
            def now = new Date()
            def startTime = new TimeOfDay(10,30)
            def endTime = new TimeOfDay(11,30)
            def eventDate = new EventDate(startDate:now, 
                                          endDate:now,
                                          startTime:startTime,
                                          endTime:endTime,
                                          isRule:false,
                                          ruleType:"once",
                                          interval:2,
                                          modifierType:"D")

        when:
            def clone = new EventDate(eventDate)

        then:
            clone.startDate == now
            clone.startTime == startTime 
            clone.endTime == endTime 
            clone.endDate == now
            clone.isRule == false
            clone.ruleType == "once"
            clone.interval == 2
            clone.modifierType == "D"
    }

    def "String contains only start date when not a rule"() {
        setup:
            def f = new SimpleDateFormat("dd-MM-yyyy");
            def date = new EventDate()

        when:
            def result = date.toString()

        then:
            f.format(new Date()) == result
    }

    def "String contains start date and times when not a bounded rule"() {
        setup:
            def f = new SimpleDateFormat("dd-MM-yyyy");
            def startTime = new TimeOfDay(10,30)
            def endTime = new TimeOfDay(11,30)
            def expected = f.format(new Date()) + " 10:30AM - 11:30AM (duration 1 hour)"
            def date = new EventDate(startTime:startTime,
                                     endTime:endTime,
                                     isRule:true,
                                     ruleType:"unbounded")

        when:
            def result = date.toString()

        then:
            expected == result
    }

    def "String contains start date, end date and times when a bounded rule"() {
        setup:
            def f = new SimpleDateFormat("dd-MM-yyyy");
            def startTime = new TimeOfDay(10,30)
            def endTime = new TimeOfDay(11,30)
            def expected = f.format(new Date()) + " " + f.format(new Date()) + " 10:30AM - 11:30AM (duration 1 hour)"
            def date = new EventDate(startTime:startTime,
                                     endTime:endTime,
                                     isRule:true,
                                     ruleType:"between")

        when:
            def result = date.toString()

        then:
            expected == result
    }
}
