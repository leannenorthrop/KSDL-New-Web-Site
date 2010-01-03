package org.samye.dzong.london.contact

class Telephone extends Contact {
    String number;
    String type;

    static constraints = {
        number(email:true)
        type(inList:['main','work','home','fax','mobile','other'])
    }

    String toString() {
        "${number} (${type})"
    }
}
