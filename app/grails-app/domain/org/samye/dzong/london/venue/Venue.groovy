package org.samye.dzong.london.venue

import org.samye.dzong.london.Publishable
import org.samye.dzong.london.media.Image
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.collections.FactoryUtils;

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
	    cache true 
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
	
    String toString() {
	    name
    }
}