package org.samye.dzong.london.buddhism

import org.samye.dzong.london.events.Event
import org.samye.dzong.london.community.Article

class BuddhismController {
    def articleService

    def index = {
        redirect(action:home)
    }

    def home = {
        def topArticles = Article.homeBuddhismArticles('datePublished','desc').list()
        def articles = Article.featuredBuddhismArticles('datePublished','desc').list()
        def total = Article.allBuddhismArticlesNotOrdered().count()
        def events = Event.buddhism('featured','desc').list()
        return render(view: 'index',model: [topArticles: topArticles, articles: articles,total:total,events:events]);
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
}
