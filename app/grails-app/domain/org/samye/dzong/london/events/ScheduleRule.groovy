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
package org.samye.dzong.london.events

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
import org.joda.time.*

/*
 * Base class for specifying a rule for recurring dates.
 */
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
        duration(nullable: true)
        isRule(nullable: false)
        ruleType(blank: true,nullable: true)
        interval(nullable: true)
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
            interval column:'the_interval'
        }
    }

    static transients = ['unbounded','bounded','daily','weekly','monthly','monthlyByPosition','yearly','yearlyByPosition','yearlyByDay','days','modifiers','offsets','onDay','between','recur','isOnDay']

    ScheduleRule() {
        this.startDate = new Date()
        this.startTime = new TimeOfDay(9, 0)
        this.endTime = new TimeOfDay(10, 0)
        this.endDate = new Date()
        this.isRule = false
        this.ruleType = "once"
        this.interval = 1
        this.modifierType = "D"
        this.duration = new MutablePeriod(startTime.toDateTimeToday(), endTime.toDateTimeToday()).toPeriod()
    }

    ScheduleRule(ScheduleRule toBeCopied) {
        this.startDate = toBeCopied.startDate
        this.startTime = toBeCopied.startTime
        this.endTime = toBeCopied.endTime
        this.endDate = toBeCopied.endDate
        this.isRule = toBeCopied.isRule
        this.ruleType = toBeCopied.ruleType
        this.duration = toBeCopied.duration
        this.interval = toBeCopied.interval
        this.modifier = toBeCopied.modifier
        this.modifierType = toBeCopied.modifierType
    }
    
    String toString() {
        return "from ${startDate} until ${endDate}: ${startTime} - ${endTime} (duration ${duration})\nis rule? ${isRule} type ${ruleType} interval ${interval} modifier ${modifier} mtype = ${modifierType}"
    }

    java.util.Date toDate() {
        def start = startDate
        start.clearTime()
        def today = new Date()
        today.clearTime()
        Recur r = toRecur()
        if (r) {
            net.fortuna.ical4j.model.Date next = r.getNextDate(new net.fortuna.ical4j.model.Date(startDate), new net.fortuna.ical4j.model.Date(today))
            def result = new Date(next.getTime())
            return result

        } else {
            return start
        }
    }

    java.util.Date toDateTime() {
        def date = toDate()
        def startDate = startTime.toDateTime(date)
        new Date(startDate.millis)
    }

    boolean isUnbounded() {
        return 'always' == ruleType
    }

    boolean isBounded() {
        return 'between' == ruleType
    }
    
    def isSeveral() {
        return 'several' == ruleType
    }  
    
    def isOnce() {
        return 'once' == ruleType
    }      

    void setBounded() {
        ruleType = 'between'
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

        date.clearTime()
        startDate.clearTime()
        Recur r = toRecur()
        if (r) {
            if (isDaily() && interval == 1 && (startDate.before(date) || startDate == date)) {
                onDay = true;
                log.debug "${this} == ${date.format('dd MM yyyy')}? == ${onDay} because it's daily"
            } else {
				if (startDate.equals(date)) {
					onDay = true;
				} else {
	                net.fortuna.ical4j.model.Date next = r.getNextDate(new net.fortuna.ical4j.model.Date(startDate),
                                                                       new net.fortuna.ical4j.model.Date(date))
	                if (next) {
	                    def nextDate = new java.util.Date(next.getTime())
                        nextDate.clearTime()
	                    onDay = date == nextDate
                        log.debug "${date.format('dd MM yyyy')} == ${nextDate.format('dd MM yyyy')} ? ${onDay}"
	                } else {
                        log.debug "${this} does not occur on ${date.format('dd MM yyyy')} because next is null"
	                    onDay = false
	                }
				}
            }
        } else {
            onDay = startDate == date
            log.debug "${this} == ${date.format('dd MM yyyy')}? == ${onDay}"
        }

        return onDay
    }

    boolean isOnDay(final startingDate, final noOfDays) {
        boolean onDay = false

        Recur r = toRecur()
        if (r) {
            startingDate.clearTime()
            startDate.clearTime()
            net.fortuna.ical4j.model.Date next = r.getNextDate(new net.fortuna.ical4j.model.Date(startDate), new net.fortuna.ical4j.model.Date(startingDate))
            if (next) {
                def nextDate = new java.util.Date(next.getTime())
                nextDate.clearTime()
                for (int i = 0; i < noOfDays; i++) {
                    onDay = (startingDate + i) == nextDate
                    if (onDay) break;
                }
            } else {
                onDay = false;
            }
        } else {
            for (int i = 0; i < noOfDays; i++) {
                onDay = (startingDate + i) == startDate
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

    def onLoad() {
        this.duration = new MutablePeriod(startTime.toDateTimeToday(), endTime.toDateTimeToday()).toPeriod()
    }
}
