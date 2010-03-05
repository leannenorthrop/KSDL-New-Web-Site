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
import org.joda.time.TimeOfDay
import org.joda.time.contrib.hibernate.PersistentPeriod
import org.joda.time.contrib.hibernate.PersistentTimeOfDay
import net.fortuna.ical4j.*
import net.fortuna.ical4j.model.*
import net.fortuna.ical4j.util.*
import net.fortuna.ical4j.model.property.*
import net.fortuna.ical4j.model.component.*
import net.fortuna.ical4j.model.parameter.*
import net.fortuna.ical4j.data.*

class ScheduleRule {
    java.util.Date startDate
    java.util.Date endDate
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

    static transients = ['unbounded','bounded','daily','weekly','monthly','monthlyByPosition','yearly','yearlyByPosition','yearlyByDay','days','modifiers','offsets','onDay','between','recur','isOnDay']

    String toString() {
        return "from ${startDate} until ${endDate}: ${startTime} - ${endTime} (duration ${duration})\nis rule? ${isRule} type ${ruleType} interval ${interval} modifier ${modifier} mtype = ${modifierType}"
    }

    boolean isUnbounded() {
        return 'always' == ruleType
    }

    boolean isBounded() {
        return 'always' != ruleType
    }

    void setBounded() {
        ruleType = 'period'
    }

    boolean isDaily() {
        return 'D' == modifierType
    }

    void setDaily() {
        modifierType = 'D'
    }

    boolean isWeekly() {
        return 'W' == modifierType
    }

    void setWeekly() {
        modifierType = 'W'
    }

    boolean isMonthly() {
        return 'MP' == modifierType || 'MD' == modifierType
    }


    void setMonthlyByPosition() {
        modifierType = 'MP'
    }

    void setMonthlyByDay() {
        modifierType = 'MD'
    }

    boolean isYearly() {
        return 'YP' == modifierType || 'YD' == modifierType
    }

    void setYearlyByPosition() {
        modifierType = 'YP'
    }

    void setYearlyByDay() {
        modifierType = 'YD'
    }

    List getDays() {
        def days = []
        days = modifier?.split(" ").findAll { it ->
            it ==~ /[MTWFS][OUEHRA]/
        }
        return days
    }

    List getModifiers() {
        def matcher = modifier =~ /[1-5][+-] [MTWFS][OUEHRA]/
        def mods = matcher.collect() { it ->
            def bits = it.split(" ")
            if (bits[0][1] == '+') {
                bits[0] = "" + bits[0][0]
            } else {
                bits[0] = "" + 5
            }

            return bits
        }
        return mods
    }

    List getOffsets() {
        def offsets = []
        offsets = modifier?.split(" ").findAll { it ->
            it ==~ /\d[+-]/
        }
        offsets = offsets.collect { it ->
            if (it[1] == '-') {
                return 5
            } else {
                return it[0]
            }
        }
        return offsets
    }


    boolean isOnDay(final date) {
        boolean onDay = false;

        Recur r = toRecur()
        if (r) {
            if (isDaily() && interval == 1) {
                onDay = true;
            } else {
                net.fortuna.ical4j.model.Date next = r.getNextDate(new net.fortuna.ical4j.model.Date(startDate), new net.fortuna.ical4j.model.Date(date))
                if (next) {
                    def nextDate = new java.util.Date(next.getTime())
                    onDay = date.equals(nextDate)
                } else {
                    onDay = false
                }
            }
        } else {
            onDay = startDate.equals(date)
        }

        return onDay
    }

    boolean isOnDay(final startingDate, final noOfDays) {
        boolean onDay = false

        Recur r = toRecur()
        if (r) {
            net.fortuna.ical4j.model.Date next = r.getNextDate(new net.fortuna.ical4j.model.Date(startDate), new net.fortuna.ical4j.model.Date(startingDate))
            if (next) {
                def nextDate = new java.util.Date(next.getTime())
                for (int i = 0; i < noOfDays; i++) {
                    onDay = (startingDate + i).equals(nextDate)
                    if (onDay) break;
                }
            } else {
                onDay = false;
            }
        } else {
            for (int i = 0; i < noOfDays; i++) {
                onDay = (startingDate + i).equals(startDate)
                if (onDay) break;
            }
        }

        return onDay
    }

    Recur toRecur() {
        Recur recur = null

        if (isRule) {
            String type;
            int count = 1;
            if (isDaily()) {
                type = Recur.DAILY;
                count = 356;
            } else if (isWeekly()) {
                type = Recur.WEEKLY;
                count = 52;
            } else if (isMonthly()) {
                type = Recur.MONTHLY;
                count = 12;
            } else if (isYearly()) {
                type = Recur.YEARLY;
                count = 1;
            }

            if (startDate == endDate) {
                recur = new Recur(type,count);
            } else {
                recur = new Recur(type, new net.fortuna.ical4j.model.Date(endDate));
            }

            def daysList = recur.getDayList();
            List days = getDays();
            days.each {
                daysList.add(new WeekDay(it));
            }

            List offsets = getOffsets()
            def posList = recur.getSetPosList()
            offsets.each {
                posList.add(Integer.valueOf(it))
            }
            recur.setInterval(interval);
            recur.setWeekStartDay(WeekDay.MO.getDay());
        }

        return recur
    }
}
