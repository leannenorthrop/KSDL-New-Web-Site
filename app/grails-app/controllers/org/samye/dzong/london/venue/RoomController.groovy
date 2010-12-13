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

import org.apache.shiro.SecurityUtils
import org.samye.dzong.london.cms.*
import org.samye.dzong.london.venue.Venue
import org.samye.dzong.london.venue.Room
import org.samye.dzong.london.community.Article
/**
 * Handler for managing and displaying room venues.
 *
 * @author Leanne Northrop
 * @since  January, 2010
 */
class RoomController extends CMSController {
    
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
                             
    def static final ADMIN_ROLES = ['VenueManager', 'Administrator'] 
    def DOMAIN_NAME = 'Room'
    
    /**
     * Although added via Bootstrap we re-add cms util methods here for
     * development purposes.
     */
    RoomController() {
        CMSUtil.addFinderMethods(this)
        CMSUtil.addCMSMethods(this)
    }
        
    def create = {
        def roomInstance = new Room()
        roomInstance.properties = params
        return ['room':roomInstance]
    }

    def index = {
        forward(action:'home')
    }
    
    def home = {
        def model = [:]         
        addPublishedContent(["RoomHomeArticles", "RoomAllArticles","RoomFeaturedRooms"],model)        
        def venues = publishedVenues().'venues'
		model.put('venues',venues)
        render(view:'index',model:model)
    }    
    
    def information = {
        def model = viewArticle(params.id)       
        addPublishedContent(["RoomAllArticles"],model)        
        def venues = publishedVenues().'venues'
		model.put('venues',venues)
        render(view:'article',model:model)
    }
    
    def view = {
        def model = viewRoom(params.id)       
        addPublishedContent(["RoomAllArticles"],model)        
        def venues = publishedVenues().'venues'
		model.put('venues',venues)
        render(view:'view',model:model)
    } 
    
    def venue = {
        def model = viewVenue(params.id)       
        if (params.id) {
            flash.id = params.id
            flash.title = model.venue.name
        }        
        addPublishedContent(["RoomAllArticles"],model)        
        def venues = publishedVenues().'venues'
		model.put('venues',venues)
        render(view:'venue',model:model)        
    }        
    
    def saveRoom(room,params,onSave,saveMsg,onError,errMsg) {
        if (!room){
            room = new Room()
            room.author = currentUser()            
        }
        if (versionCheck(params,room)) {
            Room.withTransaction { status ->
                try { 
                    room.properties = params

                    if (!room.hasErrors() && room.save()) {
                        if (params.tags) {
                            def tags = room.tags
                            def newtags = params.tags.split(',')
                            if (tags) {
                                tags.each {tag ->
                                    def found = newtags.find {newtag -> newtag == tag}
                                    if (!found) {
                                        room.removeTag(tag)
                                    }
                                }
                            } else {
                                newtags = newtags as List
                                room.setTags(newtags)
                            }
                        }
                
                        flash.message = saveMsg
                        if (onSave == manage) {
                            redirect(action: onSave)                            
                        } else {
                            def msg = "Can not save ${room.name} at this time"
                            flash.message = msg
                            flash.isError = true
                            flash.args = [room]
                            flash.bean = room                                  
                            rollback(status,msg,room)
                            render(view: onError, model: [room: room])
                        }
                    }
                    else {
                        def msg = "Can not save ${room.name} at this time"
                        rollback(status,msg,room)
                        handleError(errMsg,room,onError)
                    }
                } catch(error) {
                    def msg = "Can not save ${room.name} at this time"
                    log.error msg,error
                    rollback(status,msg,room,error)
                    redirect(action: manage,id:params.id)
                }
            }
        } else {
            redirect(action: manage)
        }
    } 
    
    def addPublishedContent(contentList,model,params=[sort:'datePublished',order:'desc']) {
        contentList.each {
            model.putAll("findPublished${it}"(params))
        }        
    }    
}
