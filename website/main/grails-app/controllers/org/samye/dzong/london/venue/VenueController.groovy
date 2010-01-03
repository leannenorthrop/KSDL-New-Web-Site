

package org.samye.dzong.london.venue

class VenueController {

    class VenueCommand { 
        String name;
        String imageName;
        String description;
        String facilities;
        String access;  
        List rooms = [];
        
        static constraints = {
        	name(size:5..512)
        	image(nullable:true)
        	description(size:5..32000)
        	facilities(blank:true)
        	access(blank:true)
        	rooms(nullable:true)
        }          
        
        Venue createVenue() {
            def venue = new Venue(name:name, description:description, facilities:facilities, access:access);
            def image = Image.findByName(imageName)
            venue.image = Image
            rooms.eachWithIndex { roomName, roomImageName, roomDescription, roomMakePublic, i -> 
                def room = new Room(name: roomName, description: description)
                def roomImage = Image.findByName(roomImageName)
                if (roomImage) { room.image = roomImage }
                room.publishState = roomMakePublic ? "Published" : "Unpublished";
                room.deleted = false;
                venue.addToRooms(room) 
            } 
            return venue
        }
    }
    
    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def manage = {
        params.max = Math.min((params.max ? params.max.toInteger() : 10),  100)
        render(view:'manage',model:[ venueInstanceList: Venue.list( params ), venueInstanceTotal: Venue.count() ])
    }
    
    def create = {
        def venueInstance = new VenueCommand()
        venueInstance.rooms << new Room(name:"Shrine")
        venueInstance.rooms << new Room(name:"Tea")
        venueInstance.properties = params
        return ['venueInstance':venueInstance]
    }

    def save = { VenueCommand cmd ->
        if(!cmd.hasErrors()) {
            def venueInstance = cmd.createVenue()
            venueInstance.save()
            flash.message = "Venue ${venueInstance.id} created"
            redirect(action:manage,id:venueInstance.id)
        }
        else {
            render(view:'create',model:[venueInstance:cmd])
        }
    }
        
    /*
    def delete = {
        def venueInstance = Venue.get( params.id )
        if(venueInstance) {
            try {
                venueInstance.delete(flush:true)
                flash.message = "Venue ${params.id} deleted"
                redirect(action:manage)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Venue ${params.id} could not be deleted"
                redirect(action:manage,id:params.id)
            }
        }
        else {
            flash.message = "Venue not found with id ${params.id}"
            redirect(action:manage)
        }
    }

    def edit = {
        def venueInstance = Venue.get( params.id )

        if(!venueInstance) {
            flash.message = "Venue not found with id ${params.id}"
            redirect(action:manage)
        }
        else {
            return [ venueInstance : venueInstance ]
        }
    }

    def update = {
        def venueInstance = Venue.get( params.id )
        if(venueInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(venueInstance.version > version) {
                    
                    venueInstance.errors.rejectValue("version", "venue.optimistic.locking.failure", "Another user has updated this Venue while you were editing.")
                    render(view:'edit',model:[venueInstance:venueInstance])
                    return
                }
            }
            venueInstance.properties = params
            if(!venueInstance.hasErrors() && venueInstance.save()) {
                flash.message = "Venue ${params.id} updated"
                redirect(action:show,id:venueInstance.id)
            }
            else {
                render(view:'edit',model:[venueInstance:venueInstance])
            }
        }
        else {
            flash.message = "Venue not found with id ${params.id}"
            redirect(action:manage)
        }
    }*/
}
