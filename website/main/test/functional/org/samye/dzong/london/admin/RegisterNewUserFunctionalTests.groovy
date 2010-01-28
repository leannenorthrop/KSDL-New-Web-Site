package org.samye.dzong.london.admin

import com.icegreen.greenmail.util.*
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.samye.dzong.london.ShiroUser

class RegisterNewUserFunctionalTests extends functionaltestplugin.FunctionalTestCase {
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

    void testUsernameInUse() {
        def un = "leanne.northrop@googlemail.com"
        def pw = "change!t"
        register(un, pw, pw, false)

        assertContentContains "Sorry, that username has already been taken."
    }

    void testPasswordsDoNotMatch() {
        def un = "xyz@googlemail.com"
        def pw = "change!t"
        register(un, pw, "a", false)

        assertContentContains "Your passwords do not match, please try again."
    }

    void testCanRegister() {
        def un = "abc@googlemail.com"
        def pw = "change!t"
        register(un, pw, pw)

        assertContentContains "You have successfully created an account."
    }

    void testSendEmail() {
        def un = "def@googlemail.com"
        def pw = "change!t"
        register(un, pw, pw)

        assertContentContains "You have successfully created an account."
        def config = ConfigurationHolder.getConfig()
        if (config?.greenmail) {
            def greenMail = config."greenmail"
            def message = greenMail.getReceivedMessages()[-1]
            def text = GreenMailUtil.toString(message)
            def lines = text.split("\n")
            def url = lines[18]
            assertTrue url.trim().startsWith("http")
        }
    }

    void testEmailHasLinkValid() {
        def un = "ghi@googlemail.com"
        def pw = "change!t"
        register(un, pw, pw)

        assertContentContains "You have successfully created an account."
        def config = ConfigurationHolder.getConfig()
        if (config?.greenmail) {
            def greenMail = config."greenmail"
            def message = greenMail.getReceivedMessages()[-1]
            def text = GreenMailUtil.toString(message)
            def lines = text.split("\n")
            def url = lines[18]
            get(url)
            assertStatus 200
            assertContentContains 'Request Permission Types'
        }
    }

    void testRequestPermissions() {
        def un = "jkl@googlemail.com"
        def pw = "change!t"
        register(un, pw, pw)

        assertContentContains "You have successfully created an account."
        def config = ConfigurationHolder.getConfig()
        if (config?.greenmail) {
            def greenMail = config."greenmail"
            def message = greenMail.getReceivedMessages()[-1]
            def text = GreenMailUtil.toString(message)
            def lines = text.split("\n")
            def url = lines[18]
            get(url)
            assertStatus 200
            assertContentContains 'Request Permission Types'
            form('requestRoles') {
                byName("role.Editor").value=true
                byId('requestRolesBtn').click()
            }
        }
        doFollowRedirect()
        assertStatus 200
        assertContentContains 'You have successfully requested permissions.'
    }

    void register(un,pw1,pw2,isvalid=true) {
        autoFollowRedirects =true
        get('/manageSite/home')
        click('Sign In')
        assertStatus 200
        assertContentContains "Become a Member"
        form('register') {
            username = "${un}"
            password = "${pw1}"
            passwordAgain= "${pw2}"
            byId('regsubmitbtn').click()
        }
        doFollowRedirect()

        assertStatus 200
    }

}
