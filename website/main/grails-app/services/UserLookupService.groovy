import org.apache.shiro.SecurityUtils
import org.samye.dzong.london.ShiroUser
import org.samye.dzong.london.ShiroRole
class UserLookupService {

    boolean transactional = false

    def username() {
        SecurityUtils.subject.principal.toString()
    }
    def lookup() {
        ShiroUser.findByUsername(username())
    }
    def allRoles() {
        ShiroRole.list()
    }
    def allUsers() {
        ShiroUser.list()
    }
}
