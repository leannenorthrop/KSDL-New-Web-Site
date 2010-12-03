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
package org.samye.dzong.london.codec.textile

import org.samye.dzong.london.codec.textile.HtmlDocumentBuilder
import org.eclipse.mylyn.wikitext.core.parser.MarkupParser
import org.eclipse.mylyn.wikitext.textile.core.TextileLanguage
import org.codehaus.groovy.grails.web.mapping.DefaultUrlCreator
import java.util.concurrent.ConcurrentHashMap
import org.codehaus.groovy.grails.web.pages.FastStringWriter

/**
 * Grails Textile codec. Uses a slightly modified textile dialect created
 * for this site. @see TextileLanguage for further information.
 *
 * @author Leanne Northrop
 * @since  1.0.0-SNAPSHOT, December 2009
 */
class TextileCodec {
    def static wikiTextileLanguage 

    def static init() {
        wikiTextileLanguage = new WikiTextileLanguage(getUrlMap())
        wikiTextileLanguage
    }

    def static getUrlMap() {
        def urls = [image: '', teacher:'', news: '',video:'',file:'',room:'',contactUs:'',teacher:'',about:'']
        urls.each { key, value ->
            if (key.equals("image")) {
                def defaultUrlCreator = new DefaultUrlCreator(key, "src")
                urls[key] = defaultUrlCreator.createURL(new HashMap(), "UTF-8")
            } else if (key.equals("room")||key.equals("venue")||key.equals("contactUs")||key.equals("visiting")||key.equals("teacher")||key.equals("about")) {
				def defaultUrlCreator = new DefaultUrlCreator("aboutUs", key == "about" ? "information" : key)
                urls[key] = defaultUrlCreator.createURL(new HashMap(), "UTF-8")	
			} else if (key.equals("video")||key.equals("file")) {
                def defaultUrlCreator = new DefaultUrlCreator("file", "src")
                urls[key] = defaultUrlCreator.createURL(new HashMap(), "UTF-8")	
			} else {
                def defaultUrlCreator = new DefaultUrlCreator(key, "view")
                urls[key] = defaultUrlCreator.createURL(new HashMap(), "UTF-8")
            }
        }
        new ConcurrentHashMap(urls)
    }

    static encode = { str ->
        def sw = new FastStringWriter()

        def builder = new HtmlDocumentBuilder(sw);
        builder.emitAsDocument = false
        builder.useInlineStyles = false
        builder.suppressBuiltInStyles = true
    
        if (!wikiTextileLanguage) {
            init()
        }

        def parser = new MarkupParser(wikiTextileLanguage)
        parser.setBuilder(builder)

        parser.parse(str)
        return sw.toString()
    }
}
