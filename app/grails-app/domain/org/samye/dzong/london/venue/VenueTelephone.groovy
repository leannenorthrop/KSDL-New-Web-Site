/** *****************************************************************************
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
 ***************************************************************************** */
package org.samye.dzong.london.venue


import org.samye.dzong.london.contact.*

/**
 * A single telephone number associated with a venue.
 * Subclasses from telephone to support exclusive 
 * association with a venue.
 */
class VenueTelephone extends Telephone {
    /** Non-persisent flag set on detached instances
     * to show that the instance should be deleted
     * from the database.
     */
 	boolean _deleted

    static transients = ['_deleted']	
    static belongsTo = Venue

    VenueTelephone() {
        type = 'main'
        name = 'Main'
        number = '020'
    }

    /**
     * {@inheritDoc}
     */
    String toString() {
        "${name}: ${number}"
    }    
}
