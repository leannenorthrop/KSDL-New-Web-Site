package org.samye.dzong.london.venue

import org.samye.dzong.london.contact.*

class VenueEmail extends Email {
 	boolean _deleted

    static transients = ['_deleted']	
    static belongsTo = Venue
}
