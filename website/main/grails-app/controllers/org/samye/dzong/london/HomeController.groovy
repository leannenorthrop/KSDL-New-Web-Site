package org.samye.dzong.london
import javax.servlet.http.Cookie

class HomeController {
    def articleService

    def index = {
        def meditationArticles = articleService.publishedByTags(['meditation','home'], [max: 1])
        def communityArticles = articleService.publishedByTags(['community', 'home'], [max: 1])
        def buddhismArticles = articleService.publishedByTags(['buddhism', 'home'], [max: 1])
        def wellbeingArticles = articleService.publishedByTags(['wellbeing', 'home'], [max: 1])
        def newsArticles = articleService.publishedByTags(['news'], [max: 5])
        model:[meditationArticles: meditationArticles, communityArticles: communityArticles, buddhismArticles: buddhismArticles, wellbeingArticles: wellbeingArticles, newsArticles: newsArticles]
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
