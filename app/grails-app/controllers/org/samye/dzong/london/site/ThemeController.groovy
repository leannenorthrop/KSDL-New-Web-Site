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

package org.samye.dzong.london.site

import org.samye.dzong.london.Publishable
import org.samye.dzong.london.Setting
import com.lucastex.grails.fileuploader.UFile
import org.apache.commons.io.*

class ThemeController {

	def index = {
        redirect(action: "setdefault")
    }

	def manage = {
        return render(view:'setDefault')
    }
    
    def setdefault = {
        return render(view:'setDefault')
    }

	def save = {
		def defaultCSSThemeSetting = Setting.findByName('DefaultTheme')
		if (!defaultCSSThemeSetting) {
			defaultCSSThemeSetting = new Setting(name: 'DefaultTheme', value: params.theme)
		} else {
			defaultCSSThemeSetting.value = params.theme
		}
		if (!defaultCSSThemeSetting.hasErrors() && defaultCSSThemeSetting.save()) {
		    Publishable.allPublished.list().each { publishable ->
		        try {
		            publishable.lastUpdated = new Date()
		            publishable.save()
		        } catch (error) {
		            log.warn("Saving new modified time failed.",error)
		        }
		    }
            flash.message = "Web-Site Theme is now ${defaultCSSThemeSetting.value}"
            redirect(controller: 'manageSite', action: 'info')
        }
        else {
            redirect(action: "setdefault")
        }		
	}
	
    def add = {
        if (!flash.message) {
            flash.message = "You may use this page to upload new Themes as a zip file which contains <emph>at least</emph> one CSS file at the top-level called screen.css. The name of the zip file will be used as the name of the Theme and should not contain any numbers or punctuation. You may include other files within the zip file which are included via screen.css such as images, fonts, or other CSS files. Maximum zip file size is 4Mb"
        }
        render(view: 'newTheme')
    }
	
    def install = {
        def uploadedFile = UFile.findById(params.ufileId)
        if (uploadedFile) {
            def zipToUnzip = new File(uploadedFile.path)
			def cssDir = new File(servletContext.getRealPath('/css/themes'), FilenameUtils.getBaseName(zipToUnzip.name))
            zipToUnzip.unzip(cssDir.absolutePath)

			def path = FilenameUtils.getFullPathNoEndSeparator(zipToUnzip.absolutePath) 
			try {
				FileUtils.deleteDirectory(new File(path))
			} catch (error) {
				log.warn "Unable to delete directory " + path, error
			}
            flash.message = "theme.installed"
            flash.args = [cssDir.name]
            redirect(action: 'manage')
        } else {
            redirect(controller: 'theme', action: 'error')
        }
    }

    def error = {
        flash.isError = true
        flash.message = "theme.upload.error"
        redirect(controller: 'manageSite', action: 'error')
    }	
}
