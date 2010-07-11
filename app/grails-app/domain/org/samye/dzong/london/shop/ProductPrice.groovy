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

package org.samye.dzong.london.shop

import java.text.NumberFormat

class ProductPrice extends Price {
    def messageSource

    boolean _deleted

    static transients = ['_deleted']

    static belongsTo = [ product : Product ]

    static constraints = {
    }

    static mapping = {
        sort category:"desc"
    }
    
    ProductPrice() {
        currency = Currency.getInstance("GBP")
        category = 'f'
        price = 0.0d
    }

    ProductPrice(ProductPrice toBeCopied) {
        this.currency = toBeCopied.currency;
        this.price = toBeCopied.price
        this.category = toBeCopied.category
    }

    String toString() {
        def locale = Locale.UK
        def thecategory = messageSource.getMessage('product.price.'+category,null,locale)
        def currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        def currencyOut = currencyFormatter.format(price);
        return messageSource.getMessage('product.display.price',[thecategory, currencyOut].toArray(),locale)
    }
}
