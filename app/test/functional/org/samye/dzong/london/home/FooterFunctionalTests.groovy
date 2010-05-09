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
        click('☎ Contact Us')
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
