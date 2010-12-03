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
package org.samye.dzong.london.venue

class VenueController {
    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'GET', save:'POST', update:'POST']

    def index = {
        redirect(action: manage)
    }

    def manage = {
        render(view:'manage',model:[ venues: Venue.notDeleted().list(), total: Venue.notDeletedCount().count() ])
    }

    def delete = {
        def venue = Venue.get( params.id )
        if(venue) {
            Venue.withTransaction{ status ->
                try {
                    venue.publishState = "Unpublished"
                    venue.deleted = true
                    venue.name += " (Deleted)" 	            
                    if (!venue.hasErrors() && venue.save()) {
                        flash.message = "Venue ${venue.name} deleted"
                        redirect(action:manage)
                    } else {
                        def msg = "Venue ${venue.name} could not be deleted"
                        rollback(msg,venue,manage)
                    }
                }
                catch(e) {
                    def msg = "Venue ${venue.name} could not be deleted"
                    rollback(msg,venue,manage)
                }
            }
        }
        else {
            notFound()
        }
    }


    def edit = {
        def venueInstance = Venue.get( params.id )

        if(!venueInstance) {
            notFound()
        }
        else {
            [ venue : venueInstance ]
        }
    }

    def update = {
        def venue = Venue.get(params.id)

        if (venue) {
            if (params.version) {
                def version = params.version.toLong()
                if (venue.version > version) {
                    venue.errors.rejectValue("version", "venue.optimistic.locking.failure", "Another user has updated ${venue.name} whilst you were editing.")
                    flash.message = "Changes could not be saved because of the following:"	
                    flash.isError = true
                    flash.bean = venue
                    flash.args = [venue]
                    render(view:'edit',model:[venue:venue])
                    return
                }
            }           
            
            Venue.withTransaction { status ->
                venue.properties = params
                try {
                    if (!venue.hasErrors() && venue.save()) {
                        flash.message = "Venue ${venue.name} updated"
                        redirect(action:manage)
                    }
                    else {
                        def msg = "Changes could not be saved because of the following:"	
                        rollback(msg,venue)
                    }
                } catch (RuntimeException e) {
                    def msg = "Changes could not be saved because of the following:"	
                    rollback(msg,venue)
                }
            }
        }
        else {
            notFound()
        }
    }

    def rollback(msg,venue,action=null,e=null) {
        if (e) {
            log.warn msg, e
        } else {
            log.warn msg
        }
        status.setRollbackOnly()
        if (!action) {
            render(view:'edit',model:[venue:venue])
        } else {
            handleError(msg, venue, edit)
        }
    }

    def notFound() {
        flash.message = "Venue not found"
        flash.isError = true
        redirect(action:manage)
    }

    def handleError(msg, venue, act=manage, id=null, a=[]) {
        flash.message = msg
        flash.isError = true
        flash.args = a ? a : [venue]
        flash.bean = venue
        if (id){
            redirect(action:act,id:id)
        } else {
            if (venue?.id) {
                redirect(action:act,id:venue.id)
            } else {
                redirect(action:act)
            }
        }
    }
}
