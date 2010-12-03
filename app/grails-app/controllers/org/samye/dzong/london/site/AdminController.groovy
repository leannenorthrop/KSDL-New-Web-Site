/*
 * Copyright © 2010 Leanne Northrop
 *
 * This file is part of Samye Content Management System.
 *
 * Samye Content Management System is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * Samye Content Management System is distributed in the hope that it will be
 * useful,but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Samye Content Management System.
 * If not, see <http://www.gnu.org/licenses/>.
 *
 * BT plc, hereby disclaims all copyright interest in the program
 * “Samye Content Management System” written by Leanne Northrop.
 */
package org.samye.dzong.london.site

import org.samye.dzong.london.users.*
import org.samye.dzong.london.contact.EmailService
import org.apache.shiro.SecurityUtils
import org.samye.dzong.london.site.Setting

class AdminController {
    def emailService
    def messageSource

    def index = {
        redirect(controller: "manageSite", action: "home")
    }

    def requestPermission = {
        log.trace "Request to set account permissions for ${params.id}"
        def user = ShiroUser.findByPasswordReset(params.id)
        if (!user) {
            log.info "Request for new user account permissions failed. ${params.id} not found."
            flash.message = "passwd.reset.failure"
            flash.args=['Unknown']
            redirect(controller: 'manageSite', action: 'error')
        } else {
            def roles = ShiroRole.list() 
            roles = roles.findAll{ item ->
                item.name != 'Admin'
            }
            log.trace "Found user rendering roleRequest"
            render(view: 'roleRequest', model:[user:user,roles:roles])
        }
    }

    def requestRoles = {
        log.trace "Requesting roles with ${params.reset}"
        def user = ShiroUser.findByPasswordReset(params.reset)
        if (user) {
            try {
                def roles = params.role
                def requestedRoles = roles.findAll {item ->
                    !item.key.startsWith('_') && item.value=='on'
                }
                emailService.sendPermissionsRequest(user.username, requestedRoles)
                log.info "${user.username} requested ${requestedRoles}, email sent."
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
