package org.samye.dzong.london
import org.grails.taggable.*


class Publishable implements Taggable {
    String publishState
    Boolean deleted
    ShiroUser author
    Date datePublished
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
        datePublished(nullable:true)
        dateCreated(nullable:true)
        lastUpdated(nullable:true)
        deleted()
        displayAuthor()
        displayDate()
    }

    static mapping = {
        tablePerHierarchy false
    }

    static transients = ['publishedOn']

    static namedQueries = {
        authorPublishState { username, publishState ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', publishState
            author {
                eq 'username', username
            }
        }

        publishState { publishState, orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', publishState
            order(orderCol, orderDir)
        }

        deletedAuthor { username ->
                eq('deleted', Boolean.TRUE)
                author {
                    eq('username', username)
                }
        }
    }

    String toString() {
        return "${publishState} by ${author.username} (${deleted ? "Deleted" : "Not Deleted"})"
    }

}
