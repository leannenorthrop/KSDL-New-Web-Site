package org.samye.dzong.london.venue

import org.samye.dzong.london.Publishable
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

	static namedQueries = {
	    orderedAuthorPublishState { username, publishState, orderCol, orderDir ->
	        eq 'deleted', Boolean.FALSE
	        eq 'publishState', publishState
	        author {
	            eq 'username', username
	        }
	        order("${orderCol}", "${orderDir}")
	    }

	    authorPublishState { username, publishState ->
	        eq 'deleted', Boolean.FALSE
	        eq 'publishState', publishState
	        author {
	            eq 'username', username
	        }
	    }

	    orderedPublishState { publishState, orderCol, orderDir ->
	        eq 'deleted', Boolean.FALSE
	        eq 'publishState', publishState
	        order("${orderCol}", "${orderDir}")
	    }

	    publishState { publishState ->
	        eq 'deleted', Boolean.FALSE
	        eq 'publishState', publishState
	    }

	    deletedAuthor { username ->
	        eq('deleted', Boolean.TRUE)
	        author {
	            eq('username', username)
	        }
	    }

	    deleted {
	        eq('deleted', Boolean.TRUE)
	    }
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