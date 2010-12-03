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

import grails.test.*
import grails.plugin.spock.*
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import java.text.SimpleDateFormat
import org.samye.dzong.london.media.Image
import org.samye.dzong.london.users.ShiroUser
import org.samye.dzong.london.community.Teacher
import org.samye.dzong.london.venue.*
import org.joda.time.*
import groovy.time.*
import org.springframework.context.support.StaticMessageSource
import org.apache.shiro.SecurityUtils
import org.spock.lang.*
import org.springframework.transaction.TransactionStatus
import org.samye.dzong.london.cms.CMSUtil

/*
 *  Unit test for Events controller
 */
class EventControllerSpec extends ControllerSpec {
    def messageSource = new StaticMessageSource()
    def roles = []
    def mockTransactionStatus

    def setup() {
        mockTransactionStatus = Mock(TransactionStatus)
        Event.metaClass.static.withTransaction = { c -> c(mockTransactionStatus) }
        CMSUtil.addCMSMethods(EventController) 
        mockLogging(EventController, true)
        mockLogging(Event, true)
        mockLogging(EventDate, true)
        mockLogging(EventPrice, true)
        registerMetaClass(Event)
        registerMetaClass(EventController)
        registerMetaClass(EventService)
        def locale = Locale.UK
        messageSource.addMessage("event.between",locale,"Events For {0}")
        messageSource.addMessage("event.date.format",locale,"MMMM yyyy")
        messageSource.addMessage("event.help",locale,"Please ensure that a Teacher entry has been created and published under the Teachers/Therapists menu before creating a new Event. Also please ensure the selected Organizer has configured their public details by going to the Settings -> About Me menu.")
        EventController.metaClass.message = { args->
            def code = args.code;
            def margs = args.values()
            margs.remove(code)
            margs = margs ? margs.flatten().toArray() : null
            messageSource.getMessage(code, margs, '', locale)
        }
        SecurityUtils.metaClass.static.getSubject = {
            return new Expando(hasRoles: { r -> roles.intersect(r)}, username: {'Rebecca'});
        }
    }   

    def 'Index redirects to home'() {
        when:
        controller.index()

        then:
        redirectArgs == [action: controller.home]
    }

    /*def 'Home generates page with events separated into 4 lists'() {
        setup: "When events are present for today, this week and this month"
        def today = new Date()
        today.clearTime()
        def regularEvents = (0..3).collect { validRegularEvent(today+it) }
        def todayEvents = (0..3).collect { validEvent(today) }
        def weekEvents = thisWeekEvents() 
        def monthEvents = thisMonthEvents()
        def othermonthEvents = (1..4).collect { validEvent(today+(31*it)) }
        Event.metaClass.static.published = { 
            return new Expando(list: { regularEvents+todayEvents+weekEvents+monthEvents+othermonthEvents })  
        }

        and: "article service is present"
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})

        when:
        def model = controller.home()

        then:
        def weekEventsOnToday = weekEvents.findAll { it.dates[0].startDate == today }.size()
        def monthEventsOnToday = monthEvents.findAll {it.dates[0].startDate == today}.size()
        def regularEventsOnToday = regularEvents.findAll{it.dates[0].startDate == today}.size() 
        def expectedTodayEvents = todayEvents.size() + weekEventsOnToday + monthEventsOnToday + regularEventsOnToday 
        model.todaysEvents.size() == expectedTodayEvents


        def weekEventsAfterToday = weekEvents.findAll { it.dates[0].startDate.after(today) }.size()
        def monthEventsAfterToday = monthEvents.findAll {it.dates[0].startDate.after(today)}.size()
        def expectedWeekEvents = weekEventsAfterToday + monthEventsAfterToday 
        model.thisWeeksEvents.size() == expectedWeekEvents 

        def cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK))
        def endOfWeek = cal.time
        model.thisMonthEvents.size() == monthEvents.findAll{it.dates[0].startDate.after(endOfWeek)}.size()  

        model.followingMonths.size() == othermonthEvents.size() 

        model.regularEvents == regularEvents 
    }*/

    def 'Current redirects to list with start and end days of current month'() {
        when:
        controller.current()

        then:
        def now = Calendar.getInstance()
        now.set(Calendar.DAY_OF_MONTH, now.getActualMinimum(Calendar.DAY_OF_MONTH))
        def start = now.format("yyyy-MM-dd")
        now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH))
        def end = now.format("yyyy-MM-dd")
        redirectArgs == [action: controller.list, params:['start': start,'end':end]]
    }

    def 'Future redirects to list with start and end day from current month to end of month next year'() {
        when:
        controller.future()

        then:
        def now = Calendar.getInstance()
        now.set(Calendar.DAY_OF_MONTH, now.getActualMinimum(Calendar.DAY_OF_MONTH))
        def start = now.format("yyyy-MM-dd")
        now.set(Calendar.DAY_OF_MONTH, now.getActualMaximum(Calendar.DAY_OF_MONTH))
        now.roll(Calendar.YEAR,1)
        def end = now.format("yyyy-MM-dd")
        redirectArgs == [action: controller.list, params:['start': start,'end':end]]
    }

    def 'Regular renders list with all regular events'() {
        setup:
        def regularEvents = (0..3).collect { validRegularEvent(new Date()) }
        Event.metaClass.static.published = { 
            return new Expando(list: { regularEvents })  
        }

        when:
        controller.regular()

        then:
        controller.modelAndView.viewName == 'list'
        controller.modelAndView.model.linkedHashMap.events == regularEvents
    }

    def 'List generates list of all events when not supplied parameters'() {
        setup:
        def weekEvents = thisWeekEvents()
        Event.metaClass.static.published = { 
            return new Expando(list: { ->weekEvents })  
        }
        def monthEvents = thisMonthEvents()
        Event.metaClass.static.unorderedPublished = { 
            return new Expando(list: { args->monthEvents })  
        }

        and: "article service is present"
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})

        when:
        def model = controller.list()

        then:
        model.events == monthEvents
        model.title == 'events.all.title'
    }

    def 'List generates list of all events when an error occurs'() {
        setup:
        def weekEvents = thisWeekEvents()
        Event.metaClass.static.published = { 
            return new Expando(list: { ->weekEvents })  
        }
        def monthEvents = thisMonthEvents()
        Event.metaClass.static.unorderedPublished = { 
            return new Expando(list: { args->monthEvents })  
        }

        and: "article service is present"
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})

        when:
        def model = controller.list([unexpectedparam:'abc'])

        then:
        model.events == monthEvents
        model.title == 'events.all.title'
    }

    def 'List returns events for given month not before today'() {
        setup:
        Event.metaClass.static.published = { 
            return new Expando(list: { -> []})  
        }
        def today = new Date()
        today.clearTime()
        def monthEvents = thisMonthEvents()
        Event.metaClass.static.unorderedPublished = { 
            return new Expando(list: { args->monthEvents })  
        }
        def expectedDates = monthEvents.collect{it.dates[0].startDate}.findAll{ it.after(today) || it == today }

        and: "article service is present"
        controller.articleService = new Expando(addHeadersAndKeywords:{a,b,c->})

        and: "params set start date to today"
        getMockParams() << [start: today.format('yyyy-MM-dd')] 

        when:
        def model = controller.list()

        then:
        model.title == "${today.format('MMMM yyyy')}"
        model.events.collect{it.dates[0].startDate} == expectedDates 
    }

    def 'Returns user unpublished events when not an editor or administrator'() {
        setup:
        def uevents = thisMonthEvents()
        controller.eventService = new Expando(userUnpublished:  { args-> [events:uevents] })

        when:
        controller.ajaxUnpublished()

        then:
        controller.modelAndView.viewName == 'unpublished'
        controller.modelAndView.model.linkedHashMap.events == uevents
    }

    def 'Returns all unpublished events when an editor, administrator or both'() {
        setup:
        def uevents = thisMonthEvents()
        controller.eventService = new Expando(unpublished:  { args-> [events:uevents] })

        when:
        this.roles = sroles
        controller.ajaxUnpublished() != null

        then:
        controller.modelAndView.viewName == 'unpublished'
        controller.modelAndView.model.linkedHashMap.events == uevents

        where:
        sroles << [['Editor'],['Administrator'],['Administrator','Editor']]
    }

    def 'Returns user archived events when not an editor or administrator'() {
        setup:
        def uevents = thisMonthEvents()
        controller.eventService = new Expando(userArchived:  { args-> [events:uevents] })

        when:
        controller.ajaxArchived()

        then:
        controller.modelAndView.viewName == 'archived'
        controller.modelAndView.model.linkedHashMap.events == uevents
    }

    def 'Returns all archived events when an editor,administrator or both'() {
        setup:
        def uevents = thisMonthEvents()
        controller.eventService = new Expando(archived:  { args-> [events:uevents] })

        when:
        this.roles = sroles
        controller.ajaxArchived() != null

        then:
        controller.modelAndView.viewName == 'archived'
        controller.modelAndView.model.linkedHashMap.events == uevents

        where:
        sroles << [['Editor'],['Administrator'],['Administrator','Editor']]
    }

    def 'Returns user published events when not an editor or administrator'() {
        setup:
        def uevents = thisMonthEvents()
        controller.eventService = new Expando(userPublished:  { args-> [events:uevents] })

        when:
        controller.ajaxPublished()

        then:
        controller.modelAndView.viewName == 'published'
        controller.modelAndView.model.linkedHashMap.events == uevents
    }

    def 'Returns all published events when an editor,administrator or both'() {
        setup:
        def uevents = thisMonthEvents()
        controller.eventService = new Expando(published:  { args-> [events:uevents] })

        when:
        this.roles = sroles
        controller.ajaxPublished() != null

        then:
        controller.modelAndView.viewName == 'published'
        controller.modelAndView.model.linkedHashMap.events == uevents

        where:
        sroles << [['Editor'],['Administrator'],['Administrator','Editor']]
    }

    def 'Returns user ready events when not an editor or administrator'() {
        setup:
        def uevents = thisMonthEvents()
        controller.eventService = new Expando(userReady:  { args-> [events:uevents] })

        when:
        controller.ajaxReady()

        then:
        controller.modelAndView.viewName == 'ready'
        controller.modelAndView.model.linkedHashMap.events == uevents
    }

    def 'Returns all ready events when an editor,administrator or both'() {
        setup:
        def uevents = thisMonthEvents()
        controller.eventService = new Expando(ready:  { args-> [events:uevents] })

        when:
        this.roles = sroles
        controller.ajaxReady() != null

        then:
        controller.modelAndView.viewName == 'ready'
        controller.modelAndView.model.linkedHashMap.events == uevents

        where:
        sroles << [['Editor'],['Administrator'],['Administrator','Editor']]
    }

    def 'Returns user deleted events when not an editor or administrator'() {
        setup:
        def uevents = thisMonthEvents()
        controller.eventService = new Expando(userDeleted:  { args-> [events:uevents] })

        when:
        controller.ajaxDeleted()

        then:
        controller.modelAndView.viewName == 'deleted'
        controller.modelAndView.model.linkedHashMap.events == uevents
    }

    def 'Returns all deleted events when an editor,administrator or both'() {
        setup:
        def uevents = thisMonthEvents()
        controller.eventService = new Expando(deleted:  { args-> [events:uevents] })

        when:
        this.roles = sroles
        controller.ajaxDeleted() != null

        then:
        controller.modelAndView.viewName == 'deleted'
        controller.modelAndView.model.linkedHashMap.events == uevents

        where:
        sroles << [['Editor'],['Administrator'],['Administrator','Editor']]
    }

    def 'getEventsForView uses offset parameter when supplied'() {
        setup:
        def uevents = thisMonthEvents()
        def params
        controller.eventService = new Expando(userDeleted:  { args-> params = args; [events:uevents] })

        and: "offset param set"
        getMockParams() << [offset: 5] 

        when:
        controller.ajaxDeleted()

        then:
        params.offset == 5
    }

    def 'getEventsForView uses max parameter when supplied'() {
        setup:
        def uevents = thisMonthEvents()
        def params
        controller.eventService = new Expando(userDeleted:  { args-> params = args; [events:uevents] })

        and: "max param set"
        getMockParams() << [max: 9] 

        when:
        controller.ajaxDeleted()

        then:
        params.max == 9
    }

    def 'Uses MIN when max parameter when supplied'() {
        setup:
        def uevents = thisMonthEvents()
        def params
        controller.eventService = new Expando(userDeleted:  { args-> params = args; [events:uevents] })

        when:
        controller.ajaxDeleted()

        then:
        params.max == 30 
    }

    def 'Uses MAX when max parameter is too large'() {
        setup:
        def uevents = thisMonthEvents()
        def params
        controller.eventService = new Expando(userDeleted:  { args-> params = args; [events:uevents] })

        and: "max param set"
        getMockParams() << [max: 1000] 

        when:
        controller.ajaxDeleted()

        then:
        params.max == 200 
    }
    
    def 'Manager renders management page'() {
        when:
        controller.manage()

        then:
        controller.modelAndView.viewName == 'manage'
    }
   
    def 'View redirects to home if event not found'() {
        setup:
        getMockParams() << [id: -1] 
        mockDomain(Event)

        when:
        controller.view()

        then:
        redirectArgs == [action: controller.home]
        mockFlash.isError == true
        mockFlash.message == "Event not found"
    }

    def 'View returns model for requested id'() {
        setup:
        def event = validEvent(new Date())
        event.id = 1
        getMockParams() << [id: event.id] 
        controller.eventService = new Expando(findSimilar:  { args-> [] })
        Event.metaClass.static.get = {id -> event}

        when:
        def model = controller.view()

        then:
        model.event == event
        model.id == mockParams.id
        model.similar == []
    }

    def 'Query redirects to home if event not found'() {
        setup:
        getMockParams() << [id: -1] 
        mockDomain(Event)

        when:
        controller.query()

        then:
        redirectArgs == [action: controller.home]
        mockFlash.isError == true
        mockFlash.message == "Event not found"
    }

    def 'Query returns model for requested id'() {
        setup:
        def event = validEvent(new Date())
        event.id = 1
        getMockParams() << [id: event.id] 
        controller.eventService = new Expando(findSimilar:  { args-> [] })
        Event.metaClass.static.get = {id -> event}

        when:
        def model = controller.query()

        then:
        model.event == event
        model.id == mockParams.id
        model.similar == []
    }

    def 'Send issues email if params are valid'() {
        setup:
        def event = validEvent(new Date())
        event.id = 1
        getMockParams() << [id: event.id] 
        controller.emailService = new Expando(sendEventQuery:  { a,b,c,d-> })
        Event.metaClass.static.get = {id -> event}
        getMockParams() << [email: 'abc@bbc.co.uk', body: 'body', subject:'subject'] 

        when:
        controller.send()

        then:
        redirectArgs == [action: controller.home]
    }

    def 'Show redirects to home if event not found'() {
        setup:
        getMockParams() << [id: -1] 
        mockDomain(Event)

        when:
        controller.show()

        then:
        redirectArgs == [action: controller.home]
        mockFlash.isError == true
        mockFlash.message == "Event not found"
    }

    def 'Show returns model for requested id'() {
        setup:
        def event = validEvent(new Date())
        event.id = 1
        getMockParams() << [id: event.id] 
        controller.eventService = new Expando(findSimilar:  { args-> [] })
        Event.metaClass.static.get = {id -> event}

        when:
        def model = controller.show()

        then:
        model.event == event
        model.id == mockParams.id
    }

    def 'Delete redirects to manage if event not found'() {
        setup:
        getMockParams() << [id: -1] 
        mockDomain(Event)

        when:
        controller.delete()

        then:
        //redirectArgs.action == controller.manage
        mockFlash.isError == true
        mockFlash.message == "Event not found"
    }

    def 'Delete redirects to manage if versions do not match'() {
        setup:
        mockDomain(Event)
        def event = validEvent(new Date())
        event.id = 1
        event.version = 2
        getMockParams() << [id: event.id, version: 1] 
        Event.metaClass.static.get = {id -> event}

        when:
        controller.delete()

        then:
        redirectArgs.action == controller.manage
        mockFlash.isError == true
        mockFlash.message == "Changes could not be saved because of the following:"
    }

    def 'Delete deletes event'(){
        setup:
        mockDomain(Event)
        def event = validEvent(new Date())
        def title = event.title
        event.id = 1
        event.version = 1
        getMockParams() << [id: event.id, version: 1] 
        Event.metaClass.static.get = {id -> event}

        when:
        controller.delete()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.message == "Event ${title} (Deleted) has been deleted"
        event.title == "${title} (Deleted)" 
        assert event.deleted
    }

    def 'Delete failure rollsback and redirects to manage'() {
        setup:
        getMockParams() << [id: 1] 
        mockDomain(Event)
        def event = validEvent(new Date())
        def title = event.title
        event.id = 1
        event.version = 1
        Event.metaClass.static.get = {id -> event}
        event.metaClass.hasErrors { -> false }
        event.metaClass.save { -> throw new RuntimeException() }

        when:
        controller.delete()

        then:
        1 * mockTransactionStatus.setRollbackOnly()
        mockFlash.isError == true 
        mockFlash.message == "Can not delete ${event.title} at this time"
        assert event.title.contains(' (Deleted)')
        redirectArgs.action == controller.manage
        redirectArgs.id == 1
    }

    def 'Delete with errors rollsback and redirects to manage'() {
        given:
        getMockParams() << [id: 1] 
        mockDomain(Event)
        def event = validEvent(new Date())
        def title = event.title
        event.id = 1
        event.version = 1
        Event.metaClass.static.get = {id -> event}
        event.metaClass.hasErrors { -> hasError}
        event.metaClass.save { -> false }

        when:
        controller.delete()

        then:
        1 * mockTransactionStatus.setRollbackOnly()
        mockFlash.isError == true 
        mockFlash.message == "Can not delete ${event.title} at this time"
        assert event.title.contains(' (Deleted)')
        redirectArgs.action == controller.manage
        redirectArgs.id == 1

        where:
        hasError << [true,false]
    }

    def 'Edit redirects to manage if event not found'() {
        setup:
        getMockParams() << [id: -1] 
        mockDomain(Event)

        when:
        controller.edit()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.isError == true
        mockFlash.message == "Event not found"
    }

    def 'Edit returns event with flash message if not already set'() {
        setup:
        mockDomain(Event)
        def event = validEvent(new Date())
        event.id = 1
        getMockParams() << [id: event.id] 
        Event.metaClass.static.get = {id -> event}

        when:
        def model = controller.edit()

        then:
        model.event == event
        model.id == mockParams.id
        mockFlash.message == 'event.help' 
    }

    def 'Edit returns event with flash message unalter if set'() {
        setup:
        mockDomain(Event)
        def event = validEvent(new Date())
        event.id = 1
        getMockParams() << [id: event.id] 
        def msg = 'hello'
        mockFlash.message = msg
        Event.metaClass.static.get = {id -> event}

        when:
        def model = controller.edit()

        then:
        model.event == event
        model.id == mockParams.id
        mockFlash.message == msg 
    }

    def 'Pre-publish redirects to manage if event not found'() {
        setup:
        getMockParams() << [id: -1] 
        mockDomain(Event)

        when:
        controller.pre_publish()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.isError == true
        mockFlash.message == "Event not found"
    }

    def 'Pre-publish returns model for requested id'() {
        setup:
        def event = validEvent(new Date())
        event.id = 1
        getMockParams() << [id: event.id] 
        controller.eventService = new Expando(findSimilar:  { args-> [] })
        Event.metaClass.static.get = {id -> event}

        when:
        controller.pre_publish()

        then:
        controller.modelAndView.viewName == 'publish'
        controller.modelAndView.model.linkedHashMap.event == event
    }

    def 'Publish redirects to manage if event not found'() {
        setup:
        getMockParams() << [id: -1] 
        mockDomain(Event)

        when:
        controller.publish()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.isError == true
        mockFlash.message == "Event not found"
    }

    def 'Publish publishes event for requested id'() {
        setup:
        def event = validEvent()
        event.id = 1
        getMockParams() << [id: event.id] 
        mockDomain(Event, [event])
        mockDomain(EventDate)
        mockDomain(EventPrice)

        when:
        controller.publish()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.message == "Event ${event.title} has been Published"
    }

    def 'Publish redirects to manage if versions do not match'() {
        setup:
        mockDomain(Event)
        def event = validEvent()
        event.id = 1
        event.version = 2
        getMockParams() << [id: event.id, version: 1] 
        Event.metaClass.static.get = {id -> event}

        when:
        controller.publish()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.isError == true
        mockFlash.message == "Changes could not be saved because of the following:"
    }
    
    def 'Update redirects to manage if event not found'() {
        setup:
        getMockParams() << [id: -1] 
        mockDomain(Event)

        when:
        controller.update()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.isError == true
        mockFlash.message == "Event not found"
    }

    def 'Publish failure rollsback and redirects to manage'() {
        setup:
        getMockParams() << [id: 1] 
        mockDomain(Event)
        def event = validEvent(new Date())
        def title = event.title
        event.id = 1
        event.version = 1
        Event.metaClass.static.get = {id -> event}
        event.metaClass.hasErrors { -> false }
        event.metaClass.save { -> throw new RuntimeException() }

        when:
        controller.publish()

        then:
        1 * mockTransactionStatus.setRollbackOnly()
        mockFlash.isError == true 
        mockFlash.message == "Can not save ${event.title} at this time"
        redirectArgs.action == controller.manage
        redirectArgs.id == 1
    }

    def 'Publish with errors rollsback and redirects to manage'() {
        given:
        getMockParams() << [id: 1] 
        mockDomain(Event)
        def event = validEvent(new Date())
        def title = event.title
        event.id = 1
        event.version = 1
        Event.metaClass.static.get = {id -> event}
        event.metaClass.hasErrors { -> hasError}
        event.metaClass.save { -> false }

        when:
        controller.publish()

        then:
        1 * mockTransactionStatus.setRollbackOnly()
        mockFlash.isError == true 
        mockFlash.message == "Can not save ${event.title} at this time"
        redirectArgs.action == controller.manage
        redirectArgs.id == 1

        where:
        hasError << [true,false]
    }

    def 'Update failure rollsback and redirects to manage'() {
        setup:
        getMockParams() << [id: 1] 
        mockDomain(Event)
        def event = validEvent(new Date())
        def title = event.title
        event.id = 1
        event.version = 1
        Event.metaClass.static.get = {id -> event}
        event.metaClass.hasErrors { -> false }
        event.metaClass.save { -> throw new RuntimeException() }

        when:
        controller.update()

        then:
        1 * mockTransactionStatus.setRollbackOnly()
        mockFlash.isError == true 
        mockFlash.message == "Can not save ${event.title} at this time"
        redirectArgs.action == controller.manage
        redirectArgs.id == 1
    }

    def 'Update with errors rollsback and redirects to manage'() {
        given:
        getMockParams() << [id: 1] 
        mockDomain(Event)
        def event = validEvent(new Date())
        def title = event.title
        event.id = 1
        event.version = 1
        Event.metaClass.static.get = {id -> event}
        event.metaClass.hasErrors { -> hasError}
        event.metaClass.save { -> false }

        when:
        controller.update()

        then:
        1 * mockTransactionStatus.setRollbackOnly()
        mockFlash.isError == true 
        mockFlash.message == "Can not save ${event.title} at this time"
        redirectArgs.action == controller.manage
        redirectArgs.id == 1

        where:
        hasError << [true,false]
    }

    def 'Update publishes event for requested id'() {
        setup:
        def event = validEvent()
        event.id = 1
        getMockParams() << [id: event.id] 
        mockDomain(Event, [event])
        mockDomain(EventDate)
        mockDomain(EventPrice)

        when:
        controller.update()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.message == "Event ${event.title} has been saved"
    }

    def 'Update redirects to manage if versions do not match'() {
        setup:
        mockDomain(Event)
        def event = validEvent()
        event.id = 1
        event.version = 2
        getMockParams() << [id: event.id, version: 1] 
        Event.metaClass.static.get = {id -> event}

        when:
        controller.update()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.isError == true
        mockFlash.message == "Changes could not be saved because of the following:"
    }

    def 'Create generates new event with organizer set'() {
        setup:
        def user = new ShiroUser(username:'leanne.northrop@abc.com')
        controller.userLookupService = new Expando(lookup:{user})
        mockDomain(Event)

        when:
        def model = controller.create()

        then:
        assert model.event
        model.event.organizer == user
        mockFlash.message == "Please ensure that a Teacher entry has been created and published under the Teachers/Therapists menu before creating a new Event. Also please ensure the selected Organizer has configured their public details by going to the Settings -> About Me menu."
    }

    def 'Create generates new event'() {
        setup:
        def user = new ShiroUser(username:'leanne.northrop@abc.com')
        controller.userLookupService = new Expando(lookup:{user})
        mockFlash.message = 'already set'
        mockDomain(Event)

        when:
        def model = controller.create()

        then:
        assert model.event
        model.event.organizer == user
        mockFlash.message == "already set"
    }

    def 'Change state redirects to manage if event not found'() {
        setup:
        getMockParams() << [id: -1] 
        mockDomain(Event)

        when:
        controller.changeState()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.isError == true
        mockFlash.message == "Event not found"
    }

    def 'Change state archives event for requested id'() {
        setup:
        def event = validEvent()
        event.id = 1
        getMockParams() << [id: event.id,state:'Archived'] 
        mockDomain(Event, [event])
        mockDomain(EventDate)
        mockDomain(EventPrice)

        when:
        controller.changeState()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.message == "Event ${event.title} has been moved to Archived"
        event.publishState == "Archived"
        event.deleted == false
        def today = new Date()
        today.clearTime()
        def publishDate = event.datePublished
        publishDate.clearTime()
        publishDate == today
    }

    def 'Change state redirects to manage if versions do not match'() {
        setup:
        mockDomain(Event)
        def event = validEvent()
        event.id = 1
        event.version = 2
        getMockParams() << [id: event.id, version: 1] 
        Event.metaClass.static.get = {id -> event}

        when:
        controller.changeState()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.isError == true
        mockFlash.message == "Changes could not be saved because of the following:"
    }

    def 'Save redirects back to create if params have errors'() {
        setup:
        getMockParams() << [organizer: 0, summary: 'summary'] 
        mockDomain(Event)
        controller.userLookupService = new Expando(lookup:{user})

        when:
        controller.save()

        then:
        redirectArgs.action == controller.create
        mockFlash.isError == true
    }

    def 'Save redirects to manage if params have no errors'() {
        setup:
        getMockParams() << validEvent().properties 
        mockDomain(Event)
        controller.userLookupService = new Expando(lookup:{user})

        when:
        controller.save()

        then:
        redirectArgs.action == controller.manage
    }

    def 'Save failure rollsback and redirects to manage'() {
        setup:
        getMockParams() << [id: 1] 
        mockDomain(Event)
        Event.metaClass.hasErrors { -> false }
        Event.metaClass.save { -> throw new RuntimeException() }
        controller.userLookupService = new Expando(lookup:{user})

        when:
        controller.save()

        then:
        1 * mockTransactionStatus.setRollbackOnly()
        mockFlash.isError == true 
        mockFlash.message == "Can not save at this time"
        redirectArgs.action == controller.create
        redirectArgs.id == 1
    }

    def 'Save with errors rollsback and redirects to manage'() {
        given:
        getMockParams() << [id: 1] 
        mockDomain(Event)
        Event.metaClass.hasErrors { -> hasError}
        Event.metaClass.save { -> false }
        controller.userLookupService = new Expando(lookup:{user})

        when:
        controller.save()

        then:
        1 * mockTransactionStatus.setRollbackOnly()
        mockFlash.isError == true 
        mockFlash.message == "event.update.error"
        redirectArgs.action == controller.create
        redirectArgs.id == 1

        where:
        hasError << [true,false]
    }

    def 'Change state failure rollsback and redirects to manage'() {
        setup:
        getMockParams() << [id: 1] 
        mockDomain(Event)
        def event = validEvent(new Date())
        def title = event.title
        event.id = 1
        event.version = 1
        Event.metaClass.static.get = {id -> event}
        event.metaClass.hasErrors { -> false }
        event.metaClass.save { -> throw new RuntimeException() }

        when:
        controller.changeState()

        then:
        1 * mockTransactionStatus.setRollbackOnly()
        mockFlash.isError == true 
        mockFlash.message == "Can not save ${event.title} at this time"
        redirectArgs.action == controller.manage
        redirectArgs.id == 1
    }

    def 'Change state with errors rollsback and redirects to manage'() {
        given:
        getMockParams() << [id: 1] 
        mockDomain(Event)
        def event = validEvent(new Date())
        def title = event.title
        event.id = 1
        event.version = 1
        Event.metaClass.static.get = {id -> event}
        event.metaClass.hasErrors { -> hasError}
        event.metaClass.save { -> false }

        when:
        controller.changeState()

        then:
        1 * mockTransactionStatus.setRollbackOnly()
        mockFlash.isError == true 
        mockFlash.message == "Can not save ${event.title} at this time"
        redirectArgs.action == controller.manage
        redirectArgs.id == 1
    }

    def 'Subscribe generate iCal for all events'() {
        setup:
        def events = thisMonthEvents()
        Event.metaClass.static.published = {new Expando(list:{events})}
        controller.metaClass.createLink = {'http://www.bbc.co.uk'}

        when:
        controller.subscribe()

        then:
        mockResponse.getContentType() == 'text/calendar; charset=utf-8'
    }

    def validEvent() {
        def event = new Event(title: "Meditation", 
                              summary: "summary", 
                              content: "content",
                              publishState: 'Published',
                              category: 'M',
                              isRepeatable: false,
                              organizer: new ShiroUser(username:"leanne.northrop@abc.com"),
                              leader: new Teacher(name:"AKA",title:'U'),
                              venue: new Venue(name:"Spa Road"),
                              deleted:false,
                              featured:false,
                              home:false)
        return event
    }

    def validEvent(date) {
        def event = validEvent() 
        event.title=date.format("dd MM yyyy")
        addValidDate(event,date)
        return event
    }

    def validRegularEvent(date) {
        def event = validEvent() 
        event.title=date.format("dd MM yyyy")
        addValidRegularDate(event,date)
        return event
    }

    def addValidPrice(event,price) {
        def eventPrice = new EventPrice(price:price)
        eventPrice.event = event
        event.prices = [eventPrice]
    }

    def addValidDate(event,date) {
        def eventDate = new EventDate()
        eventDate.event = event
        eventDate.startDate = date
        eventDate.endDate = date
        eventDate.isRule = false 
        event.dates = [eventDate]
    }

    def addValidRegularDate(event,date) {
        def eventDate = new EventDate()
        eventDate.event = event
        eventDate.startDate = date
        eventDate.endDate = date
        eventDate.ruleType = "always"
        eventDate.modifierType = "D"
        eventDate.interval = 1
        eventDate.isRule = true
        event.dates = [eventDate]
    }

    def thisWeekEvents() {
        def cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK))
        def now = cal.time
        now.clearTime()
        def weekEvents = (0..6).collect { validEvent(now+it) }
        weekEvents
    }

    def thisMonthEvents() {
        def cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH))
        def now = cal.time
        now.clearTime()
        def monthEvents = (0..20).collect { validEvent(now+it) }
        monthEvents
    }
}

