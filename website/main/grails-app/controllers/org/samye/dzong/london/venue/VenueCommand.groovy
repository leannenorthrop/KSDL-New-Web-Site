package org.samye.dzong.london.venue

import org.apache.commons.collections.ListUtils
import org.apache.commons.collections.FactoryUtils
    
class VenueCommand { 
    String name;
    String imageName;
    String description;
    String facilities;
    String access;  
    List<RoomCommand> rooms = ListUtils.lazyList([], FactoryUtils.instantiateFactory(RoomCommand))
    
    static constraints = {
    	name(size:5..512)
    	imageName(nullable:true)
    	description(size:5..32000)
    	facilities(nullable:true)
    	access(nullable:true)
    	rooms(minSize:1)
    }          
    
    Venue createVenue() {
        def venue = new Venue(name:name, description:description, facilities:facilities, access:access);
        def image = Image.findByName(imageName)
        venue.image = Image
        rooms.eachWithIndex { room, i ->             
            venue.addToRooms(room.createRoom()) 
        } 
        return venue
    }
}

class RoomCommand {
    String name
    String description
    String imageName
    Boolean makePublic

    static constraints = {
        name(nullable: false, blank: false)
        imageName(nullable: false, blank: false)        
        description(nullable: true, blank: true)
    }
    
    Room createRoom() {
        def room = new Room(name:name, description:description)
        def roomImage = Image.findByName(imageName)
        if (roomImage) { room.image = roomImage }
        room.publishState = roomMakePublic ? "Published" : "Unpublished";
        room.deleted = false; 
        return room;       
    }
}