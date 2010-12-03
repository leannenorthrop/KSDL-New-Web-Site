package org.samye.dzong.london.users

class ShiroRole {
    String name

    static hasMany = [ users: ShiroUser, permissions: String ]
    static belongsTo = ShiroUser

    static constraints = {
        name(nullable: false, blank: false, unique: true)
    }

    static mapping = {
        cache usage:'read-write', include:'non-lazy'
    }
    
	String toString() {
		name
	}
}
