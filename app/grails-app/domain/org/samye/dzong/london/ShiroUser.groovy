package org.samye.dzong.london

import org.samye.dzong.london.community.Profile

class ShiroUser implements Comparable{
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

	int	compareTo(Object o) {
		this.toString().compareTo(o.toString())
	}
	
    String toString() {
        (profile != null && profile.publicName != null) ? profile.publicName :username
    }
}
