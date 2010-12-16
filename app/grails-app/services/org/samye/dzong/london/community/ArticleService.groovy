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
                response.setHeader("Cache-Control", "public,max-age=86400,s-maxage=86400, must-revalidate")
                
                log.debug "If-Modified-Since is ${request.getDateHeader('If-Modified-Since')}"
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
                response.setHeader("Cache-Control", "public, max-age=0,s-maxage=0,must-revalidate")
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
            def allTags = []
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
                        allTags = allTags + it.tags.flatten()
                    } else {
						""
                    }
                }
            } catch(error) {
                log.warn "Unable to fetch keywords/last published date", error
            } finally {
                model.put('keywords',allTags.unique())
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
}
