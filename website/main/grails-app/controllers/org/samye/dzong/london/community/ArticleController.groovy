package org.samye.dzong.london.community
import org.apache.shiro.SecurityUtils

class ArticleController {
    def userLookupService
    def articleService

    def index = {
        if (params.tags){
            def tags = params.tags.toLowerCase().split(",").toList()
            def articles = articleService.publishedByTags(tags)
            model:[ articleInstanceList: articles, title: 'Articles With Tags ' + params.tags]
        } else {
            def publishedArticles = Article.findAllByPublishState("Published", [max:max])
            model:[ articleInstanceList: publishedArticles, title: "All Articles" ]
        }
    }

    def archived = {
        def max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        def articles = Article.findAllByPublishState("Archived", [max:max])
        render(view:'manage',model:[ articleInstanceList: articles, articleInstanceTotal: articles.count() ])
    }

    def all = {
        def max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        def articles = Article.findAllByDeleted(Boolean.FALSE, [max:max])
        render(view:'manage',model:[ articleInstanceList: articles, articleInstanceTotal: articles.count() ])
    }

    def deleted = {
        def max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        def articles = Article.findAllByDeleted(Boolean.TRUE, [max:max])
        render(view:'manage',model:[ articleInstanceList: articles, articleInstanceTotal: articles.count() ])
    }

    def everything = {
        def max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        def articles = Article.list(max:max)
        render(view:'manage',model:[ articleInstanceList: articles, articleInstanceTotal: articles.count() ])
    }

    // the save and update actions only accept POST requests
    static allowedMethods = [save:'POST', update:'POST']

    def manage = {
        def max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        def inList = ['Editor','Administrator']
        def articles
        def total
        if (SecurityUtils.subject.hasRoles(inList).any())  {
            articles = Article.findAllByDeleted(Boolean.FALSE,params)
            total = Article.findAllByDeleted(Boolean.FALSE).size()
        } else {
            def criteria = Article.createCriteria()
            articles = criteria.list(){
                and {
                    eq('deleted', Boolean.FALSE)
                    author {
                        eq('username', userLookupService.username())
                    }
                }
            }
            total = articles.size()
            def criteria1 = Article.createCriteria()
            articles = criteria1.list(params){
                and {
                    eq('deleted', Boolean.FALSE)
                    author {
                        eq('username', userLookupService.username())
                    }
                }
            }
        }
        render(view:'manage',model:[ articleInstanceList: articles, articleTotal: total ])
    }

    def view = {
        def articleInstance = Article.get( params.id )

        if(!articleInstance) {
            flash.message = "Article not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ articleInstance : articleInstance ] }
    }

    def show = {
        def articleInstance = Article.get( params.id )

        if(!articleInstance) {
            flash.message = "Article not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ articleInstance : articleInstance ] }
    }

    def delete = {
         def articleInstance = Article.get( params.id )
            if(articleInstance) {
                if(params.version) {
                    def version = params.version.toLong()
                    if(articleInstance.version > version) {
                        articleInstance.errors.rejectValue("version", "article.optimistic.locking.failure", "Another user has updated this Article while you were editing.")
                        redirect(action:manage)
                        return
                    }
                }
                articleInstance.publishState="Unpublished"
                articleInstance.deleted = true
                if(!articleInstance.hasErrors() && articleInstance.save()) {
                    flash.message = "Article ${articleInstance.title} deleted"
                    redirect(action:manage)
                }
                else {
                    redirect(action:manage)
                }
            }
            else {
                flash.message = "Article not found with id ${params.id}"
                redirect(action:manage)
            }
    }

    def edit = {
        def articleInstance = Article.get( params.id )

        if(!articleInstance) {
            flash.message = "Article not found with id ${params.id}"
            redirect(action:manage)
        }
        else {
            return [ articleInstance : articleInstance ]
        }
    }

    def pre_publish = {
        def articleInstance = Article.get( params.id )

        if(!articleInstance) {
            flash.message = "Article not found with id ${params.id}"
            redirect(action:manage)
        }
        else {
            return render(view:'publish',model:[ articleInstance : articleInstance ])
        }
    }

    def publish = {
        def articleInstance = Article.get( params.id )
        if(articleInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(articleInstance.version > version) {
                    flash.message = "Article ${articleInstance.title} was being edited - please try again."
                    redirect(action:manage,id:articleInstance.id)
                    return
                }
            }
            articleInstance.publishState = "Published"
            if (params.tags) {
                articleInstance.parseTags(params.tags)
            }
            if(!articleInstance.hasErrors() && articleInstance.save()) {
                flash.message = "Article ${articleInstance.title} has been Published"
                redirect(action:manage,id:articleInstance.id)
            }
            else {
                flash.message = "Article ${articleInstance.title} could not be ${params.state} due to an internal error. Please try again."
                redirect(action:manage,id:articleInstance.id)
            }
        }
        else {
            flash.message = "Article not found with id ${params.id}"
            redirect(action:manage)
        }
    }

    def update = {
        def articleInstance = Article.get( params.id )
        if(articleInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(articleInstance.version > version) {
                    articleInstance.errors.rejectValue("version", "article.optimistic.locking.failure", "Another user has updated this Article while you were editing.")
                    render(view:'edit',model:[articleInstance:articleInstance])
                    return
                }
            }
            articleInstance.properties = params
            if (params.tags) {
                articleInstance.parseTags(params.tags)
            }
            if(!articleInstance.hasErrors() && articleInstance.save()) {
                flash.message = "Article ${articleInstance.title} updated"
                redirect(action:manage)
            }
            else {
                render(view:'edit',model:[articleInstance:articleInstance])
            }
        }
        else {
            flash.message = "Article not found with id ${params.id}"
            redirect(action:manage)
        }
    }

    def create = {
        def articleInstance = new Article()
        articleInstance.properties = params
        return ['articleInstance':articleInstance]
    }

    def save = {
        def articleInstance = new Article(params)
//        def user = ShiroUser.read(1)
//        def users = ShiroUser.findAllByUsername(SecurityUtils.subject.principal.toString())
        articleInstance.author = userLookupService.lookup()
        if(!articleInstance.hasErrors() && articleInstance.save()) {
            flash.message = "Article ${articleInstance.title} created"
            redirect(action:manage,id:articleInstance.id)
        }
        else {
            render(view:'edit',model:[articleInstance:articleInstance])
        }
    }

    def changeState = {
        def articleInstance = Article.get( params.id )
        if(articleInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(articleInstance.version > version) {
                    flash.message = "Article ${articleInstance.title} was being edited - please try again."
                    redirect(action:manage,id:articleInstance.id)
                    return
                }
            }
            articleInstance.publishState = params.state
            if(!articleInstance.hasErrors() && articleInstance.save()) {
                flash.message = "Article ${articleInstance.title} has been ${articleInstance.publishState}"
                redirect(action:manage,id:articleInstance.id)
            }
            else {
                flash.message = "Article ${articleInstance.title} could not be ${params.state} due to an internal error. Please try again."
                redirect(action:manage,id:articleInstance.id)
            }
        }
        else {
            flash.message = "Article not found with id ${params.id}"
            redirect(action:manage)
        }
    }
}
