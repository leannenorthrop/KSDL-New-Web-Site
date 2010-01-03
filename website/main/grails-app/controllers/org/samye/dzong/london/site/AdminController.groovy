package org.samye.dzong.london.site

class AdminController {

    def userLookupService

    def index = {
        redirect(action: "home")
    }

    def home = {
        return render(view:'home',model:[])
    }

    def roles = {
	def users = userLookupService.allUsers();
	def roles = userLookupService.allRoles();
        render(view: 'assignRoles', model:[users: users, roles:roles]);
    }

    def assignRoles = {
	// need optimisitic locking checks
	def users = userLookupService.allUsers();
	def roles = userLookupService.allRoles();
	roles.each() { role ->
		def rolename = role.name
		users.each() { user ->
			def id = user.id
			def roleValue = params["${id}-${rolename}"]
			if ("on".equals(roleValue)) {
				user.addToRoles(role)
			} else {
				user.removeFromRoles(role)
			}
		}
	}
	redirect(controller: 'admin', action: 'home')
    }
}
