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

/**
 * Service helper class for Teacher domain objects.
 *
 * TODO: internationalize
 * TODO: test
 * 
 * Author: Leanne Northrop
 * Date: 28th January 2010, 21:48
 */
class TeacherService {
    boolean transactional = true
    def userLookupService

    def view(id) {
        def teacher = Teacher.get(id)
        if(!teacher) {
            flash.message = "Teacher not found (id ${params.id} unknown)"
            return null
        }
        else {
            return [teacher: teacher]
        }
    }

    def userUnpublished(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def username = userLookupService.username();
        def teachers = Teacher.orderedAuthorPublishState(username,"Unpublished", order, dir).list(params);
        def total = Teacher.authorPublishState(username,"Unpublished").count();
        return [teachers: teachers, total: total]
    }

    def userPublished(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def username = userLookupService.username();
        def teachers = Teacher.orderedAuthorPublishState(username,"Published", order, dir).list(params);
        def total = Teacher.authorPublishState(username,"Published").count();
        return [teachers: teachers, total: total]
    }

    def userArchived(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def username = userLookupService.username();
        def teachers = Teacher.orderedAuthorPublishState(username,"Archived", order, dir).list(params);
        def total = Teacher.authorPublishState(username,"Archived").count();
        return [teachers: teachers, total: total]
    }

    def userDeleted(params) {
        def username = userLookupService.username();
        def teachers = Teacher.deletedAuthor(username).list(params);
        def total = Teacher.deletedAuthor(username).count();
        return [teachers: teachers, total: total]
    }

    def unpublished(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def teachers = Teacher.orderedPublishState("Unpublished", order, dir).list(params);
        def total = Teacher.publishState("Unpublished").count();
        return [teachers: teachers, total: total]
    }

    def published(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def teachers = Teacher.orderedPublishState("Published", order, dir).list(params);
        def total = Teacher.publishState("Published").count();
        return [teachers: teachers, total: total]
    }

    def archived(params) {
        def order = params.sort?: "title"
        def dir = params.order?: "asc"
        def teachers = Teacher.orderedPublishState("Archived", order, dir).list(params);
        def total = Teacher.publishState("Archived").count();
        return [teachers: teachers, total: total]
    }

    def deleted(params) {
        def teachers = Teacher.deleted().list(params);
        def total = Teacher.deleted().count();
        return [teachers: teachers, total: total]
    }
}
