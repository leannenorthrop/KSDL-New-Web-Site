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

import  org.samye.dzong.london.community.ArticleService
import javax.servlet.http.Cookie

/**
 * Servlet filter to provide basic caching.
 *
 * @author Leanne Northrop
 * @since  April 2010
 */
class PublicCacheHandlingFilters {
    def articleService

    def filters = {			
        homepage(uri:'/*') {
            before = {
                if (!session.getAttribute('theme')) {
                    articleService.handleIfNotModifiedSince(request,response)
                }
            }
            after = { model ->
                if (params.theme) {
                    session.setAttribute('theme',params.theme)
                } 
            } 
        }
        home(controller:'home', action:'*') {
            before = {
                if (!session.getAttribute('theme')) {
                    articleService.handleIfNotModifiedSince(request,response)
                }
            }
            after = { model ->
                if (params.theme) {
                    session.setAttribute('theme',params.theme)
                } 
            } 
        }
        aboutUs(controller:'aboutUs', action:'*') {
            before = {
                if (!session.getAttribute('theme')) {
                    articleService.handleIfNotModifiedSince(request,response)
                }
            }
            after = { model ->
                if (params.theme) {
                    session.setAttribute('theme',params.theme)
                } 
            } 
        }
        aboutUs(controller:'events', action:'home') {
            before = {
                if (!session.getAttribute('theme')) {
                    articleService.handleIfNotModifiedSince(request,response)
                }
            }
            after = { model ->
                if (params.theme) {
                    session.setAttribute('theme',params.theme)
                } 
            } 
        }
        news(controller:'news', action:'*') {
            before = {
                if (!session.getAttribute('theme')) {
                    articleService.handleIfNotModifiedSince(request,response)
                }
            }
            after = { model ->
                if (params.theme) {
                    session.setAttribute('theme',params.theme)
                } 
            } 
        }
        meditation(controller:'meditation', action:'*') {
            before = {
                if (!session.getAttribute('theme')) {
                    articleService.handleIfNotModifiedSince(request,response)
                }
            }
            after = { model ->
                if (params.theme) {
                    session.setAttribute('theme',params.theme)
                } 
            } 
        }
        buddhism(controller:'buddhism', action:'*') {
            before = {
                if (!session.getAttribute('theme')) {
                    articleService.handleIfNotModifiedSince(request,response)
                }
            }
            after = { model ->
                if (params.theme) {
                    session.setAttribute('theme',params.theme)
                } 
            } 
        }
        community(controller:'community', action:'*') {
            before = {
                if (!session.getAttribute('theme')) {
                    articleService.handleIfNotModifiedSince(request,response)
                }
            }
            after = { model ->
                if (params.theme) {
                    session.setAttribute('theme',params.theme)
                } 
            } 
        }
        wellbeing(controller:'wellbeing', action:'*') {
            before = {
                if (!session.getAttribute('theme')) {
                    articleService.handleIfNotModifiedSince(request,response)
                }
            }
            after = { model ->
                if (params.theme) {
                    session.setAttribute('theme',params.theme)
                } 
            } 
        }
    }
    
}
