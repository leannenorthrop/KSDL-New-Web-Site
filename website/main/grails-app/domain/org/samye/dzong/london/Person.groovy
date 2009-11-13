package org.samye.dzong.london

class Person {

    String firstName
    String lastName
    String titlePrefix
    String titleSuffix
    Profile profile

    static constraints = {
        titlePrefix()
        firstName(blank:false, maxSize:512)
        lastName(blank:false, maxSize:512)
        titleSuffix()
        profile(nullable:true)
    }

    String toString() {
        return "${titlePrefix} ${firstName} ${lastName}, ${titleSuffix}"
    }
}
