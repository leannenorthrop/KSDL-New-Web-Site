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

import org.samye.dzong.london.community.Article
import org.samye.dzong.london.cms.*

/*
 * News content url handler. Displays only public facing pages.
 *
 * @author Leanne Northrop
 * @since  November 2009
 */
class NewsController {
    def articleService

    NewsController() {
        CMSUtil.addFinderMethods(this)        
    }
    
    def index = {
        redirect(action:home)
    }

    def home = {
        def model = [:]
        def articles = findPublishedNewsHomeArticles([sort:'datePublished',order:'desc'])
        model.putAll(articles)
        
        def archivedArticles = findArchivedNewsAllArticles([sort:'datePublished',order:'desc',max:8])
        model.putAll(archivedArticles)
        
        articles = findPublishedNewsAllArticles([sort:'datePublished',order:'desc'])
        model.put('total', findPublishedNewsAllArticles().totalAllArticles)
        
        def totalArchived = findArchivedNewsAllArticles().totalAllArticles
        model.put('totalArchived', totalArchived)
        
        articleService.addHeadersAndKeywords(model,request,response)
        render(view: 'index', model:model)
    }

    def current = {
        render(view: 'list', model: list(false,request,response))
    }

    def archived = {
        render(view: 'list', model: list(true,request,response))
    }

    def view = {
        def model = viewArticle(params.id)
        articleService.addHeadersAndKeywords(model,request,response)
        return model
    }

    def list(archived,request,response) {
        def model = [:] 
        
        def title
        if (archived) {
            model = findArchivedNewsAllArticles(params)            
            model.put('title','news.archived.title')
        } else {
            model = findPublishedNewsAllArticles(params)
            model.put('title','news.current.title')
        }

        articleService.addHeadersAndKeywords(model,request,response)
        model
    }

}
