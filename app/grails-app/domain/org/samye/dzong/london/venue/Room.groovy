package org.samye.dzong.london.venue

import org.samye.dzong.london.cms.Publishable
import org.samye.dzong.london.media.Image

class Room extends Publishable {
    String name;
    Image image;
    String summary;
    String content;
	Boolean forHire;

	static belongsTo = [venue: Venue]
	
    static constraints = {
    	name(blank:false)
	    summary(size:5..Integer.MAX_VALUE,blank:false)
	    content(maxSize:Integer.MAX_VALUE,blank:true)
	    image(nullable:true)
    	venue(nullable:false)
    }

	static mapping = {
	    columns {
	        content type:'text'
	        summary type:'text'
	    }
	}
	
    String toString() {
        name
    }
}	
