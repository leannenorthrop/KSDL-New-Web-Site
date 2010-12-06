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

import org.samye.dzong.london.*
import grails.test.*
import grails.plugin.spock.*
import org.samye.dzong.london.community.*
import org.apache.shiro.SecurityUtils

/**
 * Integration test for CMSUtil (situated under src/groovy). 
 *
 * @author Leanne Northrop
 * @since  16th November, 2010, 16:55
 */
class CMSUtilSpec extends IntegrationHelper {
    def user
    def roles = []
    def grailsApplication

    def prepare() {
        CMSUtil.addCMSMethods(String)
        CMSUtil.addFinderMethods(String)        
        clean()
        user = newUser()
        SecurityUtils.metaClass.static.getSubject = {
            return [hasRoles: { r -> roles.intersect(r)}, principal: user.username]
        }
    }

    def domainClassesToTest() {
        CMSUtil.GRAILS_APPLICATION.domainClasses.findAll { Publishable.isAssignableFrom(it.clazz) && it.name != 'Product' && it.name != 'NonDownloadable' && it.name != 'Download' && it.name != 'Publishable'}.collect{it.name}
    }

    def 'viewDomains'() {
        given:
        println "Testing ${domain}..."
        prepare()
        def obj = this."new${domain}"("Testing view",true)
    
        when:
        def results = 'a string'."view${domain}"(obj.id)
        
        then:
        results."${domain.toLowerCase()}" == obj 

        where:
        domain << domainClassesToTest()
    }
  
    def 'userUnpublished'() {
        given:
        println "Testing ${domain}..."
        prepare()
        def obj = this."new${domain}"("Testing ${domain} userUnpublished",false)
        obj.author = user
        obj.save(flush:true)
    
        when:
        def results = 'a string'."userUnpublished${domain}s"([sort:'author',order:'asc'])
       
        then:
        results.total == 1

        where:
        domain << domainClassesToTest()
    }
    
  
    def 'userPublished'() {
        given:
        println "Testing ${domain}..."
        prepare()
        def obj = this."new${domain}"("Testing ${domain} userPublished",true)
        obj.author = user
        obj.save(flush:true)
    
        when:
        def results = 'a string'."userPublished${domain}s"([sort:'author',order:'asc'])
        
        then:
        results.total == 1

        where:
        domain << domainClassesToTest()
    } 
    
    def 'userArchived'() {
        given:
        println "Testing ${domain}..."
        prepare()
        def obj = this."new${domain}"("Testing ${domain} userArchived",true)
        obj.author = user
        obj.publishState = 'Archived'
        obj.save()
    
        when:
        def results = 'a string'."userArchived${domain}s"([sort:'author',order:'asc'])
        
        then:
        results.total == 1

        where:
        domain << domainClassesToTest()
    } 
    
    def 'userDeletedArticles'() {
        given:
        println "Testing ${domain}..."
        prepare()
        def obj = this."new${domain}"("Testing ${domain} Deleted",true)
        obj.author = user
        obj.deleted = true
        obj.save()
    
        when:
        def results = 'a string'."userDeleted${domain}s"([sort:'author',order:'asc'])
        
        then:
        results.total == 1

        where:
        domain << domainClassesToTest()
    }   
    
    def 'unpublished'() {
        given:
        println "Testing ${domain}..."
        prepare()
        def obj = this."new${domain}"("Testing ${domain} Unpublished",false)
        obj.save(flush:true)
    
        when:
        def results = 'a string'."unpublished${domain}s"([sort:'author',order:'asc'])
        
        then:
        results.total == 1

        where:
        domain << domainClassesToTest()
    }
    
  
    def 'published'() {
        given:
        println "Testing ${domain}..."
        prepare()
        def obj = this."new${domain}"("Testing ${domain} Published",true)
        obj.save(flush:true)
    
        when:
        def results = 'a string'."published${domain}s"([sort:'author',order:'asc'])
        
        then:
        results.total == 1

        where:
        domain << domainClassesToTest()
    } 
    
    def 'archived'() {
        given:
        println "Testing ${domain}..."
        prepare()
        def obj = this."new${domain}"("Testing ${domain} Archived",true)
        obj.publishState = 'Archived'
        obj.save(flush:true)
    
        when:
        def results = 'a string'."archived${domain}s"([sort:'author',order:'asc'])
        
        then:
        results.total == 1

        where:
        domain << domainClassesToTest()
    }
    
    def 'deleted'() {
        given:
        println "Testing ${domain}..."
        prepare()
        def obj = this."new${domain}"("Testing ${domain} Deleted",true)
        obj.deleted = true
        obj.save()
    
        when:
        def results = 'a string'."deleted${domain}s"([sort:'author',order:'asc'])
        
        then:
        results.total == 1

        where:
        domain << domainClassesToTest()
    }   
    
    def 'findPublishedBuddhistHome'() {
        given:
        println "Testing ${domain}..."
        prepare()
        def obj = this."new${domain}"("Testing ${domain} findPublishedMeditationHome",true)
        obj.author = user
        obj.home = true
        obj.save(flush:true)
            
        when:
        def results = 'a string'."findPublishedMeditationHome${domain}s"([sort:'author',order:'asc'])
        
        then:
        results."${names.value}" == 1
        results."${names.key}" != null

        where:
        domain << ['Article','Event']
        names << ['articles':'totalHomeArticles', 'events':'totalHomeEvents']
    }                  
}

