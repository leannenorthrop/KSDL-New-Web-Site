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

package org.samye.dzong.london.media

import com.lucastex.grails.fileuploader.UFile
import org.apache.commons.io.IOUtils

/*
 * CMS content management url handler for managing additional site files to be
 * embedded into textile marked-up content e.g. PDFs, non-YouTube video, etc.
 * Displays only content management pages under the Files navigation
 * menu within the CMS area.
 *
 * TODO: Complete internationalization.
 *
 * @author Leanne Northrop
 * @since  June, 2010
 */
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
                if (request.getDateHeader("If-Modified-Since") > fileInstance.dateUploaded.time) {
                    response.setStatus(304)
                }
                //response.setContentType(imageInstance.mimeType)
                response.setDateHeader('Last-Modified', fileInstance.dateUploaded.time)
                response.setHeader("Cache-Control", "public")
                response.setHeader("ETag", "W/\"" + fileInstance.id + "\"")
                response.setContentLength((int)fileInstance.size)
                def f = new File(fileInstance.path)
                def is = f.newInputStream()
                IOUtils.copy(is,os)

                fileInstance.downloads++;
                fileInstance.save();
            } catch(error) {
                log.error error
            }
        }		
    }
}
