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
 * @author Leanne Northrop
 * @since 29th January, 2010, 16:48
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
        title(blank: false, size: 0..254)
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
        cache usage:'read-write', include:'non-lazy'
        columns {
            content type: 'text'
            summary type: 'text'
        }
        prices cascade: "all-delete-orphan", cache:true, lazy:false
        dates cascade: "all-delete-orphan", cache:true, lazy:false
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
            eq 'home', Boolean.TRUE
            dates {
                eq "isRule", Boolean.FALSE
            }
            order("${orderCol}", "${orderDir}")
        }

        meditation {orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', 'Published'
            eq 'category', 'M'
            or {
                eq 'featured', Boolean.TRUE  
                eq 'home', Boolean.TRUE                          
            }
            order("${orderCol}", "${orderDir}")
        }

        buddhism {orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', 'Published'
            eq 'category', 'B'
            or {
                eq 'featured', Boolean.TRUE  
                eq 'home', Boolean.TRUE                          
            }        
            order("${orderCol}", "${orderDir}")
        }

        community {orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', 'Published'
            eq 'category', 'C'
            or {
                eq 'featured', Boolean.TRUE  
                eq 'home', Boolean.TRUE                          
            }       
            order("${orderCol}", "${orderDir}")
        }
        wellbeing {orderCol, orderDir ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', 'Published'
            eq 'category', 'W'
            or {
                eq 'featured', Boolean.TRUE  
                eq 'home', Boolean.TRUE                          
            }
            order("${orderCol}", "${orderDir}")
        }
    }

    def getPriceList() {
        return LazyList.decorate(prices, FactoryUtils.instantiateFactory(EventPrice.class));
    }
    
    def getDateList() {
        return LazyList.decorate(dates,FactoryUtils.instantiateFactory(EventDate.class))
    }    

    def bindPrices(params) {
        def _toBeDeleted = params.findAll{it.key.contains('priceList') && it.key.contains('_deleted') && it.value.toBoolean() }.keySet()
        def delIndexes = _toBeDeleted.collect{ it.minus('priceList[').minus(']._deleted').toInteger() }
        prices.toArray().eachWithIndex{item,index ->
            if (delIndexes.contains(index)) {
                removeFromPrices(item);
            } else {
                item.save() 
            }    			
        }
    }

    def bindDates(params) {
        def _toBeDeleted = params.findAll{it.key.contains('dateList') && it.key.contains('_deleted') && it.value.toBoolean() }.keySet()
        def delIndexes = _toBeDeleted.collect{ it.minus('dateList[').minus(']._deleted').toInteger() }
        dates.toArray().eachWithIndex{item,index ->
            if (delIndexes.contains(index)) {
                removeFromDates(item);
            } else {
                try {
                    def sh = params."dateList[${index}].startTimeHour".toInteger()
                    def sm = params."dateList[${index}].startTimeMin".toInteger()
                    def eh = params."dateList[${index}].endTimeHour".toInteger()
                    def em = params."dateList[${index}].endTimeMin".toInteger()
                    item.startTime = new TimeOfDay(sh, sm)
                    item.endTime = new TimeOfDay(eh, em)
                    item.duration = new MutablePeriod(item.startTime.toDateTimeToday(), item.endTime.toDateTimeToday()).toPeriod()
                    item.save() 
                } catch (error) {
                    log.warn "Unable to save date", error
                }
            }    			
        }
    }


    String toString() {
        return "${title}"
    }

    boolean isOnDay(final date) {
        if (dates) {
            log.debug "*********${this}:"
            return dates.any{ eventdate -> eventdate.isOnDay(date) }
        } else {
            return false;
        }
    }

    boolean isOnDay(final startDate, final noOfDays) {
        if (dates) {
            return dates.any{ eventdate -> eventdate.isOnDay(startDate,noOfDays) }
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

        if (organizer) {
            URI mailToURI = new URI("MAILTO", organizer.username, null);
            properties.add(new Organizer(mailToURI));
        }

        Uid id = ug.generateUid();
        properties.add(id)

        return iCalEvent
    }
}
