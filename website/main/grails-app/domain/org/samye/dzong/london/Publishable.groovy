package org.samye.dzong.london
import org.grails.taggable.*

class Publishable implements Taggable {
    String publishState
    Boolean deleted
    ShiroUser author

    static auditable = true
    static constraints = {
        publishState(blank:false,inList:["Unpublished", "Published", "Archived"])
        author(nullable:true)
    }
    static mapping = {
        tablePerHierarchy false
    }

    String toString() {
        return "#{publishState} by #{person} (#{deleted})"
    }
}
