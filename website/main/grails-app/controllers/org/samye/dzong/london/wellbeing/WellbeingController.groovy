package org.samye.dzong.london.wellbeing

import org.samye.dzong.london.community.Article
import org.samye.dzong.london.events.Event

class WellbeingController {
    def articleService

    def index = {
        redirect(action:home)
    }

    def home = {
        def topArticles = Article.homeWellbeingArticles('datePublished','desc').list()
        def articles = Article.featuredWellbeingArticles('datePublished','desc').list()
        def totalWellbeing = Article.allWellbeingArticlesNotOrdered().count()
        def events = Event.wellbeing('featured','desc').list()
        return render(view: 'index',model: [topArticles: topArticles, articles: articles,total:totalWellbeing,events:events]);
    }

    def list = {
        def articles = Article.allWellbeingArticles('datePublished', 'desc').list()
        render(view: 'list', model:[ articles: articles, title: 'wellbeing.all.articles.title'])
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
