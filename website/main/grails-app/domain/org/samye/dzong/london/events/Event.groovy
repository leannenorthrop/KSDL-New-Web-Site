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

package org.samye.dzong.london.events

import org.samye.dzong.london.media.Image
import org.samye.dzong.london.Publishable
import org.joda.time.*
import org.joda.time.contrib.hibernate.*
import org.samye.dzong.london.ShiroUser
import org.samye.dzong.london.community.Teacher
import org.samye.dzong.london.venue.*

/**
 * Domain class for event information.
 * TODO: add reoccurring dates & rule
 * TODO: test
 * TODO: add validation for date,time,duration
 * TODO: publishedByTags
 *
 * Author: Leanne Northrop
 * Date: 29th January, 2010, 16:48
 */
class Event extends Publishable {
    String title;
    String summary;
    String content;
    Image image;
    Boolean isRepeatable;
    LocalDate eventDate;
    TimeOfDay startTime;
    Period eventDuration;
    ShiroUser organizer;
    Teacher leader;
    Venue venue;

    static hasMany = [prices: EventPrice]

    static constraints = {
        title(blank: false, unique: true,size:0..254)
        summary(size: 5..Integer.MAX_VALUE)
        content(size: 5..Integer.MAX_VALUE)
        isRepeatable(nullable: false)
        image(nullable: true)
        eventDate(blank: false)
        startTime(blank: false)
        eventDuration(blank: false)
        prices(nullable:true)
        organizer(nullable:true)
        leader(nullable:true)
        venue(nullable:true)
    }

    static mapping = {
        columns {
            eventDate type: PersistentLocalDate
            startTime type: PersistentTimeOfDay
            eventDuration type: PersistentPeriod
            content type: 'text'
            summary type: 'text'
        }
    }

    static namedQueries = {
        orderedAuthorPublishState { username, publishState, orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', publishState
            author {
                eq 'username', username
            }
            order("${orderCol}", "${orderDir}")
        }

        authorPublishState { username, publishState ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', publishState
            author {
                eq 'username', username
            }
        }

        orderedPublishState { publishState, orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', publishState
            order("${orderCol}", "${orderDir}")
        }

        publishState { publishState ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', publishState
        }

        deletedAuthor { username ->
            eq('deleted', Boolean.TRUE)
            author {
                eq('username', username)
            }
        }

        deleted {
            eq('deleted', Boolean.TRUE)
        }

        publishedByTags { tags, orderCol, orderDir ->

        }

        homePage { orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', 'Published'
            order("${orderCol}", "${orderDir}")
        }

        meditation { orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', 'Published'
            eq 'category', 'M'
            order("${orderCol}", "${orderDir}")
        }

        buddhism { orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', 'Published'
            eq 'category', 'M'
            order("${orderCol}", "${orderDir}")
        }

        community { orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', 'Published'
            eq 'category', 'M'
            order("${orderCol}", "${orderDir}")
        }
        wellbeing { orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', 'Published'
            eq 'category', 'M'
            order("${orderCol}", "${orderDir}")
        }
    }

    String toString() {
        return "${title}"
    }
}
