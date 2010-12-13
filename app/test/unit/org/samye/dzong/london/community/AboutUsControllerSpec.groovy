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

import grails.test.*
import grails.plugin.spock.*

import org.samye.dzong.london.cms.*
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.events.Event
import org.samye.dzong.london.community.Teacher
import org.samye.dzong.london.users.ShiroUser
import org.samye.dzong.london.venue.*
import org.samye.dzong.london.events.Event
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.site.Setting
import org.samye.dzong.london.community.Teacher
import org.samye.dzong.london.site.Link
import org.samye.dzong.london.media.*

/*
 * Unit test for About Us content controller.
 *
 * @author Leanne Northrop
 * @since  6th December 2010, 18:09
 */
class AboutUsControllerSpec extends ControllerSpec {

    def 'Index redirects to home'() {
        when:
        controller.index()

        then:
        forwardArgs == [action: 'home']
    }
    
    def 'Section name is AboutUs'() {
        expect
        'AboutUs' == controller.sectionName
    } 
    
    def 'navigation objects are present'() {
        setup: "When model and about us featured articles are present"
        def model = [:]
        stubFinderMethods(["AboutUsFeaturedArticles"])
        
        and: "Visiting,lineage and center teachers are present"
        def visiting = new Teacher(type:'V')
        def lineage = new Teacher(type:'L')        
        def centers = new Teacher(type:'C')                
        controller.metaClass.publishedTeachers = { params-> [teachers:[visiting,lineage,centers]] }  
        
        and: "Venue is present"
        def venue = new Venue(name:"My Place")
        controller.metaClass.publishedVenues = {params-> [venues: [venue]] }
        
        when:
        controller.populateNavigationObject(model)
        
        then:
        model.visitingTeachers == [visiting]
        model.lineage == [lineage]        
        model.teachers == [centers]                
        model.venues == [venue]                        
        model.featuredArticles == []
    }
    
    def 'section page featches home and featured articles, teachers, events and slideshow album'() {
        setup:
        setupNavObjects()
        stubFinderMethods(["AboutUsHomeArticles", "AboutUsFeaturedArticles"])
        stubCMSMethods(["Teachers","Venues"])
        mockDomain(Teacher)
        //mockDomain(Link)
        mockDomain(Article)        

        when:
        def model = controller.home()

        then:
        model.homeArticles == [] 
        model.featuredArticles == [] 
        model.teachers == [] 
    }

    def 'list should fetch all AboutUs content'() {
        setup:
        setupNavObjects()        
        stubFinderMethods(["AboutUsAllArticles"])

        when:
        def model = controller.list()

        then:
        model.allArticles == [] 
    }

    def 'view should return id of requested article'() {
        setup:
        setupNavObjects()        
        stubViewMethods(["Article"])
        mockParams << [id:1]

        when:
        def model = controller.view()

        then:
        model.id == 1
    }

    def 'contactUs should return venues'() {
        setup:
        setupNavObjects()        
        def venue = new Venue(name:"My Place")
        controller.metaClass.publishedVenues = {params-> [venues: [venue]] }

        when:
        controller.contactUs()

        then:
        controller.modelAndView.viewName == 'contact'
        controller.modelAndView.model.linkedHashMap.venues == [venue] 
    }
    

    def 'venue should return all venues when no id param supplied'() {
        setup:
        setupNavObjects()        
        def venue = new Venue(name:"My Place")
        controller.metaClass.publishedVenues = {params-> [venues: [venue]] }

        when:
        controller.venue()

        then:
        controller.modelAndView.viewName == 'visiting'
        controller.modelAndView.model.linkedHashMap.venues == [venue] 
    }   
    
    
    def 'venue should return all venues when id param supplied'() {
        setup:
        setupNavObjects()        
        def venue = new Venue(name:"My Place")
        controller.metaClass.publishedVenues = {params-> [venues: [venue]] }
        mockParams << [id:1]
        
        when:
        controller.venue()

        then:
        controller.modelAndView.viewName == 'visiting'
        controller.modelAndView.model.linkedHashMap.venues == [venue] 
        controller.flash.id == 1
    }        
    
    def 'centers returns content with the tag "centers"'() {
        setup:
        setupNavObjects()
        def articles = [new Article()]
        def receivedParam = ''

        when:
        controller.centers()

        then:
        receivedParam == 'centers'
        controller.modelAndView.viewName == 'centers'
        controller.modelAndView.model.linkedHashMap.articles == articles 
        controller.flash.title == "Our Centers"
    }
    
    def 'lineage returns content with the tag "lineage"'() {
        setup:
        setupNavObjects()
        def articles = [new Article()]
        def receivedParam = ''

        when:
        controller.lineage()

        then:
        receivedParam == 'lineage'        
        controller.modelAndView.viewName == 'teachers'
        controller.modelAndView.model.linkedHashMap.articles == articles 
        controller.flash.title == "Lineage Teachers"
    }
    
    def 'teachers returns content with the tag "center teachers"'() {
        setup:
        setupNavObjects()
        def articles = [new Article()]
        def receivedParam = ''

        when:
        controller.teachers()

        then:
        receivedParam == 'center teachers'         
        controller.modelAndView.viewName == 'teachers'
        controller.modelAndView.model.linkedHashMap.articles == articles 
        controller.flash.title == "Center Course Leaders and Teachers"
    }    
    
    def 'visiting returns content with the tag "visiting teachers"'() {
        setup:
        setupNavObjects()
        def articles = [new Article()]
        def receivedParam = ''

        when:
        controller.visiting()

        then:
        receivedParam == 'visiting teachers'         
        controller.modelAndView.viewName == 'teachers'
        controller.modelAndView.model.linkedHashMap.articles == articles 
        controller.flash.title == "Visiting Course Leaders and Teachers"
    }   
    
    def 'roomHire returns published content with the title like Room Hire%'() {
        setup:
        setupNavObjects()
        def testInstances = [] 
        mockDomain(Article, testInstances)
        newArticle("Somewhere",true).save()        
        def rh = newArticle("Room Hire",true)
        rh.save()   

        when:
        controller.roomHire()

        then:
        controller.modelAndView.viewName == 'roomHire'
        controller.modelAndView.model.linkedHashMap.article == rh        
    }   
        
            
    def newArticle(title,published=false,type='M') {
        def article = new Article(title:title,summary:"summary",publishState:(published ? "Published" : "Unpublished"),category:type,content:'',deleted:false,home:false,featured:false)
        article.validate()
        println article.errors
        article.save(flush:true)
        article
    }
    
    def stubFinderMethods(list) {
        list.each {
            controller.metaClass."findPublished${it}" = {params-> 
                def name = it - "AboutUs"
                name = name.substring(0,1).toLowerCase() + name.substring(1)
                [(name):[]]
            }            
        }      
        list.each {
            controller.metaClass."published${it}s" = {params-> 
                def name = it.toLowerCase() + "s"
                def thing = new Expando(list:{p -> [new Publishable()]})
                thing
            }            
        }           
    }
     
    
    def stubCMSMethods(list) {
        list.each {
            controller.metaClass."published${it}" = {params-> 
                [(it.toLowerCase()):[]]
            }            
        }        
    }
            
    def stubViewMethods(list) {
        list.each {
            controller.metaClass."view${it}" = {id-> 
                [(it.toLowerCase()): null, id: id, similar:[]]
            }            
        }        
    }  
    
    def setupNavObjects() {
        stubFinderMethods(["AboutUsFeaturedArticles"])
        
        def visiting = new Teacher(type:'V')
        def lineage = new Teacher(type:'L')        
        def centers = new Teacher(type:'C')                
        controller.metaClass.publishedTeachers = { params-> [teachers:[visiting,lineage,centers]] }  
        
        def venue = new Venue(name:"My Place")
        controller.metaClass.publishedVenues = {params-> [venues: [venue]] }        
    }  
}
