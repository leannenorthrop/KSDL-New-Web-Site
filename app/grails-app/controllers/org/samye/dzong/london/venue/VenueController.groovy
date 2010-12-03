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
        def venueInstance = Venue.get( params.id )
        if(venueInstance) {
            Venue.withTransaction{ status ->
                try {
                    venueInstance.publishState = "Unpublished"
                    venueInstance.deleted = true
                    venueInstance.title += " (Deleted)" 	            
                    if (!venueInstance.hasErrors() && venueInstance.save()) {
                        flash.message = "Venue ${venueInstance.name} deleted"
                        redirect(action:manage)
                    } else {
                        status.setRollbackOnly()
                        log.warn "Unable to delete venue ${venue.name}", e
                        def msg = "Venue ${venue.name} could not be deleted"
                        handleError(msg, venueInstance)
                    }
                }
                catch(e) {
                    status.setRollbackOnly()
                    log.warn "Unable to delete venue ${venue.name}", e
                    def msg = "Venue ${venue.name} could not be deleted"
                    handleError(msg, venueInstance)
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
            return [ venue : venueInstance ]
        }
    }

    def update = {
		println params['telephoneNumbersList[0]']
        def venueInstance = Venue.get( params.id )
        if(venueInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(venueInstance.version > version) {

                    venueInstance.errors.rejectValue("version", "venue.optimistic.locking.failure", "Another user has updated this Venue while you were editing.")
                    render(view:'edit',model:[venueInstance:venueInstance])
                    return
                }
            }           
            venueInstance.properties = params
			if (venueInstance.addresses) {
				def _toBeDeleted = venueInstance.addresses.findAll {it._deleted}
    			def _toBeSaved = venueInstance.addresses.findAll {!it._deleted}    			
    			if (_toBeSaved) {
    			    _toBeSaved.each{
    			        it.save()}
    			}				
				if (_toBeDeleted) {
					venueInstance.addresses.removeAll(_toBeDeleted)
				}
			}
			if (venueInstance.emails) {
				def _toBeDeleted = venueInstance.emails.findAll {it._deleted}
    			def _toBeSaved = venueInstance.emails.findAll {!it._deleted}    			
    			if (_toBeSaved) {
    			    _toBeSaved.each{
    			        it.save()}
    			}				
				if (_toBeDeleted) {
					venueInstance.emails.removeAll(_toBeDeleted)
				}
			}
			if (venueInstance.telephoneNumbers) {
				def _toBeDeleted = venueInstance.telephoneNumbersList.findAll {it._deleted}
    			def _toBeSaved = venueInstance.telephoneNumbersList.findAll {!it._deleted}    			
    			if (_toBeSaved) {
    			    _toBeSaved.each{
    			        it.save()}
    			}				
				if (_toBeDeleted) {
					venueInstance.telephoneNumbers.removeAll(_toBeDeleted)
				}	
			}					
            if(!venueInstance.hasErrors() && venueInstance.save()) {
                flash.message = "Venue ${params.id} updated"
                redirect(action:manage)
            }
            else {
                def msg = "Changes could not be saved because of the following:"	
                render(view:'edit',model:[venue:venueInstance])
                handleError(msg, venueInstance, edit)
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
