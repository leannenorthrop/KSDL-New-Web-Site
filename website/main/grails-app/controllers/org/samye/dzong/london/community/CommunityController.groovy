package org.samye.dzong.london.community

import org.samye.dzong.london.events.Event

class CommunityController {
    def articleService

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
        def events = Event.community('eventDate','desc').list()
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
}
