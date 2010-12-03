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

import org.apache.shiro.crypto.hash.Md5Hash
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.*
import static groovyx.net.http.ContentType.*
import groovyx.net.http.HttpURLClient
import groovyx.net.http.URIBuilder

/*
 * Provides a wrapper api for FlickR rest api.
 * 
 * @author Leanne Northrop
 * @since 1.0.9-SNAPSHOT, June 2010
 */
class Flickr {
	def static flickr = new HTTPBuilder('http://api.flickr.com')
	static String secret = "d38a30fcb8ec0082"
	static String key = "3d3acbb1aa734ee0d78affb39e4eeb01"
	static String frob = ""
	static String token = ""
	    
    def getPhotosets(userid) {
        try {
		    def result = flickr.get( path : '/services/rest/',
    		                         query: getPhotoSetListParams(userid),
    		                         contentType : TEXT,
    		                         headers : [Accept : 'application/xml'] )

    		def sluper = new XmlSlurper()
    		def f = result.getText()
    		def xml = sluper.parseText(f)
    		if (xml.@stat == 'ok') {
    			def photosets = xml.photosets.photoset
    			log.debug "Loading all photosets ${photosets}"
                cache.loadAll(photosets)
    		} 
	    } catch(error) {
	    }        
    }
    
    def getPhotoset(id) {  
    	def album = new Expando()
    	try {
    		def info = flickr.get( path : '/services/rest/',
    		                       query: getPhotosetInfoParams(id),
    		                       contentType : TEXT,
    		                       headers : [Accept : 'application/xml'] )
    		def sluper = new XmlSlurper()
    		def infoTxt = info.getText()
    		def infoXml = sluper.parseText(infoTxt)
    		if (infoXml.@stat == 'ok') {
    			album.title = infoXml.photoset.title
    			album.description = infoXml.photoset.description
    		    album.name = infoXml.photoset.title
    			album.albumId = infoXml.photoset.@id
    		    album.toString = {""} //"$name ($albumId)" }            			
    		} else {
    			album.title = ""
    			album.description = ""
    		    album.name = infoXml.photoset.title
    			album.albumId = infoXml.photoset.@id
    		    album.toString = {""}//"$name ($albumId)" }             			
    			System.out.println "Unable get photoset information " + xml.err.@code + " " + xml.err.@msg
    		}	

    		def result = flickr.get( path : '/services/rest/',
    		                       query: getPhotosetParams(id),
    		                       contentType : TEXT,
    		                       headers : [Accept : 'application/xml'] )


    		def xml = sluper.parseText(result.getText())
    		if (xml.@stat == 'ok') {
    			def photos = xml.photoset.photo

    			album.images = photos.collect { photo ->
    			    def image = new Expando()
    			    image.name = photo.@title
    			    image.thumbnail = photo.@url_sq
    			    image.src = photo.@url_o
    			    image.width = photo.@width_o
    			    image.height = photo.@height_o
    			    image.isAlbumCover = photo.@isprimary == 1
    			    if (image.isAlbumCover) {
    			        album.src = photo.@url_sq
    			        album.image = image
    		        }
    			    image.toString = { ""}//"$name" }
    			    image
    			}
    		} else {
    			album.images = []
    			System.out.println "Unable get photoset photos " + xml.err.@code + " " + xml.err.@msg
    		}
        } catch(error) {
            album.images = []
        }   
        album                   
    }
            
	def requestUserPermission() {
		def result = flickr.get( path : '/services/rest/',
		                       query: getFrobParams(),
		                       contentType : TEXT,
		                       headers : [Accept : 'application/xml'] )
		def f = result.getText()
		def sluper = new XmlSlurper()
		def xml = sluper.parseText(f)
		frob = xml.frob
		def uri = new URIBuilder('http://flickr.com/services/auth/').setQuery(getAuthParams('read', frob))
		return uri.toString()
	}
	
	def authenticate() {
		if (frob.equals("")) {
			requestUserPermission()
		}
		
		if (token.equals("")) {
			refreshToken()		
		}
				
		def result = flickr.get( path : '/services/rest/',
		                       query: getCheckTokensParams(),
		                       contentType : TEXT,
		                       headers : [Accept : 'application/xml'] )
		def f = result.getText()
		def sluper = new XmlSlurper()
		def xml = sluper.parseText(f)
		if (xml.@stat != 'ok') {
			log.warn "Authentication token expired " + xml.err.@code + " " + xml.err.@msg
			refreshToken()			
		}
	}

	def refreshToken() {
		def result = flickr.get(path : '/services/rest/',
	                       query: getTokenParams(),
	                       contentType : TEXT,
	                       headers : [Accept : 'application/xml'] )
		def f = result.getText()
		def sluper = new XmlSlurper()
		def xml = sluper.parseText(f)	
		if (xml.@stat == 'ok') {
			token = xml.auth.token
		} else {
			log.error "Unable to get new authentication token " + xml.err.@code + " " + xml.err.@msg
		}		
	}
	
	def getCheckTokensParams() {
		def params = ['api_key': key, 'auth_token': token, 'method':'flickr.auth.checkToken']
		return ['api_key': key, 'auth_token': token, 'method':'flickr.auth.checkToken', api_sig: sign(params)]		
	}
	
	def getFrobParams() {
		def params = ['api_key': key, 'method':'flickr.auth.getFrob']
		return ['api_key': key, 'method':'flickr.auth.getFrob', api_sig: sign(params)]		
	}
		
	def getAuthParams(perms, frob) {
		def params = ['api_key': key, 'perms': perms, 'frob': frob]
		return ['api_key': key, 'perms': perms, 'frob': frob, api_sig: sign(params)]		
	}
	
    def getTokenParams() {
	    println "frob " + frob
		def params = ['api_key': key, 'frob': frob, 'method' : 'flickr.auth.getToken']
		return ['api_key': key, 'frob': frob, 'method' : 'flickr.auth.getToken', api_sig: sign(params)]
    }

	def getPhotoSetListParams(user_id) {
		def params = ['api_key': key, 'user_id': user_id, 'method' : 'flickr.photosets.getList'] 
		return ['api_key': key, 'user_id': user_id, 'method' : 'flickr.photosets.getList', api_sig: sign(params)]
	}


	def getPhotosetInfoParams(photoset_id) {
		def params = ['api_key': key, 'photoset_id': photoset_id, 'method' : 'flickr.photosets.getInfo'] 
		return ['api_key': key, 'photoset_id': photoset_id, 'method' : 'flickr.photosets.getInfo', api_sig: sign(params)]
	}
	
	def getPhotosetParams(photoset_id) {
		def params = ['api_key': key, 'photoset_id': photoset_id, 'extras':'url_sq,url_t,url_s,url_m,url_o','media':'photos', 'method' : 'flickr.photosets.getPhotos'] 
		return [api_key: key, photoset_id: photoset_id, extras:'url_sq,url_t,url_s,url_m,url_o',media:'photos', method: 'flickr.photosets.getPhotos', api_sig: sign(params)]
	}
			
	
	def sign (params) {
		def tm = new TreeMap(params)
		def str = secret
		tm.each { key, value ->
			str += key + value
		}
		def hash = new Md5Hash(str)
		return hash.toString()
	}    
}
