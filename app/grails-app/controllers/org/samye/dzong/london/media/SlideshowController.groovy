/** *****************************************************************************
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
 ***************************************************************************** */

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
			
		if (!flash.message) {
		    flash.message = 'manage.slideshow.help'
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
