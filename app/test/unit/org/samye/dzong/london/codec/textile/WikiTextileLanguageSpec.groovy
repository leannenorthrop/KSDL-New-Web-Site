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

import org.eclipse.mylyn.wikitext.core.parser.markup.MarkupLanguage

/*
 * Unit test for Buddhist content controller.
 *
 * @author Leanne Northrop
 * @since 3rd November 2010, 20:16
 */
class WikiTextileLanguageSpec extends UnitSpec {
    def 'Constructor creates textile language mark up generator'() {
        expect:
        null != new WikiTextileLanguage([:])
    }

    def 'Site specific textile markup is added'() {
        given:
        def lang = new WikiTextileLanguage([:])
        def syntax = new MarkupLanguage.PatternBasedSyntax()

        when:
        lang.addStandardTokens(syntax)

        then:
        syntax.elements.find{it instanceof TextileLinkReplacementToken}
    }

}
