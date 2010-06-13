package org.samye.dzong.london.media

import  org.samye.dzong.london.media.FlickrService
import org.samye.dzong.london.Setting

class SlideshowController {
	def flickrService
	
    def manage = { 
		def userId = ""
		def flickrUserSetting = Setting.findByName('FlickrUserId')
		if (!flickrUserSetting) {
			userId = '66103625@N00'
			flickrUserSetting = new Setting(name: 'FlickrUserId', value: userId)
			flickrUserSetting.save()
		} else {
			userId = flickrUserSetting.value
		}
			
		def albums = flickrService.getPhotosets(userId)
		render(view: 'manage', model:[albums:albums])
	}
	
	def frob = {
		log.error "flicker called me with " + params.frob
		def flickrFrobSetting = Setting.findByName('FlickrFrob')
		if (!flickrFrobSetting) {
			flickrFrobSetting = new Setting(name: 'FlickrFrob', value: params.frob)	
		} 	
		flickrFrobSetting.value = params.frob	
		flickrFrobSetting.save()
	}
	
	def edit = {
		try {
			def photoset = flickrService.getPhotoset(params.id)
			[album:photoset,id:params.id]
		} catch (error) {
			log.error "Unable to edit slide show settings", error
		}
	}	
	
	def save = {
		try {
			def setting = Setting.findByName(params.location)
			if (!setting) {
				setting = new Setting(name: params.location, value: params.id)
			} else {
				setting.value = params.id
			}
			log.warn "params.location= " + params.location + " " + setting
			
			if (!setting.hasErrors() && setting.save()) {
	            flash.message = "Slide show settings saved."
	            redirect(controller: 'slideshow', action: 'manage')
	        }
	        else {
	            redirect(action: "edit", id: params.id)
	        }
		} catch (error) {
			log.error "Unable to save slide show settings", error
		}
	}	
}
