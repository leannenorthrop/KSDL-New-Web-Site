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
            render(view: '../admin-error')
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
                        def roles = params.roles
                        roles.each {item -> println item}
                    } catch(error) {
                        log.warn "Unable to nullify password reset field for user ${user.username}", error
                    }
                    log.info "User ${user.username}  has successfully changed password."
                    flash.message = "You have successfully changed your password.<br/> You may now sign in with your new password."
                    render(view: '../admin-info')
                } else {
                    flash.message = message(code: "login.failed")
                    // Now redirect back to the login page.
                    render(view: "../admin-error")
                }
            } catch (Exception e) {
                log.error "Failed to change user's password", e
                flash.message = message(code: "login.failed")
                // Now redirect back to the login page.
                render(view: "../admin-error")
            }
        } else {
            flash.message = "Sorry, we can not find your details. Please request again for a password reset."
            render(view: '../admin-error')
        }
    }
}
