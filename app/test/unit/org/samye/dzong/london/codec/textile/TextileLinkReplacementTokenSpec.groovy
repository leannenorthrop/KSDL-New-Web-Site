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
import org.samye.dzong.london.codec.textile.HtmlDocumentBuilder
import org.eclipse.mylyn.wikitext.core.parser.MarkupParser
import org.eclipse.mylyn.wikitext.textile.core.TextileLanguage
import org.codehaus.groovy.grails.web.mapping.DefaultUrlCreator

/*
 * Unit test for Buddhist content controller.
 *
 * @author Leanne Northrop
 * @since 3rd November 2010, 20:16
 */
class TextileLinkReplacementTokenSpec extends UnitSpec {
    def 'Constructor creates textile mark up generator'() {
        expect:
        null != new TextileLinkReplacementToken([:])
    }

    def 'Pattern group count is 2'() {
        given:
        def token = new TextileLinkReplacementToken([:])

        expect:
        2 == token.getPatternGroupCount()
    }

    def 'Pattern supports internal images'() {
        when:
        def token = new TextileLinkReplacementToken([:])
        def patternStr = token.getPattern(0)
        def pattern = java.util.regex.Pattern.compile(patternStr) 

        then:
        def m = pattern.matcher(str)
        m.matches()
        group1 == m.group(1)
        group2 == m.group(2)

        where:
        str                              |  group1                  | group2
        '[name](image)'                  | '[name]'                 | 'image'
        '[name,left](image)'             | '[name,left]'            | 'image'
        '[name,left,50px](image)'        | '[name,left,50px]'       | 'image'
        '[name,right,50px,50px](image)'  | '[name,right,50px,50px]' | 'image'
        '[name,normal,50px,50px](image)' | '[name,normal,50px,50px]'| 'image'
        '[name with spaces](image)'      | '[name with spaces]'     | 'image'
        '[name.with.periods](image)'     | '[name.with.periods]'    | 'image'
        '[name.with , comma](image)'     | '[name.with , comma]'    | 'image'
        '[1a99](image)'                  | '[1a99]'                 | 'image'
        '[1A99](image)'                  | '[1A99]'                 | 'image'
        '[1_99](image)'                  | '[1_99]'                 | 'image'
        '[1-99](image)'                  | '[1-99]'                 | 'image'
    }

    def 'Pattern supports internal files'() {
        when:
        def token = new TextileLinkReplacementToken([:])
        def patternStr = token.getPattern(0)
        def pattern = java.util.regex.Pattern.compile(patternStr) 

        then:
        def m = pattern.matcher(str)
        m.matches()
        group1 == m.group(1)
        group2 == m.group(2)

        where:
        str             |  group1                  | group2
        '[name](file)'  | '[name]'                 | 'file'
        '[1a99](file)'  | '[1a99]'                 | 'file'
        '[1A99](file)'  | '[1A99]'                 | 'file'
        '[1_99](file)'  | '[1_99]'                 | 'file'
        '[1-99](file)'  | '[1-99]'                 | 'file'
    }

    def 'Pattern supports html5 video'() {
        when:
        def token = new TextileLinkReplacementToken([:])
        def patternStr = token.getPattern(0)
        def pattern = java.util.regex.Pattern.compile(patternStr) 

        then:
        def m = pattern.matcher(str)
        m.matches()
        group1 == m.group(1)
        group2 == m.group(2)

        where:
        str              |  group1                  | group2
        '[name](video)'  | '[name]'                 | 'video'
        '[1a99](video)'  | '[1a99]'                 | 'video'
        '[1A99](video)'  | '[1A99]'                 | 'video'
        '[1_99](video)'  | '[1_99]'                 | 'video'
        '[1-99](video)'  | '[1-99]'                 | 'video'
    }

    def 'Pattern supports youtube'() {
        when:
        def token = new TextileLinkReplacementToken([:])
        def patternStr = token.getPattern(0)
        def pattern = java.util.regex.Pattern.compile(patternStr) 

        then:
        def m = pattern.matcher(str)
        m.matches()
        group1 == m.group(1)
        group2 == m.group(2)

        where:
        str                |  group1                  | group2
        '[name](youtube)'  | '[name]'                 | 'youtube'
        '[1a99](youtube)'  | '[1a99]'                 | 'youtube'
        '[1A99](youtube)'  | '[1A99]'                 | 'youtube'
        '[1_99](youtube)'  | '[1_99]'                 | 'youtube'
        '[1-99](youtube)'  | '[1-99]'                 | 'youtube'
    }

    def 'Pattern supports other'() {
        when:
        def token = new TextileLinkReplacementToken([:])
        def patternStr = token.getPattern(0)
        def pattern = java.util.regex.Pattern.compile(patternStr) 

        then:
        def m = pattern.matcher(str)
        m.matches()
        group1 == m.group(1)
        group2 == m.group(2)

        where:
        str                |  group1                  | group2
        '[name](teacher)'  | '[name]'                 | 'teacher'
        '[name](room)'     | '[name]'                 | 'room'
        '[name](venue)'    | '[name]'                 | 'venue'
    }


    def 'Processor handles internal images'() {
        given:
        StringWriter sw = new StringWriter();
        HtmlDocumentBuilder builder = new HtmlDocumentBuilder(sw);
        builder.emitAsDocument = false
        builder.useInlineStyles = false
        builder.suppressBuiltInStyles = true
        MarkupParser parser = new MarkupParser(new WikiTextileLanguage(['image':'http://']))
        parser.setBuilder(builder)

        when:
        parser.parse(str)
        def result = sw.toString()

        then:
        result == expected

        where:
        str                              | expected 
        '[name](image)'                  | '<p><img style="" title="name" alt="name" border="0" src="http:///name"/></p>' 
        '[name,left](image)'             | '<p><img style="float:left;" title="name" alt="name" border="0" src="http:///name"/></p>' 
        '[name,left,50px](image)'        | '<p><img style="float:left;width:50px;" title="name" alt="name" border="0" src="http:///name"/></p>' 
        '[name,right,50px,70px](image)'  | '<p><img style="float:right;width:50px;height:70px;" title="name" alt="name" border="0" src="http:///name"/></p>' 
    }

    def 'Processor handles file'() {
        given:
        StringWriter sw = new StringWriter();
        HtmlDocumentBuilder builder = new HtmlDocumentBuilder(sw);
        builder.emitAsDocument = false
        builder.useInlineStyles = false
        builder.suppressBuiltInStyles = true
        MarkupParser parser = new MarkupParser(new WikiTextileLanguage(['file':'http://']))
        parser.setBuilder(builder)

        when:
        parser.parse(str)
        def result = sw.toString()

        then:
        result == expected

        where:
        str              | expected 
        '[name](file)'   | '<p><a href="http:///name">name</a></p>' 
    }

    def 'Processor handles others as links'() {
        given:
        StringWriter sw = new StringWriter();
        HtmlDocumentBuilder builder = new HtmlDocumentBuilder(sw);
        builder.emitAsDocument = false
        builder.useInlineStyles = false
        builder.suppressBuiltInStyles = true
        MarkupParser parser = new MarkupParser(new WikiTextileLanguage(['teacher':'http://']))
        parser.setBuilder(builder)

        when:
        parser.parse(str)
        def result = sw.toString()

        then:
        result == expected

        where:
        str                 | expected 
        '[name](teacher)'   | '<p><a href="http:///name">name</a></p>' 
    }

    def 'Processor handles youtube video'() {
        given:
        StringWriter sw = new StringWriter();
        HtmlDocumentBuilder builder = new HtmlDocumentBuilder(sw);
        builder.emitAsDocument = false
        builder.useInlineStyles = false
        builder.suppressBuiltInStyles = true
        MarkupParser parser = new MarkupParser(new WikiTextileLanguage(['teacher':'http://']))
        parser.setBuilder(builder)

        when:
        parser.parse(str)
        def result = sw.toString()

        then:
        result == expected

        where:
        str                 | expected 
        '[name](youtube)'   | '<p><div style="text-align:center" class="youtubevideo"><object width="425" height="344"><param name="movie" value="http://www.youtube.com/v/name?rel=0&amp;autoplay=0&amp;loop=0&amp;egm=0&amp;border=0&amp;fs=1&amp;hd=1&amp;showsearch=0&amp;showinfo=0"></param><param name="allowFullScreen" value="true"></param><embed src="http://www.youtube.com/v/name?rel=0&amp;autoplay=0&amp;loop=0&amp;egm=0&amp;border=0&amp;fs=1&amp;hd=1&amp;showsearch=0&amp;showinfo=0" type="application/x-shockwave-flash" allowfullscreen="true" width="425" height="344"></embed></object></div></p>' 
    }
}
