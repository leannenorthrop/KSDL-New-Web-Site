/*******************************************************************************
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
 ******************************************************************************/

package org.samye.dzong.london.community

import grails.plugin.spock.*
import spock.lang.*

/** 
 * Spock unit test for Profile domain class.
 *
 * @author Leanne Northrop
 * @since 25th October 2010, 17:28
 **/
class ProfileSpec extends UnitSpec {

    def "String returns only public name"() {
        expect:
            "Leanne" == validProfile().toString()
    }

    def "Public name can not be larger than 512 characters"() {
        setup:
            def profile = validProfile()
            mockForConstraintsTests(Profile, [ profile ])
            profile.publicName = 'Leanne'.center(513)

        when:
            profile.validate()

        then:
            "maxSize" == profile.errors["publicName"] 
    }

    def "Public name can not be blank"() {
        setup:
            def profile = validProfile()
            mockForConstraintsTests(Profile, [ profile ])
            profile.publicName = ''

        when:
            profile.validate()

        then:
            "blank" == profile.errors["publicName"] 
    }

    def "Nick name can not be larger than 512 characters"() {
        setup:
            def profile = validProfile()
            mockForConstraintsTests(Profile, [ profile ])
            profile.nickName = 'Leanne'.center(513)

        when:
            profile.validate()

        then:
            "maxSize" == profile.errors["nickName"] 
    }

    def "Nick name can be blank"() {
        setup:
            def profile = validProfile()
            mockForConstraintsTests(Profile, [ profile ])
            profile.nickName = ''
            def result

        when:
            result = profile.validate()

        then:
            assert result
    }

    def "Mime type can not be blank"() {
        setup:
            def profile = validProfile()
            mockForConstraintsTests(Profile, [ profile ])
            profile.mimeType = ''

        when:
            profile.validate()

        then:
            "blank" == profile.errors["mimeType"] 
    }

    def "Image can not be null"() {
        setup:
            def profile = validProfile()
            mockForConstraintsTests(Profile, [ profile ])
            profile.image = null

        when:
            profile.validate()

        then:
            "nullable" == profile.errors["image"] 
    }

    def validProfile() {
        def profile = new Profile(publicName: "Leanne",
                                  nickName: "Fran",
                                  mimeType: "image/jpeg",
                                  image: "Hello".getBytes(),
                                  dateCreated: new Date(),
                                  lastUpdated: new Date(),
                                  lastLoggedIn: new Date())
        profile
    }
}
