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

class AboutUsController {
    def articleService

    def index = {
        def community = Teacher.findByName('Community');
        def lineageTeachers = Teacher.findAllByPublishStateAndType('Published', 'L',[sort: "name", order: "asc"])
        def teachers = Teacher.findAllByPublishStateAndType('Published', 'C',[sort: "name", order: "asc"])
        teachers = teachers.findAll{teacher -> teacher.name != 'Community'}
        model:[topArticles: [community], lineageTeachers: lineageTeachers, teachers:teachers];
    }

    def view = {
        def model = articleService.view(params.id)
        if (!model) {
            redirect(action:home)
        } else {
            render(view: 'view', model: model)
        }
    }

	def contact = {
		model:[]
	}
	
	def visiting = {
		model:[]
	}	
}
