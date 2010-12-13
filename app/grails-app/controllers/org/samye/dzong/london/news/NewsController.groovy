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
    NewsController() {
        CMSUtil.addFinderMethods(this)        
    }
    
    def index = {
        forward(action:'home')
    }

    def home = {
        def model = [:]
        populateNavigationData(model)
        def articles = findPublishedNewsHomeArticles([sort:'datePublished',order:'desc'])
        model.putAll(articles)
        
        def archivedArticles = findArchivedNewsAllArticles([sort:'datePublished',order:'desc',max:8])
        model.putAll(archivedArticles)
        
        articles = findPublishedNewsAllArticles([sort:'datePublished',order:'desc'])
        model.put('total', findPublishedNewsAllArticles().totalAllArticles)
        
        def totalArchived = findArchivedNewsAllArticles().totalAllArticles
        model.put('totalArchived', totalArchived)
        
        render(view: 'index', model:model)
    }

    def current = {
        log.debug "Looking for current"        
        def model = list(params)
        render(view: 'list', model: model)
    }

    def archived = {
        log.debug "Looking for archived"        
        def model = list(params,true)
        render(view: 'list', model: model)
    }
    
    def show = {
        def model = list(params, params.year != new Date().format("yyyy"))
        render(view: 'list', model: model)
    }    

    def view = {
        def model = viewArticle(params.id)
        populateNavigationData(model)        
        return model
    }

    def populateNavigationData(model) {
        def articles = findPublishedNewsAllArticles([sort:'datePublished',order:'desc']).allArticles
        
        def months = [] as Set
        articles.each { article ->
            months << article.datePublished.format('MMMM') + " " + article.datePublished.format('yyyy') 
        }
        model.put('months',months)

        def archivedNews = findArchivedNewsAllArticles([sort:'datePublished',order:'desc']).allArticles
        def years = [] as Set
        archivedNews.each { article ->
            years << article.datePublished.format('yyyy')
        }        
        model.put('years',years)                                        
    }

    def list(params,archived=null) {
        def model = [:] 

        log.debug "archived = " + (archived == true)
        if (params.month) {
            params.month = params.month.capitalize()
            model = findPublishedNewsAllArticles(params)            
            model.allArticles = model.allArticles.findAll{ article ->
                article.datePublished.format("MMMM") == params.month && article.datePublished.format("yyyy") == params.year
            }
            model.put('title',params.month + " " + params.year)
        } else if (params.year && params.year ==~ /dddd/) {
            if (archived) {
                model = findArchivedNewsAllArticles(params)
                model.allArticles = model.allArticles.findAll{ article ->             
                    article.datePublished.format("yyyy") == params.year
                }                                   
            } else {
                model = findPublishedNewsAllArticles(params)
                model.allArticles = model.allArticles.findAll{ article ->
                    article.datePublished.format("yyyy") == params.year
                }      
            }     
            model.put('title',params.year)
        } else if (archived) {
            model = findArchivedNewsAllArticles(params)            
            log.debug "Archived ${model} ${params}"
            model.put('title','news.archived.title')
        } else {
            model = findPublishedNewsFeaturedArticles(params)
            model.allArticles = model.featuredArticles
            log.debug "Published ${model}  ${params}"                
            model.put('title','news.current.title')
        } 
        populateNavigationData(model)
        log.debug model        
        model
    }
}
