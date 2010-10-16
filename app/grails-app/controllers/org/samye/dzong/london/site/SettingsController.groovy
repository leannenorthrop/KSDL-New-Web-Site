package org.samye.dzong.london.site
import org.samye.dzong.london.*
import org.samye.dzong.london.contact.EmailService
import org.apache.shiro.SecurityUtils
import org.samye.dzong.london.Setting
import org.samye.dzong.london.community.Profile

class SettingsController {
	def manage = {
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
