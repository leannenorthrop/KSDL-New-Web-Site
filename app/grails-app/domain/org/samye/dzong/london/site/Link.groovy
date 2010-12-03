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

package org.samye.dzong.london.site

import org.codehaus.groovy.grails.commons.*

/**
 * Domain class for storing a single href to either an external or interal page.
 *
 * @author Leanne Northrop
 * @since  April 2010
 */
class Link {
    def config = ConfigurationHolder.config
    
    String type                                
    String name
    String href                            
    String controller
    String action
    String refid                 
    Integer position
    String section

    boolean _deleted

    static transients = ['_deleted']

    static constraints = {
        action(nullable:true)        
        controller(nullable:true)
        href(nullable:true)
        refid(nullable:true)        
        name(blank:false)
        type(blank:false,inList:['I','E'])        
        position(nullable:false)
        section(nullable:false,inList:['H','B','M'])        
    }

    static mapping = {
        sort position:"asc"
        cache usage:'read-write', include:'non-lazy'
    }
    
    Link() {
        type = "I"
        name = "New"
        href = "/"
        position = 0
    }

    String toString() {
        def locale = Locale.UK
        def linkTypeName = ''
        def classes = ''
        def thehref = ''
        def link = ''
        if (type == 'E') {
            linkTypeName = 'external';
            classes = ['menuItem',linkTypeName].join(" ");
            thehref = this.href;
            link="<a href='${thehref}' target='_blank' class='${classes}'>${name}</a>"
        } else {
            linkTypeName = 'internal';
            thehref = controller + "/" + action
            def c = controller[0].toUpperCase()
            classes = ['menuItem',linkTypeName,action,c].join(" ")        
            link = "<a href='${thehref}' class='${classes}'>${name}</a>"
        }

        return link
    }
}
