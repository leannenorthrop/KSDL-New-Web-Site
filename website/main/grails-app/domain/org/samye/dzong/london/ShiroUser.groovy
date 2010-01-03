package org.samye.dzong.london

class ShiroUser {
    String username
    String passwordHash
    Person person
    static hasMany = [ roles: ShiroRole, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false)
        person(nullable:true)
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
