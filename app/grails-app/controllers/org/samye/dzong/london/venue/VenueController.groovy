package org.samye.dzong.london.venue

class VenueController {
    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'GET', save:'POST', update:'POST']

    def manage = {
        params.max = Math.min((params.max ? params.max.toInteger() : 10),  100)
        render(view:'manage',model:[ venues: Venue.notDeleted().list(), total: Venue.notDeleted().count() ])
    }

    def create = {
        def venueInstance = new Venue()
        venueInstance.properties = params
        return ['venueInstance':venueInstance]
    }

    def save = {
        def venueInstance = new Venue(params)
        if(!venueInstance.hasErrors() && venueInstance.save()) {
            flash.message = "Venue ${venueInstance.id} created"
            redirect(action:manage,id:venueInstance.id)
        }
        else {
            flash.isError = true
            render(view:'create',model:[venueInstance:venueInstance])
        }
    }

    def delete = {
        def venueInstance = Venue.get( params.id )
        if(venueInstance) {
            try {
				venueInstance.publishState = "Unpublished"
	            venueInstance.deleted = true
	            if (!venueInstance.hasErrors() && venueInstance.save()) {
	                flash.message = "Venue ${venueInstance.name} deleted"
	            }
                redirect(action:manage)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Venue ${params.id} could not be deleted"
                redirect(action:manage,id:params.id)
            }
        }
        else {
            flash.message = "Venue not found with id ${params.id}"
            redirect(action:manage)
        }
    }


    def edit = {
        def venueInstance = Venue.get( params.id )

        if(!venueInstance) {
            flash.message = "Venue not found with id ${params.id}"
            redirect(action:manage)
        }
        else {
            return [ venue : venueInstance ]
        }
    }

    def update = {
		println params
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
				if (_toBeDeleted) {
					venueInstance.addresses.removeAll(_toBeDeleted)
				}
			}
			if (venueInstance.emails) {
				def _toBeDeleted = venueInstance.emails.findAll {it._deleted}
				if (_toBeDeleted) {
					venueInstance.emails.removeAll(_toBeDeleted)
				}
			}
			if (venueInstance.telephoneNumbers) {
				def _toBeDeleted = venueInstance.telephoneNumbers.findAll {it._deleted}
				if (_toBeDeleted) {
					venueInstance.telephoneNumbers.removeAll(_toBeDeleted)
				}	
			}					
            if(!venueInstance.hasErrors() && venueInstance.save()) {
                flash.message = "Venue ${params.id} updated"
                redirect(action:manage)
            }
            else {
                flash.message = "Changes could not be saved because of the following:"	
				flash.isError = true
				flash.args = [venueInstance]
                render(view:'edit',model:[venue:venueInstance])
            }
        }
        else {
            flash.message = "Venue not found with id ${params.id}"
            redirect(action:manage)
        }
    }
}
