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

package org.samye.dzong.london.shop

import org.samye.dzong.london.cms.Publishable
import org.samye.dzong.london.media.Image
import org.apache.commons.collections.FactoryUtils
import org.apache.commons.collections.list.LazyList

/*
 * Domain class for storing product information.
 *
 * @author Leanne Northrop
 * @since  April 2010
 */
class Product extends Publishable {
    String title;
    String summary;
    String content; 
    Boolean isnew
    Boolean isdiscount    
    Boolean isdownloadable        
    List prices = new ArrayList()
    List meta = new ArrayList()    
    List images = new ArrayList()        
    List menuCategories = new ArrayList()
    
    static hasMany = [prices: ProductPrice,meta:Meta,images:Image,menuCategories:MenuCategory]

    static constraints = {
        title(blank: false, unique: true, size: 0..254)
        summary(size: 5..Integer.MAX_VALUE)
        content(size: 5..Integer.MAX_VALUE)   
        images(nullable: true)             
        isnew()
        isdiscount()
        prices(nullable: true)
        meta(nullable: true)   
        menuCategories(nullable: false)   
        isdownloadable(nullable:false)          
    }

    static mapping = {
        tablePerHierarchy false
        columns {
            content type:'text'
            summary type:'text'
        }
        prices cascade: "all-delete-orphan", cache:true, lazy:false
        meta cascade: "all-delete-orphan", cache:true, lazy:false  
        menuCategories cascade: "all-delete-orphan", cache:true, lazy:false     
    }
    	
    static namedQueries = {
        published {  ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', "Published"
            eq 'category', 'P'            
        }        
        unpublished {  ->
            eq 'deleted', Boolean.FALSE
            eq 'publishState', "Unpublished"
            eq 'category', 'P'            
        }
        deleted {  ->
            eq 'deleted', Boolean.TRUE
            eq 'category', 'P'            
        }                
    }
	
    def getPriceList() {
        return LazyList.decorate(prices, FactoryUtils.instantiateFactory(ProductPrice.class));
    }
    
    def getMetaList() {
        return LazyList.decorate(meta, FactoryUtils.instantiateFactory(Meta.class));
    }
        
    def getImageList() {
        return LazyList.decorate(images, FactoryUtils.instantiateFactory(Image.class));
    } 

    def getMenuCategoriesList() {
        return LazyList.decorate(menuCategories, FactoryUtils.instantiateFactory(MenuCategory.class));
    }       	
    
    String toString() {
        title
    }
}
