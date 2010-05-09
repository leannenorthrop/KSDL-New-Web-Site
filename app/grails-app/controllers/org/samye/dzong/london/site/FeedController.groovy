package org.samye.dzong.london.site

class FeedController {
    def articleService

    def index = {}

    def news = {
        def articles = articleService.publishedByTags(['news'])
        render(feedType:"rss", feedVersion:"2.0") {
            title = message(code:'title') + ": " + message(code:'news')
            link = createLink(controller:'news',action:'feed',absolute:true)
            description = message(code:'rss.news.desc')
            articles.eachWithIndex { article,index ->
                entry(article.title) {
                    link = createLink(controller:'news',action:'view',absolute:true,id:article.id)
                    author = article.author.username
                    publishedDate = article.datePublished
                    content(type:'text/html') {
                        article.summary
                    }
                }
            }
        }
    }

    def meditation = {
        def articles = articleService.publishedByTags(['meditation'])
        render(feedType:"rss", feedVersion:"2.0") {
            title = message(code:'title') + ": " + message(code:'meditation')
            link = createLink(controller:'meditation',action:'feed',absolute:true)
            description = message(code:'rss.meditation.desc')
            articles.eachWithIndex { article,index ->
                entry(article.title) {
                    link = createLink(controller:'meditation',action:'view',absolute:true,id:article.id)
                    author = article.author.username
                    publishedDate = article.datePublished
                    content(type:'text/html') {
                        article.summary
                    }
                }
            }
        }
    }

    def community = {
        def articles = articleService.publishedByTags(['community'])
        render(feedType:"rss", feedVersion:"2.0") {
            title = message(code:'title') + ": " + message(code:'community')
            link = createLink(controller:'community',action:'feed',absolute:true)
            description = message(code:'rss.community.desc')
            articles.eachWithIndex { article,index ->
                entry(article.title) {
                    link = createLink(controller:'community',action:'view',absolute:true,id:article.id)
                    author = article.author.username
                    publishedDate = article.datePublished
                    content(type:'text/html') {
                        article.summary
                    }
                }
            }
        }
    }

    def wellbeing = {
        def articles = articleService.publishedByTags(['wellbeing'])
        render(feedType:"rss", feedVersion:"2.0") {
            title = message(code:'title') + ": " + message(code:'wellbeing')
            link = createLink(controller:'wellbeing',action:'feed',absolute:true)
            description = message(code:'rss.wellbeing.desc')
            articles.eachWithIndex { article,index ->
                entry(article.title) {
                    link = createLink(controller:'wellbeing',action:'view',absolute:true,id:article.id)
                    author = article.author.username
                    publishedDate = article.datePublished
                    content(type:'text/html') {
                        article.summary
                    }
                }
            }
        }
    }

    def buddhism = {
        def articles = articleService.publishedByTags(['buddhism'])
        render(feedType:"rss", feedVersion:"2.0") {
            title = message(code:'title') + ": " + message(code:'buddhism')
            link = createLink(controller:'buddhism',action:'feed',absolute:true)
            description = message(code:'rss.buddhism.desc')
            articles.eachWithIndex { article,index ->
                entry(article.title) {
                    link = createLink(controller:'buddhism',action:'view',absolute:true,id:article.id)
                    author = article.author.username
                    publishedDate = article.datePublished
                    content(type:'text/html') {
                        article.summary
                    }
                }
            }
        }
    }
}
