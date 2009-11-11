package org.samye.dzong.london

class Person {

    String firstName
    String lastName
    String titlePrefix
    String titleSuffix

    static constraints = {
        titlePrefix()
        firstName(blank:false, maxSize:512)
        lastName(blank:false, maxSize:512)
        titleSuffix()
    }

    static mapping = {
        tablePerHierarchy false
    }

    String toString() {
        return "${titlePrefix} ${firstName} ${lastName}, ${titleSuffix}"
    }
}
