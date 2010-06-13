package org.samye.dzong.london.buddhism

import org.samye.dzong.london.events.Event
import org.samye.dzong.london.community.Article

class BuddhismController {
    def articleService
    def eventService
    def flickrService

    def index = {
        redirect(action:home)
    }

    def home = {
        def topArticles = Article.homeBuddhismArticles('datePublished','desc').list()
        def articles = Article.featuredBuddhismArticles('datePublished','desc').list()
        def total = Article.allBuddhismArticlesNotOrdered().count()
        def events = Event.buddhism('featured','desc').list()

		def images = []
		try {
			images = flickrService.getSmallPhotoset('72157623174318636')
		} catch(error) {
			
		}
		
        return render(view: 'index',model: [images:images,topArticles: topArticles, articles: articles,total:total,events:events]);
    }

    def list = {
        def articles = Article.allBuddhismArticles('datePublished', 'desc').list()
        render(view: 'list', model:[ articles: articles, title: 'buddhism.all.articles.title'])
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
        return eventService.list('B',params)
    }

    def slideshow = {
		def album = flickrService.getPhotoset('72157623174318636')
        model: [album:album]
    }
}
