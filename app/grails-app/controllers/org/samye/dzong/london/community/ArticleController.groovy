/** *****************************************************************************
 * Copyright © 2010 Leanne Northrop
 *
 * This file is part of Samye Content Management System.
 *
 * Samye Content Management System is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * Samye Content Management System is distributed in the hope that it will be
 * useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Samye Content Management System.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * BT plc, hereby disclaims all copyright interest in the program
 * “Samye Content Management System” written by Leanne Northrop.
 ***************************************************************************** */

package org.samye.dzong.london.community

import org.apache.shiro.SecurityUtils

/**
 *
 */
class ArticleController {
    def userLookupService
    def articleService

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
    static allowedMethods = [save: 'POST', update: 'POST', changeState: 'GET', preview: 'POST', updatePublished: 'POST']

    def ajaxUnpublished = {
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

    def ajaxPublished = {
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

    def ajaxArchived = {
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

    def ajaxDeleted = {
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

    def ajaxReady = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)

        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
            model = articleService.ready(params)
        } else {
            model = articleService.userReady(params)
        }
        render(view: 'ready', model: model)
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

    def preview = {
        render(view: 'preview', model: [content: params.previewcontenttxt])
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
            articleInstance.title += "(Deleted)" 
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

    def afterPublishEdit = {
        def articleInstance = Article.get(params.id)

        if (!articleInstance) {
            flash.message = "Article not found with id ${params.id}"
            redirect(action: manage)
        }
        else {
            return render(view: 'alter', model: [articleInstance: articleInstance, id: params.id])
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

    def updatePublished = {
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

            articleInstance.properties = params
            if (!params.tags || params.tags == "") {
                flash.isError = true
                flash.message = "Please enter at least one label."
                return render(view: 'alter', model: [articleInstance: articleInstance], id: params.id)
            } else if (!articleInstance.hasErrors() && articleInstance.save()) {
                def tags = articleInstance.tags
                def newtags = params.tags.split(',')
                tags.each {tag ->
                    def found = newtags.find {newtag -> newtag == tag}
                    if (!found) {
                        articleInstance.removeTag(tag)
                    }
                }
                articleInstance.addTags(newtags)

                flash.message = "Article ${articleInstance.title} has been Updated"
                redirect(action: manage)
            }
            else {
                flash.isError = true
                flash.message = "Article ${articleInstance.title} could not be updated due to an internal error. Please try again."
                return render(view: 'afterPublishEdit', model: [articleInstance: articleInstance], id: params.id)
            }
        }
        else {
            flash.message = "Article not found with id ${params.id}"
            redirect(action: manage)
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
                def tags = articleInstance.tags
                def newtags = params.tags.split(',')
                tags.each {tag ->
                    def found = newtags.find {newtag -> newtag == tag}
                    if (!found) {
                        articleInstance.removeTag(tag)
                    }
                }
                articleInstance.addTags(newtags)
            }
            if (!articleInstance.hasErrors() && articleInstance.save()) {
                if (isFirstPublish) {
                }
                println "Published article. Publish date set to ${articleInstance.datePublished}"
                flash.message = "Article ${articleInstance.title} has been Published"
                redirect(action: manage)
            }
            else {
                flash.isError = true
                flash.message = "Article ${articleInstance.title} could not be ${params.publishState} due to an internal error. Please try again."
                return render(view: 'publish', model: [articleInstance: articleInstance], id: params.id)
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
                    flash.args = [articleInstance]
                    articleInstance.errors.rejectValue("version", "article.optimistic.locking.failure", "Another user has updated this Article while you were editing.")
                    render(view: 'edit', model: [articleInstance: articleInstance, id: params.id])
                    return
                }
            }
            articleInstance.properties = params
            if (!articleInstance.hasErrors() && articleInstance.save()) {
                flash.message = "Article ${articleInstance.title} updated"
                redirect(action: manage)
            }
            else {
                flash.isError = true
                flash.message = "article.update.error"
                flash.args = [articleInstance]
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
            flash.args = [articleInstance]
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
