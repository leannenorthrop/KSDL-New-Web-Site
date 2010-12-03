/*
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
 */

package org.samye.dzong.london.events

import org.joda.time.TimeOfDay
import java.text.SimpleDateFormat
import java.text.ParsePosition
import org.joda.time.MutablePeriod
import org.joda.time.DateTime
import org.joda.time.Days

class EventService {
    boolean transactional = true
    def userLookupService
    def messageSource

    def list(category, params=[]) {
        def model = []
        try {
            def dateParser = new SimpleDateFormat("yyyy-MM-dd")
            def start = new DateTime();

            if (params?.start) {
                start = new DateTime(dateParser.parse(params.start).getTime())
            } 
            start = start.withTime(0, 0, 0, 0);

            def lastDayOfMonth = start.dayOfMonth().withMaximumValue()
            int daysUntilEndOfMonth = Days.daysBetween(start, lastDayOfMonth).getDays();
            start = start.toDate()

            def publishedEvents = Event.unorderedCategoryPublished(category).list(params);
            def events = publishedEvents.findAll { event ->
                def rule = event.dates[0]
                return event.isOnDay(start, daysUntilEndOfMonth)
            };
            events = events.sort()

            def datePat = messageSource.getMessage('event.date.format',null,Locale.UK)
            def title = messageSource.getMessage('event.list.'+category,[start.format(datePat)].toArray(),Locale.UK)
            model = [events: events, title: title]
        } catch(error) {
            log.warn "Unable to generate list of events", error
            def events = Event.unorderedPublished().list(params);
            model = [events: events, title: messageSource.getMessage('events.all.title',null,Locale.UK)]
        }
        model
    }

    def findSimilar(event, params = []) {
        def tagQuery = "a.id in (select tl.tagRef from TagLink tl where tl.type = 'event' and ("
        for (tag in event.tags) {
            tagQuery += "tl.tag.name = '${tag}' or "
        }
        tagQuery = tagQuery[0..-4] + "))"
        def events = Event.executeQuery("from Event a where a.id != ${event.id} and ${tagQuery} and a.publishState = 'Published' and a.deleted = convert('false',BOOLEAN) order by a.lastUpdated desc", params)
        return events ? (events.size() > 5 ? events[0..4] : events) : []
    }

    def countPublishedByTags(tags, inclusive = Boolean.FALSE) {
        def events
        def tagQuery = ""
        if (inclusive) {
            tagQuery = "a.id in (select tl.tagRef from TagLink tl where tl.type = 'event' and ("
            for (tag in tags) {
                tagQuery += "tl.tag.name = '${tag}' or "
            }
            tagQuery = tagQuery[0..-4] + "))"
            events = Event.executeQuery("from Event a where ${tagQuery} and a.publishState = 'Published' and a.deleted = convert('false',BOOLEAN)")
        } else {
            for (tag in tags) {
                tagQuery += "a.id in (select tl.tagRef from TagLink tl where tl.type = 'event' and tl.tag.name = '${tag}') and "
            }

            events = Event.executeQuery("from Event a where ${tagQuery} a.publishState = 'Published' and a.deleted = convert('false',BOOLEAN)")
        }

        return events ? events.size(): 0
    }

    def publishedByTags(tags, params = [], inclusive = Boolean.FALSE) {
        def events
        def tagQuery = ""
        if (inclusive) {
            tagQuery = "a.id in (select tl.tagRef from TagLink tl where tl.type = 'event' and ("
            for (tag in tags) {
                tagQuery += "tl.tag.name = '${tag}' or "
            }
            tagQuery = tagQuery[0..-4] + "))"
            events = Event.executeQuery("from Event a where ${tagQuery} and a.publishState = 'Published' and a.deleted = convert('false',BOOLEAN) order by a.lastUpdated desc", params)
        } else {
            for (tag in tags) {
                tagQuery += "a.id in (select tl.tagRef from TagLink tl where tl.type = 'event' and tl.tag.name = '${tag}') and "
            }

            events = Event.executeQuery("from Event a where ${tagQuery} a.publishState = 'Published' and a.deleted = convert('false',BOOLEAN) order by a.lastUpdated desc",params)
        }

        return events ?: []
    }

    def countArchivedByTags(tags, inclusive = Boolean.TRUE) {
        def events
        def tagQuery = ""
        if (inclusive) {
            tagQuery = "a.id in (select tl.tagRef from TagLink tl where tl.type = 'event' and ("
            for (tag in tags) {
                tagQuery += "tl.tag.name = '${tag}' or "
            }
            tagQuery = tagQuery[0..-4] + "))"
            events = Event.executeQuery("from Event a where ${tagQuery} and a.publishState = 'Archived' and a.deleted = convert('false',BOOLEAN)")
        } else {
            for (tag in tags) {
                tagQuery += "a.id in (select tl.tagRef from TagLink tl where tl.type = 'event' and tl.tag.name = '${tag}') and "
            }

            events = Event.executeQuery("from Event a where ${tagQuery} a.publishState = 'Archived' and a.deleted = convert('false',BOOLEAN)")
        }

        return events ? events.size(): 0
    }

    def archivedByTags(tags, inclusive = Boolean.TRUE) {
        def events
        def tagQuery = ""
        if (inclusive) {
            tagQuery = "a.id in (select tl.tagRef from TagLink tl where tl.type = 'event' and ("
            for (tag in tags) {
                tagQuery += "tl.tag.name = '${tag}' or "
            }
            tagQuery = tagQuery[0..-4] + "))"
            events = Event.executeQuery("from Event a where ${tagQuery} and a.publishState = 'Archived' and a.deleted = convert('false',BOOLEAN)")
        } else {
            for (tag in tags) {
                tagQuery += "a.id in (select tl.tagRef from TagLink tl where tl.type = 'event' and tl.tag.name = '${tag}') and "
            }

            events = Event.executeQuery("from Event a where ${tagQuery} a.publishState = 'Archived' and a.deleted = convert('false',BOOLEAN)")
        }

        return events ?: []
    }
}
