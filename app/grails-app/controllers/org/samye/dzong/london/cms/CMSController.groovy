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

package org.samye.dzong.london.cms

/**
 * CMS helper base class for controllers which manage CMS domain objects.
 *
 * @author Leanne Northrop
 * @since 12th November 2010, 18:09
 */
abstract class CMSController {
    def ajaxUnpublished = {
        render(view: 'unpublished',model:getModelForView('unpublished',params))
    }

    def ajaxPublished = {
        render(view: 'published',model:getModelForView('published',params))
    }

    def ajaxArchived = {
        render(view: 'archived',model:getModelForView('archived',params))
    }

    def ajaxReady = {
        render(view: 'ready',model:getModelForView('ready',params))
    }

    def ajaxDeleted = {
        render(view: 'deleted',model:getModelForView('deleted',params))
    }
}

