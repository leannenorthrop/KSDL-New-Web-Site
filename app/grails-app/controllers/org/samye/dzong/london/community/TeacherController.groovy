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

import org.samye.dzong.london.cms.*


/*
 * CMS content management url handler for both teacher and therapist information.
 * Displays only content management pages under the Teachers & Therapist navigation
 * menu.
 * 
 * TODO: Complete internationalization.
 *
 * @author Leanne Northrop
 * @since  26th January 2010, 19:00
 */
class TeacherController extends CMSController {
    // the delete, save and update actions only accept POST requests
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
                             
    def static final ADMIN_ROLES = ['EventOrganiser','Editor','Administrator'] 
    def DOMAIN_NAME = 'Teacher'
    
    /**
     * Although added via Bootstrap we re-add cms util methods here for
     * development purposes.
     */
    TeacherController() {
        CMSUtil.addFinderMethods(this)
        CMSUtil.addCMSMethods(this)
    }

    def create = {
        def teacher = new Teacher()
        teacher.properties = params
        return [teacher: teacher]
    }
       
    def saveTeacher(teacher,params,onSave,saveMsg,onError,errMsg) {
        if (!teacher){
            teacher = new Teacher()
        }
        if (versionCheck(params,teacher)) {
            Teacher.withTransaction { status ->
                try {
                    teacher.author = currentUser() 
                    teacher.properties = params

                    if (!teacher.hasErrors() && teacher.save()) {
                        if (params.tags) {
                            def tags = teacher.tags
                            def newtags = params.tags.split(',')
                            if (tags) {
                                tags.each {tag ->
                                    def found = newtags.find {newtag -> newtag == tag}
                                    if (!found) {
                                        teacher.removeTag(tag)
                                    }
                                }
                            } else {
                                newtags = newtags as List
                                teacher.setTags(newtags)
                            }
                        }
                
                        flash.message = saveMsg
                        if (onSave == manage) {
                            redirect(action: onSave)                            
                        } else {
                            def msg = "Can not save ${teacher} at this time"
                            rollback(status,msg,teacher,error)
                            redirect(action: onSave,params:[id:params.id])
                        }
                    }
                    else {
                        def msg = "Can not save ${teacher} at this time"
                        rollback(status,msg,teacher,error)
                        handleError(errMsg,teacher,onError)
                    }
                } catch(error) {
                    def msg = "Can not save ${teacher} at this time"
                    rollback(status,msg,teacher,error)
                    redirect(action: manage,id:params.id)
                }
            }
        } else {
            redirect(action: manage)
        }
    } 
}
