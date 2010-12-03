/** *****************************************************************************
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
 ***************************************************************************** */

package org.samye.dzong.london.wellbeing

import org.samye.dzong.london.community.Article
import org.samye.dzong.london.events.Event
import org.samye.dzong.london.community.Teacher

/**
 * Simple controller to display well-being content.
 * TODO: Add support for similar articles?
 * TODO: Add support for article not found
 * TODO: Add support for params to lists 
 *
 * @author Leanne Northrop
 * @since  Nov 2009
 */
class WellbeingController {
    def articleService
    def eventService
    def teacherService    

    def index = {
        redirect(action:home)
    }

    def home = {
        def topArticles = Article.homeWellbeingArticles('datePublished','desc').list()
        def articles = Article.featuredWellbeingArticles('datePublished','desc').list()
        def totalWellbeing = Article.allWellbeingArticlesNotOrdered().count()
        def events = Event.wellbeing('featured','desc').list()
        def therapists = Teacher.findAllByPublishStateAndType('Published', 'T',[sort: "name", order: "asc"])        
		def model = [topArticles: topArticles, articles: articles,total:totalWellbeing,events:events,therapists:therapists]
		articleService.addHeadersAndKeywords(model,request,response)
        return render(view: 'index',model: model);
    }

    def list = {
        def articles = Article.allWellbeingArticles('datePublished', 'desc').list()
		def model = [ articles: articles, title: 'wellbeing.all.articles.title']
		articleService.addHeadersAndKeywords(model,request,response)
        render(view: 'list', model:model)
    }

    def view = {
        def model = articleService.view(params.id)
		articleService.addHeadersAndKeywords(model,request,response)
        model
    }

    def event = {
        def event = Event.get(params.id)
        def id = params.id;
        def similar = eventService.findSimilar(event)
        def model = [event: event, id: id, similar:similar]
        articleService.addHeadersAndKeywords(model,request,response)
        model
    }

    def events = {
        def model = eventService.list('W',params)
		articleService.addHeadersAndKeywords(model,request,response)
		model
    }
}
