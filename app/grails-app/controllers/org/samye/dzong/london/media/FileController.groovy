package org.samye.dzong.london.media

import com.lucastex.grails.fileuploader.UFile
import org.apache.commons.io.IOUtils

class FileController {

    static allowedMethods = [delete:'GET']

    def manage = {
		def files = UFile.list()
        render(view:'manage',model:[ files: files])
    }

	def create = {
		[]
	}

    def install = {
		println "ok"
        def uploadedFile = UFile.findById(params.ufileId)
        if (uploadedFile) {
            flash.message = "file.installed"
            flash.args = [uploadedFile.name]
            redirect(controller: 'manageSite', action: 'info')
        } else {
            redirect(controller: 'file', action: 'error')
        }
    }

    def error = {
        flash.message = "file.upload.error"
        redirect(controller: 'manageSite', action: 'error')
    }

	def src = {
		def imageInstance = UFile.findByName(params.id)
        if(!imageInstance) {
            println "no file found for ${params.id}"
            response.outputStream << ""
        } else {
	 		def os = response.outputStream
			try {
				if (request.getDateHeader("If-Modified-Since") > imageInstance.dateUploaded.time) {
					response.setStatus(304)
				}				
				//response.setContentType(imageInstance.mimeType)
				response.setDateHeader('Last-Modified', imageInstance.dateUploaded.time)
				response.setHeader("Cache-Control", "public")			
				response.setHeader("ETag", "W/\"" + imageInstance.id + "\"")
				response.setContentLength((int)imageInstance.size)			
				def f = new File(imageInstance.path)
				def is = f.newInputStream()
				IOUtils.copy(is,os)
			} catch(error) {
				println error
			}
        }		
	}

}
