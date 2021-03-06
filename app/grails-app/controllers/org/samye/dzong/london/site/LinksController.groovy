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

package org.samye.dzong.london.site

import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList

/*
 * CMS area admin url handler for generating public pages Resource Menus of href
 * internal or external pages.
 *
 * @TODO DRY it up.
 *
 * @author Leanne Northrop
 * @since  August 2010
 */
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
