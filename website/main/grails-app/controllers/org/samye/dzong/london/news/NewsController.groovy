package org.samye.dzong.london.news
import org.samye.dzong.london.community.Article

class NewsController {
	def auditLogService
    def articleService

    def index = {
        def articles = articleService.publishedNews()
		def auditDetails = articles.collect { article ->
			def id = Long.toString(article.id,10)
			[auditLogService.publishedOn(id), auditLogService.createdOn(id), auditLogService.lastUpdatedOn(id)]
		}
        def archivedArticles =  articleService.archivedNews()
        model:[ articles: articles, auditDetails: auditDetails, archivedArticles: archivedArticles]
    }
}
