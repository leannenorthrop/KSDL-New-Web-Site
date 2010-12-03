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
 * Unit test for VenueAddress class.
 *
 * @author Leanne Northrop
 * @since 1.0.16-SNAPSHOT, 10 November, 2010 14:36
 */
class VenueAddressSpec extends UnitSpec {
    def "default constructor creates valid object"() {
        given: "A newly created address"
            def address = new VenueAddress()
            mockForConstraintsTests(VenueAddress, [address])

        when: "validation is performed"
            def result = address.validate()

        then: "no errors are detected"
            println address.errors
            result == true
    }

    def "toString uses name and place name"() {
        given: "An initialized address number"
        def address = new VenueAddress(name: 'Main', placeName:'Spa Road')

        when:
        def result = address.toString()

        then:
        result == "Main: Spa Road"
    }

    def "Name can not be blank"() {
        setup:
        def address = new VenueAddress(name:'')
        mockForConstraintsTests(VenueAddress, [address])

        expect:
        false == address.validate()
    }

    def "Line1 can not be blank"() {
        setup:
        def address = new VenueAddress(line1:'')
        mockForConstraintsTests(VenueAddress, [address])

        expect:
        false == address.validate()
    }

    def "Type must be in list"() {
        when:
        def address = new VenueAddress(type:type)
        mockForConstraintsTests(VenueAddress, [address])

        then:
        isValid == address.validate()

        where:
        type << ['main','work','home','fax','mobile','other','abc','registered','shop']
        isValid << [true, false, false, false, false, true,false,true,true]
    }

    def "placeName can not be null"() {
        when:
        def address = new VenueAddress()
        address.placeName = null
        mockForConstraintsTests(VenueAddress, [address])

        then:
        false == address.validate()
    }

    def "placeName can not be bigger than 1024"() {
        when:
        def address = new VenueAddress()
        address.placeName = "name".padLeft(1026)
        mockForConstraintsTests(VenueAddress, [address])

        then:
        false == address.validate()
    }

    def "line1 can not be bigger than 2048"() {
        when:
        def address = new VenueAddress()
        address.line1 = "name".padLeft(2049)
        mockForConstraintsTests(VenueAddress, [address])

        then:
        false == address.validate()
    }

    def "line2 can not be bigger than 2048"() {
        when:
        def address = new VenueAddress()
        address.line2 = "name".padLeft(2049)
        mockForConstraintsTests(VenueAddress, [address])

        then:
        false == address.validate()
    }

    def "county can not be bigger than 25"() {
        when:
        def address = new VenueAddress()
        address.county = "name".padLeft(26)
        mockForConstraintsTests(VenueAddress, [address])

        then:
        false == address.validate()
    }

    def "postTown can not be bigger than 256"() {
        when:
        def address = new VenueAddress()
        address.postTown = "name".padLeft(257)
        mockForConstraintsTests(VenueAddress, [address])

        then:
        false == address.validate()
    }

    def "postCode can not be bigger than 10"() {
        when:
        def address = new VenueAddress()
        address.postCode = "name".padLeft(11)
        mockForConstraintsTests(VenueAddress, [address])

        then:
        false == address.validate()
    }

    def "country can not be bigger than 128"() {
        when:
        def address = new VenueAddress()
        address.country = "name".padLeft(129)
        mockForConstraintsTests(VenueAddress, [address])

        then:
        false == address.validate()
    }
}
