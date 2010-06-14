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
package org.samye.dzong.london.venue

import org.samye.dzong.london.venue.Room

/**
 * Service helper class for Teacher domain objects.
 *
 * TODO: internationalize
 * TODO: test
 *
 * Author: Leanne Northrop
 * Date: 14th June 2010, 14:31
 */
class RoomService {
    boolean transactional = true
    def userLookupService

    def view(id) {
        def room = Room.get(id)
        if(!room) {
            flash.message = "Room not found (id ${params.id} unknown)"
            return null
        }
        else {
            return [room: room]
        }
    }

    def userUnpublished(params) {
        def order = params.sort?: "name"
        def dir = params.order?: "asc"
        def username = userLookupService.username();
        def rooms = Room.orderedAuthorPublishState(username,"Unpublished", order, dir).list(params);
        def total = Room.authorPublishState(username,"Unpublished").count();
        return [rooms: rooms, total: total]
    }

    def userPublished(params) {
        def order = params.sort?: "name"
        def dir = params.order?: "asc"
        def username = userLookupService.username();
        def rooms = Room.orderedAuthorPublishState(username,"Published", order, dir).list(params);
        def total = Room.authorPublishState(username,"Published").count();
        return [rooms: rooms, total: total]
    }

    def userArchived(params) {
        def order = params.sort?: "name"
        def dir = params.order?: "asc"
        def username = userLookupService.username();
        def rooms = Room.orderedAuthorPublishState(username,"Archived", order, dir).list(params);
        def total = Room.authorPublishState(username,"Archived").count();
        return [rooms: rooms, total: total]
    }

    def userDeleted(params) {
        def username = userLookupService.username();
        def rooms = Room.deletedAuthor(username).list(params);
        def total = Room.deletedAuthor(username).count();
        return [rooms: rooms, total: total]
    }

    def unpublished(params) {
        def order = params.sort?: "name"
        def dir = params.order?: "asc"
        def rooms = Room.orderedPublishState("Unpublished", order, dir).list(params);
        def total = Room.publishState("Unpublished").count();
        return [rooms: rooms, total: total]
    }

    def published(params) {
        def order = params.sort?: "name"
        def dir = params.order?: "asc"
        def rooms = Room.orderedPublishState("Published", order, dir).list(params);
        def total = Room.publishState("Published").count();
        return [rooms: rooms, total: total]
    }

    def archived(params) {
        def order = params.sort?: "name"
        def dir = params.order?: "asc"
        def rooms = Room.orderedPublishState("Archived", order, dir).list(params);
        def total = Room.publishState("Archived").count();
        return [rooms: rooms, total: total]
    }

    def deleted(params) {
        def rooms = Room.deleted().list(params);
        def total = Room.deleted().count();
        return [rooms: rooms, total: total]
    }
}
