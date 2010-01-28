package org.samye.dzong.london.site
import org.samye.dzong.london.*
import org.samye.dzong.london.contact.EmailService

class AdminController {
    def emailService
    def userLookupService
    def messageSource

    def index = {
        redirect(action: "home")
    }

    def home = {
        return render(view:'home',model:[])
    }

    def roles = {
        def users = userLookupService.allUsers();
        def roles = userLookupService.allRoles();
        roles = roles.findAll() { item ->
            item.id != 1
        }
        users = users.findAll() { item ->
            item.username != 'leanne'
        }
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

    def requestPermission = {
        def user = ShiroUser.findByPasswordReset(params.id)
        if (!user) {
            flash.message = "passwd.reset.failure"
            flash.args=['Unknown']
            redirect(controller: 'manageSite', action: 'error')
        } else {
            def roles = userLookupService.allRoles();
            roles = roles.findAll() { item ->
                item.id != 1
            }
            render(view: 'roleRequest', model:[user:user,roles:roles])
        }
    }

    def requestRoles = {
        log.trace "requesting roles with ${params.reset}"
        def user = ShiroUser.findByPasswordReset(params.reset)
        if (user) {
            try {
                def roles = params.role
                def requestedRoles = roles.findAll {item ->
                    !item.key.startsWith('_') && item.value=='on'
                }
                log.info "${user.username} requested ${requestedRoles}, now sending email"
                emailService.sendPermissionsRequest(user.username, requestedRoles)
                flash.message = "req.perm.success"
                try {
                    user.passwordReset = null
                    if (!user.hasErrors() && user.save()) {
                    } else {
                        log.warn "Unable to nullify passwordReset for ${user.username}"
                    }
                } catch (Exception e) {
                }
                redirect(controller: 'manageSite', action: 'info')
            } catch(error) {
                log.warn "Unable to send request permissions email for user ${user.username}", error
                flash.message = "req.perm.failure"
                redirect(controller: 'manageSite', action: 'info')
            }
        } else {
            flash.message = "req.perm.user.not.found"
            redirect(controller: 'manageSite', action: 'error')
        }
    }
}
