import org.apache.shiro.SecurityUtils
import org.samye.dzong.london.users.ShiroUser
import org.samye.dzong.london.users.ShiroRole
class UserLookupService {

    boolean transactional = false

    def username() {
        SecurityUtils.subject.principal.toString()
    }
    def lookup() {
        ShiroUser.findByUsername(username())
    }
    def find(username) {
        ShiroUser.findByUsername(username)
    }
    def get(id) {
        ShiroUser.get(id)
    }
    def allRoles() {
        ShiroRole.list()
    }
    def allUsers() {
        ShiroUser.list()
    }
}
