package org.samye.dzong.london

class Setting {
    String name
    String value

    static constraints = {
        name(maxSize:128,nullable: false, blank: false, unique:true,inList:['DefaultTheme','SSNone', 'SSHome', 'SSMeditation','SSBuddhism','FlickrFrob','FlickrUserId','Logo','ShopMenu','SiteMessage','ThumbSize','ShowHome','ShowAboutUs','ShowEvents','ShowBuddhism','ShowMeditation','ShowVolunteer','ShowWellBeing','ShowNews','ShowShop'])
        value(nullable: false, blank: false)
    }

    static namedQueries = {
        defaultTheme { name ->
            eq 'name', "DefaultTheme"
        }
		homeSlideshow { name ->
			eq 'name', "SSHome"
		}
		meditationSlideshow { name ->
			eq 'name', "SSMeditation"
		}
		buddhistSlideshow { name ->
			eq 'name', "SSBuddhism"
		}	
		logo { name ->
			eq 'name', "Logo"	
		}			
		flickrUserId {
			eq 'name', "FlickrUserId"
		}
		siteMessage {
		    eql 'name', "SiteMessage"
		}
        showHome{
		    eql 'name', "ShowHome"
		}
		showAboutUs{
		    eql 'name', "ShowAboutUs"
		}
		showEvents{
		    eql 'name', "ShowEvent"
		}
		showBuddhism{
		    eql 'name', "ShowBuddhism"
		}
		showMeditation{
		    eql 'name', "ShowMeditation"
		}
		showVolunteer{
		    eql 'name', "ShowCommunity"
		}
		showWellbeing{
		    eql 'name', "ShowWellbeing"
		}
		showNews{
		    eql 'name', "ShowNews"
		}
		showShop{
		    eql 'name', "ShowShop"
		}		
    }

    static mapping = {
        cache usage:'read-write', include:'non-lazy'
        columns {
            value type:'text'
        }
    }
    
    String toString() {
        name
    }
}
