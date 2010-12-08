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
package org.samye.dzong.london.venue

import org.samye.dzong.london.cms.*

/**
 * Provides management actions for Samye venues/buildings/sites.
 * TODO: Internaliationise messages
 *
 * @author Leanne Northrop
 * @since  January 2010
 */
class VenueController extends CMSController {
    // the delete, save and update actions only accept POST requests
    static allowedMethods = [manage: 'GET',
                             save: 'PUT', 
                             update: 'POST', 
                             changeState: 'PUT', 
                             delete: 'GET',                             
                             view: 'PUT',                             
                             show: 'PUT',                                                          
                             edit: 'GET',                                                                                       
                             pre_publish: 'PUT',
                             preview: 'POST', 
                             updatePublished: 'PUT',
                             updateAndPublish: 'PUT',
                             onAddComment: ['POST','GET']]   
                             
    def static final ADMIN_ROLES = ['VenueManager', 'Administrator'] 
    def DOMAIN_NAME = 'Venue'
    
    /**
     * Although added via Bootstrap we re-add cms util methods here for
     * development purposes.
     */
    VenueController() {
        CMSUtil.addFinderMethods(this)
        CMSUtil.addCMSMethods(this)
    }
    
    def manage = {
        render(view: 'manage',params:[max:25],model:[venues:Venue.findAllByDeleted(false)])
    }
        
    def saveRoom(venue,params,onSave,saveMsg,onError,errMsg) {
        if (!venue){
            venue = new Venue()
        }
        if (versionCheck(params,venue)) {
            Venue.withTransaction { status ->
                try {
                    venue.author = currentUser() 
                    venue.properties = params
                    ['Addresses', 'Emails', 'TelephoneNumbers'].each { venue."bind${it}"(params) }

                    if (!venue.hasErrors() && venue.save()) {
                        flash.message = saveMsg
                        if (onSave == manage) {
                            flash.message = "${venue.name} updated"                            
                            redirect(action: onSave)                            
                        } else {
                            def msg = "Changes could not be saved because of the following:"	
                            rollback(status,msg,venue)
                            redirect(action: onSave,params:[id:params.id])
                        }
                    }
                    else {
                        def msg = "Can not save ${room.name} at this time"
                        rollback(status,msg,room,error)
                        handleError(errMsg,room,onError)
                    }
                } catch(error) {
                    def msg = "Can not save ${room.name} at this time"
                    rollback(status,msg,room,error)
                    redirect(action: manage,id:params.id)
                }
            }
        } else {
            notFound(manage)
        }
    } 
}
