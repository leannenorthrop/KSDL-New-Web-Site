package org.samye.dzong.london.venue

import org.samye.dzong.london.contact.*

class VenueAddress extends Address {
 	boolean _deleted

    static transients = ['_deleted']
	
    static belongsTo = Venue
    static constraints = {
        placeName(maxSize:1024)
        streetNumber(nullable:true)
        line1(size:3..2048, blank:false)
        line2(maxSize:2048)
        county(maxSize:25)
        postTown(maxSize:256)
        postCode(maxSize:10)
        country(maxSize:108)
        type(inList:['main','shop','other','registered'])
    }    
}
