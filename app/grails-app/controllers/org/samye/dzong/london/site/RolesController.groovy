package org.samye.dzong.london.site
import org.samye.dzong.london.*
import org.samye.dzong.london.contact.EmailService
import org.apache.shiro.SecurityUtils
import org.samye.dzong.london.Setting
import org.samye.dzong.london.community.Profile

class RolesController {
    def emailService
    def userLookupService
    def messageSource

    def index = {
        redirect(controller: "manageSite", action: "home")
    }

    def manage = {
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
    							log.warn profile.errors
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
          redirect(action: 'manage')
        } catch (error) {
          log.error "Could not save permission changes ", error
          flash.message = "role.perm.failure"
          flash.isError = true
          redirect(action: 'manage')
        }
    }
}