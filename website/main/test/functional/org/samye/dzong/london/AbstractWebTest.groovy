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

import org.apache.shiro.crypto.hash.Sha1Hash

/**
 * Base class for functional tests providing useful test utility methods.
 *
 * User: Leanne Northrop
 * Date: Jan 22, 2010, 12:35:48 PM
 */
class AbstractWebTest extends functionaltestplugin.FunctionalTestCase {

    void login(un,pw) {
        autoFollowRedirects =true
        get('/manageSite/home')
        click('Sign In')
        assertStatus 200
        assertContentContains "Sign In"
        form('signIn') {
            username = "${un}"
            password = "${pw}"
            byId('submitbtn').click()
        }
        doFollowRedirect()
        assertStatus 200
    }

    void adminLogin() {
        autoFollowRedirects =true
        get('/manageSite/home')
        click('Sign In')
        assertStatus 200
        assertContentContains "Sign In"
        form('signIn') {
            username = 'leanne.northrop@googlemail.com'
            password = 'change!t'
            byId('submitbtn').click()
        }
        doFollowRedirect()
        assertStatus 200
    }

    void webAdminLogin() {
        autoFollowRedirects =true
        get('/manageSite/home')
        click('Sign In')
        assertStatus 200
        assertContentContains "Sign In"
        form('signIn') {
            username = 'web-admin'
            password = 'change!t'
            byId('submitbtn').click()
        }
        doFollowRedirect()
        assertStatus 200
    }

    void signOut() {
        autoFollowRedirects =true
        assertContentContains "Sign Out"
        click('Sign Out')
        assertStatus 200
    }

    ShiroUser addUser(un, pw, roles) {
        def user = new ShiroUser(username: un, passwordHash: new Sha1Hash(pw).toHex())
        user.save()
        roles.each { role ->
            def shiroRole = ShiroRole.findByName(role)
            println "Adding ${un} to ${shiroRole.name}"
            user.addToRoles(shiroRole)
        }
        return user
    }

}
