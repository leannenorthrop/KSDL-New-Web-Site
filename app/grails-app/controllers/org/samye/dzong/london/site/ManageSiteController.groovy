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

import org.samye.dzong.london.users.*

/*
 * CMS area landing url handler. Would like to make it user specific to display
 * number of new articles, etc as well as tailoring welcome help text according
 * to user's roles.
 *
 * @author Leanne Northrop
 * @since  October 2010
 */
class ManageSiteController {

    def index = {
        redirect(action: "welcome")
    }
    
    def welcome = {
        return render(view:'welcome')
    }
        
    def landing = {
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
