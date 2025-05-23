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

import java.util.Locale;
import java.util.TimeZone;

/**
 * The class <code>Era</code> represents a calendar era that defines a
 * period of time in which the same year numbering is used. For
 * example, Gregorian year 2004 is <I>Heisei</I> 16 in the Japanese
 * calendar system. An era starts at any point of time (Gregorian) that is
 * represented by <code>CalendarDate</code>.
 *
 * <p><code>Era</code>s that are applicable to a particular calendar
 * system can be obtained by calling {@link sune.util.calendar.CalendarSystem#getEras}
 * one of which can be used to specify a date in
 * <code>CalendarDate</code>.
 *
 * <p>The following era names are defined in this release.
 * <!-- TODO: use HTML table -->
 * <pre><tt>
 *   Calendar system         Era name         Since (in Gregorian)
 *   -----------------------------------------------------------------------
 *   Japanese calendar       Meiji            1868-01-01 midnight local time
 *                           Taisho           1912-07-30 midnight local time
 *                           Showa            1926-12-26 midnight local time
 *                           Heisei           1989-01-08 midnight local time
 *   Julian calendar         BeforeCommonEra  -292275055-05-16T16:47:04.192Z
 *                           CommonEra        0000-12-30 midnight local time
 *   Taiwanese calendar      MinGuo           1911-01-01 midnight local time
 *   Thai Buddhist calendar  BuddhistEra      -543-01-01 midnight local time
 *   -----------------------------------------------------------------------
 * </tt></pre>
 *
 * @author Masayoshi Okutsu
 * @since 1.5
 */

public final class Era {
    private final String name;
    private final String abbr;
    private final long since;
    private final sune.util.calendar.CalendarDate sinceDate;
    private final boolean localTime;

    /**
     * Constructs an <code>Era</code> instance.
     *
     * @param name the era name (e.g., "BeforeCommonEra" for the Julian calendar system)
     * @param abbr the abbreviation of the era name (e.g., "B.C.E." for "BeforeCommonEra")
     * @param since the time (millisecond offset from January 1, 1970
     * (Gregorian) UTC or local time) when the era starts, inclusive.
     * @param localTime <code>true</code> if <code>since</code>
     * specifies a local time; <code>false</code> if
     * <code>since</code> specifies UTC
     */
    public Era(String name, String abbr, long since, boolean localTime) {
        this.name = name;
        this.abbr = abbr;
        this.since = since;
        this.localTime = localTime;
        Gregorian gcal = CalendarSystem.getGregorianCalendar();
        sune.util.calendar.BaseCalendar.Date d = (BaseCalendar.Date) gcal.newCalendarDate(null);
        gcal.getCalendarDate(since, d);
        sinceDate = new sune.util.calendar.ImmutableGregorianDate(d);
    }

    public String getName() {
        return name;
    }

    public String getDisplayName(Locale locale) {
        return name;
    }

    public String getAbbreviation() {
        return abbr;
    }

    public String getDiaplayAbbreviation(Locale locale) {
        return abbr;
    }

    public long getSince(TimeZone zone) {
        if (zone == null || !localTime) {
            return since;
        }
        int offset = zone.getOffset(since);
        return since - offset;
    }

    public CalendarDate getSinceDate() {
        return sinceDate;
    }

    public boolean isLocalTime() {
        return localTime;
    }

    public boolean equals(Object o) {
        if (!(o instanceof Era)) {
            return false;
        }
        Era that = (Era) o;
        return name.equals(that.name)
            && abbr.equals(that.abbr)
            && since == that.since
            && localTime == that.localTime;
    }

    private int hash = 0;

    public int hashCode() {
        if (hash == 0) {
            hash = name.hashCode() ^ abbr.hashCode() ^ (int)since ^ (int)(since >> 32)
                ^ (localTime ? 1 : 0);
        }
        return hash;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        sb.append(getName()).append(" (");
        sb.append(getAbbreviation()).append(')');
        sb.append(" since ").append(getSinceDate());
        if (localTime) {
            sb.setLength(sb.length() - 1); // remove 'Z'
            sb.append(" local time");
        }
        sb.append(']');
        return sb.toString();
    }
}
