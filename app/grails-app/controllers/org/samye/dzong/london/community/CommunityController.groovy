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

package org.samye.dzong.london.community

import org.samye.dzong.london.events.Event
import org.samye.dzong.london.cms.*

/*
 * Volunteer content url handler which was orginally named community on navigation.
 * Displays only public facing pages.
 *
 * @author Leanne Northrop
 * @since  January 2010
 */
class CommunityController extends PublicSectionPageController {
    def articleService
    def emailService

    CommunityController() {
        CMSUtil.addFinderMethods(this)        
    }
    
    def index = {
        redirect(action:home)
    }

    def home = {
        def model = [:]         
        addPublishedContent(["CommunityHomeArticles", "CommunityFeaturedArticles","CommunityAllArticles","CommunityFeaturedEvents"],model)
        def community = model.allArticles.findAll{it.tags.find { tag -> !"volunteer".equalsIgnoreCase(tag)}}
        def volunteerOpportunities = model.allArticles.findAll{it.tags.find { tag -> "volunteer".equalsIgnoreCase(tag)}}
        model.put('volunteerOpportunities',volunteerOpportunities)
        def totalVolunteer = volunteerOpportunities.size()
        model.put('totalVolunteer',totalVolunteer)
        def totalCommunity = community.size()
        community = model.featuredArticles.findAll{it.tags.find { tag -> !"volunteer".equalsIgnoreCase(tag)}}
        model.put('community',community)        
        volunteerOpportunities = model.featuredArticles.findAll{it.tags.find { tag -> "volunteer".equalsIgnoreCase(tag)}}
        model.put('volunteerOpportunities',volunteerOpportunities)        
        articleService.addHeadersAndKeywords(model,request,response)
        return render(view: 'index',model: model);
    }

    def getSectionName() {
        "Community"
    }

    def send = {
        if (params.email && params.body && params.subject) {
            emailService.sendVolunteerRequest(params.email, params.subject, params.body)
        }
        redirect(action: home)
    }
}
