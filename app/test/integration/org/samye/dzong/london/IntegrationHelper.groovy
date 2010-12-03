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

/**
 * Integration test for ShiroUser
 *
 * @author Leanne Northrop
 * @since 16th November, 2010, 17:01
 */
abstract class IntegrationHelper extends IntegrationSpec {
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
        article.save(flush:true)
        article
    }
}

