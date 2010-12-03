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

package org.samye.dzong.london.codec.textile;

import grails.test.*
import grails.test.*
import grails.plugin.spock.*


/*
 * Unit test for Buddhist content controller.
 *
 * @author Leanne Northrop
 * @since 3rd November 2010, 20:16
 */
class HtmlDocumentBuilderSpec extends UnitSpec {
    def 'Constructor creates builder'() {
        expect:
        null != new HtmlDocumentBuilder(new StringWriter())
    }

    def 'video produces html5 video markup'() {
        given:
        def writer = new StringWriter()
        def builder = new HtmlDocumentBuilder(writer)

        when:
        builder.video(null, 'http://bbc.co.uk')

        then:
        def xmlText = writer.toString()
        def html = new XmlSlurper().parseText(xmlText)
        assert html
        html.@src.text() == 'http://bbc.co.uk'
    }

    def 'youtubeplayer produces embedded youtube player'() {
        given:
        def writer = new StringWriter()
        def builder = new HtmlDocumentBuilder(writer)

        when:
        builder.youTubePlayer(null, '_83838')

        then:
        def xmlText = writer.toString()
        def html = new XmlSlurper().parseText(xmlText)
        assert html
        html.@style.text() == 'text-align:center'
        html.@class.text() == 'youtubevideo'
        html.object.@width.text() == '425'
        html.object.@height.text() == '344'
        html.object.param[0].@name.text() == 'movie'
        html.object.param[0].@value.text() == "http://www.youtube.com/v/_83838?rel=0&autoplay=0&loop=0&egm=0&border=0&fs=1&hd=1&showsearch=0&showinfo=0" 
        html.object.param[1].@name.text() == 'allowFullScreen'
        html.object.param[1].@value.text() == 'true'
        html.object.embed.@src.text() == "http://www.youtube.com/v/_83838?rel=0&autoplay=0&loop=0&egm=0&border=0&fs=1&hd=1&showsearch=0&showinfo=0" 
        html.object.embed.@type.text() == "application/x-shockwave-flash"
        html.object.embed.@allowfullscreen.text() == "true"
        html.object.embed.@width.text() == "425"
        html.object.embed.@height.text() == "344"
    }

}
