package org.samye.dzong.london.venue
import org.samye.dzong.london.contact.Address
import org.samye.dzong.london.contact.EmailAddress
import org.samye.dzong.london.contact.TelephoneNumber
import org.samye.dzong.london.event.RegularEvent

class Venue {

    String name;
    String description;
    byte[] picture;

    //static hasMany = [addresses:Address,contactNumbers:TelephoneNumber,email:EmailAddress,openingHours:RegularEvent,facilities:Facility,transport:Transport]
    static hasMany = [addresses:Address,contactNumbers:TelephoneNumber,emails:EmailAddress,facilities:Facility,transportOptions:Transport]

    static constraints = {
        name()
        description(nullable:true, maxSize:5000)
        picture(nullable:true, maxSize:1000000)
        addresses(nullable:true)
        contactNumbers(nullable:true)
        emails(nullable:true)
        //openingHours(nullable:true)
        facilities(nullable:true)
        transportOptions(nullable:true)
        picture(nullable:true)
    }

    String toString() {
        "$name"
    }
}
