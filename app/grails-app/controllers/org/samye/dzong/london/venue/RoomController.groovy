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
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
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
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
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
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
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
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
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
            redirect(action:manage)
        }
        else { return [ room : roomInstance ] }
    }

    def view = {
        def roomInstance = Room.get( params.id )

        if(!roomInstance) {
            flash.message = "Room not found with id ${params.id}"
            redirect(action:manage)
        }
        else { return [ room : roomInstance ] }
    }

    def edit = {
        def roomInstance = Room.get( params.id )

        if(!roomInstance) {
            flash.message = "Room not found with id ${params.id}"
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
            render(view:'create',model:[room:roomInstance])
        }
    }

    def delete = {
        def room = Room.get(params.id)
        if (room) {
            if (params.version) {
                def version = params.version.toLong()
                if (room.version > version) {
                    room.errors.rejectValue("version", "room.optimistic.locking.failure", "Another user has updated this Room's details while you were editing.")
                    redirect(action: manage)
                    return
                }
            }
            room.publishState = "Unpublished"
            room.deleted = true
            if (!room.hasErrors() && room.save()) {
                flash.message = "room.deleted"
                flash.args = [room];
                redirect(action: manage)
            }
            else {
                redirect(action: manage)
            }
        }
        else {
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
                render(view: 'edit', model: [room: room, id: params.id])
            }
        }
        else {
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
                redirect(action: manage)
            }
        }
        else {
            flash.message = "room.not.found"
            flash.args = params.id
            redirect(action: manage)
        }
    }
}
