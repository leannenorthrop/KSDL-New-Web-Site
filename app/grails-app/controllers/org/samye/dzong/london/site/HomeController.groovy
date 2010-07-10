package org.samye.dzong.london.site
import javax.servlet.http.Cookie
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.events.Event
import org.samye.dzong.london.ShiroRole
import org.samye.dzong.london.Setting

class HomeController {
    def articleService
	def flickrService

    def index = {
	    def ss = Setting.homeSlideshow().list()
		def images = flickrService.getSmallPhotoset(ss && ss.size() > 0 ? ss[0].value :'72157623174318636')
        def articles = Article.homeArticles("datePublished", "desc").list()
        def meditationArticles = articles.findAll { it.category == 'M'}
        def communityArticles = articles.findAll { it.category == 'C'}
        def buddhismArticles = articles.findAll{ it.category == 'B'}
        def wellbeingArticles = articles.findAll { it.category == 'W'}
        def newsArticles = articles.findAll { it.category == 'N'}
		def topArticles = articles.findAll { it.title == 'Home Page'}
        def events = Event.homePage('lastUpdated', 'asc').list()
		
        def model = [topArticles:topArticles, images: images, meditationArticles: meditationArticles, communityArticles: communityArticles, buddhismArticles: buddhismArticles, wellbeingArticles: wellbeingArticles, newsArticles: newsArticles,events:events]
		articleService.addHeadersAndKeywords(model,request,response)
		model
    }

    def list = {
	    def model = []
        if (params.tags) {
            def tags = params.tags.toLowerCase().split(",").toList()
            def articles = articleService.publishedByTags(tags)
            model =[articleInstanceList: articles, title: 'Articles With Tags ' + params.tags]
        } else {
            def publishedArticles = Article.findAllByPublishState("Published")
            model =[articleInstanceList: publishedArticles, title: "All Articles"]
        }
		articleService.addHeadersAndKeywords(model,request,response)
		model
    }

    def view = {
        def articleInstance = Article.get(params.id)
        if (!articleInstance) {
            flash.message = "Article not found with id ${params.id}"
            redirect(action: list)
        }
        else {
            def id = params.id;
            def model = [articleInstance: articleInstance, id: id]
			articleService.addHeadersAndKeywords(model,request,response)
			model
        }
    }

    def slideshow = {
	    def ss = Setting.homeSlideshow().list()	
		def album = flickrService.getPhotoset(ss && ss.size() > 0 ? ss[0].value :'72157623174318636')
        def model = [album:album]
		articleService.addHeadersAndKeywords(model,request,response)
		model
    }

    def aboutThisSite = {		
		def devUsers = [] as Set
		def devs = ShiroRole.findAllByName("Admin")
		devs = devs.findAll { item -> item.name != "Administrator"}
		devs.each { item -> 
			devUsers.addAll(item.users)
		}
				
		def users = [] as Set
		def roles = ShiroRole.findAllByNameNotEqual("Admin")
		roles = roles.findAll { item -> item.name != "Administrator"}
		roles.each { item -> 
			users.addAll(item.users)
		}
        def model = [developers: devUsers, users: users.sort()]
		articleService.addHeadersAndKeywords(model,request,response)
		model
    }

    def error = {
        model:[]
    }

    def feed = {
        model:[]
    }

    def calendars = {
        model:[]
    }

	def legal = {
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
}
