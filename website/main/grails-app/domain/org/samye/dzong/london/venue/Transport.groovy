package org.samye.dzong.london.venue
import org.samye.dzong.london.contact.TelephoneNumber

class Transport {
    boolean publicTransport;
    String type;
    String name;
    String description;
    String websiteLink;
    TelephoneNumber telephoneNumber;

    //static hasMany = [status:TransportStatus]
    static belongsTo = Venue
    static constraints = {
        publicTransport()
        name()
        description(nullable:true)
        type(inList:["Bus", "Underground", "Train", "Bicycle", "Walking", "Car", "Coach", "Air", "Taxi"])
        websiteLink(nullable:true)
        telephoneNumber(nullable:true)
        //status(nullable:true)
    }
    String toString() {
        "$type $name"
    }
}
