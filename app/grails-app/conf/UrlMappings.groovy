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

/**
 * Configures error page mappings.
 * TODO: play with mappings to hide object ids.
 *
 * @author Leanne Northrop
 * @since October 2009
 */
class UrlMappings {
    static mappings = {
      name news: "/news/$year/$month?" {
          controller = "news"
          action = "show"
          /*constraints {
               year(matches:/d{4}/)
               month(matches:/(january|feburary|march|april|may|june|july|august|september|october|november|december)/)
          }*/
      }        
      "/news/home" {
          controller = "news"
          action = "home"
      }    
      "/news/current" {
          controller = "news"
          action = "current"
      }    
      "/news/archived" {
          controller = "news"
          action = "archived"
      } 
      "/news/view/$id" {
          controller = "news"
          action = "view"
      }                                          
      "/$controller/$action?/$id?"{
      }     
      "/events/"(controller:"event",action:"home")
      "/programme/"(controller:"event",action:"home")      
      "/whatson/"(controller:"event",action:"home")            
      "/volunteer/"(controller:"community",action:"home")                  
      "/health/"(controller:"wellbeing",action:"home")                        
      "/contact/"(controller:"aboutUs",action:"contactUs")                              
      "/contactUs/"(controller:"aboutUs",action:"contactUs")                                    
      "/meditation/"(controller:"meditation",action:"home")                                          
      "/buddhism/"(controller:"buddhism",action:"home") 
      "/events"(controller:"event",action:"home")
      "/programme"(controller:"event",action:"home")      
      "/whatson"(controller:"event",action:"home")            
      "/volunteer"(controller:"community",action:"home")                  
      "/health"(controller:"wellbeing",action:"home")                        
      "/contact"(controller:"aboutUs",action:"contactUs")                              
      "/contactUs"(controller:"aboutUs",action:"contactUs")                                    
      "/meditation"(controller:"meditation",action:"home")                                          
      "/buddhism"(controller:"buddhism",action:"home")                                                        
      "/"(controller:"home")
      "500"(view:"home/internalError")
      "404"(view:"home/notFound")
	  "401"(view:"home/unauthorised")
	  "403"(view:"home/unauthorised")	
	  "503"(view:"home/unauthorised")
    }
}
