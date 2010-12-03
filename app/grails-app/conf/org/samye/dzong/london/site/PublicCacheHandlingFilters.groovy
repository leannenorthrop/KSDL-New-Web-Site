package org.samye.dzong.london.site

import  org.samye.dzong.london.community.ArticleService

class PublicCacheHandlingFilters {
	def articleService
    def filters = {			
		home(controller:'home', action:'*') {
			before = {
				articleService.handleIfNotModifiedSince(request,response)
			}
		}
		aboutUs(controller:'aboutUs', action:'*') {
			before = {
				articleService.handleIfNotModifiedSince(request,response)
			}		
		}	
		aboutUs(controller:'events', action:'home') {
			before = {
				articleService.handleIfNotModifiedSince(request,response)
			}
		}		
		news(controller:'news', action:'*') {
			before = {
				articleService.handleIfNotModifiedSince(request,response)
			}
		}
		meditation(controller:'meditation', action:'*') {
			before = {
				articleService.handleIfNotModifiedSince(request,response)
			}
		}	
		buddhism(controller:'buddhism', action:'*') {
			before = {
				articleService.handleIfNotModifiedSince(request,response)
			}
		}	
		community(controller:'community', action:'*') {
			before = {
				articleService.handleIfNotModifiedSince(request,response)
			}
		}
		wellbeing(controller:'wellbeing', action:'*') {
			before = {
				articleService.handleIfNotModifiedSince(request,response)
			}		
		}									
    }
    
}
