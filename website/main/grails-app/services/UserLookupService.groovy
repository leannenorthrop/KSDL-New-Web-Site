import org.apache.shiro.SecurityUtils

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
