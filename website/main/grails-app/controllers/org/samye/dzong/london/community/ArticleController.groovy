package org.samye.dzong.london.community
import org.apache.shiro.SecurityUtils

class ArticleController {
    def userLookupService

    def index = {
        def publishedArticles = Article.findAllByPublishState("Published")
        // TODO put this back in params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        model:[ articleInstanceList: publishedArticles, articleInstanceTotal: publishedArticles.count() ]
    }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [save:'POST', update:'POST']

    def manage = {
        def articles = Article.findAllByDeleted(Boolean.FALSE)
        // TODO put this back in params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        render(view:'manage',model:[ articleInstanceList: articles, articleInstanceTotal: articles.count() ])
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
                    flash.message = "Article ${params.id} deleted"
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
            if(!articleInstance.hasErrors() && articleInstance.save()) {
                flash.message = "Article ${params.id} updated"
                redirect(action:manage,id:articleInstance.id)
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
            flash.message = "Article ${articleInstance.id} created"
            redirect(action:manage,id:articleInstance.id)
        }
        else {
            render(view:'edit',model:[articleInstance:articleInstance])
        }
    }
}
