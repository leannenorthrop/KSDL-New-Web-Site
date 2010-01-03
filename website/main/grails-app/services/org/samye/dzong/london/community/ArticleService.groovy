package org.samye.dzong.london.community

import java.util.Collections

class ArticleService {
    boolean transactional = true

    def findSimilar(article) {
        def articles = Article.findAllByTagWithCriteria(article.tags[0]) {
            and {
                eq('deleted', Boolean.FALSE)
                eq('publishState', "Published")
                not {
                    eq('id', article.id)
                }
                ge('id', 1 + article.id)
            }
            maxResults(5)
            order("lastUpdated", "id")
        }
        return articles ? (articles.size() > 5 ? articles[0..4] : articles) : []
    }
    
    def countPublishedByTags(tags) {
        log.trace "Looking for articles published with ${tags}"
        def mandatoryTag = tags[0]
        def otherTags = tags.size() > 1 ? tags[1..-1] : null
        def articles = Article.findAllByTagWithCriteria(mandatoryTag) {
            and {
                eq('deleted', Boolean.FALSE)
                eq('publishState', "Published")
            }
        }
        def publishedNewsArticles = []
        if (otherTags) {
            publishedNewsArticles = articles.findAll {article ->
                !Collections.disjoint(article.tags, otherTags)
            }
        } else {
            publishedNewsArticles = articles
        }
        return publishedNewsArticles ? publishedNewsArticles.size() : 0
    }
    
    def publishedByTags(tags) {
        def mandatoryTag = tags[0]
        def otherTags = tags.size() > 1 ? tags[1..-1] : null
        def articles = Article.findAllByTagWithCriteria(mandatoryTag) {
            and {
                eq('deleted', Boolean.FALSE)
                eq('publishState', "Published")
            }
        }
        def publishedNewsArticles = []
        if (otherTags) {
            publishedNewsArticles = articles.findAll {article ->
                !Collections.disjoint(article.tags, otherTags)
            }
        } else {
            publishedNewsArticles = articles
        }
        return publishedNewsArticles ?: []
    }

    def countArchivedByTags(tags) {
        def criteria = Article.createCriteria()
        def articles = criteria.list(){
            and {
                eq('deleted', Boolean.FALSE)
                eq('publishState', "Archived")
            }
        }
        def publishedNewsArticles = []
        publishedNewsArticles = articles.findAll {article ->
            !Collections.disjoint(article.tags, tags)
        }
        return publishedNewsArticles.size() ?: 0
    }   
        
    def archivedByTags(tags) {
        def criteria = Article.createCriteria()
        def articles = criteria.list(){
            and {
                eq('deleted', Boolean.FALSE)
                eq('publishState', "Archived")
            }
        }
        def publishedNewsArticles = []
        publishedNewsArticles = articles.findAll {article ->
            !Collections.disjoint(article.tags, tags)
        }
        return publishedNewsArticles ?: []
    }    

    def view(id) {
        def articleInstance = Article.get(id)
        if(!articleInstance) {
            flash.message = "Article not found (id ${params.id} unknown)"
            return null
        }
        else {
            def similar = findSimilar(articleInstance)
            return [ articleInstance : articleInstance, articles: similar ]
        }    
    }
}
