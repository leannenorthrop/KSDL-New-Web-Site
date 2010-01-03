package org.samye.dzong.london.venue

class VenueCommand { 
    String name;
    String imageName;
    String description;
    String facilities;
    String access;  
    List rooms = [];
    
    static constraints = {
    	name(size:5..512)
    	image(blank:true)
    	description(size:5..32000)
    	facilities(blank:true)
    	access(blank:true)
    	rooms()
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