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
package org.samye.dzong.london.community

class ProfileController {
	def userLookupService

    def index = { 
		try {
			def user 
			if (params.username) {
				user = userLookupService.find(params.username)
			} else if (params.id) {
				user = userLookupService.get(params.id)
			} else {
				user = userLookupService.lookup()
			}
			render(view: 'index', model:[user:user])
		} catch(error) {
			log.warn "Unable to check existance of user profile", error
		}	
	}

    def src = {
		try {
			def user 
			if (params.username) {
				user = userLookupService.find(params.username)
			} else if (params.id) {
				user = userLookupService.get(params.id)
			} else {
				user = userLookupService.lookup()
			}
	        if (user && user.profile) {
				byte[] image = user.profile.image
				if (request.getHeader("If-Modified-Since")) {
					response.setStatus(304)
				}				
				response.setContentType(user.profile.mimeType)
				response.setDateHeader('Last-Modified', user.profile.lastUpdated.time)
				response.setHeader("Cache-Control", "public")			
				response.setHeader("ETag", "W/\"" + user.profile.version + "\"")
				response.setContentLength(image.size())			
				response.outputStream << image	            
	        }
		} catch(error) {
			println error
			log.warn "Unable to check existance of user profile", error
			response.outputStream << ""
		}
    }

	def edit = {
		try {
			def user 
			if (params.username) {
				user = userLookupService.find(params.username)
			} else if (params.id) {
				user = userLookupService.get(params.id)
			} else {
				user = userLookupService.lookup()
			}
			render(view: 'edit', model:[user:user])
		} catch(error) {
			log.warn "Unable to check existance of user profile", error
		}		
	}
}
