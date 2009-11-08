package org.samye.dzong.london.venue

class Facility {
    String name;
    String description;
    boolean available;

    static belongsTo = Venue
    static constraints = {
        name()
        description(nullable:true)
        available()
    }
    String toString() {
        "$name $available"
    }
}
