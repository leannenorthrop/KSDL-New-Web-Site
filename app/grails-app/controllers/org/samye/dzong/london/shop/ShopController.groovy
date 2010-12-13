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

import org.samye.dzong.london.community.Article
import org.samye.dzong.london.site.Setting
import org.samye.dzong.london.cms.*

/*
 * Very early stages for on-line shop with payment handling to be passed on
 * to PayPal grails plugin so that ordering historym,inventories and stock
 * manangements etc systems don't have to be written.
 *
 * @author Leanne Northrop
 * @since  April 2010
 */
class ShopController extends CMSController {
    def emailService
    def ADMIN_ROLES = ['ShopManager', 'Administrator']
    def DOMAIN_NAME = 'Product'
    
    // the save and update actions only accept POST requests
    static allowedMethods = [manage: 'GET',
                             save: 'POST', 
                             update: 'POST', 
                             changeState: 'GET', 
                             delete: 'GET',                             
                             view: 'GET',                             
                             show: 'GET',                                                          
                             edit: 'GET',                                                                                       
                             pre_publish: 'GET',
                             preview: 'POST', 
                             updatePublished: 'POST',
                             updateAndPublish: 'POST',
                             onAddComment: ['POST','GET']]  
                                     
    ShopController() {
        CMSUtil.addFinderMethods(this)
        CMSUtil.addCMSMethods(this)        
    }
    
    def index = {
        forward(action:'home')
    }
    
    def home = {
        def model = [:]         
                
        def shopMenuSetting = Setting.findByName('ShopMenu');
        def menu = shopMenuSetting ? shopMenuSetting.value : '';
        model.put('menu',menu)
        

        addPublishedContent(["ShopHomeArticles", "RoomAllArticles","ShopFeaturedArticles"],model)        
        
        def venues = publishedVenues().'venues'
		model.put('venues',venues)
		
        
        def things = publishedNonDownloadables()
        def products = things.nonDownloadables
        def total = things.total
        def newProducts = []
        def discountedProducts = []        
        products.each { product ->
            if (product.isnew) { newProducts << product}
            if (product.isdiscount) { discountedProducts << product}            
        }
        model.put('total',total)
        model.put('newProducts',newProducts)
        model.put('discountedProducts',discountedProducts)                
        
        return render(view: 'home',model: model);
    }
    
    def list = {
        def model = [ products: [], title: 'community.all.articles.title']
        render(view: 'list', model:model)
    }

   
    def update = {
        def product = Product.get(params.id)
        if (product) {
            if (params.version) {
                def version = params.version.toLong()
                if (product.version > version) {
                    flash.isError = true
                    flash.message = "product.update.error"
                    event.errors.rejectValue("version", "product.optimistic.locking.failure", "Another user has updated this product while you were editing.")
                    render(view: 'edit', model: [product: event, id: params.id])
                    return
                }
            }

            product.properties = params
            if (product.prices) {
                def _toBeDeleted = product.prices.findAll {it?._deleted}
                def _toBeSaved = product.prices.findAll {!it?._deleted}
                if (_toBeSaved) {
                    _toBeSaved.each{
                        it.save()}
                }
                if (_toBeDeleted) {
                    product.prices.removeAll(_toBeDeleted)
                }
            }

            if (product.meta) {
                def _toBeDeleted = product.meta.findAll {it?._deleted}
                def _toBeSaved = product.meta.findAll {!it?._deleted}
                if (_toBeSaved) {
                    _toBeSaved.each{
                        it.save()}
                }
                if (_toBeDeleted) {
                    product.meta.removeAll(_toBeDeleted)
                }
            }
  		  
            if (product.images) {
                def _toBeDeleted = product.images.findAll {it?._deleted}
                if (_toBeDeleted) {
                    product.images.removeAll(_toBeDeleted)
                }
            }
  		  
            if (product.menuCategories) {
                def _toBeDeleted = product.menuCategories.findAll {it?._deleted}
                def _toBeSaved = product.menuCategories.findAll {!it?._deleted}
                if (_toBeSaved) {
                    _toBeSaved.each{
                        it.save()}
                }
                if (_toBeDeleted) {
                    product.menuCategories.removeAll(_toBeDeleted)
                }
            }
  		  
            if (!product.hasErrors() && product.save()) {
                flash.isError = false
                flash.message = "Product ${product.title} updated"
                redirect(action: manage)
            }
            else {
                flash.isError = true
                flash.message = "product.update.error"
                flash.args = [product]
                render(view: 'edit', model: [product: product, id: params.id])
            }
        }
        else {
            flash.message = "Event not found with id ${params.id}"
            redirect(action: manage)
        }
    }
  
    void updateMenu() {
        try {
            def topMenuNames = [] as Set
            def menuCategories = MenuCategory.findAllByLevel(0)
            menuCategories.each {
                topMenuNames << it.name
            }
            def topMenu = [:]
            topMenuNames.each{
                topMenu.put(it,[parent: null,name:it, children:[:]])
            }

            def products = Product.findAllByPublishState('Published')
            products.each {product->
                def topMenuName = product.menuCategories[0].name
                if (product.menuCategories[1]) {
                    def children = topMenu[topMenuName].children
                    def subMenuItemName = product.menuCategories[1].name
                    if (!children[subMenuItemName]) {
                        children.put(subMenuItemName,[parent: topMenuName,name:subMenuItemName, children:[:]])
                    }
                    if (product.menuCategories[2]) {
                        def subSubMenuItemName = product.menuCategories[2].name
                        def parent = subMenuItemName
                        children = children[subMenuItemName].children
                        if (!children[subSubMenuItemName]) {
                            children.put(subSubMenuItemName,[parent: parent,name:subSubMenuItemName, children:[:]])
                        }
                    }
                }
            }
            String menu = ""
            topMenu.each { topMenuItemName, topMenuItem ->
                menu += "\n* %${topMenuItemName}%"
                topMenuItem.children.each { subMenuItemName, subMenuItem ->
                    menu += "\n** %${subMenuItemName}%"
                    subMenuItem.children.each { subSubMenuItemName, subSubMenuItem ->
                        menu += "\n*** %${subSubMenuItemName}%"
                    }
                }
            }
      
            def shopMenuSetting = Setting.findByName('ShopMenu')
            if (!shopMenuSetting) {
                shopMenuSetting = new Setting(name: 'ShopMenu', value:menu)
                shopMenuSetting.save()
            } else {
                shopMenuSetting.value = menu;
                shopMenuSetting.save();
            }
        } catch (error) { error.printStackTrace()}
    }
  
    def display = {
        def product = Product.get(params.id)

        if (!product) {
            flash.message = "Product not found with id ${params.id}"
            redirect(action: product)
        }
        else {
            def shopMenuSetting = Setting.findByName('ShopMenu');
            def menu = shopMenuSetting ? shopMenuSetting.value : '';
            def model = [product: product, id: params.id, showPublication: true,menu:menu]
            addPublishedContent(["ShopFeaturedArticles"],model)             
            model            
        }
    }
  
    def displayAll = {
        def id = params.id
        def level = Integer.parseInt(params.level)
        def shopMenuSetting = Setting.findByName('ShopMenu');
        def menu = shopMenuSetting ? shopMenuSetting.value : '';
        def c = NonDownloadable.createCriteria()
        def products = c.list(sort:'title',order:'asc') {
            eq 'deleted', Boolean.FALSE
            eq 'publishState', "Published"
            eq 'category', 'P'
            menuCategories {
                eq 'level', level
                eq 'name', id
            }
        }
        def model = [menu:menu,products:products,productsTotal:products.size(),heading:id + ' ' + level]              
        addPublishedContent(["ShopFeaturedArticles"],model) 
        model
    }
    
    
    def addPublishedContent(contentList,model,params=[sort:'datePublished',order:'desc']) {
        contentList.each {
            model.putAll("findPublished${it}"(params))
        }        
    }       
}
