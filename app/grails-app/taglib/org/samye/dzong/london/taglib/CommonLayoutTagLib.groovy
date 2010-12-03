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
package org.samye.dzong.london.taglib

import org.apache.shiro.SecurityUtils
import groovy.xml.StreamingMarkupBuilder
import org.codehaus.groovy.grails.plugins.web.taglib.JavascriptValue
import org.samye.dzong.london.site.Setting

/*
 * Tag library for site specific content such as navigation, toolbars, comments
 * and more.
 *
 * @author Leanne Northrop
 * @since 1.0.0-SNAPSHOT, October 2009
 */
class CommonLayoutTagLib {
    static namespace = 'lsdc'
    def messageSource

     def nav = { attrs ->
        def navControllers = []
        ['home', 'aboutUs', 'event', 'buddhism', 'meditation','community','wellbeing','news','shop'].each { it ->
          def isShow = true
          try {
              isShow = Setting.findByName("Show" + it.capitalize()).value
          } catch (error) {log.warn "Unable to get setting ${'Show' + it.capitalize()}"}
          if (isShow == 'true' || isShow == 'Yes') {
              navControllers << it
          }
        }

         /*
        try {
            if (SecurityUtils.subject && SecurityUtils.subject.hasRole ("Administrator")) {
            navControllers = ['home', 'aboutUs', 'news', 'event', 'meditation','community','wellbeing','buddhism', 'manageSite']
        } else if (SecurityUtils.subject && SecurityUtils.subject.principal != null) {
            navControllers =['home', 'aboutUs', 'news', 'event', 'meditation','community','wellbeing','buddhism', 'manageSite']
        }
        } catch(error) {

        }  */

        def navList = {
            div (class:"nav group") {
                ul {
                    navControllers.each { controllerName ->
                        def aElem = link (controller:controllerName) {
                            "<strong>${messageSource.getMessage(controllerName,null,null)}</strong><em>${messageSource.getMessage(controllerName+".desc",null,null)}</em>"
                        }
                        if (controllerName.equals(attrs.current) && controllerName.equals(navControllers[-1])) {
                            mkp.yieldUnescaped ("<li class='last current'>${aElem}</li>")
                        } else if (controllerName.equals (navControllers[-1])) {
                            mkp.yieldUnescaped ("<li class='last'>${aElem}</li>")
                        } else if (controllerName.equals (attrs.current) && controllerName.equals (navControllers[0])) {
                            mkp.yieldUnescaped("<li class='first current'><a href='${resource(dir:'/')}'><strong>${messageSource.getMessage(controllerName,null,null)}</strong><em>${messageSource.getMessage(controllerName+".desc",null,null)}</em></a></li>")
                        } else if (controllerName.equals (navControllers[0])){
                            mkp.yieldUnescaped("<li class='first'><a href='${resource(dir:'/')}'><strong>${messageSource.getMessage(controllerName,null,null)}</strong><em>${messageSource.getMessage(controllerName+".desc",null,null)}</em></a></li>")
                        } else if (controllerName.equals (attrs.current)) {
                            mkp.yieldUnescaped ("<li class='current'>${aElem}</li>")
                        } else {
                            mkp.yieldUnescaped ("<li>${aElem}</li>")
                        }
                    }
                }
            }
        }

        def builder = new StreamingMarkupBuilder ()
        builder.encoding = "UTF-8"
        out << builder.bind (navList)
    }

     def toolbar = { attrs ->
        def content = new TreeSet()
        def media = new TreeSet()
        def settings = new TreeSet()
        
		if (SecurityUtils.subject.principal != null) {
			media = ['image','slideshow']
			settings = ['profile']			
		}
		
        if (SecurityUtils.subject.hasRole ("Editor") && !SecurityUtils.subject.hasRole ("Author")) {
            ['article','room','teacher','links'].each () { item ->
                content << item
            }
            media << 'file'
        }

        if (SecurityUtils.subject.hasRole ("Editor") && !SecurityUtils.subject.hasRole ("EventOrganiser")) {
            ['event'].each () { item ->
                content << item
            }
            media << 'file'
        }

        if (SecurityUtils.subject.hasRole ("Editor") && !SecurityUtils.subject.hasRole ("VenueManager")) {
            ['room'].each () { item ->
                content << item
            }
            media << 'file'
        }
        
        if (SecurityUtils.subject.hasRole ("Author")) {
            ['article', 'teacher','links'].each () { item ->
                content << item
            }
            media << 'file'
        }

        if (SecurityUtils.subject.hasRole ("ShopManager")) {
            ['article','shop'].each () { item ->
                content << item
            }
        }
        
        if (SecurityUtils.subject.hasRole ("VenueManager")) {
            ['venue','room'].each () { item ->
                content << item
            }
        }

        if (SecurityUtils.subject.hasRole ("EventOrganiser")) {
            ['event', 'teacher'].each() { item ->
                content << item
            }
        }

        if (SecurityUtils.subject.hasRole ("Administrator") || SecurityUtils.subject.hasRole ("Admin")) {
            ['settings', 'theme','roles'].each () { item ->
                settings << item
            }
            media << 'file'
        }

        if (SecurityUtils.subject.hasRole ("Admin")) {
            ['article', 'venue', 'teacher','room','event', 'shop','links'].each () { item ->
                content << item
            }
        }

        content = content as Set
        media = media as Set
        settings = settings as Set
        def toolbar = {
            div (id:"menu"){
                ul (id:"nav") {
                    if (SecurityUtils.subject.authenticated) {
                        [Content: content,Media:media,Settings:settings].each { title,options ->
                            li {
                                def elem = link(href:'#') {title}
                                if (elem != null) {
                                    mkp.yieldUnescaped(elem)
                                }    
                                ul {
                                    options.each { option ->
                                        li {
                                            def elem2 = link (class: option, controller: option, action:"manage") {
                                                messageSource.getMessage ('toolbar.' + option, null, null)
                                            }                                        
                                            if (elem2 != null) {
                                                mkp.yieldUnescaped(elem2)
                                            }  
                                        }
                                    }
                                }                        
                            }                                    
                        }  
                    }
                }
                ul (id:"btn") {
                    li {
                        def elem
                        if (SecurityUtils.subject.authenticated) {
                            elem = link(class: 'logout', controller: 'auth', action: "signOut") {
                                messageSource.getMessage ('toolbar.logout', null, null)
                            }
                        } else {
                            elem = link(class: 'login', controller: 'manageSite', action: "landing") {
                                messageSource.getMessage ('toolbar.login', null, null)
                            }                            
                        }
                        if (elem != null) {
                            mkp.yieldUnescaped(elem)
                        }                        
                    }
                }
            }
        }

        def builder = new StreamingMarkupBuilder ()
        builder.encoding = "UTF-8"
        out << builder.bind (toolbar)
    }

     def grid = { attrs ->
        out << """<div id="grid">"""
        0.times {
            out << """<span class="gcol"><span class="gleft">&nbsp;</span><span class="ggap">&nbsp;</span><span class="gright">&nbsp;</span></span>"""
        }
        out << "</div>"
    }

	def thumbnail = { attrs, body ->
	    def size = Setting.findByName('ThumbSize').value;
	    def src = 0
	    if (attrs?.srcid) {
	        src = attrs.remove('srcid')
        }
	    log.debug "Thumbnail id is '${src}' and attrs is ${attrs}"
		out << "<image width='${size}' height='${size}' src='${createLink(controller: 'image', action:'thumbnail', id:src)}'"
		
		attrs.each { k,v->
			out << " $k=\"$v\""
		}
		
		if (!attrs.containsKey('style')) {
		    out << " style=\"width:${size}px;height:${size}px;min-width:${size}px;min-height:${size}px;\""
		}
		
		out << "/>"
	}
	
    def remoteField = { attrs, body ->
		def paramName = attrs.paramName ? attrs.remove('paramName') : 'value'
		def value = attrs.remove('value')
		if(!value) value = ''
		out << "<textArea rows=\"35\" cols=\"40\" name=\"${attrs.remove('name')}\" onblur=\""

        if(attrs.params) {
			if(attrs.params instanceof Map) {
				attrs.params.put(paramName, new JavascriptValue('escape(this.value)'))
			}
			else {
				attrs.params += "+'${paramName}='+escape(this.value)"
			}
		}
		else {
    		attrs.params = "'${paramName}='+escape(this.value)"
		}
		out << remoteFunction(attrs)
		attrs.remove('params')
		out << "\""
		attrs.remove('url')
		attrs.each { k,v->
			out << " $k=\"$v\""
		}
		out <<" >${value}</textarea>"
	}
	
	def comments =  { attrs, body ->
		def bean = attrs.bean
		def noEscape = attrs.containsKey('noEscape') ? attrs.noEscape : false
		
		plugin.isAvailable(name:"grails-ui") {
			noEscape = true
		}
		if(bean?.metaClass?.hasProperty(bean, "comments")) {
			out << g.render(template:"/comments/comments", model:[commentable:bean, noEscape:noEscape])
		}		
	}	
}
