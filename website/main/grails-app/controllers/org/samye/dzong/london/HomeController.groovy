package org.samye.dzong.london
import javax.servlet.http.Cookie
import org.samye.dzong.london.community.Article

class HomeController {
    def articleService

    def index = {
        def articles = Article.homePageArticles('lastUpdated', 'asc').list()
        println "Found ${articles} home articles"

        def meditationArticles = articles.find { it.category == 'M'}
        def communityArticles = articles.find { it.category == 'C'}
        def buddhismArticles = articles.find { it.category == 'B'}
        def wellbeingArticles = articles.find { it.category == 'W'}
        def newsArticles = articleService.publishedByTags(['news'], [max: 5])
        model:[meditationArticles: meditationArticles, communityArticles: communityArticles, buddhismArticles: buddhismArticles, wellbeingArticles: wellbeingArticles, newsArticles: newsArticles]
    }

    def list = {
        if (params.tags) {
            def tags = params.tags.toLowerCase().split(",").toList()
            def articles = articleService.publishedByTags(tags)
            model: [articleInstanceList: articles, title: 'Articles With Tags ' + params.tags]
        } else {
            def publishedArticles = Article.findAllByPublishState("Published")
            model: [articleInstanceList: publishedArticles, title: "All Articles"]
        }
    }

    def view = {
        def articleInstance = Article.get(params.id)
        if (!articleInstance) {
            flash.message = "Article not found with id ${params.id}"
            redirect(action: list)
        }
        else {
            def id = params.id;
            return [articleInstance: articleInstance, id: id]
        }
    }

    def aboutUs = {
        model: []
    }

    def contactUs = {
        model: []
    }

    def help = {
        model: []
    }

    def siteMap = {
        model: []
    }

    def aboutThisSite = {
        model: []
    }

    def changeCssTheme = {
        model:[]
    }

    def error = {
        model:[]
    }

    def internalError = {
        render(view:internalError)
    }

    def notFound = {
        flash.message = "not.found"
        flash.default = "Oops! Did we lose a page?"
        render(notFound)
    }

    def setCSSTheme = {
        def themeCookie = new Cookie("cssTheme",params.id)
        themeCookie.setMaxAge(60*60*24*365)
        themeCookie.setPath("/")
        response.addCookie(themeCookie)
        redirect(uri: '/')
    }
}
