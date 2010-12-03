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

import org.samye.dzong.london.cms.Publishable
import org.samye.dzong.london.media.Image
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.collections.FactoryUtils;

/*
 * A Samye Dzong building or site at which events are held. Each building has 
 * zero or more postal addresses, email addresses and/or telphone numbers which 
 * belong to this object. It is a content managed class and goes through the
 * Publishable work-flow to controll it's visiblity on the public pages. All
 * details relating the publication state reside in the base class, @see Publishable,
 * for further information.
 *
 * @author Leanne Northrop
 * @since January 2010
 */
class Venue extends Publishable {
    String name;
    Image image;
    String content;
    String facilities;
    String access;
    Float latitude;
    Float longtitude;
    List addresses = new ArrayList();
    List emails = new ArrayList();
    List telephoneNumbers = new ArrayList();

    static hasMany = [rooms:Room,addresses: VenueAddress, emails: VenueEmail,telephoneNumbers:VenueTelephone]

    static constraints = {
    	name(maxSize:128,unique:true)
    	image(nullable:true)
    	content(maxSize:Integer.MAX_VALUE)
    	facilities(blank:true,maxSize:Integer.MAX_VALUE)
    	access(blank:true,maxSize:Integer.MAX_VALUE)
    	rooms(nullable:true)
		addresses(nullable:true)
		emails(nullable:true)		
		telephoneNumbers(nullable:true)
    }

	static mapping = {
	    cache usage:'nonstrict-read-write', include:'non-lazy'
	    columns {
	        content type:'text'
	        facilities type:'text'
	        access type:'text'	
	    }		
        addresses cascade: "all-delete-orphan", cache:true, lazy:false
        telephoneNumbers cascade: "all-delete-orphan", cache:true,lazy:false	
        emails cascade: "all-delete-orphan", cache:true,lazy:false	
		rooms sort: 'name', cache:true        
	}
	
	static namedQueries = {
        notDeleted { 
            eq 'deleted', Boolean.FALSE
            order("name", "desc")
        }
        notDeletedCount { 
            eq 'deleted', Boolean.FALSE
        }        
	}

    Venue() {
        name = new Date().format('dd/MM/yyyy hh:mm:ss')
        publishState = 'Unpublished'
        deleted = false
        home = false
        featured = false
        category = 'V'
        access = ''
        facilities = ''
        content = ''
        latitude = 0f
        longtitude = 0f
    }

    def getAddressesList() {
        return LazyList.decorate(addresses,FactoryUtils.instantiateFactory(VenueAddress.class))
    }

    def getEmailsList() {
        return LazyList.decorate(emails,FactoryUtils.instantiateFactory(VenueEmail.class))
    }

    def getTelephoneNumbersList() {
        return LazyList.decorate(telephoneNumbers,FactoryUtils.instantiateFactory(VenueTelephone.class))
    }

    /*
     * {@inheritDoc}
     */
    String toString() {
	    name
    }
}
