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

package org.samye.dzong.london.cms

import org.samye.dzong.london.events.Event
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.site.Setting
import org.samye.dzong.london.community.Teacher
import org.samye.dzong.london.site.Link
import org.samye.dzong.london.cms.*

/*
 * Base class for public facing section pages.
 *
 * @author Leanne Northrop
 * @since  6th December 2010, 16:41
 */
class PublicSectionPageController {
    def flickrService
    
    PublicSectionPageController() {
        CMSUtil.addFinderMethods(this)        
    }
        
    // LN: For some reason this endpoint is not inherited
    def index = {
        forward(action:'home')
    }

    def list = {
        def model = [:] 
        def sectionName = getSectionName()
        addPublishedContent(["${sectionName}AllArticles"],model)
        model
    }

    def view = {
        def model = viewArticle(params.id)
        return model
    }

    def event = {
        def model = viewEvent(params.id)
        return model
    }

    def events = {
        def model = [:]
        def sectionName = getSectionName()        
        addPublishedContent(["${sectionName}AllEvents"],model,params)
        model
    }

    def slideshow = {
        [album:getAlbum()]
    }
    
    def teacher = {
        def model = viewTeacher(params.id)
        addPublishedContent(["AboutUsFeaturedArticles"],model)
        def publishedTeachers = publishedTeachers([sort: "name", order: "asc"]).'teachers'
        def visitingTeachers = publishedTeachers.findAll {it.type == 'V'}
        model.put('visitingTeachers',visitingTeachers)                
        def teachers = publishedTeachers.findAll {it.type == 'C'}
        model.put('teachers',teachers)
        def lineage = publishedTeachers.findAll {it.type == 'L'}
        model.put('lineage',lineage)                
        def venues = publishedVenues().'venues'
        model.put('venues',venues)        
        model		
    }
    
    def addPublishedContent(contentList,model,params=[sort:'datePublished',order:'desc']) {
        contentList.each {
            model.putAll("findPublished${it}"(params))
        }        
    }
    
    def getAlbum() {
        def sectionName = getSectionName()
        sectionName = sectionName.substring(0,1).toLowerCase() + sectionName.substring(1)
        def ss = Setting."${sectionName}Slideshow"().list()
        flickrService.getPhotoset(ss ? ss[0].value :'72157623174318636')          
    }
}
