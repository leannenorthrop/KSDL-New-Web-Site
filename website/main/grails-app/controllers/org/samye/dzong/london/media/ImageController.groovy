

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
        }
        else {
            response.setContentType('image/png')
            byte[] image = imageInstance.image
            response.outputStream << image
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
            response.setContentType('image/jpeg')
            byte[] image = imageInstance.thumbnail
            response.outputStream << image
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
            def image = imageInstance.image
            imageInstance.properties = params
            imageInstance.image = image
            if (params.tags) {
                imageInstance.parseTags(params.tags)
            }
            if(!imageInstance.hasErrors() && imageInstance.save()) {
                flash.message = "Image ${params.id} updated"
                redirect(action:show,id:imageInstance.id)
            }
            else {
                render(view:'edit',model:[imageInstance:imageInstance, id: params.id])
            }
        }
        else {
            flash.message = "Image not found with id ${params.id}"
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
        def contentType = f.getContentType()
        def bytes = f.getBytes()
        def thumbnail = imageService.thumbnail(bytes)

        def imageInstance = new Image(params)
        imageInstance.thumbnail = thumbnail
        imageInstance.mimeType = contentType
        imageInstance.image = imageService.read(imageInstance.image, imageInstance.mimeType)
        if(!imageInstance.hasErrors() && imageInstance.save()) {
            if (params.tags) {
                imageInstance.parseTags(params.tags)
            }
            flash.message = "Image ${imageInstance.id} created"
            redirect(action:show,id:imageInstance.id)
        }
        else {
            render(view:'create',model:[imageInstance:imageInstance])
        }
    }
}
