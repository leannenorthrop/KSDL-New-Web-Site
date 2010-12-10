/*
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
 */

package org.samye.dzong.london.cms

/**
 * CMS helper base class for controllers which manage CMS domain objects.
 *
 * @author Leanne Northrop
 * @since  12th November 2010, 18:09
 */
abstract class CMSController {
    
    def index = {
        forward(action:manage,params:[max:25])
    }
        
    def ajaxUnpublished = {
        flash.adminRoles = ADMIN_ROLES
        flash.isArticle = DOMAIN_NAME == 'Article'        
        render(view: 'unpublished',model:getModelForView('unpublished',params))
    }

    def ajaxPublished = {
        flash.adminRoles = ADMIN_ROLES        
        flash.isArticle = DOMAIN_NAME == 'Article'        
        render(view: 'published',model:getModelForView('published',params))
    }

    def ajaxArchived = {
        flash.adminRoles = ADMIN_ROLES        
        flash.isArticle = DOMAIN_NAME == 'Article'        
        render(view: 'archived',model:getModelForView('archived',params))
    }

    def ajaxReady = {
        flash.adminRoles = ADMIN_ROLES        
        flash.isArticle = DOMAIN_NAME == 'Article'        
        render(view: 'ready',model:getModelForView('ready',params))
    }

    def ajaxDeleted = {
        flash.adminRoles = ADMIN_ROLES     
        flash.isArticle = DOMAIN_NAME == 'Article'           
        render(view: 'deleted',model:getModelForView('deleted',params))
    }
    
    def changeState = {
        def publishable = Publishable.get(params.id)
        if (publishable) {
            if (!versionCheck(params,publishable)) {
                redirect(action: manage)
                return
            } else {
                Publishable.withTransaction { status ->
                    def isFirstPublish = publishable.publishState != 'Published' && params.state == 'Published'
                    if (isFirstPublish) {
                        publishable.datePublished = new Date()
                    }
                    publishable.publishState = params.state
                    publishable.deleted = false
                    if (!publishable.hasErrors() && publishable.save()) {
                        flash.message = "${publishable} has been moved to ${params.state}"
                        render(text:flash.message,contentType:"text/plain",encoding:"UTF-8")
                    }
                    else {
                        flash.isError = true           
                        flash.bean = publishable
                        flash.args = [publishable]     
                        flash.message = "${publishable} could not be moved to ${params.state} due to the following errors. Please correct the errors and try again."
                        rollback(status,flash.message,publishable)
                        render(text:flash.message,contentType:"text/plain",encoding:"UTF-8")
                    }
                }
            }
        }
        else {
            render(text:"Sorry could not find it! Bad link?",contentType:"text/plain",encoding:"UTF-8")
        }
    }    
    
    def delete = {
        def publishable = Publishable.get(params.id)
        if (publishable) {
            if (versionCheck(params,publishable)){
                Publishable.withTransaction { status ->
                    try {
                        publishable.publishState = "Unpublished"
                        publishable.deleted = true
                        try { publishable.name += ' (Deleted)' } catch(error) { publishable.title += ' (Deleted)'}
                        if (!publishable.hasErrors() && publishable.save()) {
                            flash.message = "${publishable} has been deleted"
                            render(text:flash.message,contentType:"text/plain",encoding:"UTF-8")
                        }
                        else {
                            def msg = "Can not delete ${publishable} at this time"
                            rollback(status,msg,publishable)
                            render(text:flash.message,contentType:"text/plain",encoding:"UTF-8")
                        }
                    } catch (error) {
                        def msg = "Can not delete ${publishable} at this time"
                        rollback(status,msg,publishable,error)
                        render(text:flash.message,contentType:"text/plain",encoding:"UTF-8")
                    }
                }
            } else {
                render(text:"${publishable} has been changed by another user.",contentType:"text/plain",encoding:"UTF-8")
            }
        }
        else {
            render(text:"Can not find the item you were looking for.",contentType:"text/plain",encoding:"UTF-8")
        }
    }   
    
    def manage = {
        flash.adminRoles = ADMIN_ROLES        
        flash.isArticle = DOMAIN_NAME == 'Article'
        render(view: 'manage',params:[max:25])
    }


    def preview = {
        render(view: 'preview', model: [content: params.previewcontenttxt])
    }
        
    def view = {
        "view${DOMAIN_NAME}"(params.id)
    }
        
    def show = {
        "view${DOMAIN_NAME}"(params.id)
    }     
    
    def edit = {
        def publishable = Publishable.get(params.id)
        if (!publishable) {
            notFound(manage)
        }
        else {
            if (!flash.message) {
                flash.message = "edit.${DOMAIN_NAME.toLowerCase()}" 
            }
            [publishableInstance: publishable, id: params.id]
        }
    }    
    
    def afterPublishEdit = {
        def publishable = Publishable.get(params.id)
        if (!publishable) {
            notFound(manage)
        }
        else {
            if (!flash.message) {
                flash.message = "edit.${DOMAIN_NAME.toLowerCase()}" 
            }
            return render(view: 'alter', model: [publishableInstance: publishable, id: params.id])
        }
    }
        
    def pre_publish = {
        def publishable = Publishable.get(params.id)
        if (!publishable) {
            notFound(manage)
        }
        else {
            render(view: 'publish', model: [publishableInstance: publishable], id: params.id)
        }
    }    
    
    def onAddComment = { comment ->
        log.debug comment
    } 
    
    def save = {
        def okMsg = "New ${DOMAIN_NAME.toLowerCase()} has been created."
        def errMsg = "${DOMAIN_NAME.toLowerCase()}.update.error"
        this."save${DOMAIN_NAME}"(null,params,manage,okMsg,'create',errMsg)
    }
        
    def update = {
        def publishable = Publishable.get(params.id)
        if (publishable) {
            def okMsg = "${publishable} has been updated."
            def errMsg = "${DOMAIN_NAME.toLowerCase()}.update.error"
            this."save${DOMAIN_NAME}"(publishable,params,manage,okMsg,edit,errMsg)
        }
        else {
            notFound(manage)
        }
    }   
    
    def publish = {
        def publishable = Publishable.get(params.id)
        if (publishable) {
            params.publishState = 'Published'
            params.datePublished = new Date()
            def okMsg = "${publishable} has been Published"
            def errMsg = "${publishable} could not be ${params.state} due to errors. Please correct the errors and try again."
            this."save${DOMAIN_NAME}"(publishable,params,manage,okMsg,pre_publish,errMsg)
        }
        else {
            notFound(manage)
        }
    } 
    
    def updatePublished = {
        def publishable = Publishable.get(params.id)
        if (publishable) {
            def okMsg = "${publishable} has been updated."
            def errMsg = "${DOMAIN_NAME.toLowerCase()}.update.error"
            this."save${DOMAIN_NAME}"(publishable,params,manage,okMsg,alter,errMsg)
        }
        else {
            notFound(manage)
        }        
    }    
    
    def updateAndPublish = {
        def publishable = Publishable.get(params.id)
        if (publishable) {
            def okMsg = "${publishable} has been updated."
            def errMsg = "${DOMAIN_NAME.toLowerCase()}.update.error"
            this."save${DOMAIN_NAME}"(publishable,params,pre_publish,okMsg,edit,errMsg)
        }
        else {
            notFound(manage)
        }    
    }          
}

