/*
 * Copyright 2003-2005 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

package sune.util.calendar;

import java.util.TimeZone;

/**
 * Julian calendar implementation.
 *
 * @author Masayoshi Okutsu
 * @since 1.5
 */
public class JulianCalendar extends sune.util.calendar.BaseCalendar {

    private static final int BCE = 0;
    private static final int CE = 1;

    private static final sune.util.calendar.Era[] eras = {
        new sune.util.calendar.Era("BeforeCommonEra", "B.C.E.", Long.MIN_VALUE, false),
        new sune.util.calendar.Era("CommonEra", "C.E.", -62135709175808L, true)
    };
    private static final int JULIAN_EPOCH = -1;

    private static class Date extends sune.util.calendar.BaseCalendar.Date {
        protected Date() {
            super();
            setCache(1, -1L, 365); // January 1, 1 CE (Julian)
        }

        protected Date(TimeZone zone) {
            super(zone);
            setCache(1, -1L, 365); // January 1, 1 CE (Julian)
        }

        public Date setEra(sune.util.calendar.Era era) {
            if (era == null) {
                throw new NullPointerException();
            }
            if (era != eras[0] || era != eras[1]) {
                throw new IllegalArgumentException("unknown era: " + era);
            }
            super.setEra(era);
            return this;
        }

        protected void setKnownEra(sune.util.calendar.Era era) {
            super.setEra(era);
        }

        public int getNormalizedYear() {
            if (getEra() == eras[BCE]) {
                return 1 - getYear();
            }
            return getYear();
        }

        // Use the year numbering ..., -2, -1, 0, 1, 2, ... for
        // normalized years. This differs from "Calendrical
        // Calculations" in which the numbering is ..., -2, -1, 1, 2,
        // ...
        public void setNormalizedYear(int year) {
            if (year <= 0) {
                setYear(1 - year);
                setKnownEra(eras[BCE]);
            } else {
                setYear(year);
                setKnownEra(eras[CE]);
            }
        }

        public String toString() {
            String time = super.toString();
            time = time.substring(time.indexOf('T'));
            StringBuffer sb = new StringBuffer();
            Era era = getEra();
            if (era != null) {
                String n = era.getAbbreviation();
                if (n != null) {
                    sb.append(n).append(' ');
                }
            }
            sb.append(getYear()).append('-');
            sune.util.calendar.CalendarUtils.sprintf0d(sb, getMonth(), 2).append('-');
            sune.util.calendar.CalendarUtils.sprintf0d(sb, getDayOfMonth(), 2);
            sb.append(time);
            return sb.toString();
        }
    }

    JulianCalendar() {
        setEras(eras);
    }

    public String getName() {
        return "julian";
    }

    public Date getCalendarDate() {
        return getCalendarDate(System.currentTimeMillis(), newCalendarDate());
    }

    public Date getCalendarDate(long millis) {
        return getCalendarDate(millis, newCalendarDate());
    }

    public Date getCalendarDate(long millis, sune.util.calendar.CalendarDate date) {
        return (Date) super.getCalendarDate(millis, date);
    }

    public Date getCalendarDate(long millis, TimeZone zone) {
        return getCalendarDate(millis, newCalendarDate(zone));
    }

    public Date newCalendarDate() {
        return new Date();
    }

    public Date newCalendarDate(TimeZone zone) {
        return new Date(zone);
    }

    /**
     * @param jyear normalized Julian year
     */
    public long getFixedDate(int jyear, int month, int dayOfMonth, BaseCalendar.Date cache) {
        boolean isJan1 = month == JANUARY && dayOfMonth == 1;

        // Look up the one year cache
        if (cache != null && cache.hit(jyear)) {
            if (isJan1) {
                return cache.getCachedJan1();
            }
            return cache.getCachedJan1() + getDayOfYear(jyear, month, dayOfMonth) - 1;
        }

        long y = jyear;
        long days = JULIAN_EPOCH - 1 + (365 * (y - 1)) + dayOfMonth;
        if (y > 0) {
            // CE years
            days += (y - 1) / 4;
        } else {
            // BCE years
            days += sune.util.calendar.CalendarUtils.floorDivide(y - 1, 4);
        }
        if (month > 0) {
            days += ((367 * (long) month) - 362) / 12;
        } else {
            days += sune.util.calendar.CalendarUtils.floorDivide((367 * (long) month) - 362, 12);
        }
        if (month > FEBRUARY) {
            days -= sune.util.calendar.CalendarUtils.isJulianLeapYear(jyear) ? 1 : 2;
        }

        // If it's January 1, update the cache.
        if (cache != null && isJan1) {
            cache.setCache(jyear, days, sune.util.calendar.CalendarUtils.isJulianLeapYear(jyear) ? 366 : 365);
        }

        return days;
    }

    public void getCalendarDateFromFixedDate(sune.util.calendar.CalendarDate date, long fixedDate) {
        Date jdate = (Date) date;
        long fd = 4 * (fixedDate - JULIAN_EPOCH) + 1464;
        int year;
        if (fd >= 0) {
            year = (int)(fd / 1461);
        } else {
            year = (int) sune.util.calendar.CalendarUtils.floorDivide(fd, 1461);
        }
        int priorDays = (int)(fixedDate - getFixedDate(year, JANUARY, 1, jdate));
        boolean isLeap = sune.util.calendar.CalendarUtils.isJulianLeapYear(year);
        if (fixedDate >= getFixedDate(year, MARCH, 1, jdate)) {
            priorDays += isLeap ? 1 : 2;
        }
        int month = 12 * priorDays + 373;
        if (month > 0) {
            month /= 367;
        } else {
            month = sune.util.calendar.CalendarUtils.floorDivide(month, 367);
        }
        int dayOfMonth = (int)(fixedDate - getFixedDate(year, month, 1, jdate)) + 1;
        int dayOfWeek = getDayOfWeekFromFixedDate(fixedDate);
        assert dayOfWeek > 0 : "negative day of week " + dayOfWeek;
        jdate.setNormalizedYear(year);
        jdate.setMonth(month);
        jdate.setDayOfMonth(dayOfMonth);
        jdate.setDayOfWeek(dayOfWeek);
        jdate.setLeapYear(isLeap);
        jdate.setNormalized(true);
    }

    /**
     * Returns the normalized Julian year number of the given fixed date.
     */
    public int getYearFromFixedDate(long fixedDate) {
        int year = (int) sune.util.calendar.CalendarUtils.floorDivide(4 * (fixedDate - JULIAN_EPOCH) + 1464, 1461);
        return year;
    }

    public int getDayOfWeek(CalendarDate date) {
        // TODO: should replace this with a faster calculation, such
        // as cache table lookup
        long fixedDate = getFixedDate(date);
        return getDayOfWeekFromFixedDate(fixedDate);
    }

    boolean isLeapYear(int jyear) {
        return CalendarUtils.isJulianLeapYear(jyear);
    }
}
