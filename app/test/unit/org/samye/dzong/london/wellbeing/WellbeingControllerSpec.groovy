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

package org.samye.dzong.london.wellbeing

import grails.test.*
import grails.plugin.spock.*

import org.samye.dzong.london.community.Article
import org.samye.dzong.london.events.Event
import org.samye.dzong.london.community.Teacher
import org.samye.dzong.london.users.ShiroUser
import org.samye.dzong.london.venue.*

/*
 * Unit test for Wellbeing controller
 *
 * @author Leanne Northrop
 * @since  3rd Nov 2010 20:00
 */
class WellbeingControllerSpec extends ControllerSpec {
    def articleService
    def teacherService    

    def 'Index redirects to home'() {
        when:
        controller.index()

        then:
        redirectArgs == [action: controller.home]
    }

    def 'home'() {
        setup:
        stubFinderMethods(["WellBeingHomeArticles", "WellBeingFeaturedArticles","WellBeingAllArticles","WellBeingFeaturedEvents"])
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->},view:{a->m})
        mockDomain(Teacher)

        when:
        controller.home()

        then:
        controller.modelAndView.viewName == 'index'
        controller.modelAndView.model.linkedHashMap.wellBeingHomeArticles == []
        controller.modelAndView.model.linkedHashMap.wellBeingFeaturedArticles == []
        controller.modelAndView.model.linkedHashMap.therapists == [] 
    }

    def 'list displays list of all wellbeing articles'() {
        setup:
        stubFinderMethods(["WellBeingAllArticles"])
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})

        when:
        def model = controller.list()

        then:
        model.wellBeingAllArticles == []  
        model.title == 'wellbeing.all.articles.title'  
    }

    def 'view displays wellbeing a single article and returns requested article id'() {
        setup:
        stubViewMethods(["Article"])
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->},view:{a->m})
        mockParams << [id:1]

        when:
        def model = controller.view()

        then:
        model.id == 1
    }

    def 'event displays wellbeing event and similar items'() {
        setup: "when url id param set to 1"
        def id = 1
        mockParams << [id:id]

        and: "event is found"
        stubViewMethods(["Event"])

        and: "services are present"
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})

        when:
        def model = controller.event()

        then:
        model.id == id
    }
   
    def 'events displays list of wellbeing events'() {
        setup:
        stubFinderMethods(["WellBeingAllEvents"])
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->},view:{a->m})

        when:
        def model = controller.events()

        then:
        model.wellBeingAllEvents == []
    }

    def validEvent() {
        def event = new Event(title: "Meditation", 
                              summary: "summary", 
                              content: "content",
                              publishState: 'Published',
                              category: 'M',
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
                def name = it - "Buddhist"
                name = name.substring(0,1).toLowerCase() + name.substring(1)
                [(name):[]]
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
