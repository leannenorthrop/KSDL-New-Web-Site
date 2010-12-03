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

/**
 * Integration test for Publishable
 *
 * @author Leanne Northrop
 * @since 16th November, 2010, 16:55
 */
class PublishableIntegrationSpec extends IntegrationHelper {
    def 'allPublished'() {
        given:
        clean()
        Article.findAll().each {article -> article.delete(flush:true)}
        (0..<10).each{i -> newArticle("Meditation Article $i", i%2 == 0) }
        
        expect:
        5 == Publishable.allPublished().count()        
    }
    
    def 'similar'() {
        given:
        clean()
        Article.findAll().each {article -> article.delete(flush:true)}
        (0..<10).eachWithIndex{ it, i -> newArticle("Meditation Article $i",true) }
        Publishable.findAll().eachWithIndex{ article,i  -> article.addTags( (i%2 == 0 ? ['a', 'b'] : (i%3==0?['b','c']:['c', 'd', 'e'])) ) } 
        def a = Article.findByTitle("Meditation Article 2")
        
        expect:
        6 == Publishable.similar(a).size()         
    }    
}

