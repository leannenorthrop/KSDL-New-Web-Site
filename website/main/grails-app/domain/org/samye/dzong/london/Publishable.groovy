package org.samye.dzong.london
import org.grails.taggable.*

class Publishable implements Taggable {
    String publishState
    Boolean deleted

    static auditable = true
    static constraints = {
        publishState(blank:false,inList:["Unpublished", "Published", "Archived"])
    }
    static mapping = {
        tablePerHierarchy false
    }
}
