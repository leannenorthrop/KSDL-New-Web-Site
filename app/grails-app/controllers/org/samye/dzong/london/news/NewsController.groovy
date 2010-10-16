package org.samye.dzong.london.news
import org.samye.dzong.london.community.Article

class NewsController {
    def auditLogService
    def articleService

    def index = {
        redirect(action:home)
    }

    def home = {
        def articles = Article.featuredNewsArticles('datePublished', 'desc').list()
        def archivedArticles = Article.archivedNewsArticles('datePublished', 'desc').list(max:8)
        archivedArticles.each { a -> println "Archived ${a}"}
        articles.each { a -> println "Articles ${a}"}
        def totalPublishedNewsArticles = articles.size()
        def totalArchived = archivedArticles.size()
		def model = [ total: totalPublishedNewsArticles, totalArchived: totalArchived, articles: articles, archivedArticles: archivedArticles]
		articleService.addHeadersAndKeywords(model,request,response)
        render(view: 'index', model:model)
    }

    def current = {
        def news = Article.newsArticles('datePublished', 'desc').list()
		def model = [ news: news, title: 'news.current.title']
		articleService.addHeadersAndKeywords(model,request,response)
        render(view: 'list', model:model)
    }

    def archived = {
        def news = Article.archivedNewsArticles('datePublished', 'desc').list()
		def model = [ news: news, title: 'news.archived.title']
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
}
