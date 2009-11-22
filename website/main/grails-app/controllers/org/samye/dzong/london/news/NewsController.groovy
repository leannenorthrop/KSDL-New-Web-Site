package org.samye.dzong.london.news
import org.samye.dzong.london.community.Article

class NewsController {

    def articleService

    def index = {
        def articles = articleService.publishedNews()
        def archivedArticles =  articleService.archivedNews()
        model:[ articles: articles, archivedArticles: archivedArticles]
    }
}
