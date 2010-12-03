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

class RoomController {
    def userLookupService
	def roomService
	
    def index = { }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [save:'POST', update:'POST', changeState: 'GET']

	def ajaxUnpublishedRooms = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Admin']).any()) {
            model = roomService.unpublished(params)
        } else {
            model = roomService.userUnpublished(params)
        }
        render(view: 'unpublished', model: model)
    }

    def ajaxPublishedRooms = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)

        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Admin']).any()) {
            model = roomService.published(params)
        } else {
            model = roomService.userPublished(params)
        }
        render(view: 'published', model: model)
    }

    def ajaxArchivedRooms = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)

        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Admin']).any()) {
            model = roomService.archived(params)
        } else {
            model = roomService.userArchived(params)
        }
        render(view: 'archived', model: model)
    }

    def ajaxDeletedRooms = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)

        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Admin']).any()) {
            model = roomService.deleted(params)
        } else {
            model = roomService.userDeleted(params)
        }
        render(view: 'deleted', model: model)
    }

    def manage = {
        render(view: 'manage')
    }

    def show = {
        def roomInstance = Room.get( params.id )

        if(!roomInstance) {
            flash.message = "Room not found with id ${params.id}"
            flash.isError = true
            redirect(action:manage)
        }
        else { return [ room : roomInstance ] }
    }

    def view = {
        def roomInstance = Room.get( params.id )

        if(!roomInstance) {
            flash.message = "Room not found with id ${params.id}"
            flash.isError = true            
            redirect(action:manage)
        }
        else { return [ room : roomInstance ] }
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
		roomInstance.author = userLookupService.lookup()
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
