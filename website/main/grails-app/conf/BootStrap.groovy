import org.samye.dzong.london.contact.Address
import org.samye.dzong.london.contact.EmailAddress
import org.samye.dzong.london.contact.TelephoneNumber
import org.samye.dzong.london.event.RegularEvent
import org.samye.dzong.london.venue.Venue
import org.samye.dzong.london.venue.Facility

class BootStrap {

     def init = { servletContext ->
        def manorPlaceNumber = new TelephoneNumber(label: 'Enquiries', type: 'Main', number: '+44 (0)20 7708 8969').save()
        def manorPlaceEmail = new EmailAddress(label: 'Enquiries', type: 'Main', email: 'manorplace@samye.org').save()
        def manorPlaceAddress = new Address(placeName:'Manor Place',placeNumber:'33',street1:'Manor Place',town:'London',county:'',postCode:'SE17 3BD',type:'Main').save()
        def disabledAccess = new Facility(name: 'Disabled Access', available: false)
        def manorPlace = new Venue(name:'Manor Place');
        if (!manorPlace.save()){ event1.errors.allErrors.each{ error ->
            println "An error occured with manorPlace: ${manorPlace}"
            }
        }
        manorPlace.addToAddresses(manorPlaceAddress)
        manorPlace.addToEmails(manorPlaceEmail)
        manorPlace.addToContactNumbers(manorPlaceNumber)
        manorPlace.addToFacilities(disabledAccess)
        manorPlace.save()
     }

     def destroy = {
     }
}