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
 * Unit test for VenueEmail class.
 *
 * @author Leanne Northrop
 * @since  1.0.16-SNAPSHOT, 10th November 2010, 15:17
 */
class VenueEmailSpec extends UnitSpec {
    def "default constructor creates valid object"() {
        given: "A newly created email"
            def email = new VenueEmail()
            mockForConstraintsTests(VenueEmail, [email])

        when: "validation is performed"
            def result = email.validate()

        then: "no errors are detected"
            result == true
    }

    def "toString uses name and email"() {
        given: "An initialized email number"
        def email = new VenueEmail(address:'reception@samye.org',name:'Reception')

        when:
        def result = email.toString()

        then:
        result == "Reception: reception@samye.org"
    }

    def "Name can not be blank"() {
        setup:
        def email = new VenueEmail(name:'')
        mockForConstraintsTests(VenueEmail, [email])

        expect:
        false == email.validate()
    }

    def "Address can not be blank"() {
        setup:
        def email = new VenueEmail(address:'',name:'Reception')
        mockForConstraintsTests(VenueEmail, [email])

        expect:
        false == email.validate()
    }

    def "Address must be a valid email address"() {
        when:
        def email = new VenueEmail(address:emailAddress,name:'Reception')
        mockForConstraintsTests(VenueEmail, [email])

        then:
        false == email.validate()

        where:
        emailAddress << ['abc','0ab', '9,','898 9909']
    }

    def "Type must be in list"() {
        when:
        def email = new VenueEmail(address:'reception@samye.org',name:'Reception',type:type)
        mockForConstraintsTests(VenueEmail, [email])

        then:
        isValid == email.validate()

        where:
        type << ['main','work','home','fax','mobile','other','abc']
        isValid << [true, true, true, false, false, true,false]
    }
}
