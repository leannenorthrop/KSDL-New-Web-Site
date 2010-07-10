/*******************************************************************************
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
 ******************************************************************************/

package org.samye.dzong.london.community

import org.samye.dzong.london.venue.Venue
import org.samye.dzong.london.venue.Room

class AboutUsController {
    def articleService
    def teacherService

    def index = {
        def community = Teacher.findByName('Community');
        def lineageTeachers = Teacher.findAllByPublishStateAndType('Published', 'L',[sort: "name", order: "asc"])
        def teachers = Teacher.findAllByPublishStateAndType('Published', 'C',[sort: "name", order: "asc"])
        teachers = teachers.findAll{teacher -> teacher.name != 'Community'}
		def venues = Venue.notDeleted.list()
		def allArticles = Article.allAboutUsArticles("title", "asc").list()
        model:[topArticles: [community], articles: allArticles, lineageTeachers: lineageTeachers, teachers:teachers,venues:venues];
    }

    def view = {
        def model = articleService.view(params.id)
        if (!model) {
            redirect(action:home)
        } else {
            render(view: 'view', model: model)
        }
    }

	def contactUs = {
		def venues = Venue.notDeleted.list()
		render(view: 'contact', model:[venues:venues])
	}
	
	def visiting = {
		def venues = Venue.notDeleted.list()
		model:[venues:venues]
	}	
	
	def room = {
		model:[room:Room.get(params.id)]
	}
	
	def venue = {
		def venues = []
		venues << Venue.get(params.id)
		render(view:'visiting',model:[venues:venues])
	}	
	
	def teacher = {
        def teacher = Teacher.get(params.id)
        if (!teacher) {
            // TODO: render 404
            redirect(uri: '/')
        }
        else {
            /* TODO link in articles that mention the teacher
            def aboutUsArticles = articleService.publishedByTags(['about us']);
            aboutUsArticles = aboutUsArticles.findAll { article -> article.id != params.id }
            if (model['articles']) {
                def articles = model['articles']
                articles << aboutUsArticles
            } else {
                model['articles'] = aboutUsArticles
            }*/
            def events = teacherService.events(params.id);
            def articles = []
            if (teacher.name == 'Community'){
                articles = articleService.publishedByTags(['about us']);
            }
            return [teacher: teacher, id: params.id, events:events, articles:articles]
        }		
	}
	
	def lineage = {
		def lineageArticles = []
		def lineageTeachers = []
        try {
            lineageArticles = articleService.findByTag('lineage',[])
			lineageTeachers = Teacher.findAllByPublishStateAndType('Published', 'L',[sort: "name", order: "asc"])
        } catch (error) {
            log.error("AboutUs controller encountered an error.",error)
        }
		
        model:[teachers:lineageTeachers,articles:lineageArticles];		
	}
	
	def teachers = {
		def teachers = Teacher.findAllByPublishState('Published', [sort: "name", order: "asc"])
        teachers = teachers.findAll{teacher -> teacher.name != 'Community' && teacher.type != 'L'}
        model:[teachers:teachers];		
	}	
}
