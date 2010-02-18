package org.samye.dzong.london.meditation

import org.samye.dzong.london.events.Event
import org.samye.dzong.london.community.Article

class MeditationController {
    def articleService

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

        def total = Article.allMeditationArticlesNotOrdered.count();
        def events = Event.meditation('eventDate','desc').list()
        render(view: 'index', model:[meditationArticles: meditationArticles, topArticles: topArticles,events:events,total:total])
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
}
