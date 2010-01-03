package org.samye.dzong.london

class FeedController {
	def auditLogService
    def articleService

	def index = {}
	
	def news = {
        def articles = articleService.publishedByTags(['news'])
		def auditDetails = articles.collect { article ->
			def id = Long.toString(article.id,10)
			[auditLogService.publishedOn(id), auditLogService.createdOn(id), auditLogService.lastUpdatedOn(id)]
		}	
		render(feedType:"rss", feedVersion:"2.0") {
            title = "Kagyu Samye Dzong London News"
            link = "http://localhost:8080/main/news/feed"
            description = "Lastest news articles from Kagyu Samye Dzong London"
            articles.eachWithIndex { article,index ->
                entry(article.title) {
                    link = "http://localhost:8080/main/article/view/${article.id}"
					author = article.author.username
					publishedDate = auditDetails[index][0] != null &&   auditDetails[index][0].dateCreated != null? auditDetails[index][0].dateCreated : new Date()
					content(type:'text/html') {
                    	article.content.encodeAsTextile()
					}
                }
            }
        }		
	}
	
	def meditation = {
        def articles = articleService.publishedByTags(['meditation'])
		def auditDetails = articles.collect { article ->
			def id = Long.toString(article.id,10)
			[auditLogService.publishedOn(id), auditLogService.createdOn(id), auditLogService.lastUpdatedOn(id)]
		}	
		render(feedType:"rss", feedVersion:"2.0") {
            title = "Kagyu Samye Dzong London News"
            link = "http://localhost:8080/main/meditation/feed"
            description = "Lastest meditation articles from Kagyu Samye Dzong London"
            articles.eachWithIndex { article,index ->
                entry(article.title) {
                    link = "http://localhost:8080/main/article/view/${article.id}"
					author = article.author.username
					publishedDate = auditDetails[index][0] != null &&   auditDetails[index][0].dateCreated != null? auditDetails[index][0].dateCreated : new Date()
					content(type:'text/html') {
                    	article.content.encodeAsTextile()
					}
                }
            }
        }		
	}
	
	def community = {
        def articles = articleService.publishedByTags(['community'])
		def auditDetails = articles.collect { article ->
			def id = Long.toString(article.id,10)
			[auditLogService.publishedOn(id), auditLogService.createdOn(id), auditLogService.lastUpdatedOn(id)]
		}	
		render(feedType:"rss", feedVersion:"2.0") {
            title = "Kagyu Samye Dzong London Community Articles"
            link = "http://localhost:8080/main/community/feed"
            description = "Lastest community articles from Kagyu Samye Dzong London"
            articles.eachWithIndex { article,index ->
                entry(article.title) {
                    link = "http://localhost:8080/main/article/view/${article.id}"
					author = article.author.username
					publishedDate = auditDetails[index][0] != null && auditDetails[index][0].dateCreated != null? auditDetails[index][0].dateCreated : new Date()
					content(type:'text/html') {
                    	article.content.encodeAsTextile()
					}
                }
            }
        }		
	}
	
	def wellbeing = {
        def articles = articleService.publishedByTags(['wellbeing'])
		def auditDetails = articles.collect { article ->
			def id = Long.toString(article.id,10)
			[auditLogService.publishedOn(id), auditLogService.createdOn(id), auditLogService.lastUpdatedOn(id)]
		}	
		render(feedType:"rss", feedVersion:"2.0") {
            title = "Kagyu Samye Dzong London Well-Being Articles"
            link = "http://localhost:8080/main/wellbeing/feed"
            description = "Lastest well-being articles from Kagyu Samye Dzong London"
            articles.eachWithIndex { article,index ->
                entry(article.title) {
                    link = "http://localhost:8080/main/article/view/${article.id}"
					author = article.author.username
					publishedDate = auditDetails[index][0] != null &&   auditDetails[index][0].dateCreated != null? auditDetails[index][0].dateCreated : new Date()
					content(type:'text/html') {
                    	article.content.encodeAsTextile()
					}
                }
            }
        }		
	}			
}
