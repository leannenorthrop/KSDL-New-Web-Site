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
                        status.setRollbackOnly()
                        log.warn "Unable to delete venue ${venue.name}"
                        def msg = "Venue ${venue.name} could not be deleted"
                        handleError(msg, venue)
                    }
                }
                catch(e) {
                    status.setRollbackOnly()
                    log.warn "Unable to delete venue ${venue.name}", e
                    def msg = "Venue ${venue.name} could not be deleted"
                    handleError(msg, venue)
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
        def venueInstance = Venue.get(params.id)

        if (venueInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (venueInstance.version > version) {
                    venueInstance.errors.rejectValue("version", "venue.optimistic.locking.failure", "Another user has updated ${venue.name} whilst you were editing.")
                    render(view:'edit',model:[venueInstance:venueInstance])
                    return
                }
            }           
            
            Venue.withTransaction { status ->
                venueInstance.properties = params
                try {
                    if (!venueInstance.hasErrors() && venueInstance.save()) {
                        flash.message = "Venue ${venue.name} updated"
                        redirect(action:manage)
                    }
                    else {
                        println "failed to save"
                        status.setRollbackOnly()
                        def msg = "Changes could not be saved because of the following:"	
                        render(view:'edit',model:[venue:venueInstance])
                        handleError(msg, venueInstance, edit)
                    }
                } catch (RuntimeException e) {
                    println e
                    status.setRollbackOnly()
                    def msg = "Changes could not be saved because of the following:"	
                    render(view:'edit',model:[venue:venueInstance])
                    handleError(msg, venueInstance, edit)
                }
            }
        }
        else {
            notFound()
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
