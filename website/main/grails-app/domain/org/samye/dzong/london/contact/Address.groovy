package org.samye.dzong.london.contact

class Address extends Contact {
    String placeName;
    Integer streetNumber;
    String line1;
    String line2;
    String county;
    String postTown;
    String postCode;
    String country;
    String type;

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
        type(inList:['main','shop','other'])
    }

    String toString() {
        "${placeName} (${type})"
    }
}
