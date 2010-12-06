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

package org.samye.dzong.london.news

import grails.test.*
import grails.plugin.spock.*
import org.samye.dzong.london.community.Article

/*
 * Unit test for NewsController.
 *
 * @author Leanne Northrop
 * @since 3rd November 2010, 20:00
 */
class NewsControllerSpec extends ControllerSpec {
    def 'Index redirects to home'() {
        when:
        controller.index()

        then:
        redirectArgs == [action: controller.home]
    }

    def 'Home generates list of current and archived news items'() {
        setup:
        stubFinderMethods("Published",["NewsFeaturedArticles","NewsAllArticles"])
        stubFinderMethods("Archived",["NewsAllArticles"])        

        and: "article service is present"
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})

        when:
        controller.home()

        then:
        controller.modelAndView.viewName == 'index'
        controller.modelAndView.model.linkedHashMap.featuredArticles == [] 
        controller.modelAndView.model.linkedHashMap.allArticles == [] 
    }

    def 'current generates list of current news articles'() {
        setup:
        stubFinderMethods("Published",["NewsAllArticles"])

        and: "article service is present"
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})

        when:
        def model = controller.current()

        then:
        model.allArticles == []
        model.title == 'news.current.title' 
    }

    def 'archived generates list of older news articles'() {
        setup:
        stubFinderMethods("Archived",["NewsAllArticles"])

        and: "article service is present"
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})

        when:
        def model = controller.archived()

        then:
        model.allArticles == []
        model.title == 'news.archived.title' 
    }

    def 'view displays requested news article'() {
        setup:
        stubViewMethods(["Article"])
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->},view:{a->m})
        mockParams << [id:1]

        when:
        def model = controller.view()

        then:
        model.id == 1
    }

    
    def stubFinderMethods(state, list) {
        list.each {
            controller.metaClass."find${state}${it}" = {params-> 
                def name = it - "News"
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
