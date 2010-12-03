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

/**
 * Handler for managing and displaying room venues.
 *
 * @author Leanne Northrop
 * @since  2010
 */
class RoomController extends CMSController {
    def index = { 
        redirect(action:manage)
    }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [save:'POST', update:'POST', changeState: 'GET']
    def static final ADMIN_ROLES = ['Editor', 'Administrator'] 

    def manage = {
        render(view: 'manage')
    }

    def show = {
        viewRoom( params.id )
    }

    def view = {
        viewRoom( params.id )
    }

    def edit = {
        def roomInstance = Room.get( params.id )

        if(!roomInstance) {
            flash.message = "Room not found with id ${params.id}"
            flash.isError = true            
            redirect(action:manage)
        }
        else {
            return [ room : roomInstance ]
        }
    }

    def create = {
        def roomInstance = new Room()
        roomInstance.properties = params
        return ['room':roomInstance]
    }

    def save = {
        def roomInstance = new Room(params)
		roomInstance.author = currentUser() 
        if(!roomInstance.hasErrors() && roomInstance.save()) {
            flash.message = "Room ${roomInstance.name} created"
            redirect(action:manage)
        }
        else {
			flash.isError = true
			flash.message = "Could not create room due to the following errors:"
            flash.args = [roomInstance]			
            flash.bean = roomInstance
            render(view:'create',model:[room:roomInstance])
        }
    }

    def delete = {
        def room = Room.get(params.id)
        if (room) {
            if (params.version) {
                def version = params.version.toLong()
                if (room.version > version) {
                    flash.isError = true                    
                    room.errors.rejectValue("version", "room.optimistic.locking.failure", "Another user has updated this Room's details while you were editing.")
                    redirect(action: manage)
                    return
                }
            }
            room.publishState = "Unpublished"
            room.deleted = true
            room.name += " (Deleted)"             
            if (!room.hasErrors() && room.save()) {
                flash.message = "room.deleted"
                flash.args = [room];                
                redirect(action: manage)
            }
            else {
                flash.isError = true                  
                flash.bean = room
                redirect(action: manage)
            }
        }
        else {
            flash.isError = true            
            flash.message = "room.not.found"
            flash.args = params.id
            redirect(action: manage)
        }
    }

    def update = {
        def room = Room.get(params.id)
        if (room) {
            if (params.version) {
                def version = params.version.toLong()
                if (room.version > version) {
                    flash.isError = true
                    teacher.errors.rejectValue("version", "room.optimistic.locking.failure", "Another user has updated this Room's details while you were editing.")
                    render(view: 'edit', model: [room: room, id: params.id])
                    return
                }
            }
            room.properties = params
            if (!room.hasErrors() && room.save()) {
                flash.message = "room.updated"
                flash.args = [room]
                redirect(action: manage)
            }
            else {
                flash.isError = true
                flash.message = "room.update.error"
                flash.args = [room]
                flash.bean = room
                render(view: 'edit', model: [room: room, id: params.id])
            }
        }
        else {
            flash.isError = true
            flash.message = "room.not.found"
            flash.args = params.id
            redirect(action: manage)
        }
    }

    def changeState = {
        def room = Room.get(params.id)
        if (room) {
            if (params.version) {
                def version = params.version.toLong()
                if (room.version > version) {
                    flash.isError = true
                    room.errors.rejectValue("version", "room.optimistic.locking.failure", "Another user has updated this Room's details while you were editing.")
                    redirect(action: manage)
                    return
                }
            }
            def isFirstPublish = room.publishState != 'Published' && params.state == 'Published'
            if (isFirstPublish) {
                room.datePublished = new Date()
            }
            room.publishState = params.state
            room.deleted = false
            if (!room.hasErrors() && room.save()) {
                flash.message = "${room.name} has been moved to ${room.publishState}"
                redirect(action: manage)
            }
            else {
                flash.isError = true
                flash.bean = room
                redirect(action: manage)
            }
        }
        else {
            flash.message = "room.not.found"
            flash.args = params.id
            flash.isError = true
            redirect(action: manage)
        }
    }
}
