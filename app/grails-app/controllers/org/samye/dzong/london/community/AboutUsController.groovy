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

package org.samye.dzong.london.community

import org.samye.dzong.london.cms.*
import org.samye.dzong.london.venue.Venue
import org.samye.dzong.london.venue.Room

/*
 * About Us content url handler. Displays only public facing pages.
 *
 * @author Leanne Northrop
 * @since  Feburary, 2010
 */
class AboutUsController extends PublicSectionPageController  {
    def articleService
    def teacherService
    
    AboutUsController() {
        CMSUtil.addCMSMethods(this)
        CMSUtil.addFinderMethods(this)        
    }
    
    def getSectionName() {
        "AboutUs"
    }

    def index = {
        redirect(action:home)
    }
            
    def home = {
        def model = [:]         
        addPublishedContent(["AboutUsHomeArticles", "AboutUsFeaturedArticles"],model)
        populateNavigationObject(model)
        articleService.addHeadersAndKeywords(model,request,response)
        render(view:'index', model:model)
    }

    def contactUs = {
        def model = publishedVenues()
        populateNavigationObject(model)    
        articleService.addHeadersAndKeywords(model,request,response)
        render(view: 'contact', model:model)
    }
	
    def venue = {
        def model = publishedVenues()
        populateNavigationObject(model)          
        articleService.addHeadersAndKeywords(model,request,response)
        if (params.id) {
            flash.id = params.id
        }
        render(view:'visiting',model:model)
    }

    def centers = {
        def model = [:]
        populateNavigationObject(model)
        def lineageArticles = []
        try {
            lineageArticles = articleService.findByTag('centers',[])
        } catch (error) {
            log.error("AboutUs controller encountered an error.",error)
        }
        model.put('articles',lineageArticles)
        flash.title="Our Centers"                        
        
        articleService.addHeadersAndKeywords(model,request,response)
        render(view:'visiting',model:model)
    }
    	
    def lineage = {
        def model = [:]
        populateNavigationObject(model)
        model.put('teachers',model.lineage)
        def lineageArticles = []
        try {
            lineageArticles = articleService.findByTag('lineage',[])
        } catch (error) {
            log.error("AboutUs controller encountered an error.",error)
        }
        model.put('articles',lineageArticles)
        flash.title="Lineage Teachers"                        
        
        articleService.addHeadersAndKeywords(model,request,response)
        render(view:'teachers',model:model)
    }
	
    def teachers = {
        def model = [:]
        populateNavigationObject(model)
        model.put('teachers',model.teachers)
        def lineageArticles = []
        try {
            lineageArticles = articleService.findByTag('center teachers',[])
        } catch (error) {
            log.error("AboutUs controller encountered an error.",error)
        }
        model.put('articles',lineageArticles)
        flash.title="Center Course Leaders and Teachers"               
        
        articleService.addHeadersAndKeywords(model,request,response)
        render(view:'teachers',model:model)
    }

    def visiting = {      
        def model = [:]
        populateNavigationObject(model)
        model.put('teachers',model.visitingTeachers)
        def lineageArticles = []
        try {
            lineageArticles = articleService.findByTag('visiting teachers',[])
        } catch (error) {
            log.error("AboutUs controller encountered an error.",error)
        }
        model.put('articles',lineageArticles)
        flash.title="Visiting Course Leaders and Teachers"              
        
        articleService.addHeadersAndKeywords(model,request,response)
        render(view:'teachers',model:model)
    }
    
    def view = {
        def model = viewArticle(params.id)          
        populateNavigationObject(model)
        articleService.addHeadersAndKeywords(model,request,response)
        return model
    }
    
    def list = {
        def model = [:] 
        def sectionName = getSectionName()
        addPublishedContent(["${sectionName}AllArticles"],model)
        populateNavigationObject(model)
        articleService.addHeadersAndKeywords(model,request,response)
        model
    }
    
    def populateNavigationObject(model) {
        def publishedTeachers = publishedTeachers([sort: "name", order: "asc"]).'teachers'
        def visitingTeachers = publishedTeachers.findAll {it.type == 'V'}
        model.put('visitingTeachers',visitingTeachers)                
        def teachers = publishedTeachers.findAll {it.type == 'C'}
        model.put('teachers',teachers)
        def lineage = publishedTeachers.findAll {it.type == 'L'}
        model.put('lineage',lineage)                
        def venues = publishedVenues().'venues'
        model.put('venues',venues)  
        def lineageArticles = [] 
        addPublishedContent(["AboutUsFeaturedArticles"],model)                     
    }    
}
