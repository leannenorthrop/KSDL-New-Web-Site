import org.samye.dzong.london.Publishable

class PublicAreaFilters {
    def filters = {
		home(controller:'home', action:'*') {
			before = {
				if (Publishable.published().count() > 0) {
					def newestPublishedItem= Publishable.published().list()[0]
					if (request.getDateHeader("If-Modified-Since") >= newestPublishedItem.datePublished.time) {
						response.setStatus(304)
						response.flushBuffer()
						return				
					}
					response.setDateHeader('Last-Modified', newestPublishedItem.datePublished.time)
					response.setHeader("Cache-Control", "public")				
				}
			}
		}
		aboutUs(controller:'aboutUs', action:'*') {
			before = {
				if (Publishable.published().count() > 0) {
					def newestPublishedItem= Publishable.published().list()[0]
					if (request.getDateHeader("If-Modified-Since") >= newestPublishedItem.datePublished.time) {
						response.setStatus(304)
						response.flushBuffer()
						return				
					}
					response.setDateHeader('Last-Modified', newestPublishedItem.datePublished.time)
					response.setHeader("Cache-Control", "public")				
				}
			}
		}	
		aboutUs(controller:'events', action:'home') {
			before = {
				if (Publishable.published().count() > 0) {
					def newestPublishedItem= Publishable.published().list()[0]
					if (request.getDateHeader("If-Modified-Since") >= newestPublishedItem.datePublished.time) {
						response.setStatus(304)
						response.flushBuffer()
						return				
					}
					response.setDateHeader('Last-Modified', newestPublishedItem.datePublished.time)
					response.setHeader("Cache-Control", "public")				
				}
			}
		}		
		news(controller:'news', action:'*') {
			before = {
				if (Publishable.published().count() > 0) {
					def newestPublishedItem= Publishable.published().list()[0]
					if (request.getDateHeader("If-Modified-Since") >= newestPublishedItem.datePublished.time) {
						response.setStatus(304)
						response.flushBuffer()
						return				
					}
					response.setDateHeader('Last-Modified', newestPublishedItem.datePublished.time)
					response.setHeader("Cache-Control", "public")				
				}
			}
		}
		meditation(controller:'meditation', action:'*') {
			before = {
				if (Publishable.published().count() > 0) {
					def newestPublishedItem= Publishable.published().list()[0]
					if (request.getDateHeader("If-Modified-Since") >= newestPublishedItem.datePublished.time) {
						response.setStatus(304)
						response.flushBuffer()
						return				
					}
					response.setDateHeader('Last-Modified', newestPublishedItem.datePublished.time)
					response.setHeader("Cache-Control", "public")				
				}
			}
		}	
		buddhism(controller:'buddhism', action:'*') {
			before = {
				if (Publishable.published().count() > 0) {
					def newestPublishedItem= Publishable.published().list()[0]
					if (request.getDateHeader("If-Modified-Since") >= newestPublishedItem.datePublished.time) {
						response.setStatus(304)
						response.flushBuffer()
						return				
					}
					response.setDateHeader('Last-Modified', newestPublishedItem.datePublished.time)
					response.setHeader("Cache-Control", "public")				
				}
			}
		}	
		community(controller:'community', action:'*') {
			before = {
				if (Publishable.published().count() > 0) {
					def newestPublishedItem= Publishable.published().list()[0]
					if (request.getDateHeader("If-Modified-Since") >= newestPublishedItem.datePublished.time) {
						response.setStatus(304)
						response.flushBuffer()
						return				
					}
					response.setDateHeader('Last-Modified', newestPublishedItem.datePublished.time)
					response.setHeader("Cache-Control", "public")				
				}
			}
		}
		wellbeing(controller:'wellbeing', action:'*') {
			before = {
				if (Publishable.published().count() > 0) {
					def newestPublishedItem= Publishable.published().list()[0]
					if (request.getDateHeader("If-Modified-Since") >= newestPublishedItem.datePublished.time) {
						response.setStatus(304)
						response.flushBuffer()
						return				
					}
					response.setDateHeader('Last-Modified', newestPublishedItem.datePublished.time)
					response.setHeader("Cache-Control", "public")				
				}
			}
		}									
    }
}
