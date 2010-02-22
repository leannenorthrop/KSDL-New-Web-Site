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

package org.samye.dzong.london

import org.joda.time.Period
import org.joda.time.contrib.hibernate.PersistentPeriod
import org.joda.time.TimeOfDay
import org.joda.time.contrib.hibernate.PersistentTimeOfDay

class ScheduleRule {
    Date startDate
    Date endDate
    TimeOfDay startTime;
    TimeOfDay endTime;
    Period duration
    Boolean isRule
    String ruleType
    Integer interval
    String modifier
    String modifierType

    static constraints = {
        startTime(nullable: false)
        endTime(nullable: false)
        startTime(nullable: false)
        endTime(nullable: false)
        duration(blank: true,nullable: true)
        isRule(blank: false)
        ruleType(blank: true,nullable: true)
        interval(blank: true,nullable: true)
        modifier(blank: true,nullable: true)
        modifierType(blank: true,inList:["D", "W", "MP", "MD", "YM", "YD"],nullable: true)
    }

    static mapping = {
        columns {
            startTime type: PersistentTimeOfDay
            endTime type: PersistentTimeOfDay
            duration type: PersistentPeriod
            content type: 'text'
            summary type: 'text'
        }
    }

    String toString() {
        return "${startDate} ${startTime} ${endTime} until ${endDate} (duration ${duration}) is rule? ${isRule}"
    }
}
