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
            try {
                def dateParser = new SimpleDateFormat("yyyy-MM-dd")
                def start;
                def end;

                if (params.start) {
                    start = new DateTime(dateParser.parse(params.start).getTime())
                    start = start.withTime(0, 0, 0, 0);
                } else {
                    start = new DateTime();
                    start = start.withTime(0, 0, 0, 0);
                }

                if (params.end) {
                    end = new DateTime(dateParser.parse(params.end).getTime())
                    end = end.withTime(0, 0, 0, 0);
                } else {
                    end = new DateTime();
                    end = end.withTime(0, 0, 0, 0);
                }

                if (!end.isAfter(start)) {
                    def tmp = end
                    end = start
                    start = tmp
                }

                end = end.dayOfMonth().withMaximumValue()
                int monthdays = Days.daysBetween(start, end).getDays();

                start = start.toDate()
                end = end.toDate()
                def publishedEvents = Event.unorderedCategoryPublished(category).list(params);
                def events = publishedEvents.findAll { event ->
                    def rule = event.dates[0]
                    return event.isOnDay(start, monthdays)
                };

                def formatter = new SimpleDateFormat(messageSource.getMessage('event.date.format',null,null))
                def args = [formatter.format(start),formatter.format(end)]
                return [events: events, title: messageSource.getMessage('event.between',args.toArray(),null)]
            } catch(error) {
                error.printStackTrace();
                def events = Event.unorderedCategoryPublished(category).list(params);
                return [events: events, title: messageSource.getMessage('events.all.title',null,null)]
            }
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

    def view(id) {
        def eventInstance = Event.get(id)
        if(!eventInstance) {
            flash.message = "Event not found (id ${params.id} unknown)"
            return null
        }
        else {
            def similar = findSimilar(eventInstance)
            return [ eventInstance : eventInstance, events: similar ]
        }
    }

    def userUnpublished(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def username = userLookupService.username();
        def events = Event.orderedAuthorPublishState(username,"Unpublished", order, dir).list(params);
        def total = Event.authorPublishState(username,"Unpublished").count();
        println "found ${events.size()}"
        return [events: events, total: total]
    }

    def userPublished(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def username = userLookupService.username();
        def events = Event.orderedAuthorPublishState(username,"Published", order, dir).list(params);
        def total = Event.authorPublishState(username,"Published").count();
        return [events: events, total: total]
    }

    def userArchived(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def username = userLookupService.username();
        def events = Event.orderedAuthorPublishState(username,"Archived", order, dir).list(params);
        def total = Event.authorPublishState(username,"Archived").count();
        return [events: events, total: total]
    }

    def userReady(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def username = userLookupService.username();
        def events = Event.orderedAuthorPublishState(username,"Ready For Publication", order, dir).list(params);
        def total = Event.authorPublishState(username,"Ready For Publication").count();
        return [events: events, total: total]
    }

    def userDeleted(params) {
        def username = userLookupService.username();
        def events = Event.deletedAuthor(username).list(params);
        def total = Event.deletedAuthor(username).count();
        return [events: events, total: total]
    }

    def unpublished(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def events = Event.orderedPublishState("Unpublished", order, dir).list(params);
        def total = Event.publishState("Unpublished").count();
        println "found ${events.size()} of ${total}"
        return [events: events, total: total]
    }

    def published(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def events = Event.orderedPublishState("Published", order, dir).list(params);
        def total = Event.publishState("Published").count();
        return [events: events, total: total]
    }

    def archived(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def events = Event.orderedPublishState("Archived", order, dir).list(params);
        def total = Event.publishState("Archived").count();
        return [events: events, total: total]
    }

    def deleted(params) {
        def events = Event.deleted().list(params);
        def total = Event.deleted().count();
        return [events: events, total: total]
    }

    def ready(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def events = Event.orderedPublishState("Ready For Publication", order, dir).list(params);
        def total = Event.publishState("Ready For Publication").count();
        return [events: events, total: total]
    }


}
