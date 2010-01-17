package org.samye.dzong.london.admin

class SettingsFunctionalTests extends functionaltestplugin.FunctionalTestCase {

    void testTwitterSettings() {

    }

    void testEmailSettings() {

    }

    void login() {
        def o = redirectEnabled
        redirectEnabled = false
        get('/manageSite/home')
        click('Hi, please sign in')
        assertStatus 200
        assertContentContains "Sign In"
        form('signIn') {
            username = 'web-admin'
            password = 'change!t'
            byId('submitbtn').click()
        }
        followRedirect()
        assertStatus 200
        redirectEnabled = o
    }
}
