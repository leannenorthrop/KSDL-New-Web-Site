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

import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.mock.web.*

/*
 * Unit test for textile codec.
 *
 * @author Leanne Northrop
 * @since 15th November 2010, 16:55 
 */
class TextileCodecSpec extends MvcSpec {
    def provideMvcClassUnderTest() {
        TextileCodec
    }

    def initializeMvcMocking(Class classUnderTest) {
        loadCodec(TextileCodec)
    }

    def getMockRequest() { new MockHttpServletRequest() }
    def getMockResponse() { new MockHttpServletResponse() }
    def getMockSession() { new MockHttpSession() }

    def 'encode parses textile markup and produces valid html'() {
        when:
        def result = "*abc*".encodeAsTextile()

        then:
        result == "<p><strong>abc</strong></p>"
    }

    def 'check textile library'() {
        given:
        def str = """h2{color:green}. This is a title

h3. This is a subhead

p{color:red}. This is some text of dubious character. Isn't the use of "quotes" just lazy writing -- and theft of 'intellectual property' besides? I think the time has come to see a block quote.

bq[fr]. This is a block quote. I'll admit it's not the most exciting block quote ever devised.

Simple list:

#{color:blue} one
# two
# three

Multi-level list:

# one
## aye
## bee
## see
# two
## x
## y
# three

Mixed list:

* Point one
* Point two
## Step 1
## Step 2
## Step 3
* Point three
** Sub point 1
** Sub point 2


Well, that went well. How about we insert an <a href="/" title="watch out">old-fashioned hypertext link</a>? Will the quote marks in the tags get messed up? No!

"This is a link (optional title)":http://www.textism.com

An image:

!/common/textist.gif(optional alt text)!

# Librarians rule
# Yes they do
# But you knew that

Some more text of dubious character. Here is a noisome string of CAPITAL letters. Here is something we want to _emphasize_. 
That was a linebreak. And something to indicate *strength*. Of course I could use <em>my own HTML tags</em> if I <strong>felt</strong> like it.

h3. Coding

This <code>is some code, "isn't it"</code>. Watch those quote marks!""" 

        when:
        def result = str.encodeAsTextile()

        then:
        def expHtmlStr = """<body><h2 id="Thisisatitle" style="color:green">This is a title</h2><h3 id="Thisisasubhead">This is a subhead</h3><p style="color:red">This is some text of dubious character. Isn&#8217;t the use of &#8220;quotes&#8221; just lazy writing &#8212; and theft of &#8216;intellectual property&#8217; besides? I think the time has come to see a block quote.</p><blockquote lang="fr"><p>This is a block quote. I&#8217;ll admit it&#8217;s not the most exciting block quote ever devised.</p></blockquote><p>Simple list:</p><ol style="color:blue"><li>one</li><li>two</li><li>three</li></ol><p>Multi-level list:</p><ol><li>one<ol><li>aye</li><li>bee</li><li>see</li></ol></li><li>two<ol><li>x</li><li>y</li></ol></li><li>three</li></ol><p>Mixed list:</p><ul><li>Point one</li><li>Point two<ol><li>Step 1</li><li>Step 2</li><li>Step 3</li></ol></li><li>Point three<ul><li>Sub point 1</li><li>Sub point 2</li></ul></li></ul><p>Well, that went well. How about we insert an <a href="/" title="watch out">old-fashioned hypertext link</a>? Will the quote marks in the tags get messed up? No!</p><p><a href="http://www.textism.com">This is a link (optional title)</a></p><p>An image:</p><p><img alt="optional alt text" title="optional alt text" border="0" src="/common/textist.gif"/></p><ol><li>Librarians rule</li><li>Yes they do</li><li>But you knew that</li></ol><p>Some more text of dubious character. Here is a noisome string of CAPITAL letters. Here is something we want to <em>emphasize</em>. <br/>That was a linebreak. And something to indicate <strong>strength</strong>. Of course I could use <em>my own HTML tags</em> if I <strong>felt</strong> like it.</p><h3 id="Coding">Coding</h3><p>This <code>is some code, "isn&#8217;t it"</code>. Watch those quote marks!</p></body>"""
        def expectedHTML = new XmlSlurper().parseText(expHtmlStr)
        def actualHTML = new XmlSlurper().parseText("<body>$result</body>")
        expectedHTML == actualHTML
    }
}
