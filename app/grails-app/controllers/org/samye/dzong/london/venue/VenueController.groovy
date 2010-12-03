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

import org.samye.dzong.london.cms.*

/**
 * Provides management actions for Samye venues/buildings/sites.
 * TODO: Internaliationise messages
 * TODO: Add back in save for child associations 
 *
 * @author Leanne Northrop
 * @since  January 2010
 */
class VenueController extends CMSController {
    static allowedMethods = [delete:'GET', update:'POST']

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
                        rollback(status,msg,venue)
                        redirect(action: manage,id:params.id)
                    }
                }
                catch(e) {
                    def msg = "Venue ${venue.name} could not be deleted"
                    rollback(status,msg,venue,e)
                    redirect(action: manage,id:params.id)
                }
            }
        }
        else {
            notFound(manage)
        }
    }


    def edit = {
        def venueInstance = Venue.get( params.id )

        if(!venueInstance) {
            notFound(manage)
        }
        else {
            [ venue : venueInstance ]
        }
    }

    def update = {
        def venue = Venue.get(params.id)

        if (venue) {
            if (!versionCheck(params,venue)) {
                render(view:'edit',model:[venue:venue])
                return
            }           
            
            Venue.withTransaction { status ->
                venue.properties = params
                try {
                    ['Addresses', 'Emails', 'TelephoneNumbers'].each { venue."bind${it}"(params) }
                    if (!venue.hasErrors() && venue.save()) {
                        flash.message = "Venue ${venue.name} updated"
                        redirect(action:manage)
                    }
                    else {
                        def msg = "Changes could not be saved because of the following:"	
                        rollback(status,msg,venue)
                        render(view:'edit',model:[venue:venue])
                    }
                } catch (RuntimeException e) {
                    def msg = "Changes could not be saved because of the following:"	
                    rollback(status,msg,venue,e)
                    render(view:'edit',model:[venue:venue])
                }
            }
        }
        else {
            notFound(manage)
        }
    }
}
