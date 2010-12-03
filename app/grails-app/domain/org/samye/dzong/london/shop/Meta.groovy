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

package org.samye.dzong.london.shop


/*
 * Domain class for downloadable shop items.
 *
 * @author Leanne Northrop
 * @since  April 2010
 */
class Meta {
    String type
    String value
    boolean _deleted

    static transients = ['_deleted']

    static belongsTo = [ product : Product ]

    static constraints = {
    }

    Meta() {
        type = 'New'
        value = ""
    }

    Meta(Meta toBeCopied) {
        this.type = toBeCopied.type
        this.value = toBeCopied.value
    }

    String toString() {
        type + " " + value
    }
}
