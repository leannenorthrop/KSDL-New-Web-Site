package org.samye.dzong.london.shop

class Meta {
    String type
    String value
    boolean _deleted

    static transients = ['_deleted']

    static belongsTo = [ product : Product ]

    static constraints = {
    }

    Meta() {
        type = 'New'
        value = ""
    }

    Meta(Meta toBeCopied) {
        this.type = toBeCopied.type
        this.value = toBeCopied.value
    }

    String toString() {
        type + " " + value
    }
}
