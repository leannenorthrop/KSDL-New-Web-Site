/*
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
 */

package org.samye.dzong.london.admin

import org.samye.dzong.london.AbstractWebTest

/*
 * Functional tests for Web Administrators Handling Users & Roles
 *
 * User: Leanne Northrop
 * Date: Jan 22, 2010, 13:06:00 PM
 */
class RolesFunctionalTests extends AbstractWebTest {

    void testGlobalAdminRoleNotShown() {
        webAdminLogin()
        click "Users"
        assertContentDoesNotContain "<th>Admin</th>"
    }

    void testGlobalAdminUserNotShown() {
        webAdminLogin()
        click "Users"
        assertContentDoesNotContain "leanne.northrop@googlemail.com"
    }

    void testAddRole() {
        // Setup
        def un = "RoleTest"
        def pw = "change!t"
        //def user = addUser(un, pw, ['Administrator'])

        login(un,pw)
        click "Users"
        def checkboxId = "3-VenueManager"
        assertFalse byName(checkboxId).isChecked()

        // Test
        form('roles') {
            byName(checkboxId).setChecked(true)
            byId('requestRolesBtn').click()
        }
        doFollowRedirect()

        // Confirm
        assertStatus 200
        //assertContentContains "OK"
        assertContentContains "Saved Successfully"
        assertTrue byName(checkboxId).isChecked()
    }

    void testRemoveRole() {
        // Setup
        def un = "RoleTest"
        def pw = "change!t"
        //def user = addUser(un, pw, ['Administrator'])

        login(un,pw)
        click "Users"
        def checkboxId = "3-VenueManager"
        assertTrue byName(checkboxId).isChecked()

        // Test
        form('roles') {
            byName(checkboxId).setChecked(false)
            byId('requestRolesBtn').click()
        }
        doFollowRedirect()

        // Confirm
        assertStatus 200
        //assertContentContains "OK"
        assertContentContains "Saved Successfully"
        assertFalse byName(checkboxId).isChecked()
    }

    void testRoleChangeError() {

    }
}
