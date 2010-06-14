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
        return render(view: 'index',model: [events:events,topArticles: topArticles, community: community,volunteerOpportunities:volunteer,totalCommunity:totalCommunity,totalVolunteer:totalVolunteer]);
    }

    def list = {
        def articles = Article.allCommunityArticles('datePublished', 'desc').list()
        render(view: 'list', model:[ articles: articles, title: 'community.all.articles.title'])
    }

    def view = {
        def model = articleService.view(params.id)
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
            return [event: event, id: id, similar:similar]
        }
    }

    def events = {
        return eventService.list('C',params)
    }

    def send = {
        if (params.email && params.body && params.subject) {
            emailService.sendVolunteerRequest(params.email, params.subject, params.body)
        }
        redirect(action: home)
    }
}
