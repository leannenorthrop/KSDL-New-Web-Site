package org.samye.dzong.london.news
import org.samye.dzong.london.community.Article

class NewsController {

    def articleService

    def index = {
        def max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        def articles = articleService.publishedNews(max)
        model:[ articles: articles, articlesTotal: articles.size() ]
    }
}
