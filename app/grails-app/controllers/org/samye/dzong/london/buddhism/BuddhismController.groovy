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

package org.samye.dzong.london.buddhism

import org.samye.dzong.london.events.Event
import org.samye.dzong.london.community.Article
import org.samye.dzong.london.site.Setting
import org.samye.dzong.london.community.Teacher
import org.samye.dzong.london.site.Link
import org.samye.dzong.london.cms.*

/*
 * Buddhist content url handler. Displays only public facing pages.
 *
 * @author Leanne Northrop
 * @since  November 2009
 */
class BuddhismController extends PublicSectionPageController {
    def articleService
    
    BuddhismController() {
        CMSUtil.addFinderMethods(this)        
    }
        
    def index = {
        redirect(action:home)
    }

    def home = {
        def model = [:] 
        
        addPublishedContent(["BuddhistHomeArticles", "BuddhistFeaturedArticles","BuddhistAllArticles","BuddhistFeaturedEvents"],model)
		
        def lineageTeachers = Teacher.findAllByPublishStateAndType('Published', 'L',[sort: "name", order: "asc"])
        model.put('teachers',lineageTeachers)

        def album = getAlbum()
        model.put('album',album)
        model.put('links',Link.findAllBySection("B"))

		
        articleService.addHeadersAndKeywords(model,request,response)
        return render(view: 'index',model: model);
    }

    def getSectionName() {
        "Buddhist"
    }
}
