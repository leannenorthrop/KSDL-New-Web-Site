package org.samye.dzong.london.media

import net.sf.ehcache.*
import net.sf.ehcache.util.*
import net.sf.ehcache.loader.*
import net.sf.ehcache.config.*
import static groovyx.net.http.ContentType.*
import net.sf.ehcache.store.*

class FlickrService {
    static final Object _lock = new Object();
    static transactional = false
    static Flickr flickr = new Flickr()
    
	def getPhotosets(userid) {
	    Cache cache = CacheManager.getInstance().getCache("Slideshow")
	    def photosets
	    def photosetElement = cache.get("photosets")
	    if (photosetElement == null || photosetElement.isExpired()) {
            try {
    		    def result = flickr.flickr.get( path : '/services/rest/',
        		                         query: flickr.getPhotoSetListParams(userid),
        		                         contentType : TEXT,
        		                         headers : [Accept : 'application/xml'] )

        		def sluper = new XmlSlurper()
        		def f = result.getText()
        		def xml = sluper.parseText(f)
        		if (xml.@stat == 'ok') {
        		    photosets = xml.photosets.photoset
        		    cache.put(new Element("photosets", photosets))
        		} 
    	    } catch(error) {
    	        log.error error
    	    } 	 
        } else {
            photosets = photosetElement.getObjectValue()
        }
        
	    def albums = photosets.collect { photoset -> 
		    def id = photoset.@id.toString()
		    def element = cache.get(id)
		    if (element == null || element.isExpired()) {
	            cache.put(new Element(id, flickr.getPhotoset(id)))
	        }
	        cache.get(id).getObjectValue()
		}
        				    	    
	    return albums
	}
	
	def getPhotosetCover(id) {	
	    Cache cache = CacheManager.getInstance().getCache("Slideshow")
	    def element = cache.get(id)
	    if (element == null || element.isExpired()) {
	        log.debug "Not in cache so load"
            cache.put(new Element(id, flickr.getPhotoset(id)))
        }
        cache.get(id).getObjectValue()
	}
	
	def getPhotoset(id) {
	    Cache cache = CacheManager.getInstance().getCache("Slideshow")
	    def element = cache.get(id)
	    if (element == null || element.isExpired()) {
            cache.put(new Element(id, flickr.getPhotoset(id)))
        }
        cache.get(id).getObjectValue()	    
	}

}
