/** *****************************************************************************
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
 ***************************************************************************** */

package org.samye.dzong.london.events

import org.samye.dzong.london.ScheduleRule
import org.joda.time.*
import org.joda.time.format.*


/**
 * Event date iCalendar rule. Subclassed from ScheduleRule to support one to many 
 * association with Event (this class being on the many side). Transient _deleted 
 * property is only used to mark instances of this class that require deletion.
 */
class EventDate extends ScheduleRule {

    boolean _deleted

    static transients = ['_deleted']

    static belongsTo = [ event : Event ]

    static mapping = {
        sort startDate:"desc"
    }

    EventDate() {
        super()
    }

    EventDate(EventDate toBeCopied) {
        super(toBeCopied)
    }   
    
    String toString() {
        def startDate = new DateTime(startDate.getTime())
        def endDate = new DateTime(endDate.getTime())
        def startTime = startTime.toDateTimeToday()
        def endTime = endTime.toDateTimeToday()
        def fmt = DateTimeFormat.forPattern("dd-MM-yyyy");
        def tfmt = DateTimeFormat.forPattern("hh:mma");
        def pfmt = PeriodFormat.getDefault()
        if (isRule) {
            if (isBounded()) {
                return "${fmt.print(startDate)} ${fmt.print(endDate)} ${tfmt.print(startTime)} - ${tfmt.print(endTime)} (duration ${pfmt.print(duration)})"; 
            } else {
                return fmt.print(startDate) + " ${tfmt.print(startTime)} - ${tfmt.print(endTime)} (duration ${pfmt.print(duration)})";            
            }
        } else {
            return fmt.print(startDate);
        }
    }
     
}
