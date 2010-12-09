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

package org.samye.dzong.london.community

import org.apache.shiro.SecurityUtils
import org.samye.dzong.london.cms.*

/*
 * Principal CMS content management url handler. Displays only content created
 * under the Content menu.
 * 
 * @author Leanne Northrop
 * @since  October 2009
 */
class ArticleController extends CMSController {
    def ADMIN_ROLES = ['Editor', 'Administrator']
    def DOMAIN_NAME = 'Article'
    
    /**
     * Although added via Bootstrap we re-add cms util methods here for
     * development purposes.
     */
    ArticleController() {
        CMSUtil.addFinderMethods(this)
        CMSUtil.addCMSMethods(this)
    }

    // the save and update actions only accept POST requests
    static allowedMethods = [manage: 'GET',
                             save: 'POST', 
                             update: 'POST', 
                             changeState: 'GET', 
                             delete: 'GET',                             
                             view: 'GET',                             
                             show: 'GET',                                                          
                             edit: 'GET',                                                                                       
                             pre_publish: 'GET',
                             preview: 'POST', 
                             updatePublished: 'POST',
                             updateAndPublish: 'POST',
                             onAddComment: ['POST','GET']]   

    def create = {
        def articleInstance = new Article()
        articleInstance.properties = params
        return ['articleInstance': articleInstance]
    }
    
    def saveArticle(article,params,onSave,saveMsg,onError,errMsg) {
        if (!article){
            article = new Article()
            article.author = currentUser()             
        }
        if (versionCheck(params,article)) {
            Article.withTransaction { status ->
                try {
                    article.properties = params

                    if (!article.hasErrors() && article.save()) {
                        if (params.tags) {
                            def tags = article.tags
                            def newtags = params.tags.split(',')
                            if (tags) {
                                tags.each {tag ->
                                    def found = newtags.find {newtag -> newtag == tag}
                                    if (!found) {
                                        article.removeTag(tag)
                                    }
                                }
                            } else {
                                newtags = newtags as List
                                article.setTags(newtags)
                            }
                        }
                
                        flash.message = saveMsg
                        if (onSave == manage) {
                            redirect(action: onSave)                            
                        } else {
                            redirect(action: onSave,params:[id:params.id])
                        }
                    }
                    else {
                        def msg = "Can not save ${article.title} at this time"
                        flash.message = msg
                        flash.isError = true
                        flash.args = [article]
                        flash.bean = article                              
                        rollback(status,msg,article)
                        render(view: onError, model: [articleInstance: article])
                    }
                } catch(error) {
                    def msg = "Can not save ${article.title} at this time"
                    log.error msg, error                          
                    rollback(status,msg,article,error)
                    redirect(action: manage,id:params.id)
                }
            }
        } else {
            redirect(action: manage)
        }
    }    
}
