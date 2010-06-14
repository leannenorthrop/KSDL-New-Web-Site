package org.samye.dzong.london

class Setting {
    String name
    String value

    static constraints = {
        name(nullable: false, blank: false, unique:true,inList:['DefaultTheme','SSNone', 'SSHome', 'SSMeditation','SSBuddhism','FlickrFrob','FlickrUserId','Logo'])
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
    }

    String toString() {
        name
    }
}
