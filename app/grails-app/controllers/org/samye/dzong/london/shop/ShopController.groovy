package org.samye.dzong.london.shop

import org.samye.dzong.london.community.Article
import org.samye.dzong.london.Setting

class ShopController {
    def articleService
	def emailService
    def userLookupService
    
    def index = {
        redirect(action:home)
    }

    def home = {
        def shopMenuSetting = Setting.findByName('ShopMenu');
        def menu = shopMenuSetting ? shopMenuSetting.value : '';
        def products = NonDownloadable.published().list(sort:'lastUpdated',order:'desc')
        def newProducts = []
        def discountedProducts = []        
        products.each { product ->
            if (product.isnew) { newProducts << product}
            if (product.isdiscount) { discountedProducts << product}            
        }
        def topArticles = Article.featuredShopArticles('datePublished','desc').list()
        def articles = Article.allNonFeaturedShopArticles('datePublished','desc').list()
        def total = Article.allShopArticlesNotOrdered().count()
		def model = [topArticles: topArticles, articles: articles,total:total,menu:menu,totalNewProducts:newProducts.size(),newProducts:newProducts,totalDiscountedProducts:discountedProducts.size(),discountedProducts:discountedProducts]
		articleService.addHeadersAndKeywords(model,request,response)
        return render(view: 'home',model: model);
    }
    
    def list = {
		def model = [ products: [], title: 'community.all.articles.title']
		articleService.addHeadersAndKeywords(model,request,response)
        render(view: 'list', model:model)
    }

    def view = {
        def model = articleService.view(params.id)
		articleService.addHeadersAndKeywords(model,request,response)
        if (!model) {
            redirect(action:home)
        } else {
            render(view: 'view', model: model)
        }
    }    
    
    def manage = {
        render(view: 'manage')
    }
    
    def ajaxUnpublished = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        def model = [products:Product.unpublished().list(params),total:Product.unpublished().count()]
        render(view: 'unpublished', model: model)
    }

    def ajaxPublished = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        def model  = [products:Product.published().list(params),total:Product.published().count()]
        render(view: 'published', model: model)
    }

    def ajaxDeleted = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        def model = [products:Product.deleted().list(params),total:Product.deleted().count()]
        render(view: 'deleted', model: model)
    }        
    
    def create = {
        def product = new NonDownloadable()
        product.isdownloadable = false
        product.properties = params
        return [product: product]
    }

    def save = {
        def product = new NonDownloadable()
        product.author = userLookupService.lookup()

        product.properties = params
        if (!product.hasErrors() && product.save()) {
            flash.isError = false
            flash.message = "Product ${product.title} created"
            redirect(action: manage)
        }
        else {
            flash.isError = true
            flash.message = "product.update.error"
            flash.args = [product]
            render(view: 'create', model: [product: product])
        }
    }    
    
    def edit = {
       def product = Product.get(params.id)

       if (!product) {
           flash.message = "Event not found with id ${params.id}"
           redirect(action: product)
       }
       else {
           return [product: product, id: params.id, showPublication: true]
       }
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
  
  def changeState = {
      try {
          def product = Product.get(params.id)
          if (product) {
              if (params.version) {
                  def version = params.version.toLong()
                  if (product.version > version) {
                      flash.message = "Product ${product.title} was being edited - please try again."
                      redirect(action: manage)
                      return
                  }
              }
              def isFirstPublish = product.publishState != 'Published' && params.state == 'Published'
              if (isFirstPublish) {
                  product.datePublished = new Date()
              }
              product.publishState = params.state
              product.deleted = false
              if (!product.hasErrors() && product.save()) {
                  updateMenu();
                  flash.message = "Product ${product.title} has been moved to ${product.publishState}"
                  redirect(action: manage)
              }
              else {
                  flash.message = "Product ${product.title} could not be ${params.state} due to an internal error. Please try again."
                  redirect(action: manage)
              }
          }
          else {
              flash.message = "Product not found with id ${params.id}"
              redirect(action: manage)
          }
    } catch (error){
        
    }
  }  
  
  def delete = {
      def product = Product.get(params.id)
      if (product) {
          if (params.version) {
              def version = params.version.toLong()
              if (product.version > version) {
                  product.errors.rejectValue("version", "produt.optimistic.locking.failure", "Another user has updated this Product while you were editing.")
                  redirect(action: manage)
                  return
              }
          }
          product.publishState = "Unpublished"
          product.deleted = true
          product.title += "(Deleted)" 
          if (!product.hasErrors() && product.save()) {
              flash.message = "Product ${product.title} deleted"
              redirect(action: manage)
          }
          else {
              redirect(action: manage)
          }
      }
      else {
          flash.message = "Product not found with id ${params.id}"
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
          def articles = Article.allNonFeaturedShopArticles('datePublished','desc').list()          
          return [product: product, id: params.id, showPublication: true,menu:menu,articles: articles,total:articles.size()]
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
      
      def articles = Article.allNonFeaturedShopArticles('datePublished','desc').list()
      def total = Article.allShopArticlesNotOrdered().count()
	  def model = [articles: articles,total:total,menu:menu,products:products,productsTotal:products.size(),heading:id + ' ' + level]
	  articleService.addHeadersAndKeywords(model,request,response)
      model
  }
}
