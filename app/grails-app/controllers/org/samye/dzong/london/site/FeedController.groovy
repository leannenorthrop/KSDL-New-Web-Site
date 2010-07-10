package org.samye.dzong.london.site

import  org.samye.dzong.london.community.Article
import com.sun.syndication.feed.synd.SyndImageImpl
import org.samye.dzong.london.media.Image

class FeedController {
    def articleService

    def index = {}

    def news = {
        def articles = Article.newsArticles("datePublished","desc").list()
        render(feedType:"rss", feedVersion:"2.0") {
            title = message(code:'title') + ": " + message(code:'news')
            link = createLink(controller:'news',action:'feed',absolute:true)
            description = message(code:'rss.news.desc')
            articles.eachWithIndex { article,index ->
                entry(article?.title) {
                    link = createLink(controller:'news',action:'view',absolute:true,id:article.id)
                    author = article.author
                    publishedDate = article.datePublished
					content(type:'text/html') {	
						article.summary.encodeAsTextile()
                    }
                }
            }
        }
    }

    def meditation = {
        def articles = Article.allMeditationArticles("datePublished","desc").list()
        render(feedType:"rss", feedVersion:"2.0") {
            title = message(code:'title') + ": " + message(code:'meditation')
            link = createLink(controller:'meditation',action:'feed',absolute:true)
            description = message(code:'rss.meditation.desc')
            articles.eachWithIndex { article,index ->
                entry(article.title) {
                    link = createLink(controller:'meditation',action:'view',absolute:true,id:article.id)
                    author = article.author
                    publishedDate = article.datePublished
                    content(type:'text/html') {
                        article.summary.encodeAsTextile()
                    }
                }
            }
        }
    }

    def community = {
        def articles = Article.allCommunityArticles("datePublished","desc").list()
        render(feedType:"rss", feedVersion:"2.0") {
            title = message(code:'title') + ": " + message(code:'community')
            link = createLink(controller:'community',action:'feed',absolute:true)
            description = message(code:'rss.community.desc')
            articles.eachWithIndex { article,index ->
                entry(article.title) {
                    link = createLink(controller:'community',action:'view',absolute:true,id:article.id)
                    author = article.author
                    publishedDate = article.datePublished
                    content(type:'text/html') {
                        article.summary.encodeAsTextile()
                    }
                }
            }
        }
    }

    def wellbeing = {
        def articles = Article.allWellbeingArticles("datePublished","desc").list()
        render(feedType:"rss", feedVersion:"2.0") {
            title = message(code:'title') + ": " + message(code:'wellbeing')
            link = createLink(controller:'wellbeing',action:'feed',absolute:true)
            description = message(code:'rss.wellbeing.desc')
            articles.eachWithIndex { article,index ->
                entry(article.title) {
                    link = createLink(controller:'wellbeing',action:'view',absolute:true,id:article.id)
                    author = article.author
                    publishedDate = article.datePublished
                    content(type:'text/html') {
                        article.summary.encodeAsTextile()
                    }
                }
            }
        }
    }

    def buddhism = {
        def articles = Article.allBuddhismArticles("datePublished","desc").list()
        render(feedType:"rss", feedVersion:"2.0") {
            title = message(code:'title') + ": " + message(code:'buddhism')
            link = createLink(controller:'buddhism',action:'feed',absolute:true)
            description = message(code:'rss.buddhism.desc')
            articles.eachWithIndex { article,index ->
                entry(article.title) {
                    link = createLink(controller:'buddhism',action:'view',absolute:true,id:article.id)
                    author = article.author
                    publishedDate = article.datePublished
                    content(type:'text/html') {
                        article.summary.encodeAsTextile()
                    }
                }
            }
        }
    }
}
