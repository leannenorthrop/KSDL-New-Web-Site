package org.samye.dzong.london.community

import org.apache.shiro.SecurityUtils
import com.burtbeckwith.grails.twitter.service.*

class ArticleController {
    def userLookupService
    def articleService
    def twitterService

    def index = {
        if (params.tags) {
            def tags = params.tags.toLowerCase().split(",").toList()
            def articles = articleService.publishedByTags(tags)
            model: [articleInstanceList: articles, title: 'Articles With Tags ' + params.tags, auditLogs: auditDetails]
        } else {
            def publishedArticles = Article.findAllByPublishState("Published")
            model: [articleInstanceList: publishedArticles, title: "All Articles", auditLogs: auditDetails]
        }
    }

    // the save and update actions only accept POST requests
    static allowedMethods = [save: 'POST', update: 'POST',changeState: 'GET']

    def ajaxUnpublishedArticles = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        println params
        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
            model = articleService.unpublished(params)
        } else {
            model = articleService.userUnpublished(params)
        }
        render(view: 'unpublished', model: model)
    }

    def ajaxPublishedArticles = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)

        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
            model = articleService.published(params)
        } else {
            model = articleService.userPublished(params)
        }
        render(view: 'published', model: model)
    }

    def ajaxArchivedArticles = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)

        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
            model = articleService.archived(params)
        } else {
            model = articleService.userArchived(params)
        }
        render(view: 'archived', model: model)
    }

    def ajaxDeletedArticles = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)

        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
            model = articleService.deleted(params)
        } else {
            model = articleService.userDeleted(params)
        }
        render(view: 'deleted', model: model)
    }

    def manage = {
        render(view: 'manage')
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

    def show = {
        def articleInstance = Article.get(params.id)

        if (!articleInstance) {
            flash.message = "Article not found with id ${params.id}"
            redirect(action: list)
        }
        else {
            return [articleInstance: articleInstance, id: params.id]
        }
    }

    def delete = {
        def articleInstance = Article.get(params.id)
        if (articleInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (articleInstance.version > version) {
                    articleInstance.errors.rejectValue("version", "article.optimistic.locking.failure", "Another user has updated this Article while you were editing.")
                    redirect(action: manage)
                    return
                }
            }
            articleInstance.publishState = "Unpublished"
            articleInstance.deleted = true
            if (!articleInstance.hasErrors() && articleInstance.save()) {
                flash.message = "Article ${articleInstance.title} deleted"
                redirect(action: manage)
            }
            else {
                redirect(action: manage)
            }
        }
        else {
            flash.message = "Article not found with id ${params.id}"
            redirect(action: manage)
        }
    }

    def edit = {
        def articleInstance = Article.get(params.id)

        if (!articleInstance) {
            flash.message = "Article not found with id ${params.id}"
            redirect(action: manage)
        }
        else {
            return [articleInstance: articleInstance, id: params.id]
        }
    }

    def pre_publish = {
        def articleInstance = Article.get(params.id)

        if (!articleInstance) {
            flash.message = "Article not found with id ${params.id}"
            redirect(action: manage)
        }
        else {
            return render(view: 'publish', model: [articleInstance: articleInstance], id: params.id)
        }
    }

    def publish = {
        def articleInstance = Article.get(params.id)
        if (articleInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (articleInstance.version > version) {
                    flash.message = "Article ${articleInstance.title} was being edited - please try again."
                    redirect(action: manage)
                    return
                }
            }

            def isFirstPublish = articleInstance.publishState != 'Published'
            if (isFirstPublish) {
                articleInstance.datePublished = new Date()
            }
            println "first publish? ${isFirstPublish} published on ${articleInstance.datePublished}"

            articleInstance.properties = params
            articleInstance.publishState = "Published"
            if (params.tags) {
                articleInstance.parseTags(params.tags)
            }

            if (!articleInstance.hasErrors() && articleInstance.save()) {
                if (isFirstPublish) {
                    try {

                        twitterService.setStatus("We've just published ${articleInstance.title}.'", [username: 'lsdci', password: 'change!t']);
                    } catch (error) {

                    }
                }
                println "Published article. Publish date set to ${articleInstance.datePublished}"
                flash.message = "Article ${articleInstance.title} has been Published"
                redirect(action: manage)
            }
            else {
                flash.message = "Article ${articleInstance.title} could not be ${params.state} due to an internal error. Please try again."
                redirect(action: pre_publish, id: params.id)
            }
        }
        else {
            flash.message = "Article not found with id ${params.id}"
            redirect(action: manage)
        }
    }

    def update = {
        def articleInstance = Article.get(params.id)
        if (articleInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (articleInstance.version > version) {
                    flash.isError = true
                    flash.message = "article.update.error"
                    articleInstance.errors.rejectValue("version", "article.optimistic.locking.failure", "Another user has updated this Article while you were editing.")
                    render(view: 'edit', model: [articleInstance: articleInstance, id: params.id])
                    return
                }
            }
            articleInstance.properties = params
            if (params.tags) {
                articleInstance.parseTags(params.tags)
            }
            if (!articleInstance.hasErrors() && articleInstance.save()) {
                flash.message = "Article ${articleInstance.title} updated"
                redirect(action: manage)
            }
            else {
                flash.isError = true
                flash.message = "article.update.error"
                render(view: 'edit', model: [articleInstance: articleInstance, id: params.id])
            }
        }
        else {
            flash.message = "Article not found with id ${params.id}"
            redirect(action: manage)
        }
    }

    def create = {
        def articleInstance = new Article()
        articleInstance.properties = params
        return ['articleInstance': articleInstance]
    }

    def save = {
        def articleInstance = new Article(params)
//        def user = ShiroUser.read(1)
//        def users = ShiroUser.findAllByUsername(SecurityUtils.subject.principal.toString())
        articleInstance.author = userLookupService.lookup()
        if (!articleInstance.hasErrors() && articleInstance.save()) {
            flash.message = "Article ${articleInstance.title} created"
            redirect(action: manage)
        }
        else {
            flash.isError = true
            flash.message = "article.update.error"
            render(view: 'create', model: [articleInstance: articleInstance, id: params.id])
        }
    }

    def changeState = {
        def articleInstance = Article.get(params.id)
        if (articleInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (articleInstance.version > version) {
                    flash.message = "Article ${articleInstance.title} was being edited - please try again."
                    redirect(action: manage)
                    return
                }
            }
            def isFirstPublish = articleInstance.publishState != 'Published' && params.state == 'Published'
            if (isFirstPublish) {
                articleInstance.datePublished = new Date()
            }
            articleInstance.publishState = params.state
            articleInstance.deleted = false
            if (!articleInstance.hasErrors() && articleInstance.save()) {
                if (isFirstPublish) {
                    try {
                        twitterService.setStatus("We've just published ${articleInstance}.'", [username: 'lsdci', password: 'change!t']);
                    } catch (error) {

                    }
                }
                flash.message = "Article ${articleInstance.title} has been moved to ${articleInstance.publishState}"
                redirect(action: manage)
            }
            else {
                flash.message = "Article ${articleInstance.title} could not be ${params.state} due to an internal error. Please try again."
                redirect(action: manage)
            }
        }
        else {
            flash.message = "Article not found with id ${params.id}"
            redirect(action: manage)
        }
    }
}
