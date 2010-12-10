/*
 * Copyright © 2010 Leanne Northrop
 *
 * This file is part of Samye Content Management System.
 *
 * Samye Content Management System is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * Samye Content Management System is distributed in the hope that it will be
 * useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Samye Content Management System.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * BT plc, hereby disclaims all copyright interest in the program
 * “Samye Content Management System” written by Leanne Northrop.
 */

package org.samye.dzong.london.community

import org.samye.dzong.london.cms.Publishable

/**
 * Helper class for all things related to PubLished items.
 * TODO: Rename 
 * 
 * Caching handled by both handleIfNotModifiedSince() and addHeadersAndKeywords() 
 * https://www.pivotaltracker.com/story/show/6982203
 */
class ArticleService {
    boolean transactional = true
    def userLookupService

    def archiveNewsArticles() {
        def articles = findPublishedNewsAllArticles([sort:'datePublished',order:'desc'])
        
        // Find News items published within last 90 days
        def now = new java.util.Date() 
        now = now - 90 
        def toKeep = articles.findAll { article ->            
            article.datePublished  
        };
        articles.removeAll(toKeep)
        
        // Archive older news items
        articles.each { article ->
            Article.withTransaction { status ->
                try {
                    article.publishState = "Archived"
                    article.save()
                } catch (error) {
                    log.warn "Could not archive news article ${article}",error
                    status.setRollbackOnly()
                }
            }
        }        
    }
    
    def getNewestPublishedItem() {
        def newestPublishedItem = Publishable.allPublished().list(sort:"datePublished", order:"desc")[0]
        def newestUpdatedItem = Publishable.allPublished().list(sort:"lastUpdated", order:"desc")[0]
        def latestDate = [newestPublishedItem.datePublished,newestUpdatedItem.lastUpdated].max()        
        def date = new Date()
        date.setTime(latestDate.getTime())
        log.debug "Max of ${newestPublishedItem.datePublished.format('dd MM yyyy')}  and ${newestUpdatedItem.lastUpdated.format('dd MM yyyy')} is  ${date.format('dd MM yyyy')}"        
        if (latestDate == newestPublishedItem.datePublished) {
            [item:newestPublishedItem,date:date]
        } else {
            [item:newestUpdatedItem,date:date]
        }
    }
    def handleIfNotModifiedSince(request,response) {     
        try {
            if (Publishable.allPublished().count() > 0) {
                def now = new Date()
                now = now + 1
                now.clearTime()                   
                
                def newestItemInfo = getNewestPublishedItem()
                response.setDateHeader('Expires', now.time)                
                response.setHeader("Cache-Control", "public,max-age=604800,s-maxage=604800, must-revalidate")
                
                log.debug "If-Modified-Since is  ${request.getDateHeader('If-Modified-Since')}"
                def modifiedSinceDate = new Date()
                modifiedSinceDate.setTime(request.getDateHeader("If-Modified-Since"))
                if (modifiedSinceDate.after(newestItemInfo.date)) {
                    response.setStatus(304)
                    response.flushBuffer()
                    return
                }
                
                log.debug "If-None-Match is ${request.getHeader('If-None-Match')}"
                if (request.getHeader('If-None-Match')){
                    if (request.getHeader("If-None-Match") != newestItemInfo.item.version) {
                        response.setStatus(304)
                        response.flushBuffer()
                        return
                    }                    
                }
            } else {
                response.setDateHeader('Expires', new Date().time)
                response.setHeader("Cache-Control", "public")                
            }
        } catch (error) {
            log.warn "Can not determine cache control headers.", error
            if (response) {
                response.setDateHeader('Expires', new Date().time)
                response.setHeader("Cache-Control", "public, must-revalidate")
            }
        }
    }

    def addHeadersAndKeywords(model, request, response) {
        if (model) {
            try {
                def newestItemInfo = getNewestPublishedItem()
                response.setDateHeader('Last-Modified', newestItemInfo.date.time)
                response.setHeader("ETag", "W/\"${newestItemInfo.item.version}\"")                
            } catch(error) {
                
            }
            def allTags = [] as Set
            try {                
                def result = model.groupBy {
                    try {
                        return it.value.datePublished || it.value[0].datePublished
                    } catch (error) {
                        return false
                    }
                }
                def articles = result[true].collect {
                    it.value
                }
                articles.each {
                    if (it.tags) {
                        allTags.addAll it.tags
                    } else {
						""
                    }
                }
            } catch(error) {
                log.warn "Unable to fetch keywords/last published date", error
            } finally {
                model.put('keywords',allTags)
            }
        }
    }

    def findSimilar(article, params = []) {
        def tagQuery = "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and ("
        for (tag in article.tags) {
            tagQuery += "tl.tag.name = '${tag}' or "
        }
        tagQuery = tagQuery[0..-4] + "))"
        def articles = Article.executeQuery("from Article a where a.category = '${article.category}' and a.id != ${article.id} and ${tagQuery} and (a.publishState = 'Published' or a.publishState = 'Archived') and a.deleted = convert('false',BOOLEAN) order by a.lastUpdated desc", params)
        return articles ? (articles.size() > 16 ? articles[0..14] : articles) : []
    }

    def findSimilarAllCategories(article, params = []) {
        def tagQuery = "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and ("
        for (tag in article.tags) {
            tagQuery += "tl.tag.name = '${tag}' or "
        }
        tagQuery = tagQuery[0..-4] + "))"
        def articles = []
        try {
            log.debug "**params = ${params} ********** from Article a where a.id != ${article.id} and ${tagQuery} and (a.publishState = 'Published' or a.publishState = 'Archived') and a.deleted = convert('false',BOOLEAN) order by a.lastUpdated desc"
            articles = Article.executeQuery("from Article a where a.id != ${article.id} and ${tagQuery} and (a.publishState = 'Published' or a.publishState = 'Archived') and a.deleted = convert('false',BOOLEAN) order by a.lastUpdated desc", params)
        } catch (error) {
            log.error error
        }
        return articles ? (articles.size() > 16 ? articles[0..14] : articles) : []
    }

    def findByTag(tagname, params = []) {
        def tagQuery = "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and ("
        tagQuery += "tl.tag.name = '${tagname}' or "
        tagQuery = tagQuery[0..-4] + "))"
        def articles = Article.executeQuery("from Article a where ${tagQuery} and (a.publishState = 'Published' or a.publishState = 'Archived') and a.deleted = convert('false',BOOLEAN) order by a.lastUpdated desc", params)
        return articles ? (articles.size() > 16 ? articles[0..14] : articles) : []
    }

    def countPublishedByTags(tags, inclusive = Boolean.FALSE) {
        def articles
        def tagQuery = ""
        if (inclusive) {
            tagQuery = "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and ("
            for (tag in tags) {
                tagQuery += "tl.tag.name = '${tag}' or "
            }
            tagQuery = tagQuery[0..-4] + "))"
            articles = Article.executeQuery("from Article a where ${tagQuery} and a.publishState = 'Published' and a.deleted = convert('false',BOOLEAN)")
        } else {
            for (tag in tags) {
                tagQuery += "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and tl.tag.name = '${tag}') and "
            }

            articles = Article.executeQuery("from Article a where ${tagQuery} a.publishState = 'Published' and a.deleted = convert('false',BOOLEAN)")
        }

        return articles ? articles.size(): 0
    }

    def publishedByTags(tags, params = [], inclusive = Boolean.FALSE) {
        def articles
        def tagQuery = ""
        if (inclusive) {
            tagQuery = "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and ("
            for (tag in tags) {
                tagQuery += "tl.tag.name = '${tag}' or "
            }
            tagQuery = tagQuery[0..-4] + "))"
            articles = Article.executeQuery("from Article a where ${tagQuery} and a.publishState = 'Published' and a.deleted = convert('false',BOOLEAN) order by a.lastUpdated desc", params)
        } else {
            for (tag in tags) {
                tagQuery += "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and tl.tag.name = '${tag}') and "
            }

            articles = Article.executeQuery("from Article a where ${tagQuery} a.publishState = 'Published' and a.deleted = convert('false',BOOLEAN) order by a.lastUpdated desc",params)
        }

        return articles ?: []
    }

    def countArchivedByTags(tags, inclusive = Boolean.TRUE) {
        def articles
        def tagQuery = ""
        if (inclusive) {
            tagQuery = "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and ("
            for (tag in tags) {
                tagQuery += "tl.tag.name = '${tag}' or "
            }
            tagQuery = tagQuery[0..-4] + "))"
            articles = Article.executeQuery("from Article a where ${tagQuery} and a.publishState = 'Archived' and a.deleted = convert('false',BOOLEAN)")
        } else {
            for (tag in tags) {
                tagQuery += "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and tl.tag.name = '${tag}') and "
            }

            articles = Article.executeQuery("from Article a where ${tagQuery} a.publishState = 'Archived' and a.deleted = convert('false',BOOLEAN)")
        }

        return articles ? articles.size(): 0
    }

    def archivedByTags(tags, inclusive = Boolean.TRUE) {
        def articles
        def tagQuery = ""
        if (inclusive) {
            tagQuery = "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and ("
            for (tag in tags) {
                tagQuery += "tl.tag.name = '${tag}' or "
            }
            tagQuery = tagQuery[0..-4] + "))"
            articles = Article.executeQuery("from Article a where ${tagQuery} and a.publishState = 'Archived' and a.deleted = convert('false',BOOLEAN)")
        } else {
            for (tag in tags) {
                tagQuery += "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and tl.tag.name = '${tag}') and "
            }

            articles = Article.executeQuery("from Article a where ${tagQuery} a.publishState = 'Archived' and a.deleted = convert('false',BOOLEAN)")
        }

        return articles ?: []
    }

    def view(id) {
        def articleInstance = Article.get(id)
        if(!articleInstance) {
            flash.message = "Article not found (id ${params.id} unknown)"
            return null
        }
        else {
            def similar = findSimilarAllCategories(articleInstance)
            return [ articleInstance : articleInstance, articles: similar ]
        }
    }

    def userUnpublished(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def username = userLookupService.username();
        def articles = Article.orderedAuthorPublishState(username,"Unpublished", order, dir).list(params);
        def total = Article.authorPublishState(username,"Unpublished").count();
        println "found ${articles.size()}"
        return [articles: articles, total: total]
    }

    def userReady(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def username = userLookupService.username();
        def articles = Article.orderedAuthorPublishState(username,"Ready For Publication", order, dir).list(params);
        def total = Article.authorPublishState(username,"Ready For Publication").count();
        println "found ${articles.size()} of ${total}"
        return [articles: articles, total: total]
    }

    def userPublished(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def username = userLookupService.username();
        def articles = Article.orderedAuthorPublishState(username,"Published", order, dir).list(params);
        def total = Article.authorPublishState(username,"Published").count();
        return [articles: articles, total: total]
    }

    def userArchived(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def username = userLookupService.username();
        def articles = Article.orderedAuthorPublishState(username,"Archived", order, dir).list(params);
        def total = Article.authorPublishState(username,"Archived").count();
        return [articles: articles, total: total]
    }

    def userDeleted(params) {
        def username = userLookupService.username();
        def articles = Article.deletedAuthor(username).list(params);
        def total = Article.deletedAuthor(username).count();
        return [articles: articles, total: total]
    }

    def unpublished(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def articles = Article.orderedPublishState("Unpublished", order, dir).list(params);
        def total = Article.publishState("Unpublished").count();
        println "found ${articles.size()} of ${total}"
        return [articles: articles, total: total]
    }

    def ready(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def articles = Article.orderedPublishState("Ready For Publication", order, dir).list(params);
        def total = Article.publishState("Ready For Publication").count();
        println "found ${articles.size()} of ${total}"
        return [articles: articles, total: total]
    }

    def published(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def articles = Article.orderedPublishState("Published", order, dir).list(params);
        def total = Article.publishState("Published").count();
        return [articles: articles, total: total]
    }

    def archived(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def articles = Article.orderedPublishState("Archived", order, dir).list(params);
        def total = Article.publishState("Archived").count();
        return [articles: articles, total: total]
    }

    def deleted(params) {
        def articles = Article.deleted().list(params);
        def total = Article.deleted().count();
        return [articles: articles, total: total]
    }
}
