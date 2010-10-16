package org.samye.dzong.london.venue

import org.samye.dzong.london.contact.*

class VenueTelephone extends Telephone {
 	boolean _deleted

    static transients = ['_deleted']	
    static belongsTo = Venue
    
    String toString() {
        "${name}: ${number}"
    }    
}
