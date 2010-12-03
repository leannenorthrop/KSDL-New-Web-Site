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

import org.samye.dzong.london.contact.EmailService
import org.apache.shiro.SecurityUtils
import org.samye.dzong.london.site.Setting
import org.samye.dzong.london.users.*
import org.samye.dzong.london.cms.Publishable
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
