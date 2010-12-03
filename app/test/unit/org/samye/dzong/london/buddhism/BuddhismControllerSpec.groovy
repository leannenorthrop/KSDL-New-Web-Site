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

package org.samye.dzong.london.buddhism

import grails.test.*
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

/*
 * Unit test for Buddhist content controller.
 *
 * @author Leanne Northrop
 * @since 3rd November 2010, 20:16
 */
class BuddhismControllerSpec extends ControllerSpec {

    def 'Index redirects to home'() {
        when:
        controller.index()

        then:
        redirectArgs == [action: controller.home]
    }

    def 'home'() {
        setup:
        Article.metaClass.static.homeBuddhismArticles = {col,ord -> new Expando(list: {[]}) }
        Article.metaClass.static.featuredBuddhismArticles = {col,ord -> new Expando(list: {[]}) }
        Article.metaClass.static.allBuddhismArticlesNotOrdered = { new Expando(count: {0}) }
        Event.metaClass.static.buddhism = {col,ord-> new Expando(list: {[]}) }
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->},view:{a->m})
        Setting.metaClass.static.buddhistSlideshow = { new Expando(list: {[]}) }
        FlickrService.metaClass.getPhotosetCover = { [] }
        controller.flickrService = new FlickrService()
        mockDomain(Teacher)
        mockDomain(Link)

        when:
        controller.home()

        then:
        controller.modelAndView.viewName == 'index'
        controller.modelAndView.model.linkedHashMap.links == [] 
        controller.modelAndView.model.linkedHashMap.album == [] 
        controller.modelAndView.model.linkedHashMap.topArticles == [] 
        controller.modelAndView.model.linkedHashMap.articles == [] 
        controller.modelAndView.model.linkedHashMap.total == 0 
        controller.modelAndView.model.linkedHashMap.events == [] 
        controller.modelAndView.model.linkedHashMap.teachers == [] 
    }

    def 'list'() {
        setup:
        def m = [:]
        Article.metaClass.static.allBuddhismArticles = {col,ord -> new Expando(list: {[]}) }
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->},view:{a->m})

        when:
        def model = controller.list()

        then:
        model.articles == [] 
        model.title == 'buddhism.all.articles.title'
    }

    def 'view with bad param'() {
        setup:
        def m = [:]
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->},view:{a->m})
        mockParams << [id:1]

        when:
        def model = controller.view()

        then:
        redirectArgs == [action: controller.home]
    }
    
    def 'event'() {
        setup:
        def event = validEvent()
        event.id = 1
        mockParams << [id:1]
        mockDomain(Event,[event])
        controller.eventService = new Expando(findSimilar:{a->[]}) 
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->},view:{a->m})

        when:
        def model = controller.event()

        then:
        model.event == event
        model.id == 1
        model.similar == []
    }

    def 'events'() {
        setup:
        mockDomain(Event)
        def event = validEvent()
        event.save()
        controller.eventService = new Expando(list:{a,b->[events:[event]]}) 
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->},view:{a->m})

        when:
        def model = controller.events()

        then:
        model.events == [event]
    }

    def 'slideshow'() {
        setup:
        def album = []
        Setting.metaClass.static.buddhistSlideshow = { new Expando(list: {[]}) }
        FlickrService.metaClass.getPhotoset = { album }
        controller.flickrService = new FlickrService()

        when:
        def model = controller.slideshow()

        then:
        model.album == album
    }

    def 'teacher'() {
        def teacher = new Teacher()
        teacher.id = 1
        mockDomain(Teacher,[teacher])
        mockDomain(Event)
        def event = validEvent()
        event.save()
        controller.teacherService = new Expando(events:{id->[event]}) 
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->},view:{a->m})
        mockParams << [id:1]

        when:
        def model = controller.teacher()

        then:
        model.events == [event]
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
}
