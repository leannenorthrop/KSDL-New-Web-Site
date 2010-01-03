package org.samye.dzong.london
import org.grails.taggable.*


class Publishable implements Taggable {
    String publishState
    Boolean deleted
    ShiroUser author
    Date publishedOn
    Date dateCreated
    Date lastUpdated    
    Boolean displayAuthor;
    Boolean displayDate;
        
    def auditLogService
        
    static auditable = true
    
    static constraints = {
        publishState(blank:false,inList:["Unpublished", "Published", "Archived"])
        author(nullable:true)
        displayAuthor(nullable:true)
        displayDate(nullable:true)        
    }

    static mapping = {
        tablePerHierarchy false
    }

    static transients = ['publishedOn']

    String toString() {
        return "#{publishState} by #{person} (#{deleted})"
    }
    
    def onLoad = { 
        if (displayDate && publishState != "Unpublished") {
            try {
                log.trace("audit log service is ${auditLogService} id is ${id}")
                this.publishedOn = auditLogService.publishedOn(id)
            } catch (error) {
                log.warn("Unable to get audit details for article ${this.id}", error)
            } finally {
                if (!this.publishedOn) {
                    this.publishedOn = this.lastUpdated
                }
            }
        }
    }  
}
