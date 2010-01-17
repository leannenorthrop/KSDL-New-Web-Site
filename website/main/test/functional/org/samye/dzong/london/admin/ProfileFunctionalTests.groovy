package org.samye.dzong.london.admin

class ProfileFunctionalTests extends functionaltestplugin.FunctionalTestCase {

    void testChangePassword() {

    }

    void login() {
        def o = redirectEnabled
        redirectEnabled = false
        get('/manageSite/home')
        click('Hi, please sign in')
        assertStatus 200
        assertContentContains "Sign In"
        form('signIn') {
            username = 'leanne'
            password = 'change!t'
            byId('submitbtn').click()
        }
        followRedirect()
        assertStatus 200
        redirectEnabled = o
    }
}
