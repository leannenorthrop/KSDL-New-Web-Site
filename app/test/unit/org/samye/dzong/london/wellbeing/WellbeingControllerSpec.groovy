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
    def eventService
    def teacherService    

    def 'Index redirects to home'() {
        when:
        controller.index()

        then:
        redirectArgs == [action: controller.home]
    }

    def 'home'() {
        setup:
        def homeArticles = []
        def topArticles = []
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})
        Article.metaClass.static.homeWellbeingArticles = {a,b-> return new Expando(list: { homeArticles })} 
        Article.metaClass.static.featuredWellbeingArticles = {a,b-> return new Expando(list: { topArticles })} 
        Event.metaClass.static.wellbeing = {a,b-> return new Expando(list: { homeArticles })} 
        Article.metaClass.static.allWellbeingArticlesNotOrdered = {return new Expando(count: { 0})} 
        mockDomain(Teacher)

        when:
        controller.home()

        then:
        controller.modelAndView.viewName == 'index'
        controller.modelAndView.model.linkedHashMap.topArticles == topArticles  
        controller.modelAndView.model.linkedHashMap.articles == homeArticles  
        controller.modelAndView.model.linkedHashMap.total == 0 
        controller.modelAndView.model.linkedHashMap.therapists == [] 
    }

    def 'list displays list of wellbeing articles'() {
        setup:
        def articles = []
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})
        Article.metaClass.static.allWellbeingArticles = {a,b-> return new Expando(list: { articles })} 

        when:
        controller.list()

        then:
        controller.modelAndView.viewName == 'list'
        controller.modelAndView.model.linkedHashMap.articles == articles  
        controller.modelAndView.model.linkedHashMap.title == 'wellbeing.all.articles.title'  
    }

    def 'view displays wellbeing article'() {
        setup:
        def m = [:]
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->},view:{a->m})
        mockParams << [id:1]

        when:
        def model = controller.view()

        then:
        model == m
    }

    def 'event displays wellbeing event and similar items'() {
        setup: "when url id param set to 1"
        def id = 1
        mockParams << [id:id]

        and: "event is found"
        def event = validEvent()
        event.id = id
        mockDomain(Event,[event])
        event.save()

        and: "services are present"
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})
        controller.eventService = new Expando(findSimilar:{e->[]})

        when:
        def model = controller.event()

        then:
        model.event == event
        model.id == id
        model.similar == [] 
    }
   
    def 'events displays list of wellbeing events'() {
        setup:
        def m = [:]
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})
        controller.eventService = new Expando(list:{a,b->m})

        when:
        def model = controller.events()

        then:
        model == m
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
}
