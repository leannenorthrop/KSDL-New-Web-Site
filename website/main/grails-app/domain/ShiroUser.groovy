import org.samye.dzong.london.Profile

class ShiroUser {
    String username
    String passwordHash
    Profile profile
    static hasMany = [ roles: ShiroRole, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false)
        profile(nullable:true)
    }
}
