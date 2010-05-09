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

package org.samye.dzong.london.admin

import org.codehaus.groovy.grails.commons.ConfigurationHolder

import org.samye.dzong.london.ShiroUser
import org.samye.dzong.london.AbstractWebTest
import com.icegreen.greenmail.util.*

/**
 * Functional tests for Web Content Users Log In
 *
 * User: Leanne Northrop
 * Date: Jan 18, 2010, 12:35:48 PM
 */
class LoginFunctionalTests extends AbstractWebTest {

    protected void tearDown() {
        println "Doing teardown"
        def user = ShiroUser.findByUsername('leanne.northrop@googlemail.com')
        if (user) {
            user.passwordHash = 'f86c09c1159dc2082ee27f7aef08f1ed6a5be03d'
            if (!user.hasErrors() && user.save()) {
            } else {
                log.error 'Unable to reset password back to original value!'
                println 'Unable to reset password back to original value!'
            }
        }
        super.tearDown()
    }

    void testCanLogin() {
        adminLogin()

        assertContentContains "Home"
        assertContentContains "Users"
        assertContentContains "Articles"
        assertContentContains "Images"
        assertContentContains "Venues"
        assertContentContains "Events"
        assertContentContains "Sign Out"
    }

    void testCanGotoHome() {
        adminLogin()
        get('/manageSite/home')
        click('Home')
        assertStatus 200
    }

    void testCanGotoUsers() {
        adminLogin()
        get('/manageSite/home')
        click('Users')
        assertStatus 200
    }

    void testCanGotoArticles() {
        adminLogin()
        get('/manageSite/home')
        click('Articles')
        assertStatus 200
    }

    void testCanGotoImages() {
        adminLogin()
        get('/manageSite/home')
        click('Images')
        assertStatus 200
    }

    void testCanGotoVenues() {
        adminLogin()
        get('/manageSite/home')
        click('Venues')
        assertStatus 200
    }

    void testCanGotoEvents() {
        adminLogin()
        get('/manageSite/home')
        click('Events')
        assertStatus 200
    }

    void testCanSignOut() {
        adminLogin()
        signOut()
        assertStatus 200
    }

    void testPasswordReminderWithInvalidEmail() {
        def invalidEmail = 'leanne.northrop@abc.com'
        redirectEnabled = false
        get('/manageSite/home')
        click('Sign In')
        assertStatus 200
        assertContentContains "Sign In"
        click('Forgot your password?')
        assertStatus 200
        assertContentContains "Your Email Address"
        form('resetpasswordform') {
            username = invalidEmail
            byId('resetPassword').click()
        }
        followRedirect()
        assertStatus 200
        assertContentContains "We could not find an account with the e-mail address ${invalidEmail}. Did you type it correctly?"
    }

    void testPasswordReminderWithValidEmail() {
        def validEmail = 'leanne.northrop@googlemail.com'
        redirectEnabled = false
        get('/manageSite/home')
        click('Sign In')
        assertStatus 200
        assertContentContains "Sign In"
        click('Forgot your password?')
        assertStatus 200
        assertContentContains "Your Email Address"
        form('resetpasswordform') {
            username = validEmail
            byId('resetPassword').click()
        }
        followRedirect()
        assertStatus 200
        assertContentContains "An e-mail has been sent to ${validEmail} describing how to reset your password."
        def config = ConfigurationHolder.getConfig()
        if (config?.greenmail) {
            def greenMail = config."greenmail"
            def message = greenMail.getReceivedMessages()[-1]
            def text = GreenMailUtil.toString(message)
            def lines = text.split("\n")
            def url = lines[18]
            get(url)
            form('changePassword'){
                password = 'not24get'
                password2 = 'not24get'
                byId('changePasswordBtn').click()
            }
            followRedirect()
            assertStatus 200
            assertContentContains 'You have successfully changed your password.'
        }
    }

    void testPasswordReminderWithValidEmailOnlyOnce() {
        def validEmail = 'leanne.northrop@googlemail.com'
        redirectEnabled = false
        get('/manageSite/home')
        click('Sign In')
        assertStatus 200
        assertContentContains "Sign In"
        click('Forgot your password?')
        assertStatus 200
        assertContentContains "Your Email Address"
        form('resetpasswordform') {
            username = validEmail
            byId('resetPassword').click()
        }
        followRedirect()
        assertStatus 200
        assertContentContains "An e-mail has been sent to ${validEmail} describing how to reset your password."
        def config = ConfigurationHolder.getConfig()
        if (config?.greenmail) {
            def greenMail = config."greenmail"
            def message = greenMail.getReceivedMessages()[-1]
            def text = GreenMailUtil.toString(message)
            def lines = text.split("\n")
            def url = lines[18]
            get(url)
            form('changePassword'){
                password = 'not24get'
                password2 = 'not24get'
                byId('changePasswordBtn').click()
            }
            followRedirect()
            assertStatus 200
            assertContentContains 'You have successfully changed your password.'
            get(url)
            followRedirect()
            assertStatus 200
            assertContentContains 'We could not find an account with the e-mail address Unknown. Did you type it correctly'
        }
    }

    void testWebAdminCanLogin() {
        webAdminLogin()

        assertContentContains "Home"
        assertContentContains "Users"
        assertContentContains "Sign Out"
    }

}
