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
package org.samye.dzong.london.contact

/**
 * Contains single postal address details.
 */
class Address extends Contact {
    String placeName;
    Integer streetNumber;
    String line1;
    String line2;
    String county;
    String postTown;
    String postCode;
    String country;
    String type;

    static constraints = {
        placeName(maxSize:1024)
        streetNumber(nullable:true)
        line1(size:3..2048, blank:false)
        line2(maxSize:2048)
        county(maxSize:25)
        postTown(maxSize:255)
        postCode(maxSize:10)
        country(maxSize:108)
        type(inList:['main','shop','other'])
    }

    /**
     * {@inheritDoc}
     */
    String toString() {
        "${name}: ${placeName}"
    }
}
