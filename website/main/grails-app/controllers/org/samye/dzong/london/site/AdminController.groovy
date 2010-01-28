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
            flash.message = messageSource.getMessage("passwd.reset.failure", ['Unknown'].toArray(), null)
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
        println "requesting roles"
        def user = ShiroUser.findByPasswordReset(params.reset)
        if (user) {
            try {
                user.passwordReset = null
                if (!user.hasErrors() && user.save()) {
                    try {
                        def roles = params.role
                        def requestedRoles = roles.findAll {item ->
                            !item.key.startsWith('_') && item.value=='on'
                        }
                        log.info "${user.username} requested ${requestedRoles}, now sending email"
                        emailService.sendPermissionsRequest(user.username, requestedRoles)
                    } catch(error) {
                        log.warn "Unable to nullify password reset field for user ${user.username}", error
                    }
                    flash.message = "You have successfully requested permissions.<br/> An email has been sent to the Administrators of this site to perform your request, which may take up to several days. If you do not receive an email within the next 3 days please write to admin@lsd.org."
                    redirect(controller: 'manageSite', action: 'info')
                } else {
                    flash.message = message(code: "login.failed")
                    // Now redirect back to the login page.
                    redirect(controller: 'manageSite', action: 'error')
                }
            } catch (Exception e) {
                log.error "Failed to change user's password", e
                flash.message = message(code: "login.failed")
                // Now redirect back to the login page.
                redirect(controller: 'manageSite', action: 'error')
            }
        } else {
            flash.message = "Sorry, we can not find your details. Please request again for a password reset."
            redirect(controller: 'manageSite', action: 'error')
        }
    }
}
