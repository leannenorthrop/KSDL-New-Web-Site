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

package org.samye.dzong.london

import grails.test.*
import grails.plugin.spock.*
import org.apache.shiro.crypto.hash.Sha1Hash
import org.samye.dzong.london.community.*
import org.samye.dzong.london.users.*
import org.samye.dzong.london.cms.*
import org.samye.dzong.london.venue.*
import org.samye.dzong.london.events.*

/**
 * Integration test for ShiroUser
 *
 * @author Leanne Northrop
 * @since 16th November, 2010, 17:01
 */
abstract class IntegrationHelper extends IntegrationSpec {
    def clean() {
        [Room,Event,Venue,Article,Teacher].each {
            it.findAll().each {d -> d.delete(flush:true)}
        }
        [Room,Event,Venue,Article,Teacher].each {
            println "$it ${it.findAll().size()}"
        }
    } 
    
    def newUser(username='leanne.northrop@abc.def') {
        def auser = ShiroUser.findByUsername(username)
        if (auser) {
            return auser
        } else {
            def profile = new Profile(publicName: 'Not Known', mimeType: 'image/png', image: [], lastLoggedIn: new Date())
            profile.save()
            def token = new Sha1Hash(new Date().toString()).toHex()
            def user = new ShiroUser(username: username, passwordHash: new Sha1Hash('pwd').toHex(), passwordReset: token)
            user.profile = profile
            user.validate()
            println user.errors
            user.save(flush:true)
            return user        
        }
    }
    
    def newArticle(title,published=false,type='M') {
        def article = new Article(title:title,summary:"summary",publishState:(published ? "Published" : "Unpublished"),category:type,content:'',deleted:false,home:false,featured:false)
        article.validate()
        println article.errors
        article.save(flush:true)
        article
    }

    def newTeacher(title,published=false) {
        def teacher = new Teacher(name:title,publishState:(published ? "Published" : "Unpublished"),title:'U',type:'L',category:'T',summary:'summary',content:'',deleted:false,home:false,featured:false)
        teacher.validate()
        println teacher.errors
        teacher.save(flush:true)
        teacher
    }

    def newVenue(title,published=false) {
        def venue = new Venue(placeName:title,publishState:(published ? "Published" : "Unpublished"))
        println venue.errors
        venue.save(flush:true)
        venue 
    }

    def newEvent(title,published=false) {
        def user = newUser("leanne.northrop@abc.com")
        def teacher = newTeacher("AKA")
        def venue = newVenue("Spa Road")
        def event = new Event(title: "Meditation", 
                              summary: "summary", 
                              content: "content",
                              publishState: (published ? "Published" : "Unpublished"),
                              category: 'M',
                              isRepeatable: false,
                              organizer: user,
                              leader: teacher,
                              venue: venue, 
                              deleted:false,
                              featured:false,
                              home:false)
        event.validate()
        println event.errors
        event.save(flush:true)
        event 
    }

    def newRoom(title,published=false) {
        def venue = newVenue('' + title + ' Venue',published)
        def room = new Room(name:title,publishState:(published ? "Published" : "Unpublished"), summary:'summary',content:'',category:'R',deleted:false,home:false,featured:false,forHire:false)
        venue.addToRooms(room)
        room.venue = venue
        room.validate()
        println room.errors
        room.save(flush:true)
        room
    }
}

