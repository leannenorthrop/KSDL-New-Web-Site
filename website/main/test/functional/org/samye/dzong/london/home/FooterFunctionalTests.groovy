package org.samye.dzong.london.home

class FooterFunctionalTests extends functionaltestplugin.FunctionalTestCase {

    void testCanChangeCssTheme() {
        redirectEnabled = true
        get('/')
        assertStatus 200
        assertContentContains "Change Theme"
        click('Change Theme')
        assertStatus 200
        assertContentContains "You can change the way this website looks by clicking any of the links below:"
        click('pastel')
        assertStatus 200
        assertContentContains "/main/css/themes/pastel/screen.css"
    }

    void testAboutUs() {
        redirectEnabled = true
        get('/')
        assertStatus 200
        assertContentContains "About Us"
        click('About Us')
        assertStatus 200
    }

    void testContactUs() {
        redirectEnabled = true
        get('/')
        assertStatus 200
        assertContentContains "Contact Us"
        click('Contact Us')
        assertStatus 200
    }

    void testHelp() {
        redirectEnabled = true
        get('/')
        assertStatus 200
        assertContentContains "Help"
        click('Help')
        assertStatus 200
    }

    void testSiteMap() {
        redirectEnabled = true
        get('/')
        assertStatus 200
        assertContentContains "Site Map"
        click('Site Map')
        assertStatus 200
    }

    void testAboutThisSite() {
        redirectEnabled = true
        get('/')
        assertStatus 200
        assertContentContains "About This Site"
        click('About This Site')
        assertStatus 200
    }
}
