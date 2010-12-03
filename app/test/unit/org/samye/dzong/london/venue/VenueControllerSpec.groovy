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
package org.samye.dzong.london.venue

import grails.test.*
import grails.plugin.spock.*
import spock.lang.*
import org.springframework.transaction.TransactionStatus
import org.samye.dzong.london.cms.CMSUtil

/*
 * Unit test for Venue controller class.
 *
 * @author Leanne Northrop
 * @since 1.0.16-SNAPSHOT, 11th November 2010, 15:42
 */
class VenueControllerSpec extends ControllerSpec {
    def mockTransactionStatus

    def setup() {
        mockTransactionStatus = Mock(TransactionStatus)
        Venue.metaClass.static.withTransaction = { c -> c(mockTransactionStatus) }
        CMSUtil.addCMSMethods(VenueController) 
    }

    def 'Index redirects to home'() {
        when:
        controller.index()

        then:
        redirectArgs == [action: controller.manage]
    }

    def 'Manage returns lsits of non-deleted venue'() {
        given:
        mockDomain(Venue)
        def venue = new Venue()
        venue.save()
        Venue.metaClass.static.notDeleted = { 
            return new Expando(list: { args->Venue.findAll()})  
        }
        Venue.metaClass.static.notDeletedCount = { 
            return new Expando(count: { args->Venue.findAll().size()})  
        }

        when:
        controller.manage()

        then:
        controller.modelAndView.viewName == 'manage'
        controller.modelAndView.model.linkedHashMap.venues == [venue] 
        controller.modelAndView.model.linkedHashMap.total == 1 
    }

    def 'Delete with no params redirects to manage'() {
        setup:
        mockDomain(Venue)

        when:
        controller.delete()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.isError == true
        mockFlash.message == "Venue not found"
    }

    def 'Delete with bad id param redirects to manage'() {
        setup:
        mockDomain(Venue)
        getMockParams() << [id: -1] 

        when:
        controller.delete()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.isError == true
        mockFlash.message == "Venue not found"
    }

    def 'Delete with id param redirects to manage'() {
        setup:
        getMockParams() << [id: 1] 
        def venue = mockVenue()

        when:
        controller.delete()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash?.isError == null 
        mockFlash.message == "Venue Spa Road (Deleted) deleted"
        venue.publishState == "Unpublished"
        venue.deleted == true
        venue.name == 'Spa Road (Deleted)' 
    }

    def 'Delete failure rollsback and redirects to manage'() {
        setup:
        getMockParams() << [id: 1] 
        def venue = mockVenue()
        venue.metaClass.hasErrors { -> false }
        venue.metaClass.save { -> throw new RuntimeException() }

        when:
        controller.delete()

        then:
        1 * mockTransactionStatus.setRollbackOnly()
        mockFlash.isError == true 
        mockFlash.message == "Venue Spa Road (Deleted) could not be deleted"
        venue.name == 'Spa Road (Deleted)' 
        redirectArgs.action == controller.manage
        redirectArgs.id == 1
    }

    def 'Delete with errors rollsback and redirects to manage'() {
        given:
        getMockParams() << [id: 1] 
        def venue = mockVenue()
        venue.metaClass.hasErrors { -> hasError }
        venue.metaClass.save { -> false }

        when:
        controller.delete()

        then:
        1 * mockTransactionStatus.setRollbackOnly()
        mockFlash.isError == true 
        mockFlash.message == "Venue Spa Road (Deleted) could not be deleted"
        venue.name == 'Spa Road (Deleted)' 
        redirectArgs.action == controller.manage
        redirectArgs.id == 1

        where:
        hasError << [true,false]
    }

    def 'Edit with no id returns not found and redirects to manage'() {
        setup:
        mockDomain(Venue)

        when:
        controller.edit()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.isError == true
        mockFlash.message == "Venue not found"
    }

    def 'Edit with bad id param redirects to manage'() {
        setup:
        mockDomain(Venue)
        getMockParams() << [id: -1] 

        when:
        controller.edit()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.isError == true
        mockFlash.message == "Venue not found"
    }

    def 'Edit with valid id param returns venue'() {
        setup:
        getMockParams() << [id: 1] 
        def venue = mockVenue()

        when:
        def model = controller.edit()

        then:
        model.venue == venue
    }

    def 'Update with no id returns not found and redirects to manage'() {
        setup:
        mockDomain(Venue)

        when:
        controller.update()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.isError == true
        mockFlash.message == "Venue not found"
    }

    def 'Update with bad id param redirects to manage'() {
        setup:
        mockDomain(Venue)
        getMockParams() << [id: -1] 

        when:
        controller.update()

        then:
        redirectArgs == [action: controller.manage]
        mockFlash.isError == true
        mockFlash.message == "Venue not found"
    }

    def 'Update with valid id param returns venue'() {
        setup:
        getMockParams() << [id: 1] 
        def venue = mockVenue()

        when:
        controller.update()

        then:
        redirectArgs.action == controller.manage
        mockFlash?.isError == null 
        mockFlash.message == "Venue Spa Road updated"
    }

    def 'Update with errors rollsback and returns to edit'() {
        given:
        getMockParams() << [id: 1] 
        def venue = mockVenue()
        venue.metaClass.hasErrors { -> hasError }
        venue.metaClass.save { -> false }

        when:
        controller.update()

        then:
        1 * mockTransactionStatus.setRollbackOnly()
        mockFlash.isError == true 
        mockFlash.message == "Changes could not be saved because of the following:"
        venue.name == 'Spa Road'
        controller.modelAndView.viewName == 'edit'
        controller.modelAndView.model.linkedHashMap.venue == venue 

        where:
        hasError << [true,false]
    }

    def 'Update returns to edit if versions do not match'() {
        given:
        def venue = mockVenue()
        venue.version = 2
        getMockParams() << [id: 1, version: 1] 

        when:
        controller.update()

        then:
        controller.modelAndView.viewName == 'edit'
        controller.modelAndView.model.linkedHashMap.venue == venue 
        mockFlash.isError == true
    }

    def 'Update with valid id and version params returns venue'() {
        setup:
        getMockParams() << [id: 1, version: 1] 
        def venue = mockVenue()

        when:
        controller.update()

        then:
        redirectArgs.action == controller.manage
        mockFlash?.isError == null 
        mockFlash.message == "Venue Spa Road updated"
    }

    def 'Update failure rollsback and redirects to manage'() {
        setup:
        getMockParams() << [id: 1] 
        def venue = mockVenue()
        venue.metaClass.hasErrors { -> false }
        venue.metaClass.save { -> throw new RuntimeException() }

        when:
        controller.update()

        then:
        1 * mockTransactionStatus.setRollbackOnly()
        mockFlash.isError == true 
        mockFlash.message == "Changes could not be saved because of the following:"
        venue.name == 'Spa Road' 
        controller.modelAndView.viewName == 'edit'
        controller.modelAndView.model.linkedHashMap.venue == venue 
    }

    def mockVenue(save=true) {
        mockDomain(Venue, [])
        def venue = new Venue(name:'Spa Road',id:1,version:1)
        if (save) {
            venue.save()
        }
        Venue.metaClass.static.get = {id -> venue}
        venue
    }
}
