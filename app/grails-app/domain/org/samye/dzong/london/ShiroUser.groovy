package org.samye.dzong.london

import org.samye.dzong.london.community.Profile

class ShiroUser {
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

    String toString() {
        username
    }
}
