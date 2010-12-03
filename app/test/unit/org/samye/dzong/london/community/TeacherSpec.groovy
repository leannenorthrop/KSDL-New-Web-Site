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
import org.springframework.context.support.StaticMessageSource

/** 
 * Spock unit test for teacher domain class.
 * Store information both about teachers and center therapists.
 *
 * @author Leanne Northrop
 * @since 25th October 2010, 18:09
 **/
class TeacherSpec extends UnitSpec {
    def messageSource = new StaticMessageSource()

    def "Valid teacher"() {
        setup:
            def teacher = validTeacher()
            mockForConstraintsTests(Teacher, [ teacher ])
            def result

        when:
            result = teacher.validate()

        then:
            assert result
    }

    def "Title can not be blank"() {
        setup:
            def teacher = validTeacher()
            teacher.title = ''
            mockForConstraintsTests(Teacher, [ teacher ])
            def result

        when:
            teacher.validate()

        then:
            "blank" == teacher.errors["title"]
    }

    
    def "Title must be in list"() {
        expect:
            def teacher = validTeacher()
            teacher.title = title 
            mockForConstraintsTests(Teacher, [ teacher ])
            assert teacher.validate()

        where:
            title << ['V','L','R','M','MS','MZ','MSS','K','HH','HE','HS','U','D']
    }

    def "Title not in list fails"() {
        expect:
            def teacher = validTeacher()
            teacher.title = title 
            mockForConstraintsTests(Teacher, [ teacher ])
            assert !teacher.validate()

        where:
            title << ('a'..'z')
    }

    def "Name can not be blank"() {
        setup:
            def teacher = validTeacher()
            teacher.name = ''
            mockForConstraintsTests(Teacher, [ teacher ])
            def result

        when:
            teacher.validate()

        then:
            "blank" == teacher.errors["name"]
    }

    def "Name must be unique"() {
        setup:
            def teacher1 = validTeacher()
            def teacher2 = validTeacher()
            mockForConstraintsTests(Teacher, [ teacher1, teacher2 ])
            def result

        when:
            teacher2.validate()

        then:
            "unique" == teacher2.errors["name"]
    }

    def "Type can not be blank"() {
        setup:
            def teacher = validTeacher()
            teacher.type = ''
            mockForConstraintsTests(Teacher, [ teacher ])
            def result

        when:
            teacher.validate()

        then:
            "blank" == teacher.errors["type"]
    }

    def "Type must be in list"() {
        expect:
            def teacher = validTeacher()
            teacher.type = type 
            mockForConstraintsTests(Teacher, [ teacher ])
            assert teacher.validate()

        where:
            type << ['L','V','C','O','T']
    }

    def "Type not in list fails"() {
        expect:
            def teacher = validTeacher()
            teacher.type = type 
            mockForConstraintsTests(Teacher, [ teacher ])
            assert !teacher.validate()

        where:
            type << ('a'..'z')
    }

    def "Summary can not be blank"() {
        setup:
            def teacher = validTeacher()
            teacher.summary = ''
            mockForConstraintsTests(Teacher, [ teacher ])
            def result

        when:
            teacher.validate()

        then:
            "blank" == teacher.errors["summary"]
    }

    def "Summary can not be less than 5 characters"() {
        setup:
            def teacher = validTeacher()
            teacher.summary = 'abcd'
            mockForConstraintsTests(Teacher, [ teacher ])
            def result

        when:
            teacher.validate()

        then:
            "size" == teacher.errors["summary"]
    }

    def "Content can be blank"() {
        setup:
            def teacher = validTeacher()
            teacher.content = ''
            mockForConstraintsTests(Teacher, [ teacher ])
            def result

        when:
            result = teacher.validate()

        then:
            assert result
    }

    def "Image can be null"() {
        setup:
            def teacher = validTeacher()
            teacher.image = null 
            mockForConstraintsTests(Teacher, [ teacher ])
            def result

        when:
            result = teacher.validate()

        then:
            assert result
    }

    def "Teacher with title unknown only returns name"() {
        setup:
            def name = "hello"
            def teacher = validTeacher()
            teacher.title = 'U' 
            teacher.name = name
            mockForConstraintsTests(Teacher, [ teacher ])
            def result

        when:
            result = teacher.toString()

        then:
            name == result
    }

    def "Teacher with title other than unknown returns name with title"() {
        setup:
            def name = "hello"
            def teacher = validTeacher()
            teacher.title = 'V' 
            teacher.name = name
            teacher.messageSource = messageSource
            def locale = Locale.UK
            messageSource.addMessage("teacher.title.V.msg",locale,"Venerable {0}")
            mockForConstraintsTests(Teacher, [ teacher ])
            def result

        when:
            result = teacher.toString()

        then:
            "Venerable ${name}" == result
    }

    def validTeacher() {
        def teacher = new Teacher(title: "V", 
                                  name: "name",
                                  summary: "summary", 
                                  content: "content",
                                  publishState: 'Unpublished',
                                  category: 'T',
                                  type: 'V',
                                  deleted:false,
                                  featured:false,
                                  home:false)
        teacher    
    }
}
