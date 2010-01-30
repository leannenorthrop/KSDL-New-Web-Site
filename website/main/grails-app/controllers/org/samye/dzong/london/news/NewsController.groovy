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
        def totalPublishedNewsArticles = Article.newsArticles('datePublished', 'desc').count()
        def totalArchived = Article.archivedNewsArticles('datePublished', 'desc').count()
        render(view: 'index', model:[ total: totalPublishedNewsArticles, totalArchived: totalArchived, articles: articles, archivedArticles: archivedArticles])
    }

    def current = {
        def articles = Article.newsArticles('datePublished', 'dsc')
        render(view: 'list', model:[ articles: articles, title: 'Current News'])
    }

    def archive = {
        def articles = Article.archivedNewsArticles('datePublished', 'dsc')
        render(view: 'list', model:[ articles: articles, title: 'Older News'])
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
