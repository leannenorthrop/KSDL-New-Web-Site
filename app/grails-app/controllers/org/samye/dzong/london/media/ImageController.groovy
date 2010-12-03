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

/*
 * CMS content management url handler for managing all site images.
 * Displays both content management pages under the Image navigation
 * menu within the CMS area, and provides url endpoints for src and
 * thumbnail display.
 *
 * TODO: Complete internationalization.
 * TODO: DRY it up.
 *
 * @author Leanne Northrop
 * @since  October, 2009
 */
class ImageController {
    def imageService

    def src = {
        def imageInstance
        try {
            imageInstance = Image.get( params.id )
        } catch (error) {
            log.warn "Can't find image by id '${params.id}' so searching by name instead."
            def coder = new org.apache.commons.codec.net.URLCodec()
            def id2 = coder.decode(params.id)            
            imageInstance = Image.findByName(id2)
        }
        if(!imageInstance) {
            log.warn "no image ${params.id}"
            response.outputStream << ""
        } else {
            try {
                if (request.getDateHeader("If-Modified-Since") >= imageInstance.lastUpdated.time ||
                    request.getHeader("If-None-Match") == "W/\"${imageInstance.version}\"") {
                    response.setStatus(304)
                    response.flushBuffer()
                    return
                }
                response.setContentType(imageInstance.mimeType)
                response.setDateHeader('Last-Modified', imageInstance.lastUpdated.time)
                def now = new Date()
                now = now + 7
                response.setDateHeader('Expires', now.time)
                response.setHeader("Cache-Control", "public,max-age=604800,s-maxage=604800")
                response.setHeader("ETag", "W/\"${imageInstance.version}\"")
                byte[] image = imageInstance.image
                response.setContentLength(image.size())
                response.outputStream << image
            } catch(error) {
                log.error error
                response.outputStream << ""
            }
        }
    }

    def thumbnail = {
        def imageInstance
        try {
            imageInstance = Image.get( params.id )
        } catch (error) {
            imageInstance = Image.findByName( params.id )
        }
        if(!imageInstance) {
            log.warn "no image ${params.id}"
            response.outputStream << ""
        }
        else {
            try {
                if (request.getDateHeader("If-Modified-Since") >= imageInstance.lastUpdated.time ||
                    request.getHeader("If-None-Match") == "W/\"${imageInstance.version}\"") {
                    response.setStatus(304)
                    response.flushBuffer()
                    return
                }
                def mimeType = imageInstance.mimeType.toLowerCase()
                if (mimeType.contains("svg")) {				
                    response.setContentType(imageInstance.mimeType)
                } else {
                    response.setContentType('image/jpeg')
                }
                response.setDateHeader('Last-Modified', imageInstance.lastUpdated.time)
                def now = new Date()
                now = now + 7
                response.setDateHeader('Expires', now.time)
                response.setHeader("Cache-Control", "public,max-age=604800,s-maxage=604800")
                response.setHeader("ETag", "W/\"${imageInstance.version}\"")
                def thumbnail = imageInstance.thumbnail
                response.setContentLength(thumbnail.size())
                response.outputStream << thumbnail
            } catch(error) {
                log.error error
                response.outputStream << ""
            }
        }
    }

    def index = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  1000)
        if (params.tags){
            log.debug("Fetching tagged images " + params.tags)
            def tags = params.tags.toLowerCase().split(",").toList()
            def images = Image.findAllByTagInList(tags, params)
            def total = images.size()
            log.debug("Found " + total + " images")
            [ images: images, total: total]
        } else {
            log.debug("Fetching all images")
            def images = Image.list(params)
            def total = images.size()
            log.debug("Found " + total + " images")
            [ images: images, total: total ]
        }
    }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [save:'POST', update:'POST']

    def manage = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        flash.message = "image.manage.help"
        render(view:'list',model:[ imageInstanceList: Image.list( params ), imageInstanceTotal: Image.count() ])
    }

    def show = {
        def imageInstance = Image.get( params.id )

        if(!imageInstance) {
            flash.message = "Image not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ imageInstance : imageInstance, id: params.id ] }
    }

    def delete = {
        def imageInstance = Image.get( params.id )
        if(imageInstance) {
            try {
                imageInstance.delete(flush:true)
                flash.message = "Image ${params.id} deleted"
                redirect(action:manage)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Image ${params.id} could not be deleted"
                flash.isError = true
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "Image not found with id ${params.id}"
            flash.isError = true            
            redirect(action:list)
        }
    }

    def edit = {
        def imageInstance = Image.get( params.id )

        if(!imageInstance) {
            flash.message = "Image not found with id ${params.id}"
            flash.isError = true            
            redirect(action:manage)
        }
        else {
            return [ imageInstance : imageInstance, id: imageInstance.id ]
        }
    }
    
    def updateAllThumbnails = {
        def images = Image.list()
        images.each { imageInstance ->
            if(imageInstance && imageInstance.image) {
                log.info "Image instance image = ${imageInstance.image.length}"
                try {
                    def mimeType = imageInstance.mimeType.toLowerCase()
                    if (mimeType.contains("svg")){
                        flash.message = "SVG thumbnail ok."    
                        redirect(action:manage)
                    } else if (mimeType.endsWith("png") || mimeType.endsWith("gif")) {
                        def bytes = imageService.pngToJpg(imageInstance.image)
                        def thumbnail = imageService.thumbnail(bytes,mimeType)
                        imageInstance.image = imageInstance.image
                        imageInstance.thumbnail = thumbnail       
                        imageInstance.save()                         
                    } else {
                        def thumbnail = imageService.thumbnail(imageInstance.image,mimeType)
                        imageInstance.thumbnail = thumbnail       
                        imageInstance.image = imageInstance.image
                        imageInstance.save()                         
                    }
                } catch(error) {
                    log.error error
                }            
            } 
        } 
        flash.message = "Thumbnails updated"
        redirect(controller:'settings',action:'manage')
    }
    
    def update = {
        def imageInstance = Image.get( params.id )
        if(imageInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(imageInstance.version > version) {
                    flash.isError = true
                    flash.message = 'image.save.error'
                    imageInstance.errors.rejectValue("version", "image.optimistic.locking.failure", "Another user has updated this Image while you were editing.")
                    render(view:'edit',model:[imageInstance:imageInstance, id: params.id])
                    return
                }
            }
            if (!params.tags || params.tags == []) {
                flash.message = "image.tags.save.error"
                flash.isError = true
                flash.args = [params.name]
                render(view:'edit',model:[imageInstance:imageInstance, id: params.id])
            }

            def image = imageInstance.image
            imageInstance.properties = params
            imageInstance.image = image
            if (!params.tags || params.tags == "") {
                flash.isError = true
                flash.message = "image.tags.save.error"
                return render(view: 'edit', model: [imageInstance:imageInstance], id: params.id)
            } else if(!imageInstance.hasErrors() && imageInstance.save()) {
                def tags = imageInstance.tags
                def newtags = params.tags.split(',')
                tags.each {tag ->
                    def found = newtags.find {newtag -> newtag == tag}
                    if (!found) {
                        imageInstance.removeTag(tag)
                    }
                }
                imageInstance.addTags(newtags)
                flash.message = "image.updated"
                flash.args = [params.name]
                redirect(action:show,id:imageInstance.id)
            }
            else {
                flash.isError = true
                flash.message = "Image ${imageInstance.name} could not be updated due to an internal error. Please try again."
                flash.args = [params.name]
                render(view:'edit',model:[imageInstance:imageInstance, id: params.id])
            }
        }
        else {
            flash.message = "Image not found with id ${params.id}"
            flash.args = [params.name]
            flash.isError = true
            redirect(action:manage)
        }
    }

    def create = {
        def imageInstance = new Image()
        imageInstance.properties = params
        if (!flash.message) {
            flash.message = "image.create.help"
        }
        return ['imageInstance':imageInstance]
    }

    def save = {
        def f = request.getFile('image')
        def contentType = f?.getContentType()
        def bytes = f?.getBytes()

        def imageInstance = new Image(params)
        
        def thumbnail
        try {
            def mimeType = contentType.toLowerCase()
            if (mimeType.contains("svg")) {
                thumbnail = bytes                
            } else if (mimeType.endsWith("png") || mimeType.endsWith("gif")) {
                bytes = imageService.pngToJpg(bytes)
                thumbnail = imageService.thumbnail(bytes,mimeType)
                imageInstance.image = imageService.read(imageInstance.image, imageInstance.mimeType)                
            } else {
                thumbnail = imageService.thumbnail(bytes,mimeType)
                imageInstance.image = imageService.read(imageInstance.image, imageInstance.mimeType)                
            }
        } catch(error) {
            log.error "Unable to create thumbnail.", error
            thumbnail = []
            contentType='image/png'
        }

        imageInstance.thumbnail = thumbnail
        imageInstance.mimeType = contentType
        if(!imageInstance.hasErrors() && imageInstance.save()) {
            if (params.tags) {
                imageInstance.parseTags(params.tags)
            }
            flash.message = "Image ${imageInstance.name} created"
            flash.args = [imageInstance]
            redirect(action:manage, params:[offset:0,max:50])
        }
        else {
            flash.message = "Image can not be saved"
            flash.args = [imageInstance]
            flash.isError = true
            render(view:'create',model:[imageInstance:imageInstance])
        }
    }
    
    def createProductImage = {
        def f = request.getFile('image')     
        def name = f.getOriginalFilename().replace('.',' ')
        if (Image.findByName(name)) {
            def imageInstance = Image.findByName(name)
            render(contentType:"text/xml") {
                product{
                    image(name:imageInstance.name,id:imageInstance.id)
                }
            }            
        } else {
            def contentType = f?.getContentType()
            def bytes = f?.getBytes()

            def thumbnail
            try {
                if (contentType.toLowerCase().endsWith("png")) {
                    bytes = imageService.pngToJpg(bytes)
                    thumbnail = imageService.thumbnail(bytes,contentType)
                } else {
                    thumbnail = imageService.thumbnail(bytes,contentType)
                }
            } catch(error) {
                thumbnail = []
                contentType='image/png'
            }

            def imageInstance = new Image(params)
            imageInstance.name = name
            imageInstance.thumbnail = thumbnail
            imageInstance.mimeType = contentType
            imageInstance.image = imageService.read(imageInstance.image, imageInstance.mimeType)
            if(!imageInstance.hasErrors() && imageInstance.save()) {
                imageInstance.parseTags("product")
                render(contentType:"text/xml") {
                    product{
                        image(name:imageInstance.name,id:imageInstance.id)
                    }
                }
            }
            else {
                render(contentType:"text/xml") {
                    product {
                        image(name:imageInstance.name,id:-1)
                    }
                }
            }
        }
    }    
}
