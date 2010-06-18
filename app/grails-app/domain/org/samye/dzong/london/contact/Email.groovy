package org.samye.dzong.london.contact

class Email extends Contact {
    String address;
    String type;

    static constraints = {
        address(email:true)
        type(inList:['main','work','home','other'])
    }

    String toString() {
        "${name} (${type}): ${address}"
    }
}
