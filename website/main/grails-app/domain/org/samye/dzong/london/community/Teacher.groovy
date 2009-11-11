package org.samye.dzong.london.community
import org.samye.dzong.london.Person

class Teacher extends Person {

    String bio
    String summary

    static constraints = {
        bio(maxSize:80000)
        summary()
    }

    String toString() {
        return "${titlePrefix} ${firstName} ${lastName} ${titleSuffix}"
    }
}
