package org.samye.dzong.london.media

import  org.samye.dzong.london.media.FlickrService

class SlideshowController {
	def flickrService
	
    def index = { 
		def images = flickrService.getPhotoset('72157623174318636')
		render(view: 'index', model:[images:images])
	}
	
	def frob = {
		log.error "flicker called me with " + params.frob
	}
}
