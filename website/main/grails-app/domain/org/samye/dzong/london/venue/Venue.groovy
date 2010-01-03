package org.samye.dzong.london.venue

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
	description(size:512..32000)
	facilities(blank:true)
	access(blank:true)
	rooms(nullable:true)
    }

    String toString() {
	return name;
    }
}
