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
package org.samye.dzong.london.venue

import grails.test.*
import grails.plugin.spock.*
import spock.lang.*
import org.springframework.transaction.TransactionStatus
import org.samye.dzong.london.cms.CMSUtil
import org.samye.dzong.london.users.*
import org.apache.shiro.SecurityUtils
import org.codehaus.groovy.grails.commons.DefaultGrailsDomainClass

/*
 * Unit test for Venue controller class.
 *
 * @author Leanne Northrop
 * @since 1.0.16-SNAPSHOT, 21st November 2010, 15:34
 */
class RoomControllerSpec extends ControllerSpec {
    def mockTransactionStatus
    def venue
    def roles = []
    def user
    def rooms

    def setup() {
        mockTransactionStatus = Mock(TransactionStatus)
        mockDomain(Room,[])
        mockDomain(Venue,[])
        Room.metaClass.static.withTransaction = { c -> c(mockTransactionStatus) }
        CMSUtil.GRAILS_APPLICATION = [domainClasses: [Room].collect{new DefaultGrailsDomainClass(it)}]
        CMSUtil.addCMSMethods(RoomController) 
        venue = newVenue('Somewhere Venue',false)
        SecurityUtils.metaClass.static.getSubject = {
            return new Expando(hasRoles: { r -> roles.intersect(r)}, username: {'leanne.northrop@abc.com'});
        }
        user = new ShiroUser(username:'leanne.northrop@abc.com')
        mockDomain(ShiroUser,[user])
        RoomController.metaClass.currentUser = { user }
        Room.metaClass.static.authorPublishState = { String username, String s -> [list:{args-> rooms}] }
        Room.metaClass.static.publishState = { String s -> [list:{args-> rooms}] }
        Room.metaClass.static.authorDeleted = { String username -> [list:{args-> rooms}] }
        Room.metaClass.static.deleted = { String s -> [list:{args-> rooms}] }
    }

    def 'Index redirects to home'() {
        when:
        controller.index()

        then:
        redirectArgs == [action: controller.manage]
    }

    def 'Returns user unpublished rooms when not an editor or administrator'() {
        setup:
        rooms = (0.3).collect{def room = newRoom('Room ${it}',false); room.author = user; room}

        when:
        controller.ajaxUnpublished()

        then:
        controller.modelAndView.viewName == 'unpublished'
        controller.modelAndView.model.linkedHashMap.total == rooms.size()
    }

    def 'Returns all unpublished rooms when an editor, administrator or both'() {
        setup:
        rooms = (0.3).collect{newRoom('Room ${it}',false)}

        when:
        this.roles = testRoles
        controller.ajaxUnpublished() != null

        then:
        controller.modelAndView.viewName == 'unpublished'
        controller.modelAndView.model.linkedHashMap.total == rooms.size()

        where:
        testRoles << [['Editor'],['Administrator'],['Administrator','Editor']]
    }

    def 'Returns user archived rooms when not an editor or administrator'() {
        setup:
        rooms = (0.3).collect{def room = newRoom('Room ${it}',false); room.author = user; room}

        when:
        controller.ajaxArchived()

        then:
        controller.modelAndView.viewName == 'archived'
        controller.modelAndView.model.linkedHashMap.total == rooms.size()
    }

    def 'Returns all archived rooms when an editor,administrator or both'() {
        setup:
        rooms = (0.3).collect{newRoom('Room ${it}',false)}

        when:
        this.roles =testRoles 
        controller.ajaxArchived() != null

        then:
        controller.modelAndView.viewName == 'archived'
        controller.modelAndView.model.linkedHashMap.total == rooms.size()

        where:
        testRoles << [['Editor'],['Administrator'],['Administrator','Editor']]
    }

    def 'Returns user published rooms when not an editor or administrator'() {
        setup:
        rooms = (0.3).collect{def room = newRoom('Room ${it}',false); room.author = user; room}

        when:
        controller.ajaxPublished()

        then:
        controller.modelAndView.viewName == 'published'
        controller.modelAndView.model.linkedHashMap.total == rooms.size()
    }

    def 'Returns all published rooms when an editor,administrator or both'() {
        setup:
        rooms = (0.3).collect{newRoom('Room ${it}',false)}

        when:
        this.roles = testRoles 
        controller.ajaxPublished() != null

        then:
        controller.modelAndView.viewName == 'published'
        controller.modelAndView.model.linkedHashMap.total == rooms.size()

        where:
        testRoles << [['Editor'],['Administrator'],['Administrator','Editor']]
    }

    def 'Returns user ready rooms when not an editor or administrator'() {
        setup:
        rooms = (0.3).collect{def room = newRoom('Room ${it}',false); room.author = user; room}

        when:
        controller.ajaxReady()

        then:
        controller.modelAndView.viewName == 'ready'
        controller.modelAndView.model.linkedHashMap.total == rooms.size()
    }

    def 'Returns all ready rooms when an editor,administrator or both'() {
        setup:
        rooms = (0.3).collect{newRoom('Room ${it}',false)}

        when:
        this.roles = testRoles 
        controller.ajaxReady() != null

        then:
        controller.modelAndView.viewName == 'ready'
        controller.modelAndView.model.linkedHashMap.total == rooms.size()

        where:
        testRoles << [['Editor'],['Administrator'],['Administrator','Editor']]
    }

    def 'Returns user deleted rooms when not an editor or administrator'() {
        setup:
        rooms = (0.3).collect{def room = newRoom('Room ${it}',false);room.deleted=true;room}

        when:
        controller.ajaxDeleted()

        then:
        controller.modelAndView.viewName == 'deleted'
        controller.modelAndView.model.linkedHashMap.total == rooms.size()
    }

    def 'Returns all deleted rooms when an editor,administrator or both'() {
        setup:
        rooms = (0.3).collect{newRoom('Room ${it}',false)}

        when:
        this.roles = testRoles 
        controller.ajaxDeleted() != null

        then:
        controller.modelAndView.viewName == 'deleted'
        controller.modelAndView.model.linkedHashMap.total == rooms.size()

        where:
        testRoles << [['Editor'],['Administrator'],['Administrator','Editor']]
    }

    def newVenue(title,published=false) {
        def venue = new Venue(placeName:title,publishState:(published ? "Published" : "Unpublished"))
        venue.save(flush:true)
        venue 
    }

    def newRoom(title,published=false) {
        def room = new Room(name:title,publishState:(published ? "Published" : "Unpublished"), summary:'summary',content:'',category:'R',deleted:false,home:false,featured:false,forHire:false)
        venue.addToRooms(room)
        room.venue = venue
        room.validate()
        room.save(flush:true)
        room
    }

}
