package org.samye.dzong.london.site
import javax.servlet.http.Cookie
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.events.Event
import org.samye.dzong.london.ShiroRole
import org.samye.dzong.london.Setting
import groovy.xml.StreamingMarkupBuilder

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
		def topArticles = articles.findAll { it.category == 'H'}
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
	
	def siteMap = {
		withFormat {
			html {[]}
			xml {
				def urls = [buddhism: [actions:['home','list','events'],priority:[0.8,0.5,0.7],changeFreq:['weekly','weekly','daily']],
							aboutUs: [actions:['index','contactUs','visiting','lineage','teachers'], priority:[0.95,0.9,0.9,0.8,0.75], changeFreq:['weekly','monthly','monthly','monthly','monthly']],
							community: [actions:['home','list','events'],priority:[0.8,0.5,0.7],changeFreq:['weekly','weekly','daily']],
							event: [actions:['home','current','future','regular','list'],priority:[0.9,0.9,0.8,0.8,0.7],changeFreq:['daily','daily','daily','daily','daily']],
							meditation: [actions:['home','all','events'],priority:[0.8,0.5,0.7],changeFreq:['weekly','weekly','daily']],
							news: [actions:['home','current','archived'],priority:[0.8,0.5,0.7],changeFreq:['weekly','weekly','daily']],
							feed: [actions:['news','meditation','community','wellbeing','buddhism'],priority:[0.6,0.6,0.6,0.6,0.6],changeFreq:['daily','daily','daily','daily','daily']],
							home: [actions:['index','aboutThisSite','feed','calendars','legal','siteMap'],priority:[1,0.1,0.5,0.5,0.2,0.1],changeFreq:['weekly','yearly','yearly','yearly','yearly','daily']],
							wellbeing: [actions:['home','list','events'],priority:[0.8,0.5,0.7],changeFreq:['weekly','weekly','daily']]
				            ]
							
				//def publishableItems = [aboutUs: [actions:['room','venue','teacher'],ids[],priority:[],changeFreq:[],lastmod:[]]]
				def markup = {
					urlset(xmlns:"http://www.sitemaps.org/schemas/sitemap/0.9") {
						urls.each { controller,props ->
							def actions = props['actions']
							actions.eachWithIndex { value,index ->
								url {
									loc(createLink(controller:controller,action:value,absolute:true))
									['priority','changeFreq','lastmod'].each { key ->
										if (props[key] && props[key][index]) {
											"${key}"(props[key][index])
										}
									}
								}
							}
						}
					}
				}
				render contentType: "text/xml; charset=utf-8",markup
			}
		}
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
