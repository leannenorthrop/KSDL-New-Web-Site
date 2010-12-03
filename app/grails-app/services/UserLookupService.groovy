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

import org.apache.shiro.SecurityUtils
import org.samye.dzong.london.users.ShiroUser
import org.samye.dzong.london.users.ShiroRole

class UserLookupService {

    boolean transactional = false

    def username() {
        SecurityUtils.subject.principal.toString()
    }
    def lookup() {
        ShiroUser.findByUsername(username())
    }
    def find(username) {
        ShiroUser.findByUsername(username)
    }
    def get(id) {
        ShiroUser.get(id)
    }
    def allRoles() {
        ShiroRole.list()
    }
    def allUsers() {
        ShiroUser.list()
    }
}
