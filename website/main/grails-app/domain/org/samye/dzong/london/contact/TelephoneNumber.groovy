package org.samye.dzong.london.contact

class TelephoneNumber {
    String number;
    String type;
    String label;

    static constraints = {
        number()
        type(inList:["Main", "Home", "Work", "Other"])
        label(nullable:true)
    }

    String toString() {
        "$label ($type), $number"
    }
}
