import org.apache.shiro.SecurityUtils

class UserLookupService {

    boolean transactional = false

    def lookup() {
        def username = SecurityUtils.subject.principal.toString()
        //puts "Looking for #{username}"
        ShiroUser.findByUsername(username)
    }
}
