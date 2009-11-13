package org.samye.dzong.london.community
import org.samye.dzong.london.Person

class Teacher extends Person {

    String bio
    String blurb

    static constraints = {
        bio(maxSize:80000)
        blurb()
    }

    String toString() {
        return "${titlePrefix} ${firstName} ${lastName} ${titleSuffix}"
    }
}
