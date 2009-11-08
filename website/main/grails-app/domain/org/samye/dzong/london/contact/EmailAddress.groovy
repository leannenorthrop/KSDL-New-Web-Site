package org.samye.dzong.london.contact

class EmailAddress {
    String email;
    String type;
    String label;

    static constraints = {
        email()
        type(inList:["Main", "Home", "Work", "Other"])
        label(nullable:true)
    }

    String toString() {
        "$label ($type), $email"
    }
}
