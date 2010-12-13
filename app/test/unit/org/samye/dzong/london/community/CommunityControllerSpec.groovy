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
 * Unit test for Community content controller.
 *
 * @author Leanne Northrop
 * @since  6th December 2010, 18:09
 */
class CommunityControllerSpec extends ControllerSpec {

    def 'Index redirects to home'() {
        when:
        controller.index()

        then:
        redirectArgs == [action: controller.home]
    }

    def 'section page featches home and featured articles, teachers, and events'() {
        setup:
        stubFinderMethods(["CommunityHomeArticles", "CommunityFeaturedArticles","CommunityAllArticles","CommunityFeaturedEvents"])
        mockDomain(Teacher)

        when:
        controller.home()

        then:
        controller.modelAndView.viewName == 'index'
        controller.modelAndView.model.linkedHashMap.homeArticles == [] 
        controller.modelAndView.model.linkedHashMap.featuredArticles == [] 
        controller.modelAndView.model.linkedHashMap.featuredEvents == [] 
        controller.modelAndView.model.linkedHashMap.volunteerOpportunities == [] 
        controller.modelAndView.model.linkedHashMap.community == []         
    }

    def 'list should fetch all Community content'() {
        setup:
        stubFinderMethods(["CommunityAllArticles"])

        when:
        def model = controller.list()

        then:
        model.allArticles == [] 
    }

    def 'view should return id of requested article'() {
        setup:
        stubViewMethods(["Article"])
        mockParams << [id:1]

        when:
        def model = controller.view()

        then:
        model.id == 1
    }
    
    def 'event'() {
        setup:
        stubViewMethods(["Event"])
        mockParams << [id:1]
        
        when:
        def model = controller.event()

        then:
        model.id == 1
    }

    def 'events'() {
        setup:
        stubFinderMethods(["CommunityAllEvents"])

        when:
        def model = controller.events()

        then:
        model.allEvents == []
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
                def name = it - "Community"
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
