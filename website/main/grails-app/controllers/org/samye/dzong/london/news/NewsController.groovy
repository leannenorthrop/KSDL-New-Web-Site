package org.samye.dzong.london.news
import org.samye.dzong.london.community.Article

class NewsController {
    def auditLogService
    def articleService

    def index = {
        redirect(action:home)
    }

    def home = {
        def articles = Article.newsArticles('datePublished', 'desc').list(max:15)
        def archivedArticles = Article.archivedNewsArticles('datePublished', 'desc').list(max:8)
        def totalPublishedNewsArticles = Article.categoryArticles('N').count()
        def totalArchived = Article.categoryArticles('N').count()
        render(view: 'index', model:[ total: totalPublishedNewsArticles, totalArchived: totalArchived, articles: articles, archivedArticles: archivedArticles])
    }

    def current = {
        def news = Article.newsArticles('datePublished', 'desc').list()
        render(view: 'list', model:[ news: news, title: 'news.current.title'])
    }

    def archived = {
        def news = Article.archivedNewsArticles('datePublished', 'desc').list()
        render(view: 'list', model:[ news: news, title: 'news.archived.title'])
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
