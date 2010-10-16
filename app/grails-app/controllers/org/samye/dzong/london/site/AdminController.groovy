package org.samye.dzong.london.site
import org.samye.dzong.london.*
import org.samye.dzong.london.contact.EmailService
import org.apache.shiro.SecurityUtils
import org.samye.dzong.london.Setting
import org.samye.dzong.london.community.Profile

class AdminController {
    def emailService
    def userLookupService
    def messageSource

    def index = {
        redirect(controller: "manageSite", action: "home")
    }

    def roles = {
        log.trace "Looking up Users and Roles"
        def users = userLookupService.allUsers();
        def roles = userLookupService.allRoles();
        log.trace "Removing Admin role"
        roles = roles.findAll { item ->
            item.name != 'Admin'
        }

        try {
          def adminRole = ShiroRole.findByName('Admin')
          users = users.findAll{ item ->
              !item.roles.find { role -> role == adminRole}
          }
        } catch(error) {
          log.warn "Unabled to find Admin role...skipping"
        }
        log.trace "Rendering assignRoles with ${users.size()} users and ${roles.size()}"
        render(view: 'assignRoles', model:[users: users, roles:roles]);
    }

    // TODO: need optimisitic locking checks
    def assignRoles = {
        log.trace "Assigning roles to users (no locking checks)"
        def users = userLookupService.allUsers();
        def roles = userLookupService.allRoles();
        try {
          roles.each() { role ->
              def rolename = role.name
              if (rolename != 'Admin') {
                users.each() { user ->
                    def id = user.id
                    def name = user.username
                    
                    try {
    			        if (user.profile == null) {
    						def imageBytes = new File(servletContext.getRealPath('/images/user.png')).readBytes()
    			            def profile = new Profile(publicName: 'Not Known', mimeType: 'image/png', image: imageBytes, lastLoggedIn: new Date())
    			 			if (!profile.hasErrors() && profile.save()) {
    							user.profile = profile
    							user.save()
    						} else {
    							println profile.errors
    						}
    			        }			
        			} catch(error) {
        				log.warn "Unable to create user profile", error
        			}
        			                    
                    def roleValue = params["${id}-${rolename}"]
                    if ("on".equals(roleValue)) {
                        log.trace "Adding ${name} to ${rolename}"
                        user.addToRoles(role)
                    } else {
                        log.trace "Removing ${name} from ${rolename}"
                        user.removeFromRoles(role)
                    }
                }
              }
          }
          flash.message = "role.perm.success"
          flash.isError = false
          redirect(action: 'roles')
        } catch (error) {
          log.error "Could not save permission changes ", error
          flash.message = "role.perm.failure"
          flash.isError = true
          redirect(action: 'roles')
        }
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
            def roles = userLookupService.allRoles();
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

	def settings = {
		def flickrUserSetting = Setting.findByName('Logo')
		if (!flickrUserSetting) {
			flickrUserSetting = new Setting(name: 'Logo', value: 1)
			flickrUserSetting.save()
		} 		
		model: [settings: Setting.list()]
	}
	
	def save = {
	    log.debug "Saving settings"
		params.settings.each { key,value ->
		    try {
		        log.debug "Looking for ${key}"
    			def setting = Setting.findByName(key)
    			setting.value = value.toString()
    			log.debug "Attempting to save ${setting}"
    			setting.save(flush:true)
		    } catch(error) {
		        log.error "Unable to save setting ${key}", error
		    }
		}
		redirect(controller: 'manageSite', action: 'home')
	}
}
