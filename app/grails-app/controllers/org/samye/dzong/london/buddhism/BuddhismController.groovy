package org.samye.dzong.london.buddhism

import org.samye.dzong.london.events.Event
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.Setting

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
		    def ss = Setting.buddhistSlideshow().list()
			images = flickrService.getSmallPhotoset(ss && ss.size() > 0 ? ss[0].value :'72157623174318636')			
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
	    def ss = Setting.buddhistSlideshow().list()
		def album = flickrService.getPhotoset(ss && ss.size() > 0 ? ss[0].value :'72157623174318636')
        model: [album:album]
    }
}
