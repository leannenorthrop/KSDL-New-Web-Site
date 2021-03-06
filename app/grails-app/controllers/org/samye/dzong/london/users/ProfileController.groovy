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

package org.samye.dzong.london.users

import org.samye.dzong.london.cms.*

/*
 * CMS area User profile url handler.
 *
 * @TODO Internalize.
 *
 * @author Leanne Northrop
 * @since  April 2010
 */
class ProfileController extends CMSController {
    def imageService

    def manage = { 
        try {
            def user
            if (params.username) {
                user = ShiroUser.findByUsername(params.username)
            } else if (params.id) {
                user = ShiroUser.get(params.id)
            } else {
                user = currentUser()
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
                user = ShiroUser.findByUsername(params.username)
            } else if (params.id) {
                user = ShiroUser.get(params.id)
            } else {
                user = currentUser()
            }
            if (user && user.profile) {
                byte[] image = user.profile.image
                if (request.getDateHeader("If-Modified-Since") > user.profile.lastUpdated.time) {
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
                user = ShiroUser.findByUsername(params.username)
            } else if (params.id) {
                user = ShiroUser.get(params.id)
            } else {
                user = currentUser()
            }
            render(view: 'edit', model:[user:user])
        } catch(error) {
            log.warn "Unable to check existance of user profile", error
            flash.message = "profile.save.error"
            flash.isError=true
            render(view:'edit',model:[user:user])
        }
    }
	
    def save = {
        def user = currentUser()
		
        try {
            def f = request.getFile('image')
            def contentType = f?.getContentType()
            def bytes = f?.getBytes()

            user.profile.publicName = params.publicName
            user.profile.nickName = params.nickName
			
            if (bytes != null && bytes.length > 0) {
                if (imageService.isThumbnail(bytes)) {
                    user.profile.mimeType = contentType
                    user.profile.image = bytes
                } else {
                    user.profile.mimeType = "image/jpeg"
                    user.profile.image = imageService.profileThumbnail(bytes,contentType)
                }
            }
            if(!user.profile.hasErrors() && user.profile.save()) {
                flash.message = "profile.save.success"
                redirect(action:'manage',id:user.id)
            }
            else {
                flash.message = "profile.save.error"
                flash.isError=true
                render(view:'edit',model:[user:user])
            }
        } catch (error) {
            log.error "Unable to save profile changes", error
            flash.message = "profile.save.error"
            flash.isError = true
            render(view:'edit',model:[user:user])
        }
    }	
}
