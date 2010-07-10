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
 * Empahsis is now on category rather than tags... tags have been relegated to
 * be used to find similar articles.
 *
 * Category
 * <ol>
 * <li>N - News</li>
 * <li>M - Meditation</li>
 * <li>C - Community</li>
 * <li>W - Wellbeing</li>
 * <li>B - Buddhism</li>
 * <li>T - Teachers</li>
 * </ol>
 * TODO: Test
 * TODO: Change dates to joda dates
 */
class Publishable implements Taggable {
    String publishState
    ShiroUser author
    Date datePublished
    Date dateCreated
    Date lastUpdated
    Boolean displayAuthor
    Boolean displayDate
    Boolean home
    Boolean featured
    Boolean deleted
    String category

    def auditLogService

    static auditable = true

    static constraints = {
        publishState(blank:false,inList:["Unpublished", "Ready For Publication", "Published", "Archived"])
        author(nullable:true)
        displayAuthor(nullable:true)
        displayDate(nullable:true)
        datePublished(nullable:true)
        dateCreated(nullable:true)
        lastUpdated(nullable:true)
        deleted()
        home()
        featured()
        category(blank:false,inList:['M','N','C','W','B','T','V','R','A'])
    }

    static mapping = {
        tablePerHierarchy false
    }

    String toString() {
        return "${publishState} by ${author.username} on ${lastUpdated} (${deleted ? "Deleted" : "Not Deleted"})"
    }

    static namedQueries = {
        allPublished {  ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', "Published"
            order("datePublished", "desc")
        }
	}

}
