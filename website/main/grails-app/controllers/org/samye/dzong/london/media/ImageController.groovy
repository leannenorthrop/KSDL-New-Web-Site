package org.samye.dzong.london.media

class ImageController {

    def index = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
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
}
