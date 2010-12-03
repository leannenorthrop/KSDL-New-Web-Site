package org.samye.dzong.london.media

import com.lucastex.grails.fileuploader.UFile
import org.apache.commons.io.IOUtils

class FileController {

    static allowedMethods = [delete:'GET']

    def manage = {
		def files = UFile.list()
        render(view:'manage',model:[files: files])
    }

	def create = {
	    if (!flash.message) {
	        flash.message = "You may use this page to upload new files. Maximum file size is 12Mb"
        }
		[]
	}
	
	def delete = {
        def uploadedFile = UFile.findById(params.id)
        if (uploadedFile) {
            try{
                uploadedFile.delete()
            } catch(error) {
                log.error error
            }
            flash.message = "file.deleted"
            flash.args = [uploadedFile.name]
        } else {
            flash.message = "file.delete.error"
            flash.args = [uploadedFile.name]
            flash.isError = true            
        }
        redirect(controller: 'file', action: 'manage')        
	}	

    def install = {
        def uploadedFile = UFile.findById(params.ufileId)
        if (uploadedFile) {
            flash.message = "file.installed"
            flash.args = [uploadedFile.name]
        } else {
            flash.message = "file.install.error"
            flash.args = [uploadedFile.name]
            flash.isError = true            
        }
        redirect(controller: 'file', action: 'manage')        
    }

    def error = {
        flash.isError = true
        def msg = flash.message
        flash.message = msg
        redirect(controller: 'file', action: 'create')
    }

	def src = {
        def coder = new org.apache.commons.codec.net.URLCodec()
        def id2 = coder.decode(params.id) 	    
		def fileInstance = UFile.findByName(id2)
        if(!fileInstance) {
            log.warn "no file found for ${params.id}"
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
				def f = new File(fileInstance.path)
				def is = f.newInputStream()
				IOUtils.copy(is,os)
			} catch(error) {
				log.error error
			}
        }		
	}

}
