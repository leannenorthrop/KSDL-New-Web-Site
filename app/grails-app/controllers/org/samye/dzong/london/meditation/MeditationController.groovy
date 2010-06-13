package org.samye.dzong.london.meditation

import org.samye.dzong.london.events.Event
import org.samye.dzong.london.community.Article

class MeditationController {
    def articleService
    def eventService
	def flickrService

    def index = {
        redirect(action:home)
    }

    def home = {
        def meditationArticles = []
        def topArticles = []

        try {
            meditationArticles = Article.featuredmeditationArticles('datePublished', 'desc').list()
            topArticles = Article.homeMeditationArticles('datePublished', 'desc').list()
        } catch (error) {
            log.error("Meditation controller encountered an error.",error)
        }

		def images = []
		try {
			images = flickrService.getSmallPhotoset('72157623174318636')
		} catch(error) {
			
		}

        def total = Article.allMeditationArticlesNotOrdered.count();
        def events = Event.meditation('featured','desc').list()
        render(view: 'index', model:[images: images, meditationArticles: meditationArticles, topArticles: topArticles,events:events,total:total])
    }

    def all = {
        def articles = Article.allMeditationArticles('datePublished', 'desc').list()
        render(view: 'list', model:[ articles: articles, title: 'meditation.all.articles.title'])
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
        return eventService.list('M',params)
    }

    def slideshow = {
		def album = flickrService.getPhotoset('72157623174318636')
        model: [album:album]
    }
}
