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
package org.samye.dzong.london.venue

import grails.test.*
import grails.plugin.spock.*
import spock.lang.*

/*
 * Unit test for Venue domain class.
 *
 * @author Leanne Northrop
 * @since 1.0.16-SNAPSHOT, 10th November 2010, 15:20
 */
class VenueSpec extends UnitSpec {
    def "default constructor creates valid object"() {
        given: "A newly created venue"
            def venue = new Venue()
            mockForConstraintsTests(Venue, [venue])

        when: "validation is performed"
            def result = venue.validate()

        then: "no errors are detected"
            result == true
    }

    def "toString uses name and number"() {
        given: "An initialized venue number"
        def venue = new Venue(name:'Spa Road')

        when:
        def result = venue.toString()

        then:
        result == "Spa Road"
    }

    def "Address list is decorated with factory"() {
        given:
        def venue = new Venue()

        when:
        def list = venue.addressesList

        then:
        list.get(2).toString() == new VenueAddress().toString()
    }

    def "Email list is decorated with factory"() {
        given:
        def venue = new Venue()

        when:
        def list = venue.emailsList

        then:
        list.get(2).toString() == new VenueEmail().toString()
    }

    def "Telephone list is decorated with factory"() {
        given:
        def venue = new Venue()

        when:
        def list = venue.telephoneNumbersList

        then:
        list.get(2).toString() == new VenueTelephone().toString()
    }


}
