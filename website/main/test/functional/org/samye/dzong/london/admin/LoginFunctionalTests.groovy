package org.samye.dzong.london.admin

class LoginFunctionalTests extends functionaltestplugin.FunctionalTestCase {

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

    void testPasswordReminder() {
        assertStatus 200
    }

    void testRegisterUsernameInUse() {
        assertStatus 200
    }

    void testRegisterCaptcha() {

    }

    void testRegisterUsernameUnique() {
        // on successful register redirect to instructions page
        assertStatus 200
    }

    void testRegisterSendsConfirmationEmail() {
        assertStatus 200
    }

    void testRegisterConfirmationSendsRolesEmail() {
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
            username = 'leanne'
            password = 'change!t'
            byId('submitbtn').click()
        }
        followRedirect()
        assertStatus 200
        redirectEnabled = o
    }
}
