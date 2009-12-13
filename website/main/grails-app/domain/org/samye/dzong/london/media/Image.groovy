package org.samye.dzong.london.media
import org.grails.taggable.*
class Image implements Taggable {
    String name
    String mimeType
    byte[] image
    byte[] thumbnail

    static auditable = true

    static constraints = {
        name(blank: false, unique: true)
        mimeType()
        image()
        thumbnail()
    }

    String toString() {
        return "${name} (${mimeType})"
    }
}
