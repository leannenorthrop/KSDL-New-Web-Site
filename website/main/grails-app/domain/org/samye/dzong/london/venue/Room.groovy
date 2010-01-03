package org.samye.dzong.london.venue

import org.samye.dzong.london.Publishable
import org.samye.dzong.london.media.Image

class Room extends Publishable {

    String name;
    Image image;
    String description;

    static constraints = {
    	name(blank:false)
    	description()
    	image(nullable:true)
    }

    String toString() {
        name
    }
}
