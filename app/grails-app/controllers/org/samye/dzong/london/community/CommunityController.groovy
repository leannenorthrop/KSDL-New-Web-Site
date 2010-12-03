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

import org.samye.dzong.london.events.Event

class CommunityController {
    def articleService
    def eventService
	def emailService

    def index = {
        redirect(action:home)
    }

    def home = {
        def topArticles = Article.homeCommunityArticles('datePublished','desc').list()
        def community = Article.featuredCommunityArticles('datePublished','desc').list()
        def volunteer = Article.featuredCommunityArticles('datePublished','desc').list()
        community = community.findAll { article ->
            article.tags.find { tag -> !"volunteer".equalsIgnoreCase(tag)}
        }
        volunteer = volunteer.findAll { article ->
            article.tags.find { tag -> "volunteer".equalsIgnoreCase(tag)}
        }
        def totalCommunity = Article.allCommunityArticlesNotOrdered().count()
        def totalVolunteer = volunteer.size()
        def events = Event.community('featured','desc').list()
		def model = [events:events,topArticles: topArticles, community: community,volunteerOpportunities:volunteer,totalCommunity:totalCommunity,totalVolunteer:totalVolunteer]
		articleService.addHeadersAndKeywords(model,request,response)
        return render(view: 'index',model: model);
    }

    def list = {
        def articles = Article.allCommunityArticles('datePublished', 'desc').list()
		def model = [ articles: articles, title: 'community.all.articles.title']
		articleService.addHeadersAndKeywords(model,request,response)
        render(view: 'list', model:model)
    }

    def view = {
        def model = articleService.view(params.id)
		articleService.addHeadersAndKeywords(model,request,response)
        if (!model) {
            redirect(action:home)
        } else {
            render(view: 'view', model: model)
        }
    }

    def event = {
        def event = Event.get(params.id)
        if (!event) {
            flash.message = "Event not found with id ${params.id}"
            redirect(action: home)
        }
        else {
            def id = params.id;
            def similar = eventService.findSimilar(event)
            def model = [event: event, id: id, similar:similar]
			articleService.addHeadersAndKeywords(model,request,response)
			model
        }
    }

    def events = {
        def model = eventService.list('C',params)
		articleService.addHeadersAndKeywords(model,request,response)
		model
    }

    def send = {
        if (params.email && params.body && params.subject) {
            emailService.sendVolunteerRequest(params.email, params.subject, params.body)
        }
        redirect(action: home)
    }
}
