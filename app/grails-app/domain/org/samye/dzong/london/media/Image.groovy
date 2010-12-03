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

package org.samye.dzong.london.media

import org.grails.taggable.*

/*
 * Domain class for handling images.
 *
 * @author Leanne Northrop
 * @since  December 2009
 */
class Image implements Taggable {
    String name
    String mimeType
    byte[] image
    byte[] thumbnail
    Date dateCreated
    Date lastUpdated

    boolean _deleted

    static transients = ['_deleted']
    
    static auditable = true

    static constraints = {
        name(blank: false, unique: true,matches:/[a-zA-Z0-9 ]*/)
        mimeType(blank: false)
        image(nullable: false)
        thumbnail()
        dateCreated(nullable:true)
        lastUpdated(nullable:true)
    }
    
    static mapping = {
        cache usage:'nonstrict-read-write'
    }

    Image() {
        name = 'New'
        mimeType = "image/jpeg"
        image = []
        thumbnail = []
    }

    Image(Image toBeCopied) {
        this.name = toBeCopied.name + ' Copy'
        this.mimeType = toBeCopied.mimeType
        this.image = toBeCopied.image
        this.thumbnail = toBeCopied.thumbnail
    }
    
    String toString() {
        return "${name} (${mimeType})"
    }
}
