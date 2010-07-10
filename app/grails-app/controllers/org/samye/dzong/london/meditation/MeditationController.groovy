package org.samye.dzong.london.meditation

import org.samye.dzong.london.events.Event
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.Setting

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
		    def ss = Setting.meditationSlideshow().list()
			images = flickrService.getSmallPhotoset(ss && ss.size() > 0 ? ss[0].value :'72157623174318636')			
		} catch(error) {
			
		}

        def total = Article.allMeditationArticlesNotOrdered.count();
        def events = Event.meditation('featured','desc').list()
		def model = [images: images, meditationArticles: meditationArticles, topArticles: topArticles,events:events,total:total]
		articleService.addHeadersAndKeywords(model,request,response)
        render(view: 'index', model:model)
    }

    def all = {
        def articles = Article.allMeditationArticles('datePublished', 'desc').list()
		def model = [ articles: articles, title: 'meditation.all.articles.title']
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
			def model =  [event: event, id: id, similar:similar]
			articleService.addHeadersAndKeywords(model,request,response)
            model
        }
    }

    def events = {
        def model = eventService.list('M',params)
		articleService.addHeadersAndKeywords(model,request,response)
        model
    }

    def slideshow = {
	    def ss = Setting.meditationSlideshow().list()	
		def album = flickrService.getPhotoset(ss && ss.size() > 0 ? ss[0].value :'72157623174318636')
        model: [album:album]
    }
}
