import org.samye.dzong.london.Person

class ShiroUser {
    String username
    String passwordHash
    Person person
    static hasMany = [ roles: ShiroRole, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false)
        person(nullable:true)
    }
}
