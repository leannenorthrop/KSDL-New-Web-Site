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
    	name(size:5..512)
    	image(nullable:true)
    	description(size:5..32000)
    	facilities(blank:true)
    	access(blank:true)
    	rooms(nullable:true)
    }

    String toString() {
	    name
    }
}
