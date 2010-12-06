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

package org.samye.dzong.london.community

import grails.test.*
import grails.plugin.spock.*

import org.samye.dzong.london.community.Article
import org.samye.dzong.london.events.Event
import org.samye.dzong.london.community.Teacher
import org.samye.dzong.london.users.ShiroUser
import org.samye.dzong.london.venue.*
import org.samye.dzong.london.events.Event
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.site.Setting
import org.samye.dzong.london.community.Teacher
import org.samye.dzong.london.site.Link
import org.samye.dzong.london.media.*

/*
 * Unit test for About Us content controller.
 *
 * @author Leanne Northrop
 * @since  6th December 2010, 18:09
 */
class AboutUsControllerSpec extends ControllerSpec {


    def 'section page featches home and featured articles, teachers, events and slideshow album'() {
        setup:
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->},view:{a->m})        
        stubFinderMethods(["AboutUsHomeArticles", "AboutUsFeaturedArticles"])
        stubCMSMethods(["Teachers","Venues"])
        mockDomain(Teacher)
        mockDomain(Link)

        when:
        def model = controller.index()

        then:
        model.homeArticles == [] 
        model.featuredArticles == [] 
        model.teachers == [] 
    }

    def 'list should fetch all AboutUs content'() {
        setup:
        stubFinderMethods(["AboutUsAllArticles"])
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->},view:{a->m})

        when:
        def model = controller.list()

        then:
        model.allArticles == [] 
    }

    def 'view should return id of requested article'() {
        setup:
        stubViewMethods(["Article"])
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->},view:{a->m})
        mockParams << [id:1]

        when:
        def model = controller.view()

        then:
        model.id == 1
    }

    def 'teacher'() {
        stubViewMethods(["Teacher"])
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->},view:{a->m})
        mockParams << [id:1]

        when:
        def model = controller.teacher()

        then:
        model.id == 1  
    }

    def validEvent() {
        def event = new Event(title: "Meditation", 
                              summary: "summary", 
                              content: "content",
                              publishState: 'Published',
                              category: 'B',
                              isRepeatable: false,
                              organizer: new ShiroUser(username:"leanne.northrop@abc.com"),
                              leader: new Teacher(name:"AKA",title:'U'),
                              venue: new Venue(name:"Spa Road"),
                              deleted:false,
                              featured:false,
                              home:false)
        return event
    }
    
    def stubFinderMethods(list) {
        list.each {
            controller.metaClass."findPublished${it}" = {params-> 
                def name = it - "AboutUs"
                name = name.substring(0,1).toLowerCase() + name.substring(1)
                [(name):[]]
            }            
        }        
    }
     
    
    def stubCMSMethods(list) {
        list.each {
            controller.metaClass."published${it}" = {params-> 
                [(it.toLowerCase()):[]]
            }            
        }        
    }
            
    def stubViewMethods(list) {
        list.each {
            controller.metaClass."view${it}" = {id-> 
                [(it.toLowerCase()): null, id: id, similar:[]]
            }            
        }        
    }    
}
