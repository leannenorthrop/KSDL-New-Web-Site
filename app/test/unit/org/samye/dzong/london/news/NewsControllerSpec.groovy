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
        def news = []
        def archived = []
        Article.metaClass.static.featuredNewsArticles = {a,b-> return new Expando(list: { news })} 
        Article.metaClass.static.archivedNewsArticles = {a,b-> return new Expando(list: { m -> archived })} 
        Article.metaClass.static.publishState = {a-> return new Expando(count: { 0 })} 

        and: "article service is present"
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})

        when:
        controller.home()

        then:
        controller.modelAndView.viewName == 'index'
        controller.modelAndView.model.linkedHashMap.articles == news 
        controller.modelAndView.model.linkedHashMap.archivedArticles == archived 
        controller.modelAndView.model.linkedHashMap.total == news.size()
        controller.modelAndView.model.linkedHashMap.totalArchived == 0 
    }

    def 'current generates list of current news articles'() {
        setup:
        def news = []
        Article.metaClass.static.newsArticles = {a,b-> return new Expando(list: { news })} 

        and: "article service is present"
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})

        when:
        controller.current()

        then:
        controller.modelAndView.viewName == 'list'
        controller.modelAndView.model.linkedHashMap.news == news 
        controller.modelAndView.model.linkedHashMap.title == 'news.current.title' 
    }

    def 'archived generates list of older news articles'() {
        setup:
        def news = []
        Article.metaClass.static.archivedNewsArticles = {a,b-> return new Expando(list: { news })} 

        and: "article service is present"
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})

        when:
        controller.archived()

        then:
        controller.modelAndView.viewName == 'list'
        controller.modelAndView.model.linkedHashMap.news == news 
        controller.modelAndView.model.linkedHashMap.title == 'news.archived.title' 
    }

    def 'view displays requested news article'() {
        setup:
        def m = [:]
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->},view:{id->m})
        mockParams << [id:1]

        when:
        controller.view()

        then:
        controller.modelAndView.viewName == 'view'
        controller.modelAndView.model.linkedHashMap == m 
    }

}
