package org.samye.dzong.london

class ShiroUser {
    String username
    String passwordHash
    static hasMany = [ roles: ShiroRole, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false)
        passwordHash(nullable: false, blank: false)
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
