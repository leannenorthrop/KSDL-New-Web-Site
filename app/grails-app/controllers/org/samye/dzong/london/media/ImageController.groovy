

package org.samye.dzong.london.media


class ImageController {
    def imageService

    def src = {
        def imageInstance
        try {
            imageInstance = Image.get( params.id )
        } catch (error) {
            imageInstance = Image.findByName( params.id )
        }
        if(!imageInstance) {
            println "no image ${params.id}"
            response.outputStream << ""
        } else {
			try {
				if (request.getDateHeader("If-Modified-Since") >= imageInstance.lastUpdated.time) {
					response.setStatus(304)
					response.flushBuffer()
					return
				}				
				response.setContentType(imageInstance.mimeType)
				response.setDateHeader('Last-Modified', imageInstance.lastUpdated.time)
				response.setHeader("Cache-Control", "public")			
				response.setHeader("ETag", "W/\"" + imageInstance.version + "\"")
	 			byte[] image = imageInstance.image
				response.setContentLength(image.size())			
				response.outputStream << image
			} catch(error) {
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
            println "no image ${params.id}"
            response.outputStream << ""
        }
        else {
			try {
				if (request.getDateHeader("If-Modified-Since") >= imageInstance.lastUpdated.time) {
					response.setStatus(304)
					response.flushBuffer()
					return					
				}				
				response.setContentType(imageInstance.mimeType)
				response.setDateHeader('Last-Modified', imageInstance.lastUpdated.time)
				response.setHeader("Cache-Control", "public")			
				response.setHeader("ETag", "W/\"" + imageInstance.version + "\"")
	 			byte[] image = imageInstance.thumbnail
				response.setContentLength(image.size())			
				response.outputStream << image
			} catch(error) {
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
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "Image not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def imageInstance = Image.get( params.id )

        if(!imageInstance) {
            flash.message = "Image not found with id ${params.id}"
            redirect(action:manage)
        }
        else {
            return [ imageInstance : imageInstance, id: imageInstance.id ]
        }
    }

    def update = {
        def imageInstance = Image.get( params.id )
        if(imageInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(imageInstance.version > version) {
                    imageInstance.errors.rejectValue("version", "image.optimistic.locking.failure", "Another user has updated this Image while you were editing.")
                    render(view:'edit',model:[imageInstance:imageInstance, id: params.id])
                    return
                }
            }
            if (!params.tags || params.tags == []) {
                flash.message = "Labels can not be empty"
                flash.isError = true
                flash.args = [params.name]
                render(view:'edit',model:[imageInstance:imageInstance, id: params.id])
            }

            def image = imageInstance.image
            imageInstance.properties = params
            imageInstance.image = image
            if (!params.tags || params.tags == "") {
                flash.isError = true
                flash.message = "Please enter at least one label."
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
                flash.message = "Image ${params.name} updated"
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
        return ['imageInstance':imageInstance]
    }

    def save = {
        def f = request.getFile('image')
        def contentType = f?.getContentType()
        def bytes = f?.getBytes()


        def thumbnail
        try {
            if (contentType.toLowerCase().endsWith("png")) {
                bytes = imageService.pngToJpg(bytes)
                thumbnail = imageService.thumbnail(bytes)
            } else {
                thumbnail = imageService.thumbnail(bytes)
            }
        } catch(error) {
            thumbnail = []
            contentType='image/png'
        }

        def imageInstance = new Image(params)
        imageInstance.thumbnail = thumbnail
        imageInstance.mimeType = contentType
        imageInstance.image = imageService.read(imageInstance.image, imageInstance.mimeType)
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
                    thumbnail = imageService.thumbnail(bytes)
                } else {
                    thumbnail = imageService.thumbnail(bytes)
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
