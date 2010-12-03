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
 * Integration test for Publishable
 *
 * @author Leanne Northrop
 * @since 16th November, 2010, 16:55
 */
class CMSUtilSpec extends IntegrationHelper {
    def user
    def roles = []
    
    def setup() {
        clean()
        user = newUser()
        CMSUtil.addCMSMethods(String)
        SecurityUtils.metaClass.static.getSubject = {
            return [hasRoles: { r -> roles.intersect(r)}, principal: user.username]
        }
    }
    
    def 'viewArticle'() {
        given:
        def article = newArticle("Testing view",true)
    
        when:
        def results = 'a string'.viewArticle(article.id)
        
        then:
        results.Article == article
    }
  
    def 'userUnpublishedArticles'() {
        given:
        def article = newArticle("Testing User Unpublished")
        article.author = user
        article.save()
    
        when:
        def results = 'a string'.userUnpublishedArticles([sort:'title',order:'asc'])
        
        then:
        results.total == 1
    }
    
  
    def 'userPublishedArticles'() {
        given:
        def article = newArticle("Testing User Published",true)
        article.author = user
        article.save()
    
        when:
        def results = 'a string'.userPublishedArticles([sort:'title',order:'asc'])
        
        then:
        results.total == 1
    } 
    
    def 'userArchivedArticles'() {
        given:
        def article = newArticle("Testing User Archived",true)
        article.author = user
        article.publishState = 'Archived'
        article.save()
    
        when:
        def results = 'a string'.userArchivedArticles([sort:'title',order:'asc'])
        
        then:
        results.total == 1
    } 
    
    def 'userDeletedArticles'() {
        given:
        def article = newArticle("Testing User Deleted")
        article.author = user
        article.deleted = true
        article.save()
    
        when:
        def results = 'a string'.userDeletedArticles([sort:'title',order:'asc'])
        
        then:
        results.total == 1
    }   
    
    def 'unpublishedArticles'() {
        given:
        def article = newArticle("Testing Unpublished")
        article.save()
    
        when:
        def results = 'a string'.unpublishedArticles([sort:'title',order:'asc'])
        
        then:
        results.total == 1
    }
    
  
    def 'publishedArticles'() {
        given:
        def article = newArticle("Testing Published",true)
        article.save()
    
        when:
        def results = 'a string'.publishedArticles([sort:'title',order:'asc'])
        
        then:
        results.total == 1
    } 
    
    def 'archivedArticles'() {
        given:
        def article = newArticle("Testing Archived",true)
        article.publishState = 'Archived'
        article.save()
    
        when:
        def results = 'a string'.archivedArticles([sort:'title',order:'asc'])
        
        then:
        results.total == 1
    }
    
    def 'deletedArticles'() {
        given:
        def article = newArticle("Testing Deleted")
        article.author = user
        article.deleted = true
        article.save()
    
        when:
        def results = 'a string'.deletedArticles([sort:'title',order:'asc'])
        
        then:
        results.total == 1
    }                     
}

