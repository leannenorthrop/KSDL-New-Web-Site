package org.samye.dzong.london.wellbeing

import org.samye.dzong.london.community.Article
import org.samye.dzong.london.events.Event
import org.samye.dzong.london.community.Teacher

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
        def model = eventService.list('W',params)
		articleService.addHeadersAndKeywords(model,request,response)
		model
    }
}
