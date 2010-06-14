package org.samye.dzong.london.venue

import org.samye.dzong.london.Publishable
import org.samye.dzong.london.media.Image

class Venue extends Publishable {
    String name;
    Image image;
    String description;
    String facilities;
    String access;
    
    static hasMany = [rooms:Room]

    static constraints = {
    	name(maxSize:128,unique:true)
    	image(nullable:true)
    	description(maxSize:Integer.MAX_VALUE)
    	facilities(blank:true)
    	access(blank:true)
    	rooms(nullable:true)
    }

	static mapping = {
		rooms sort: 'name'
	    columns {
	        content type:'text'
	        facilities type:'text'
	        access type:'text'	
	    }		
	}
    String toString() {
	    name
    }
}
