/** *****************************************************************************
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
 ***************************************************************************** */

package org.samye.dzong.london.events

import org.samye.dzong.london.media.Image
import org.samye.dzong.london.Publishable
import org.joda.time.*
import org.joda.time.contrib.hibernate.*
import org.samye.dzong.london.ShiroUser
import org.samye.dzong.london.community.Teacher
import org.samye.dzong.london.venue.*
import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList
import net.fortuna.ical4j.model.component.VEvent
import net.fortuna.ical4j.util.UidGenerator
import net.fortuna.ical4j.model.Recur
import net.fortuna.ical4j.model.property.RRule
import net.fortuna.ical4j.model.property.Summary
import net.fortuna.ical4j.model.property.DtStart
import net.fortuna.ical4j.model.property.Uid
import net.fortuna.ical4j.model.property.DtEnd
import net.fortuna.ical4j.model.TimeZoneRegistry
import net.fortuna.ical4j.model.TimeZoneRegistryFactory
import net.fortuna.ical4j.model.property.Attendee
import net.fortuna.ical4j.model.parameter.Role
import net.fortuna.ical4j.model.property.Organizer
import net.fortuna.ical4j.model.property.Description
import net.fortuna.ical4j.model.property.Location
import net.fortuna.ical4j.model.property.Attach
import net.fortuna.ical4j.util.TimeZones
import net.fortuna.ical4j.model.component.VTimeZone

/**
 * Domain class for event information.
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
    ShiroUser organizer;
    Teacher leader;
    Venue venue;
    List prices = new ArrayList();
    List dates = new ArrayList();

    static hasMany = [prices: EventPrice, dates: EventDate]

    static transients = ['onDay']

    static constraints = {
        title(blank: false, unique: true, size: 0..254)
        summary(size: 5..Integer.MAX_VALUE)
        content(size: 5..Integer.MAX_VALUE)
        isRepeatable(nullable: false)
        image(nullable: true)
        dates(nullable: false)
        prices(nullable: true)
        organizer(nullable: false)
        leader(nullable: false)
        venue(nullable: false)
    }

    static mapping = {
        columns {
            content type: 'text'
            summary type: 'text'
        }
        prices cascade: "all-delete-orphan"
        dates cascade: "all-delete-orphan"
    }

    static namedQueries = {
        orderedAuthorPublishState {username, publishState, orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', publishState
            author {
                eq 'username', username
            }
            order("${orderCol}", "${orderDir}")
        }

        authorPublishState {username, publishState ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', publishState
            author {
                eq 'username', username
            }
        }

        orderedPublishState {publishState, orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', publishState
            order("${orderCol}", "${orderDir}")
        }

        publishState {publishState ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', publishState
        }

        deletedAuthor {username ->
            eq('deleted', Boolean.TRUE)
            author {
                eq('username', username)
            }
        }

        deleted {
            eq('deleted', Boolean.TRUE)
        }

        unorderedPublished {
            eq 'deleted', Boolean.FALSE
            eq 'publishState', "Published"
        }

        unorderedCategoryPublished { final category ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', "Published"
            eq 'category', "${category}"
        }

        published {
            eq 'deleted', Boolean.FALSE
            eq 'publishState', "Published"
            order("datePublished", "desc")
        }

        homePage {orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', 'Published'
            order("${orderCol}", "${orderDir}")
        }

        meditation {orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', 'Published'
            eq 'category', 'M'
            order("${orderCol}", "${orderDir}")
        }

        buddhism {orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', 'Published'
            eq 'category', 'B'
            order("${orderCol}", "${orderDir}")
        }

        community {orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', 'Published'
            eq 'category', 'C'
            order("${orderCol}", "${orderDir}")
        }
        wellbeing {orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', 'Published'
            eq 'category', 'W'
            order("${orderCol}", "${orderDir}")
        }
    }

    def getPriceList() {
        return LazyList.decorate(prices, FactoryUtils.instantiateFactory(EventPrice.class));
    }
    
    def getDateList() {
        return LazyList.decorate(dates,FactoryUtils.instantiateFactory(EventDate.class))
    }    

    String toString() {
        return "${title}"
    }

    def calculateDerivedValues() {
        dates.each { it.calculateDerivedValues() }
    }
    
    boolean isOnDay(final date) {
        if (dates && dates[0]) {
            return dates[0].isOnDay(date)
        } else {
            return false;
        }
    }

    boolean isOnDay(final startDate, final noOfDays) {
        if (dates && dates[0]) {
            return dates[0].isOnDay(startDate, noOfDays)
        } else {
            return false;
        }
    }

    VEvent toiCalVEvent() {
        EventDate date = dates[0]
        VEvent iCalEvent = null
        net.fortuna.ical4j.model.PropertyList properties;

        // Generate a UID for the event..
        UidGenerator ug = new UidGenerator(id.toString());

        DateTime sd = date.startTime.toDateTime(new DateTime(date.startDate.getTime()))
        DateTime ed = date.endTime.toDateTime(new DateTime(date.endDate.getTime()))

        net.fortuna.ical4j.model.DateTime start = new net.fortuna.ical4j.model.DateTime(sd.toDate())
        net.fortuna.ical4j.model.DateTime end  = new net.fortuna.ical4j.model.DateTime(ed.toDate())

        if (dates[0].isRule) {
            Recur recur = date.toRecur();
            RRule rrule = new RRule(recur);

            end  = new net.fortuna.ical4j.model.DateTime(sd.toDate())

            iCalEvent = new VEvent(start, end, title);
            properties = iCalEvent.getProperties()
            properties.add(rrule);
        } else {
            iCalEvent = new VEvent(start,end, title);
            properties = iCalEvent.getProperties()
        }

        TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = registry.getTimeZone(TimeZones.GMT_ID);
        VTimeZone tz = timezone.getVTimeZone();
        properties.add(tz.getTimeZoneId());
        properties.add(new Description(summary));
        properties.add(new Location(venue.name));

        /*if (organizer) {
            URI mailToURI = new URI("MAILTO", organizer.username, null);
            properties.add(new Organizer(mailToURI));
        }*/

        Uid id = ug.generateUid();
        // net.fortuna.ical4j.model.Property.UID
        properties.add(id)

        //Summary summary = new Summary(summary);
        //iCalEvent.getProperties().add(summary);

        return iCalEvent
    }
}
