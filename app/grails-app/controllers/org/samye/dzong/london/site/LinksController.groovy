package org.samye.dzong.london.site

import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList

class LinksController {

    def index = { 
        redirect(action:manage)        
    }
    
    def manage = {
    }   
        
    def homeMenu = {
        def controllers = ['home','aboutUs','event','buddhism','meditation','community','wellbeing','news']
        def actions = [home:['index','slideshow','aboutThisSite','legal','siteMap','feed','calendars','donate'],
                       aboutUs:['index','contactUs','visiting','teachers'],
                       event:['home','current','future','regular','list'],
                       buddhism:['home','list','events','slideshow'],
                       meditation:['home','all','events','slideshow'],
                       community:['home','events'],
                       wellbeing:['home','events'],
                       news:['home','current','archived'],
                       shop:[]]
        render(view:'tab',model: [links:Link.findAllBySection("H"),controllers:controllers,actions:actions,saveTo:'updateHomeMenu'])
    } 
    
    def meditationMenu = {
        def controllers = ['home','aboutUs','event','buddhism','meditation','community','wellbeing','news']
        def actions = [home:['index','slideshow','aboutThisSite','legal','siteMap','feed','calendars','donate'],
                       aboutUs:['index','contactUs','visiting','teachers'],
                       event:['home','current','future','regular','list'],
                       buddhism:['home','list','events','slideshow'],
                       meditation:['home','all','events','slideshow'],
                       community:['home','events'],
                       wellbeing:['home','events'],
                       news:['home','current','archived'],
                       shop:[]]
        render(view:'tab',model:[links:Link.findAllBySection("M"),controllers:controllers,actions:actions,saveTo:'updateMeditationMenu'])
    }
    
    def buddhismMenu = {
        def controllers = ['home','aboutUs','event','buddhism','meditation','community','wellbeing','news']
        def actions = [home:['index','slideshow','aboutThisSite','legal','siteMap','feed','calendars','donate'],
                       aboutUs:['index','contactUs','visiting','teachers'],
                       event:['home','current','future','regular','list'],
                       buddhism:['home','list','events','slideshow'],
                       meditation:['home','all','events','slideshow'],
                       community:['home','events'],
                       wellbeing:['home','events'],
                       news:['home','current','archived'],
                       shop:[]]
        render(view:'tab',model: [links:Link.findAllBySection("B"),controllers:controllers,actions:actions,saveTo:'updateBuddhismMenu'])
    }        
    
    def updateHomeMenu = { LinkListCommand cmd ->
  		save(cmd,"H")
    }  
    
    def updateMeditationMenu = { LinkListCommand cmd ->
  		save(cmd,"M")
    }
    
    def updateBuddhismMenu = { LinkListCommand cmd ->
  		save(cmd,"B")
    }
            
    def save(cmd, type) {
  		cmd.links.eachWithIndex { link, index ->
  		    if (link?._deleted) {
  		        Link.findAllBySection(type)[index].delete();
  		    }
  		}
  		def _toBeSaved = cmd.links.findAll {  		    
  		    !it?._deleted
  		}    			
  		if (_toBeSaved) {
  		    def links = Link.findAllBySection(type);
  		    _toBeSaved.eachWithIndex{link,index->
  		        try {
  		            def foundLink = links[index]  		            
  		            println "Found link ${foundLink}"
  		            foundLink.position = link.position;
  		            foundLink.save(flush:true)  		            
  		        } catch(error) {
  		            if (link.name != 'New') {
      		            link.section = type
          		        link.save(flush:true)
  		            }
	            }
  		    }
  		}    			

        if (!cmd.hasErrors()) {
            flash.isError = false
            flash.message = "Menu updated"
            redirect(action: manage)
        }
        else {
            flash.isError = true
            flash.message = "menu.update.error"
            redirect(action: manage)
        }        
    }
}

class LinkListCommand {
    def links = new ArrayList()
    
    def getLinks() {
        return LazyList.decorate(links, FactoryUtils.instantiateFactory(Link.class))        
    }
    
    static constraints = {
    }   
}
