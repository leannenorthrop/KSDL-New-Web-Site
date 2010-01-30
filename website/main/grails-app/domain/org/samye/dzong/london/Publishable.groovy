/*******************************************************************************
 * Copyright © 2010 Leanne Northrop
 *
 * This file is part of Samye Content Management System.
 *
 * Samye Content Management System is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * Samye Content Management System is distributed in the hope that it will be
 * useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Samye Content Management System.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * BT plc, hereby disclaims all copyright interest in the program
 * “Samye Content Management System” written by Leanne Northrop.
 ******************************************************************************/

package org.samye.dzong.london
import org.grails.taggable.*

/**
 * All content that contains long text content should be inherited from this
 * domain class. Used to control the publish state of the object. Usually I
 * prefer delegation to inheritance but it may be useful to perform a lose
 * join to find all objects of a particular type in a particular publish state.
 *
 * TODO: Test
 * TODO: Change dates to joda dates
 * TODO: Add category so that tags are not used
 * TODO: Add home boolean
 * TODO: Add front boolean
 * TODO: Add isReady for publication boolean
 */
class Publishable implements Taggable {
    String publishState
    Boolean deleted
    ShiroUser author
    Date datePublished
    Date dateCreated
    Date lastUpdated
    Boolean displayAuthor
    Boolean displayDate
    String category

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
        category(blank:false,inList:['M','N','C','W','B'])
    }

    static mapping = {
        tablePerHierarchy false
    }

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

        orderedAuthorPublishState { username, publishState, orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', publishState
            author {
                eq 'username', username
            }
            order("${orderCol}", "${orderDir}")
        }

        orderedPublishState { publishState, orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', publishState
            order("${orderCol}", "${orderDir}")
        }

        deleted {
            eq('deleted', Boolean.TRUE)
        }

    }

    String toString() {
        return "${publishState} by ${author.username} on ${lastUpdated} (${deleted ? "Deleted" : "Not Deleted"})"
    }

}
