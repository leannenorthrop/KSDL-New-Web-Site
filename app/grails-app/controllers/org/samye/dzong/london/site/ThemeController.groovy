package org.samye.dzong.london.site

import org.samye.dzong.london.Setting
import com.lucastex.grails.fileuploader.UFile
import org.apache.commons.io.*

class ThemeController {

	def index = {
        redirect(action: "setdefault")
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
            flash.message = "Web-Site Theme is now ${defaultCSSThemeSetting.value}"
            redirect(controller: 'manageSite', action: 'info')
        }
        else {
            redirect(action: "setdefault")
        }		
	}
	
    def add = {
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
			uploadedFile.delete()
            flash.message = "theme.installed"
            flash.args = [cssDir.name]
            redirect(controller: 'manageSite', action: 'info')
        } else {
            redirect(controller: 'theme', action: 'error')
        }
    }

    def error = {
        flash.message = "theme.upload.error"
        redirect(controller: 'manageSite', action: 'error')
    }	
}
