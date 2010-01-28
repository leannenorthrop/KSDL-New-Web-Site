/** *****************************************************************************
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
 ***************************************************************************** */

package org.samye.dzong.london.community

import org.apache.shiro.SecurityUtils

/**
 * Handler for web requests regarding teachers.
 * Author: Leanne Northrop
 * Date: 26th January 2010, 19:00
 * TODO: test
 * TODO: internationalize
 * TODO: change publish to simple state change.
 */
class TeacherController {
    def userLookupService
    def teacherService

    def index = {
        def teachers = Teacher.findAllByPublishState("Published")
        model: [teachers: teachers, title: "all.teachers"]
    }

    // the save and update actions only accept POST requests
    static allowedMethods = [save: 'POST', update: 'POST', changeState: 'GET']

    def ajaxUnpublishedTeachers = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)
        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
            model = teacherService.unpublished(params)
        } else {
            model = teacherService.userUnpublished(params)
        }
        render(view: 'unpublished', model: model)
    }

    def ajaxPublishedTeachers = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)

        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
            model = teacherService.published(params)
        } else {
            model = teacherService.userPublished(params)
        }
        render(view: 'published', model: model)
    }

    def ajaxArchivedTeachers = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)

        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
            model = teacherService.archived(params)
        } else {
            model = teacherService.userArchived(params)
        }
        render(view: 'archived', model: model)
    }

    def ajaxDeletedTeachers = {
        params.offset = params.offset ? params.offset.toInteger() : 0
        params.max = Math.min(params.max ? params.max.toInteger() : 10, 100)

        def model
        if (SecurityUtils.subject.hasRoles(['Editor', 'Administrator']).any()) {
            model = teacherService.deleted(params)
        } else {
            model = teacherService.userDeleted(params)
        }
        render(view: 'deleted', model: model)
    }

    def manage = {
        render(view: 'manage')
    }

    def view = {
        def teacher = Teacher.get(params.id)
        if (!teacher) {
            flash.message = "teacher.not.found"
            flash.args = params.id
            redirect(action: list)
        }
        else {
            return [teacher: teacher, id: params.id]
        }
    }

    def show = {
        def teacher = Teacher.get(params.id)

        if (!teacher) {
            flash.message = "teacher.not.found"
            flash.args = params.id
            redirect(action: list)
        }
        else {
            return [teacher: teacher, id: params.id]
        }
    }

    def delete = {
        def teacher = Teacher.get(params.id)
        if (teacher) {
            if (params.version) {
                def version = params.version.toLong()
                if (teacher.version > version) {
                    teacher.errors.rejectValue("version", "teacher.optimistic.locking.failure", "Another user has updated this Teacher's details while you were editing.")
                    redirect(action: manage)
                    return
                }
            }
            teacher.publishState = "Unpublished"
            teacher.deleted = true
            if (!teacher.hasErrors() && teacher.save()) {
                flash.message = "teacher.deleted"
                flash.args = [teacher];
                redirect(action: manage)
            }
            else {
                redirect(action: manage)
            }
        }
        else {
            flash.message = "teacher.not.found"
            flash.args = params.id
            redirect(action: manage)
        }
    }

    def edit = {
        def teacher = Teacher.get(params.id)

        if (!teacher) {
            flash.message = "teacher.not.found"
            flash.args = params.id
            redirect(action: manage)
        }
        else {
            return [teacher: teacher, id: params.id]
        }
    }

    def pre_publish = {
        def teacher = Teacher.get(params.id)

        if (!teacher) {
            flash.message = "teacher.not.found"
            flash.args = params.id
            redirect(action: manage)
        }
        else {
            return render(view: 'publish', model: [teacher: teacher], id: params.id)
        }
    }

    def publish = {
        def teacher = Teacher.get(params.id)
        if (teacher) {
            if (params.version) {
                def version = params.version.toLong()
                if (teacher.version > version) {
                    teacher.errors.rejectValue("version", "teacher.optimistic.locking.failure", "Another user has updated this Teacher's details while you were editing.")
                    redirect(action: manage)
                    return
                }
            }

            def isFirstPublish = teacher.publishState != 'Published'
            if (isFirstPublish) {
                teacher.datePublished = new Date()
            }
            teacher.properties = params
            teacher.publishState = "Published"

            if (!teacher.hasErrors() && teacher.save()) {
                flash.message = "teacher.change.state"
                flash.args = [teacher, teacher.publishState]
                redirect(action: manage)
            }
            else {
                redirect(action: pre_publish, id: params.id)
            }
        }
        else {
            flash.message = "teacher.not.found"
            flash.args = params.id
            redirect(action: manage)
        }
    }

    def update = {
        def teacher = Teacher.get(params.id)
        if (teacher) {
            if (params.version) {
                def version = params.version.toLong()
                if (teacher.version > version) {
                    flash.isError = true
                    teacher.errors.rejectValue("version", "teacher.optimistic.locking.failure", "Another user has updated this Teacher's details while you were editing.")
                    render(view: 'edit', model: [teacher: teacher, id: params.id])
                    return
                }
            }
            teacher.properties = params
            if (!teacher.hasErrors() && teacher.save()) {
                flash.message = "teacher.updated"
                flash.args = [teacher]
                redirect(action: manage)
            }
            else {
                flash.isError = true
                flash.message = "teacher.update.error"
                flash.args = [teacher]
                render(view: 'edit', model: [teacher: teacher, id: params.id])
            }
        }
        else {
            flash.message = "teacher.not.found"
            flash.args = params.id
            redirect(action: manage)
        }
    }

    def create = {
        def teacher = new Teacher()
        teacher.properties = params
        return [teacher: teacher]
    }

    def save = {
        def teacher = new Teacher(params)
        teacher.author = userLookupService.lookup()
        if (!teacher.hasErrors() && teacher.save()) {
            flash.message = "Article ${teacher.title} created"
            redirect(action: manage)
        }
        else {
            flash.isError = true
            flash.message = "teacher.update.error"
            render(view: 'create', model: [teacher: teacher, id: params.id])
        }
    }

    def changeState = {
        def teacher = Teacher.get(params.id)
        if (teacher) {
            if (params.version) {
                def version = params.version.toLong()
                if (teacher.version > version) {
                    flash.isError = true
                    teacher.errors.rejectValue("version", "teacher.optimistic.locking.failure", "Another user has updated this Teacher's details while you were editing.")
                    redirect(action: manage)
                    return
                }
            }
            def isFirstPublish = teacher.publishState != 'Published' && params.state == 'Published'
            if (isFirstPublish) {
                teacher.datePublished = new Date()
            }
            teacher.publishState = params.state
            teacher.deleted = false
            if (!teacher.hasErrors() && teacher.save()) {
                flash.message = "Article ${teacher.title} has been moved to ${teacher.publishState}"
                redirect(action: manage)
            }
            else {
                flash.isError = true
                redirect(action: manage)
            }
        }
        else {
            flash.message = "teacher.not.found"
            flash.args = params.id
            redirect(action: manage)
        }
    }
}
