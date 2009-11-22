package org.samye.dzong.london.community

class ArticleService {

    boolean transactional = true

    def publishedNews() {
        def criteria = Article.createCriteria()
        // TODO move max because we filter on line 16
        // And date published is this month
        // Need automatic archive for news articles?
        def articles = criteria.list(){
            and {
                eq('deleted', Boolean.FALSE)
                eq('publishState', "Published")
            }
        }
        def publishedNewsArticles = articles.findAll {article -> article.tags.contains("news")}
        publishedNewsArticles
    }

    def archivedNews(){
        def criteria = Article.createCriteria()
        // TODO move max because we filter on line 16
        def articles = criteria.list(){
            and {
                eq('deleted', Boolean.FALSE)
                eq('publishState', "Archived")
            }
        }
        def publishedNewsArticles = articles.findAll {article -> article.tags.contains("news")}
        publishedNewsArticles
    }
}