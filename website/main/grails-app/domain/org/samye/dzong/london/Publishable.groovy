package org.samye.dzong.london
import org.grails.taggable.*

class Publishable implements Taggable {
    String publishState
    Boolean deleted
    ShiroUser author
    Date publishedOn
    Date dateCreated
    Date lastUpdated    
    
    def auditLogService
        
    static auditable = true
    
    static constraints = {
        publishState(blank:false,inList:["Unpublished", "Published", "Archived"])
        author(nullable:true)
    }

    static mapping = {
        tablePerHierarchy false
    }

    static transients = ['publishedOn']

    String toString() {
        return "#{publishState} by #{person} (#{deleted})"
    }
    
    def onLoad = { 
        try {
            this.publishedOn = auditLogService.publishedOn(this.id);
        } catch (error) {
            println("Trouble getting audit details for article ${this.id}")
            log.warn("Unable to get audit details for article ${this.id}", error)
        }
    }  
}
