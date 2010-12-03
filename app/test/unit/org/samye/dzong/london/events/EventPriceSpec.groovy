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
package org.samye.dzong.london.events

import org.springframework.context.support.StaticMessageSource
import grails.plugin.spock.*
import spock.lang.*

/**
 * Unit test for EventPrice class.
 */
class EventPriceSpec extends UnitSpec {
    def messageSource 

    def setup() {
        messageSource = new StaticMessageSource()
        def locale = Locale.UK
        messageSource.addMessage("event.display.price",locale,"{0} {1}")
        messageSource.addMessage("event.price.F",locale,"full price")
    }   
   
    def "default constructor creates valid object"() {
        setup:
            def eventprice = new EventPrice()
            eventprice.messageSource = messageSource
            eventprice.event = new Event()
            mockForConstraintsTests(EventPrice, [ eventprice ])

        expect:
            assert eventprice.validate()
    }

    def "default constructor creates full price 0 gbp"() {
        setup:
            def eventprice = new EventPrice()
            eventprice.messageSource = messageSource

        expect:
            eventprice.price == 0.0d
            eventprice.category == 'F' 
            eventprice.currency == Currency.getInstance("GBP") 
    }

    def "copy constructor clones all fields"() {
        setup:
            def eventprice = new EventPrice(currency:Currency.getInstance("EUR"),
                                       category:'D',
                                       price: 13.95)
            eventprice.messageSource = messageSource

        when:
            def eventclone = new EventPrice(eventprice)

        then:
            eventclone.price == 13.95d
            eventclone.category == 'D' 
            eventclone.currency == Currency.getInstance("EUR") 
    }

    def "string includes both category and price"() {
        setup:
            def eventprice = new EventPrice()
            eventprice.messageSource = messageSource

        expect:
            "full price £0.00" == eventprice.toString()
    }

    def "string includes price when error occurs"() {
        setup:
            def eventprice = new EventPrice()

        expect:
            "£0.00" == eventprice.toString()
    }

    def "validation fails when currency not set"() {
        setup:
            def eventprice = new EventPrice()
            eventprice.currency = null 
            mockForConstraintsTests(EventPrice, [ eventprice ])
            def result

        when:
            result = eventprice.validate()

        then:
            "nullable" == eventprice.errors["currency"]
    }

    def "validation fails when price not set"() {
        setup:
            def eventprice = new EventPrice()
            eventprice.price = null 
            mockForConstraintsTests(EventPrice, [ eventprice ])
            def result

        when:
            result = eventprice.validate()

        then:
            "nullable" == eventprice.errors["price"]
    }

    def "validation fails when category not set"() {
        setup:
            def eventprice = new EventPrice()
            eventprice.category = null 
            mockForConstraintsTests(EventPrice, [ eventprice ])
            def result

        when:
            result = eventprice.validate()

        then:
            "nullable" == eventprice.errors["category"]
    }

    def "validation passes when category in list"() {
        setup:
            def eventprice = new EventPrice()
            eventprice.messageSource = messageSource
            eventprice.event = new Event()
            mockForConstraintsTests(EventPrice, [ eventprice ])

        expect:
            eventprice.category = category 
            assert eventprice.validate()

        where:
            category << ['F','O','S','M','D']
    }

    def "validation fails when category not in list"() {
        setup:
            def eventprice = new EventPrice()
            eventprice.messageSource = messageSource
            eventprice.event = new Event()
            mockForConstraintsTests(EventPrice, [ eventprice ])

        expect:
            eventprice.category = category 
            assert !eventprice.validate()

        where:
            category << ('a'..'z')
    }
}
