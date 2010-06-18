package org.samye.dzong.london.contact

class Telephone extends Contact {
    String number;
    String type;

    static constraints = {
        number(matches:"\\d+",blank:false)
        type(inList:['main','work','home','fax','mobile','other'])
    }

    String toString() {
        "${name} (${type}): ${number}"
    }
}
