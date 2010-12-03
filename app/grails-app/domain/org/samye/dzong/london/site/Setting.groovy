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

/**
 * Domain class for storing a single site user-configurable setting.
 *
 * @author Leanne Northrop
 * @since  April 2010
 */
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
