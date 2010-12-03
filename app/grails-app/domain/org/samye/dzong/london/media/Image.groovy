package org.samye.dzong.london.media
import org.grails.taggable.*
class Image implements Taggable {
    String name
    String mimeType
    byte[] image
    byte[] thumbnail
    Date dateCreated
    Date lastUpdated

    boolean _deleted

    static transients = ['_deleted']
    
    static auditable = true

    static constraints = {
        name(blank: false, unique: true,matches:/[a-zA-Z0-9 ]*/)
        mimeType(blank: false)
        image(nullable: false)
        thumbnail()
        dateCreated(nullable:true)
        lastUpdated(nullable:true)
    }
    
    static mapping = {
        cache usage:'nonstrict-read-write'
    }

    Image() {
        name = 'New'
        mimeType = "image/jpeg"
        image = []
        thumbnail = []
    }

    Image(Image toBeCopied) {
        this.name = toBeCopied.name + ' Copy'
        this.mimeType = toBeCopied.mimeType
        this.image = toBeCopied.image
        this.thumbnail = toBeCopied.thumbnail
    }
    
    String toString() {
        return "${name} (${mimeType})"
    }
}
