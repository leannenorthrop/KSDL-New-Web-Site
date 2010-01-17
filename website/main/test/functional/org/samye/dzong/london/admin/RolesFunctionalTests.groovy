package org.samye.dzong.london.admin

class RolesFunctionalTests extends functionaltestplugin.FunctionalTestCase {

    void testCanLogin() {
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

        assertContentContains "Home"
        assertContentContains "Users"
        assertContentContains "Sign Out"
    }

    void testDeleteUserNotLoggedIn() {

    }

    void testDeleteUserLoggedIn() {

    }

    void testAddRole() {

    }

    void testRemoveRole() {

    }

    void testGlobalAdminRoleNotShown() {

    }

    void testGlobalAdminUserNotShown() {

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

    void register(username) {
        redirectEnabled = false
        get('/manageSite/home')
        click('Hi, please sign in')
        assertStatus 200
        assertContentContains "Sign In"
        form('register') {
            username = 'newuser'
            password = 'change!t'
            password2 = 'change!t'
            byId('registerbtn').click()
        }
        followRedirect()
        assertStatus 200
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
