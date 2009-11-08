package org.samye.dzong.london.contact

class Address {
    String placeName;
    String placeNumber;
    String street1;
    String street2;
    String town;
    String county;
    String postCode;
    String type;
    String label;

    static constraints = {
        placeName(nullable:true)
        placeNumber()
        street1()
        street2(nullable:true)
        town()
        county(nullable:true)
        postCode()
        type(inList:["Main", "Home", "Work", "Other"])
        label(nullable:true)
    }

    String toString() {
        "$label ($type), $placeName $placeNumber $street1, $street2, $town, $county, $postCode."
    }
}
