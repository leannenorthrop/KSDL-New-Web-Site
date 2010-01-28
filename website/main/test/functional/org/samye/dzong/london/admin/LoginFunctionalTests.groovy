package org.samye.dzong.london.admin
import com.icegreen.greenmail.util.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import com.gargoylesoftware.htmlunit.*
import org.samye.dzong.london.ShiroUser

class LoginFunctionalTests extends functionaltestplugin.FunctionalTestCase {

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
        login()

        assertContentContains "Home"
        assertContentContains "Users"
        assertContentContains "Articles"
        assertContentContains "Images"
        assertContentContains "Venues"
        assertContentContains "Events"
        assertContentContains "Sign Out"
    }

    void testCanGotoHome() {
        login()
        get('/manageSite/home')
        click('Home')
        assertStatus 200
    }

    void testCanGotoUsers() {
        login()
        get('/manageSite/home')
        click('Users')
        assertStatus 200
    }

    void testCanGotoArticles() {
        login()
        get('/manageSite/home')
        click('Articles')
        assertStatus 200
    }

    void testCanGotoImages() {
        login()
        get('/manageSite/home')
        click('Images')
        assertStatus 200
    }

    void testCanGotoVenues() {
        login()
        get('/manageSite/home')
        click('Venues')
        assertStatus 200
    }

    void testCanGotoEvents() {
        login()
        get('/manageSite/home')
        click('Events')
        assertStatus 200
    }

    void testCanSignOut() {
        login()
        redirectEnabled = false
        get('/manageSite/home')
        click('Sign Out')
        followRedirect()
        assertStatus 200
        //assertTitle 'Kagyu Samye Dzong London Welcome'
    }

    void testPasswordReminderWithInvalidEmail() {
        def invalidEmail = 'leanne.northrop@abc.com'
        redirectEnabled = false
        get('/manageSite/home')
        click('Hi, Please Sign In')
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
        click('Hi, Please Sign In')
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
            assertStatus 200
            assertContentContains 'You have successfully changed your password.'
        }
    }

    void testPasswordReminderWithValidEmailOnlyOnce() {
        def validEmail = 'leanne.northrop@googlemail.com'
        redirectEnabled = false
        get('/manageSite/home')
        click('Hi, Please Sign In')
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
            assertStatus 200
            assertContentContains 'You have successfully changed your password.'
            get(url)
            assertContentContains 'We could not find an account with the e-mail address Unknown. Did you type it correctly'
        }
    }

    void login() {
        def o = redirectEnabled
        redirectEnabled = false
        get('/manageSite/home')
        click('Hi, Please Sign In')
        assertStatus 200
        assertContentContains "Sign In"
        form('signIn') {
            username = 'leanne.northrop@googlemail.com'
            password = 'change!t'
            byId('submitbtn').click()
        }
        followRedirect()
        assertStatus 200
        redirectEnabled = o
    }

}
