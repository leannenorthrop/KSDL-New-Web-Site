package org.samye.dzong.london.meditation

import org.samye.dzong.london.events.Event

class MeditationController {
    def articleService

    def index = {
        redirect(action:home)
    }

    def home = {
        def adviceArticles = []
        def benefitsArticles = []
        def topArticles = []

        // TODO 
        try {
            adviceArticles = articleService.publishedByTags(['meditation','advice', 'front'])
            benefitsArticles = articleService.publishedByTags(['meditation','benefits', 'front'])
            topArticles = articleService.publishedByTags(['meditation', 'home'], [max: 1])
        } catch (error) {
            log.error("Meditation controller encountered an error.")
        }

        def events = Event.meditation('eventDate','desc').list()
        render(view: 'index', model:[adviceArticles: adviceArticles, benefitsArticles: benefitsArticles, topArticles: topArticles,events:events])
    }

    def current = {
        def articles = articleService.publishedByTags(['meditation'])
        render(view: 'list', model:[ articles: articles, title: 'Current News'])
    }

    def archive = {
        def articles = articleService.archivedByTags(['meditation'])
        render(view: 'list', model:[ articles: articles, title: 'Older Meditation Articles'])
    }

    def all = {
        def articles = articleService.publishedByTags(['meditation'])
        def articles2 = articleService.archivedByTags(['meditation'])
        articles2.each() { item ->
            articles << item
        }
        render(view: 'list', model:[ articles: articles, title: 'Meditation Articles'])
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
