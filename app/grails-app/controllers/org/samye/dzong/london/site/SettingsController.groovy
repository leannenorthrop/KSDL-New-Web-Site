package org.samye.dzong.london.site
import org.samye.dzong.london.*
import org.samye.dzong.london.contact.EmailService
import org.apache.shiro.SecurityUtils
import org.samye.dzong.london.Setting
import org.samye.dzong.london.community.Profile
import org.samye.dzong.london.Publishable
import net.sf.ehcache.*

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
		def updateThumbnails = params.settings['ThumbSize'].value.toString() != Setting.findByName('ThumbSize').value.toString()
		log.debug "Original thumb size ${params.settings['ThumbSize'].value} new thumb size ${Setting.findByName('ThumbSize').value} and result ${updateThumbnails}"
		    
		params.settings.each { key,value ->
		    try {
		        log.debug "Looking for ${key}"
    			def setting = Setting.findByName(key)
    			setting.value = value.toString()
    			log.debug "Attempting to save ${setting}"
    			setting.save(flush:true)
    			flash.message = "Settings saved."
		    } catch(error) {
		        flash.isError = true
		        flash.message = "Unable to save setting ${key} at this time."
		        log.error "Unable to save setting ${key}", error
		    }
		}
		
		try {
		    CacheManager.getInstance().getCache("Slideshow").flush()
		} catch(error) {
		    log.warn "Unable to flush Slideshow cache."
		}
        Publishable.allPublished.list().each { publishable ->
	        try {
	            publishable.lastUpdated = new Date()
	            publishable.save()
	        } catch (error) {
	            log.warn("Saving new modified time failed.",error)
	        }
	    }
		    		
	    if (updateThumbnails) {
		    redirect(action: 'updateAllThumbnails', controller:'image')
		} else {
		    redirect(action: 'manage')
	    }
	}
}
