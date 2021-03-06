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
        def model = [:]         
        addPublishedContent(["AboutUsHomeArticles", "AboutUsFeaturedArticles"],model)
        def publishedTeachers = publishedTeachers([sort: "name", order: "asc"]).'teachers'
        def visitingTeachers = publishedTeachers.findAll {it.type == 'V'}
        model.put('visitingTeachers',visitingTeachers)                
        def teachers = publishedTeachers.findAll {it.type == 'C'}
        model.put('teachers',teachers)        
        def venues = publishedVenues().'venues'
        model.put('venues',venues)
        articleService.addHeadersAndKeywords(model,request,response)
        model
    }

    def contactUs = {
        def model = publishedVenues()
        articleService.addHeadersAndKeywords(model,request,response)
        render(view: 'contact', model:model)
    }
	
    def visiting = {
        def model = publishedVenues()
        articleService.addHeadersAndKeywords(model,request,response)
        model
    }
	
    def room = {
        def venues = publishedVenues().'venues'
        def model = [room:Room.get(params.id),venues:venues]
        articleService.addHeadersAndKeywords(model,request,response)
        model
    }
	
    def venue = {
        def model = publishedVenues()
        articleService.addHeadersAndKeywords(model,request,response)
        render(view:'visiting',model:model)
    }
	
    def lineage = {
        def lineageArticles = []
        def lineageTeachers = []
        try {
            lineageArticles = articleService.findByTag('lineage',[])
            def publishedTeachers = publishedTeachers([sort: "name", order: "asc"]).'teachers'
            lineageTeachers = publishedTeachers.findAll {it.type == 'L'}
        } catch (error) {
            log.error("AboutUs controller encountered an error.",error)
        }
		
        def model = [teachers:lineageTeachers,articles:lineageArticles];		
        articleService.addHeadersAndKeywords(model,request,response)
        model
    }
	
    def teachers = {
        def publishedTeachers = publishedTeachers([sort: "name", order: "asc"]).'teachers'
        def teachers = publishedTeachers.findAll {it.type == 'C'}
        def model = [teachers:teachers];		
        articleService.addHeadersAndKeywords(model,request,response)
        model
    }
	
    def roomHire = {
        def allArticles = Article.allAboutUsArticles("title", "asc").list()
        def article = allArticles.find { article -> article.title == 'Room Hire' }
        def venues = this.publishedVenues()['venues']
        def model = [article:article,venues:venues];		
        articleService.addHeadersAndKeywords(model,request,response)
        model
    }
}
