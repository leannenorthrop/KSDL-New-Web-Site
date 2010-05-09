package org.samye.dzong.london.contact

class Contact {
    String name

    static auditable = true
    
    static constraints = {
        name(blank:false)
    }

    static mapping = {
        tablePerHierarchy false
    }

    String toString() {
        name
    }
}
