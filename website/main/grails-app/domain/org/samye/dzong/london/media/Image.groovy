package org.samye.dzong.london.media
import org.grails.taggable.*
class Image implements Taggable {
    String name
    byte[] image

    static auditable = true

    static constraints = {
        name(blank: false, unique: true)
        image()
    }

    String toString() {
        return name
    }
}
