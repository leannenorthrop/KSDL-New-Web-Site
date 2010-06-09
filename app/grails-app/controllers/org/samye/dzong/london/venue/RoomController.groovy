

package org.samye.dzong.london.venue

class RoomController {
    
    def index = { }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def manage = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ roomInstanceList: Room.list( params ), roomInstanceTotal: Room.count() ]
    }

    def show = {
        def roomInstance = Room.get( params.id )

        if(!roomInstance) {
            flash.message = "Room not found with id ${params.id}"
            redirect(action:manage)
        }
        else { return [ roomInstance : roomInstance ] }
    }

    def delete = {
        def roomInstance = Room.get( params.id )
        if(roomInstance) {
            try {
                roomInstance.delete(flush:true)
                flash.message = "Room ${params.id} deleted"
                redirect(action:manage)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Room ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "Room not found with id ${params.id}"
            redirect(action:manage)
        }
    }

    def edit = {
        def roomInstance = Room.get( params.id )

        if(!roomInstance) {
            flash.message = "Room not found with id ${params.id}"
            redirect(action:manage)
        }
        else {
            return [ roomInstance : roomInstance ]
        }
    }

    def update = {
        def roomInstance = Room.get( params.id )
        if(roomInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(roomInstance.version > version) {
                    
                    roomInstance.errors.rejectValue("version", "room.optimistic.locking.failure", "Another user has updated this Room while you were editing.")
                    render(view:'edit',model:[roomInstance:roomInstance])
                    return
                }
            }
            roomInstance.properties = params
            if(!roomInstance.hasErrors() && roomInstance.save()) {
                flash.message = "Room ${params.id} updated"
                redirect(action:show,id:roomInstance.id)
            }
            else {
                render(view:'edit',model:[roomInstance:roomInstance])
            }
        }
        else {
            flash.message = "Room not found with id ${params.id}"
            redirect(action:manage)
        }
    }

    def create = {
        def roomInstance = new Room()
        roomInstance.properties = params
        return ['roomInstance':roomInstance]
    }

    def save = {
        def roomInstance = new Room(params)
        if(!roomInstance.hasErrors() && roomInstance.save()) {
            flash.message = "Room ${roomInstance.id} created"
            redirect(action:show,id:roomInstance.id)
        }
        else {
            render(view:'create',model:[roomInstance:roomInstance])
        }
    }
}
