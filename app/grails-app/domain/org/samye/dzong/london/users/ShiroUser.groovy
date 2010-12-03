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

package org.samye.dzong.london.users


/**
 * Domain class for a single Content Management user.
 *
 * @author Leanne Northrop
 * @since  December,2009
 */
class ShiroUser implements Comparable {
    String username
    String passwordHash
    String passwordReset
    Profile profile
    static hasMany = [ roles: ShiroRole, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false, email:true,unique:true)
        passwordHash(nullable: false, blank: false)
        passwordReset(nullable:true)
    }

    static namedQueries = {
        similar { username ->
            ilike 'username', "%${username}%"
        }
    }
    
    static mapping = {
        cache usage:'read-write', include:'non-lazy'
    }    

    int	compareTo(Object o) {
        this.toString().compareTo(o.toString())
    }
	
    String toString() {
        (profile != null && profile.publicName != null) ? profile.publicName :username
    }
}
