package org.samye.dzong.london.community

class ArticleService {
    boolean transactional = true
    def userLookupService

    def findSimilar(article, params = []) {
        def tagQuery = "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and ("
        for (tag in article.tags) {
            tagQuery += "tl.tag.name = '${tag}' or "
        }
        tagQuery = tagQuery[0..-4] + "))"
        def articles = Article.executeQuery("from Article a where a.category = '${article.category}' and a.id != ${article.id} and ${tagQuery} and (a.publishState = 'Published' or a.publishState = 'Archived') and a.deleted = false order by a.lastUpdated desc", params)
        return articles ? (articles.size() > 16 ? articles[0..14] : articles) : []
    }

    def findSimilarAllCategories(article, params = []) {
        def tagQuery = "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and ("
        for (tag in article.tags) {
            tagQuery += "tl.tag.name = '${tag}' or "
        }
        tagQuery = tagQuery[0..-4] + "))"
        def articles = Article.executeQuery("from Article a where a.id != ${article.id} and ${tagQuery} and (a.publishState = 'Published' or a.publishState = 'Archived') and a.deleted = false order by a.lastUpdated desc", params)
        return articles ? (articles.size() > 16 ? articles[0..14] : articles) : []
    }

    def findByTag(tagname, params = []) {
        def tagQuery = "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and ("
        tagQuery += "tl.tag.name = '${tagname}' or "
        tagQuery = tagQuery[0..-4] + "))"
        def articles = Article.executeQuery("from Article a where ${tagQuery} and (a.publishState = 'Published' or a.publishState = 'Archived') and a.deleted = false order by a.lastUpdated desc", params)
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
            articles = Article.executeQuery("from Article a where ${tagQuery} and a.publishState = 'Published' and a.deleted = false")
        } else {
            for (tag in tags) {
                tagQuery += "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and tl.tag.name = '${tag}') and "
            }

            articles = Article.executeQuery("from Article a where ${tagQuery} a.publishState = 'Published' and a.deleted = false")
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
            articles = Article.executeQuery("from Article a where ${tagQuery} and a.publishState = 'Published' and a.deleted = false order by a.lastUpdated desc", params)
        } else {
            for (tag in tags) {
                tagQuery += "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and tl.tag.name = '${tag}') and "
            }

            articles = Article.executeQuery("from Article a where ${tagQuery} a.publishState = 'Published' and a.deleted = false order by a.lastUpdated desc",params)
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
            articles = Article.executeQuery("from Article a where ${tagQuery} and a.publishState = 'Archived' and a.deleted = false")
        } else {
            for (tag in tags) {
                tagQuery += "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and tl.tag.name = '${tag}') and "
            }

            articles = Article.executeQuery("from Article a where ${tagQuery} a.publishState = 'Archived' and a.deleted = false")
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
            articles = Article.executeQuery("from Article a where ${tagQuery} and a.publishState = 'Archived' and a.deleted = false")
        } else {
            for (tag in tags) {
                tagQuery += "a.id in (select tl.tagRef from TagLink tl where tl.type = 'article' and tl.tag.name = '${tag}') and "
            }

            articles = Article.executeQuery("from Article a where ${tagQuery} a.publishState = 'Archived' and a.deleted = false")
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
