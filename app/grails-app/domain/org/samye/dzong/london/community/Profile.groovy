package org.samye.dzong.london.community

class Profile {
    String publicName
    String nickName
    String mimeType
    byte[] image
    Date dateCreated
    Date lastUpdated
    Date lastLoggedIn

    static constraints = {
        publicName(maxSize:512)
        nickName(nullable:true,maxSize:512)
		mimeType(blank: false)
	    image(blank: false)
		dateCreated()
		lastUpdated()
		lastLoggedIn()
    }

    String toString() {
        publicName
    }
}
