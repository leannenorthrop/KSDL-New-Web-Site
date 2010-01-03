package org.samye.dzong.london.venue

class Transport {
    String description;
    
    static auditable = true
    static belongsTo = Venue
    
    static constraints = {
	    description(blank:false)
    }  
}
