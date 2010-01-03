package org.samye.dzong.london.venue

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
        return name;
    }
}
