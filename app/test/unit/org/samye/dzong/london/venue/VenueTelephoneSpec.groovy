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
 * Unit test for VenueTelephone class.
 *
 * @author Leanne Northrop
 * @since 1.0.16-SNAPSHOT, 10th November 2010, 15:18
 */
class VenueTelephoneSpec extends UnitSpec {
    def "default constructor creates valid object"() {
        given: "A newly created telephone number"
            def telephone = new VenueTelephone()
            mockForConstraintsTests(VenueTelephone, [telephone])

        when: "validation is performed"
            def result = telephone.validate()

        then: "no errors are detected"
            result == true
    }

    def "toString uses name and number"() {
        given: "An initialized telephone number"
        def telephone = new VenueTelephone(number:'020999999',name:'Reception')

        when:
        def result = telephone.toString()

        then:
        result == "Reception: 020999999"
    }

    def "Name can not be blank"() {
        setup:
        def telephone = new VenueTelephone(name:'')
        mockForConstraintsTests(VenueTelephone, [telephone])

        expect:
        false == telephone.validate()
    }

    def "Number can not be blank"() {
        setup:
        def telephone = new VenueTelephone(number:'',name:'Reception')
        mockForConstraintsTests(VenueTelephone, [telephone])

        expect:
        false == telephone.validate()
    }

    def "Number can not contain non digit characters"() {
        when:
        def telephone = new VenueTelephone(number:telephoneNumber,name:'Reception')
        mockForConstraintsTests(VenueTelephone, [telephone])

        then:
        false == telephone.validate()

        where:
        telephoneNumber << ['abc','0ab', '9,','898 9909']
    }

    def "Type must be in list"() {
        when:
        def telephone = new VenueTelephone(number:'090',name:'Reception',type:type)
        mockForConstraintsTests(VenueTelephone, [telephone])

        then:
        isValid == telephone.validate()

        where:
        type << ['main','work','home','fax','mobile','other','abc']
        isValid << [true, true, true, true, true, true,false]
    }
}
