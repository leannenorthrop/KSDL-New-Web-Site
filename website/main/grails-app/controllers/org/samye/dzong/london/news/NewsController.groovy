package org.samye.dzong.london.news
import org.samye.dzong.london.community.Article

class NewsController {
    def auditLogService
    def articleService

    def index = {
        redirect(action:home)  
    }
    
    def home = {
        def totalPublishedNewsArticles = articleService.countPublishedByTags(['news'])
        def totalArchived = articleService.countArchivedByTags(['news'])
        def articles = articleService.publishedByTags(['news'], [max : 15])
        def auditDetails = articles.collect { article ->
            def id = Long.toString(article.id,10)
            [auditLogService.publishedOn(id), auditLogService.createdOn(id), auditLogService.lastUpdatedOn(id)]
        }
        def archivedArticles = articleService.archivedByTags(['news'])
        render(view: 'index', model:[ total: totalPublishedNewsArticles, totalArchived: totalArchived, articles: articles, auditDetails: auditDetails, archivedArticles: archivedArticles])    
    }

    def current = {
        def articles = articleService.publishedByTags(['news'])
        def auditDetails = articles.collect { article ->
            def id = Long.toString(article.id,10)
            [auditLogService.publishedOn(id), auditLogService.createdOn(id), auditLogService.lastUpdatedOn(id)]
        }
        render(view: 'list', model:[ articles: articles, title: 'Current News'])     
    }
        
    def archive = {
        def articles = articleService.archivedByTags(['news'])
        def auditDetails = articles.collect { article ->
            def id = Long.toString(article.id,10)
            [auditLogService.publishedOn(id), auditLogService.createdOn(id), auditLogService.lastUpdatedOn(id)]
        }
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
