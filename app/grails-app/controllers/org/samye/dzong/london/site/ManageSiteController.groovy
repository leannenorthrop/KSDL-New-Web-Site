package org.samye.dzong.london.site

import org.samye.dzong.london.ShiroUser
import org.samye.dzong.london.community.Profile

class ManageSiteController {
	def userLookupService

    def index = {
        redirect(action: "welcome")
    }
    
    def welcome = {
        return render(view:'welcome')
    }
        
    def landing = {
		try {
			def user = userLookupService.lookup()
	        if (user && user.profile == null) {
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
			println error
			log.warn "Unable to check existance of user profile", error
		}	
        return render(view:'home')
    }

    def info = {
        return render(view:'info')
    }

    def error = {
        return render(view:'error')
    }

    def textile = {
        render(view:'textile-format',model:[])
    }

    def preview = {
        try {
            render(view: 'preview', model: [content: params.previewcontenttxt])
        } catch(error) {

        }
    }
}
