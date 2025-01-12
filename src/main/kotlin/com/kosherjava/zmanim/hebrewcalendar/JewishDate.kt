/*
 * Zmanim Java API
 * Copyright (C) 2011 - 2021 Eliyahu Hershfeld
 * Copyright (C) September 2002 Avrom Finkelstien
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful,but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA,
 * or connect to: http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 */
package com.kosherjava.zmanim.hebrewcalendar

import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

/**
 * The JewishDate is the base calendar class, that supports maintenance of a [java.util.GregorianCalendar]
 * instance along with the corresponding Jewish date. This class can use the standard Java Date and Calendar
 * classes for setting and maintaining the dates, but it does not subclass these classes or use them internally
 * in any calculations. This class also does not have a concept of a time (which the Date class does). Please
 * note that the calendar does not currently support dates prior to 1/1/1 Gregorian. Also keep in mind that the
 * Gregorian calendar started on October 15, 1582, so any calculations prior to that are suspect (at least from
 * a Gregorian perspective). While 1/1/1 Gregorian and forward are technically supported, any calculations prior to [Hillel II's (Hakatan's](http://en.wikipedia.org/wiki/Hillel_II)) calendar (4119 in the Jewish Calendar / 359
 * CE Julian as recorded by [Rav Hai Gaon](http://en.wikipedia.org/wiki/Hai_Gaon)) would be just an
 * approximation.
 *
 * This open source Java code was written by [Avrom Finkelstien](http://www.facebook.com/avromf) from his C++
 * code. It was refactored to fit the KosherJava Zmanim API with simplification of the code, enhancements and some bug
 * fixing.
 *
 * Some of Avrom's original C++ code was translated from
 * [C/C++ code](https://web.archive.org/web/20120124134148/http://emr.cs.uiuc.edu/~reingold/calendar.C) in
 * [Calendrical Calculations](http://www.calendarists.com) by Nachum Dershowitz and Edward M.
 * Reingold, Software-- Practice &amp; Experience, vol. 20, no. 9 (September, 1990), pp. 899- 928. Any method with the mark
 * "ND+ER" indicates that the method was taken from this source with minor modifications.
 *
 * If you are looking for a class that implements a Jewish calendar version of the Calendar class, one is available from
 * the [ICU (International Components for Unicode)](http://site.icu-project.org/) project, formerly part of
 * IBM's DeveloperWorks.
 *
 * @see JewishCalendar
 *
 * @see HebrewDateFormatter
 *
 * @see java.util.Date
 *
 * @see java.util.Calendar
 *
 * @author  Avrom Finkelstien 2002
 * @author  Eliyahu Hershfeld 2011 - 2021
 */
open class JewishDate : Comparable<JewishDate>, Cloneable {
    /** the internal Jewish month. */
    private var jewishMonth = 0

    /** the internal Jewish day. */
    private var jewishDay = 0

    /** the internal Jewish year. */
    private var jewishYear = 0
    /**
     * Returns the molad hours. Only a JewishDate object populated with [.getMolad],
     * [.setJewishDate] or [.setMoladHours] will have this field
     * populated. A regular JewishDate object will have this field set to 0.
     *
     * @return the molad hours
     * @see .setMoladHours
     * @see .getMolad
     * @see .setJewishDate
     */
    /**
     * Sets the molad hours.
     *
     * @param moladHours
     * the molad hours to set
     * @see .getMoladHours
     * @see .getMolad
     * @see .setJewishDate
     */
    /** the internal count of *molad* hours. */
    var moladHours = 0
    /**
     * Returns the molad minutes. Only an object populated with [.getMolad],
     * [.setJewishDate] or or [.setMoladMinutes] will have these fields
     * populated. A regular JewishDate object will have this field set to 0.
     *
     * @return the molad minutes
     * @see .setMoladMinutes
     * @see .getMolad
     * @see .setJewishDate
     */
    /**
     * Sets the molad minutes. The expectation is that the traditional minute-less chalakim will be broken out to
     * minutes and [chalakim/parts][.setMoladChalakim] , so 793 (TaShTZaG) parts would have the minutes set to
     * 44 and chalakim to 1.
     *
     * @param moladMinutes
     * the molad minutes to set
     * @see .getMoladMinutes
     * @see .setMoladChalakim
     * @see .getMolad
     * @see .setJewishDate
     */
    /** the internal count of *molad* minutes. */
    var moladMinutes = 0
    /**
     * Returns the molad chalakim/parts. Only an object populated with [.getMolad],
     * [.setJewishDate] or or [.setMoladChalakim] will have these fields
     * populated. A regular JewishDate object will have this field set to 0.
     *
     * @return the molad chalakim/parts
     * @see .setMoladChalakim
     * @see .getMolad
     * @see .setJewishDate
     */
    /**
     * Sets the molad chalakim/parts. The expectation is that the traditional minute-less chalakim will be broken out to
     * [minutes][.setMoladMinutes] and chalakim, so 793 (TaShTZaG) parts would have the minutes set to 44 and
     * chalakim to 1.
     *
     * @param moladChalakim
     * the molad chalakim/parts to set
     * @see .getMoladChalakim
     * @see .setMoladMinutes
     * @see .getMolad
     * @see .setJewishDate
     */
    /** the internal count of *molad* *chalakim*. */
    var moladChalakim = 0

    /**
     * Returns the last day in a gregorian month
     *
     * @param month
     * the Gregorian month
     * @return the last day of the Gregorian month
     */
    fun getLastDayOfGregorianMonth(month: Int): Int {
        return getLastDayOfGregorianMonth(month, gregorianYear)
    }

    /**
     * Returns is the year passed in is a [Gregorian leap year](https://en.wikipedia.org/wiki/Leap_year#Gregorian_calendar).
     * @param year the Gregorian year
     * @return if the year in question is a leap year.
     */
    fun isGregorianLeapYear(year: Int): Boolean {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    }

    /**
     * The month, where 1 == January, 2 == February, etc... Note that this is different than the Java's Calendar class
     * where January ==0
     */
    private var gregorianMonth = 0

    /** The day of the Gregorian month  */
    private var gregorianDayOfMonth = 0

    /** The Gregorian year  */
    private var gregorianYear = 0
    /**
     * Returns the day of the week as a number between 1-7.
     *
     * @return the day of the week as a number between 1-7.
     */
    /** 1 == Sunday, 2 == Monday, etc...  */
    var dayOfWeek = 0
        private set
    /**
     * Returns the absolute date (days since January 1, 0001 on the Gregorian calendar).
     *
     * @return the number of days since January 1, 1
     */
    /** Returns the absolute date (days since January 1, 0001 on the Gregorian calendar).
     * @see .getAbsDate
     * @see .absDateToJewishDate
     */
    var absDate = 0
        private set

    /**
     * Computes the Gregorian date from the absolute date. ND+ER
     * @param absDate the absolute date
     */
    private fun absDateToDate(absDate: Int) {
        var year = absDate / 366 // Search forward year by year from approximate year
        while (absDate >= gregorianDateToAbsDate(year + 1, 1, 1)) {
            year++
        }
        var month = 1 // Search forward month by month from January
        while (absDate > gregorianDateToAbsDate(year, month, getLastDayOfGregorianMonth(month, year))) {
            month++
        }
        val dayOfMonth = absDate - gregorianDateToAbsDate(year, month, 1) + 1
        setInternalGregorianDate(year, month, dayOfMonth)
    }

    /**
     * Returns if the year the calendar is set to is a Jewish leap year. Years 3, 6, 8, 11, 14, 17 and 19 in the 19 year
     * cycle are leap years.
     *
     * @return true if it is a leap year
     * @see .isJewishLeapYear
     */
    val isJewishLeapYear: Boolean
        get() = isJewishLeapYear(getJewishYear())

    /**
     * Returns the number of chalakim (parts - 1080 to the hour) from the original hypothetical Molad Tohu to the Jewish
     * year and month that this Object is set to.
     *
     * @return the number of chalakim (parts - 1080 to the hour) from the original hypothetical Molad Tohu
     */
    val chalakimSinceMoladTohu: Long
        get() = getChalakimSinceMoladTohu(jewishYear, jewishMonth)

    /**
     * Returns the number of days for the current year that the calendar is set to.
     *
     * @return the number of days for the Object's current Jewish year.
     * @see .isCheshvanLong
     * @see .isKislevShort
     * @see .isJewishLeapYear
     */
    val daysInJewishYear: Int
        get() = getDaysInJewishYear(getJewishYear())

    /**
     * Returns if Cheshvan is long (30 days VS 29 days) for the current year that the calendar is set to. The method
     * name isLong is done since in a Kesidran (ordered) year Cheshvan is short.
     *
     * @return true if Cheshvan is long for the current year that the calendar is set to
     * @see .isCheshvanLong
     */
    val isCheshvanLong: Boolean
        get() = isCheshvanLong(getJewishYear())

    /**
     * Returns if the Kislev is short for the year that this class is set to. The method name isShort is done since in a
     * Kesidran (ordered) year Kislev is long.
     *
     * @return true if Kislev is short for the year that this class is set to
     */
    val isKislevShort: Boolean
        get() = isKislevShort(getJewishYear())

    /**
     * Returns the Cheshvan and Kislev kviah (whether a Jewish year is short, regular or long). It will return
     * [.SHELAIMIM] if both cheshvan and kislev are 30 days, [.KESIDRAN] if Cheshvan is 29 days and Kislev
     * is 30 days and [.CHASERIM] if both are 29 days.
     *
     * @return [.SHELAIMIM] if both cheshvan and kislev are 30 days, [.KESIDRAN] if Cheshvan is 29 days and
     * Kislev is 30 days and [.CHASERIM] if both are 29 days.
     * @see .isCheshvanLong
     * @see .isKislevShort
     */
    val cheshvanKislevKviah: Int
        get() = if (isCheshvanLong && !isKislevShort) {
            SHELAIMIM
        } else if (!isCheshvanLong && isKislevShort) {
            CHASERIM
        } else {
            KESIDRAN
        }

    /**
     * Returns the number of days of the Jewish month that the calendar is currently set to.
     *
     * @return the number of days for the Jewish month that the calendar is currently set to.
     */
    val daysInJewishMonth: Int
        get() = getDaysInJewishMonth(getJewishMonth(), getJewishYear())

    /**
     * Computes the Jewish date from the absolute date.
     */
    private fun absDateToJewishDate() {
        // Approximation from below
        jewishYear = (absDate - JEWISH_EPOCH) / 366
        // Search forward for year from the approximation
        while (absDate >= jewishDateToAbsDate(jewishYear + 1, TISHREI, 1)) {
            jewishYear++
        }
        // Search forward for month from either Tishri or Nisan.
        jewishMonth = if (absDate < jewishDateToAbsDate(jewishYear, NISSAN, 1)) {
            TISHREI // Start at Tishri
        } else {
            NISSAN // Start at Nisan
        }
        while (absDate > jewishDateToAbsDate(jewishYear, jewishMonth, daysInJewishMonth)) {
            jewishMonth++
        }
        // Calculate the day by subtraction
        jewishDay = absDate - jewishDateToAbsDate(jewishYear, jewishMonth, 1) + 1
    }

    /**
     * Returns the molad for a given year and month. Returns a JewishDate [Object] set to the date of the molad
     * with the [hours][.getMoladHours], [minutes][.getMoladMinutes] and [ chalakim][.getMoladChalakim] set. In the current implementation, it sets the molad time based on a midnight date rollover. This
     * means that Rosh Chodesh Adar II, 5771 with a molad of 7 chalakim past midnight on Shabbos 29 Adar I / March 5,
     * 2011 12:00 AM and 7 chalakim, will have the following values: hours: 0, minutes: 0, Chalakim: 7.
     *
     * @return a JewishDate [Object] set to the date of the molad with the [hours][.getMoladHours],
     * [minutes][.getMoladMinutes] and [chalakim][.getMoladChalakim] set.
     */
    val molad: JewishDate
        get() {
            val moladDate = JewishDate(chalakimSinceMoladTohu)
            if (moladDate.moladHours >= 6) {
                moladDate.forward(Calendar.DATE, 1)
            }
            moladDate.moladHours = (moladDate.moladHours + 18) % 24
            return moladDate
        }

    /**
     * Constructor that creates a JewishDate based on a molad passed in. The molad would be the number of chalakim/parts
     * starting at the beginning of Sunday prior to the molad Tohu BeHaRaD (Be = Monday, Ha= 5 hours and Rad =204
     * chalakim/parts) - prior to the start of the Jewish calendar. BeHaRaD is 23:11:20 on Sunday night(5 hours 204/1080
     * chalakim after sunset on Sunday evening).
     *
     * @param molad the number of chalakim since the beginning of Sunday prior to BaHaRaD
     */
    constructor(molad: Long) {
        absDateToDate(moladToAbsDate(molad))
        // long chalakimSince = getChalakimSinceMoladTohu(year, TISHREI);// tishrei
        val conjunctionDay = (molad / CHALAKIM_PER_DAY.toLong()).toInt()
        val conjunctionParts = (molad - conjunctionDay * CHALAKIM_PER_DAY.toLong()).toInt()
        setMoladTime(conjunctionParts)
    }

    /**
     * Sets the molad time (hours minutes and chalakim) based on the number of chalakim since the start of the day.
     *
     * @param chalakim
     * the number of chalakim since the start of the day.
     */
    private fun setMoladTime(chalakim: Int) {
        var adjustedChalakim = chalakim
        moladHours = adjustedChalakim / CHALAKIM_PER_HOUR
        adjustedChalakim = adjustedChalakim - moladHours * CHALAKIM_PER_HOUR
        moladMinutes = adjustedChalakim / CHALAKIM_PER_MINUTE
        moladChalakim = adjustedChalakim - moladMinutes * CHALAKIM_PER_MINUTE
    }

    /**
     * returns the number of days from Rosh Hashana of the date passed in, to the full date passed in.
     *
     * @return the number of days
     */
//	@JvmStatic
    val daysSinceStartOfJewishYear: Int
        get() = getDaysSinceStartOfJewishYear(getJewishYear(), getJewishMonth(), jewishDayOfMonth)

    /**
     * Creates a Jewish date based on a Jewish year, month and day of month.
     *
     * @param jewishYear
     * the Jewish year
     * @param jewishMonth
     * the Jewish month. The method expects a 1 for Nissan ... 12 for Adar and 13 for Adar II. Use the
     * constants [.NISSAN] ... [.ADAR] (or [.ADAR_II] for a leap year Adar II) to avoid any
     * confusion.
     * @param jewishDayOfMonth
     * the Jewish day of month. If 30 is passed in for a month with only 29 days (for example [.IYAR],
     * or [.KISLEV] in a year that [.isKislevShort]), the 29th (last valid date of the month)
     * will be set
     * @throws IllegalArgumentException
     * if the day of month is &lt; 1 or &gt; 30, or a year of &lt; 0 is passed in.
     */
    constructor(jewishYear: Int, jewishMonth: Int, jewishDayOfMonth: Int) {
        setJewishDate(jewishYear, jewishMonth, jewishDayOfMonth)
    }

    /**
     * Default constructor will set a default date to the current system date.
     */
    constructor() {
        resetDate()
    }

    /**
     * A constructor that initializes the date to the [Date][java.util.Date] paremeter.
     *
     * @param date
     * the `Date` to set the calendar to
     * @throws IllegalArgumentException
     * if the date would fall prior to the January 1, 1 AD
     */
    constructor(date: Date?) {
        setDate(date)
    }

    /**
     * A constructor that initializes the date to the [Calendar][java.util.Calendar] paremeter.
     *
     * @param calendar
     * the `Calendar` to set the calendar to
     * @throws IllegalArgumentException
     * if the [Calendar.ERA] is [GregorianCalendar.BC]
     */
    constructor(calendar: Calendar) {
        setDate(calendar)
    }

    /**
     * A constructor that initializes the date to the [LocalDate][java.time.LocalDate] paremeter.
     *
     * @param localDate
     * the `LocalDate` to set the calendar to
     * @throws IllegalArgumentException
     * if the [Calendar.ERA] is [GregorianCalendar.BC]
     */
    constructor(localDate: LocalDate) {
        setDate(localDate)
    }

    /**
     * Sets the date based on a [Calendar][java.util.Calendar] object. Modifies the Jewish date as well.
     *
     * @param calendar
     * the `Calendar` to set the calendar to
     * @throws IllegalArgumentException
     * if the [Calendar.ERA] is [GregorianCalendar.BC]
     */
    fun setDate(calendar: Calendar) {
        require(calendar[Calendar.ERA] != GregorianCalendar.BC) {
            ("Calendars with a BC era are not supported. The year "
                    + calendar[Calendar.YEAR] + " BC is invalid.")
        }
        gregorianMonth = calendar[Calendar.MONTH] + 1
        gregorianDayOfMonth = calendar[Calendar.DATE]
        gregorianYear = calendar[Calendar.YEAR]
        absDate = gregorianDateToAbsDate(gregorianYear, gregorianMonth, gregorianDayOfMonth) // init the date
        absDateToJewishDate()
        dayOfWeek = Math.abs(absDate % 7) + 1 // set day of week
    }

    /**
     * Sets the date based on a [Date][java.util.Date] object. Modifies the Jewish date as well.
     *
     * @param date
     * the `Date` to set the calendar to
     * @throws IllegalArgumentException
     * if the date would fall prior to the year 1 AD
     */
    fun setDate(date: Date?) {
        val cal = Calendar.getInstance()
        cal.time = date
        setDate(cal)
    }

    /**
     * Sets the date based on a [LocalDate][java.time.LocalDate] object. Modifies the Jewish date as well.
     *
     * @param localDate
     * the `LocalDate` to set the calendar to
     * @throws IllegalArgumentException
     * if the date would fall prior to the year 1 AD
     */
    fun setDate(localDate: LocalDate) {
        val cal = Calendar.getInstance()
        cal[localDate.year, localDate.monthValue - 1] = localDate.dayOfMonth
        setDate(cal)
    }

    /**
     * Sets the Gregorian Date, and updates the Jewish date accordingly. Like the Java Calendar A value of 0 is expected
     * for January.
     *
     * @param year
     * the Gregorian year
     * @param month
     * the Gregorian month. Like the Java Calendar, this class expects 0 for January
     * @param dayOfMonth
     * the Gregorian day of month. If this is &gt; the number of days in the month/year, the last valid date of
     * the month will be set
     * @throws IllegalArgumentException
     * if a year of &lt; 1, a month &lt; 0 or &gt; 11 or a day of month &lt; 1 is passed in
     */
    fun setGregorianDate(year: Int, month: Int, dayOfMonth: Int) {
        validateGregorianDate(year, month, dayOfMonth)
        setInternalGregorianDate(year, month + 1, dayOfMonth)
    }

    /**
     * Sets the hidden internal representation of the Gregorian date , and updates the Jewish date accordingly. While
     * public getters and setters have 0 based months matching the Java Calendar classes, This class internally
     * represents the Gregorian month starting at 1. When this is called it will not adjust the month to match the Java
     * Calendar classes.
     *
     * @param year the year
     * @param month the month
     * @param dayOfMonth the day of month
     */
    private fun setInternalGregorianDate(year: Int, month: Int, dayOfMonth: Int) {
        // make sure date is a valid date for the given month, if not, set to last day of month
        var dayOfMonth = dayOfMonth
        if (dayOfMonth > getLastDayOfGregorianMonth(month, year)) {
            dayOfMonth = getLastDayOfGregorianMonth(month, year)
        }
        // init month, date, year
        gregorianMonth = month
        gregorianDayOfMonth = dayOfMonth
        gregorianYear = year
        absDate = gregorianDateToAbsDate(gregorianYear, gregorianMonth, gregorianDayOfMonth) // init date
        absDateToJewishDate()
        dayOfWeek = Math.abs(absDate % 7) + 1 // set day of week
    }

    /**
     * Sets the Jewish Date and updates the Gregorian date accordingly.
     *
     * @param year
     * the Jewish year. The year can't be negative
     * @param month
     * the Jewish month starting with Nisan. A value of 1 is expected for Nissan ... 12 for Adar and 13 for
     * Adar II. Use the constants [.NISSAN] ... [.ADAR] (or [.ADAR_II] for a leap year Adar
     * II) to avoid any confusion.
     * @param dayOfMonth
     * the Jewish day of month. valid values are 1-30. If the day of month is set to 30 for a month that only
     * has 29 days, the day will be set as 29.
     * @throws IllegalArgumentException
     * if a A Jewish date earlier than 18 Teves, 3761 (1/1/1 Gregorian), a month &lt; 1 or &gt; 12 (or 13 on a
     * leap year) or the day of month is &lt; 1 or &gt; 30 is passed in
     */
    fun setJewishDate(year: Int, month: Int, dayOfMonth: Int) {
        setJewishDate(year, month, dayOfMonth, 0, 0, 0)
    }

    /**
     * Sets the Jewish Date and updates the Gregorian date accordingly.
     *
     * @param year
     * the Jewish year. The year can't be negative
     * @param month
     * the Jewish month starting with Nisan. A value of 1 is expected for Nissan ... 12 for Adar and 13 for
     * Adar II. Use the constants [.NISSAN] ... [.ADAR] (or [.ADAR_II] for a leap year Adar
     * II) to avoid any confusion.
     * @param dayOfMonth
     * the Jewish day of month. valid values are 1-30. If the day of month is set to 30 for a month that only
     * has 29 days, the day will be set as 29.
     *
     * @param hours
     * the hour of the day. Used for Molad calculations
     * @param minutes
     * the minutes. Used for Molad calculations
     * @param chalakim
     * the chalakim/parts. Used for Molad calculations. The chalakim should not exceed 17. Minutes should be
     * used for larger numbers.
     *
     * @throws IllegalArgumentException
     * if a A Jewish date earlier than 18 Teves, 3761 (1/1/1 Gregorian), a month &lt; 1 or &gt; 12 (or 13 on a
     * leap year), the day of month is &lt; 1 or &gt; 30, an hour &lt; 0 or &gt; 23, a minute &lt; 0 &gt; 59 or chalakim &lt; 0 &gt;
     * 17. For larger a larger number of chalakim such as 793 (TaShTzaG) break the chalakim into minutes (18
     * chalakim per minutes, so it would be 44 minutes and 1 chelek in the case of 793 (TaShTzaG).
     */
    fun setJewishDate(year: Int, month: Int, dayOfMonth: Int, hours: Int, minutes: Int, chalakim: Int) {
        var dayOfMonth = dayOfMonth
        validateJewishDate(year, month, dayOfMonth, hours, minutes, chalakim)

        // if 30 is passed for a month that only has 29 days (for example by rolling the month from a month that had 30
        // days to a month that only has 29) set the date to 29th
        if (dayOfMonth > getDaysInJewishMonth(month, year)) {
            dayOfMonth = getDaysInJewishMonth(month, year)
        }
        jewishMonth = month
        jewishDay = dayOfMonth
        jewishYear = year
        moladHours = hours
        moladMinutes = minutes
        moladChalakim = chalakim
        absDate = jewishDateToAbsDate(jewishYear, jewishMonth, jewishDay) // reset Gregorian date
        absDateToDate(absDate)
        dayOfWeek = Math.abs(absDate % 7) + 1 // reset day of week
    }

    /**
     * Returns this object's date as a [java.util.Calendar] object.
     *
     * @return The [java.util.Calendar]
     */
    val gregorianCalendar: Calendar
        get() {
            val calendar = Calendar.getInstance()
            calendar[getGregorianYear(), getGregorianMonth()] = getGregorianDayOfMonth()
            return calendar
        }

    /**
     * Returns this object's date as a [java.time.LocalDate] object.
     *
     * @return The [java.time.LocalDate]
     */
    val localDate: LocalDate
        get() = LocalDate.of(getGregorianYear(), getGregorianMonth() + 1, getGregorianDayOfMonth())

    /**
     * Resets this date to the current system date.
     */
    fun resetDate() {
        val calendar = Calendar.getInstance()
        setDate(calendar)
    }

    /**
     * Returns a string containing the Jewish date in the form, "day Month, year" e.g. "21 Shevat, 5729". For more
     * complex formatting, use the formatter classes.
     *
     * @return the Jewish date in the form "day Month, year" e.g. "21 Shevat, 5729"
     * @see HebrewDateFormatter.format
     */
    override fun toString(): String {
        return HebrewDateFormatter().format(this)
    }

    /**
     * Rolls the date, month or year forward by the amount passed in. It modifies both the Gregorian and Jewish dates accordingly.
     * If manipulation beyond the fields supported here is required, use the [Calendar] class [Calendar.add]
     * or [Calendar.roll] methods in the following manner.
     *
     * <pre>
     * `
     * Calendar cal = jewishDate.getTime(); // get a java.util.Calendar representation of the JewishDate
     * cal.add(Calendar.MONTH, 3); // add 3 Gregorian months
     * jewishDate.setDate(cal); // set the updated calendar back to this class
    ` *
    </pre> *
     *
     * @param field the calendar field to be forwarded. The must be [Calendar.DATE], [Calendar.MONTH] or [Calendar.YEAR]
     * @param amount the positive amount to move forward
     * @throws IllegalArgumentException if the field is anything besides [Calendar.DATE], [Calendar.MONTH] or [Calendar.YEAR]
     * or if the amount is less than 1
     *
     * @see .back
     * @see Calendar.add
     * @see Calendar.roll
     */
    fun forward(field: Int, amount: Int) {
        require(field != Calendar.DATE && field != Calendar.MONTH && field != Calendar.YEAR) { "Unsupported field was passed to Forward. Only Calendar.DATE, Calendar.MONTH or Calendar.YEAR are supported." }
        require(amount >= 1) { "JewishDate.forward() does not support amounts less than 1. See JewishDate.back()" }
        if (field == Calendar.DATE) {
            // Change Gregorian date
            for (i in 0 until amount) {
                if (gregorianDayOfMonth == getLastDayOfGregorianMonth(gregorianMonth, gregorianYear)) {
                    gregorianDayOfMonth = 1
                    // if last day of year
                    if (gregorianMonth == 12) {
                        gregorianYear++
                        gregorianMonth = 1
                    } else {
                        gregorianMonth++
                    }
                } else { // if not last day of month
                    gregorianDayOfMonth++
                }

                // Change the Jewish Date
                if (jewishDay == daysInJewishMonth) {
                    // if it last day of elul (i.e. last day of Jewish year)
                    if (jewishMonth == ELUL) {
                        jewishYear++
                        jewishMonth++
                        jewishDay = 1
                    } else if (jewishMonth == getLastMonthOfJewishYear(jewishYear)) {
                        // if it is the last day of Adar, or Adar II as case may be
                        jewishMonth = NISSAN
                        jewishDay = 1
                    } else {
                        jewishMonth++
                        jewishDay = 1
                    }
                } else { // if not last date of month
                    jewishDay++
                }
                if (dayOfWeek == 7) { // if last day of week, loop back to Sunday
                    dayOfWeek = 1
                } else {
                    dayOfWeek++
                }
                absDate++ // increment the absolute date
            }
        } else if (field == Calendar.MONTH) {
            forwardJewishMonth(amount)
        } else if (field == Calendar.YEAR) {
            setJewishYear(getJewishYear() + amount)
        }
    }

    /**
     * Forward the Jewish date by the number of months passed in.
     * FIXME: Deal with forwarding a date such as 30 Nisan by a month. 30 Iyar does not exist. This should be dealt with similar to
     * the way that the Java Calendar behaves (not that simple since there is a difference between add() or roll().
     *
     * @throws IllegalArgumentException if the amount is less than 1
     * @param amount the number of months to roll the month forward
     */
    private fun forwardJewishMonth(amount: Int) {
        require(amount >= 1) { "the amount of months to forward has to be greater than zero." }
        for (i in 0 until amount) {
            if (getJewishMonth() == ELUL) {
                setJewishMonth(TISHREI)
                setJewishYear(getJewishYear() + 1)
            } else if (!isJewishLeapYear && getJewishMonth() == ADAR || isJewishLeapYear && getJewishMonth() == ADAR_II) {
                setJewishMonth(NISSAN)
            } else {
                setJewishMonth(getJewishMonth() + 1)
            }
        }
    }

    /**
     * Rolls the date back by 1 day. It modifies both the Gregorian and Jewish dates accordingly. The API does not
     * currently offer the ability to forward more than one day at a time, or to forward by month or year. If such
     * manipulation is required use the [Calendar] class [Calendar.add] or
     * [Calendar.roll] methods in the following manner.
     *
     * <pre>
     * `
     * Calendar cal = jewishDate.getTime(); // get a java.util.Calendar representation of the JewishDate
     * cal.add(Calendar.MONTH, -3); // subtract 3 Gregorian months
     * jewishDate.setDate(cal); // set the updated calendar back to this class
    ` *
    </pre> *
     *
     * @see .back
     * @see Calendar.add
     * @see Calendar.roll
     */
    fun back() {
        // Change Gregorian date
        if (gregorianDayOfMonth == 1) { // if first day of month
            if (gregorianMonth == 1) { // if first day of year
                gregorianMonth = 12
                gregorianYear--
            } else {
                gregorianMonth--
            }
            // change to last day of previous month
            gregorianDayOfMonth = getLastDayOfGregorianMonth(gregorianMonth, gregorianYear)
        } else {
            gregorianDayOfMonth--
        }
        // change Jewish date
        if (jewishDay == 1) { // if first day of the Jewish month
            if (jewishMonth == NISSAN) {
                jewishMonth = getLastMonthOfJewishYear(jewishYear)
            } else if (jewishMonth == TISHREI) { // if Rosh Hashana
                jewishYear--
                jewishMonth--
            } else {
                jewishMonth--
            }
            jewishDay = daysInJewishMonth
        } else {
            jewishDay--
        }
        if (dayOfWeek == 1) { // if first day of week, loop back to Saturday
            dayOfWeek = 7
        } else {
            dayOfWeek--
        }
        absDate-- // change the absolute date
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * @see Object.equals
     */
    override fun equals(`object`: Any?): Boolean {
        if (this === `object`) {
            return true
        }
        if (`object` !is JewishDate) {
            return false
        }
        return absDate == `object`.absDate
    }

    /**
     * Compares two dates as per the compareTo() method in the Comparable interface. Returns a value less than 0 if this
     * date is "less than" (before) the date, greater than 0 if this date is "greater than" (after) the date, or 0 if
     * they are equal.
     */
    override fun compareTo(jewishDate: JewishDate): Int {
        return Integer.compare(absDate, jewishDate.absDate)
    }

    /**
     * Returns the Gregorian month (between 0-11).
     *
     * @return the Gregorian month (between 0-11). Like the java.util.Calendar, months are 0 based.
     */
    fun getGregorianMonth(): Int {
        return gregorianMonth - 1
    }

    /**
     * Returns the Gregorian day of the month.
     *
     * @return the Gregorian day of the mont
     */
    fun getGregorianDayOfMonth(): Int {
        return gregorianDayOfMonth
    }

    /**
     * Returns the Gregotian year.
     *
     * @return the Gregorian year
     */
    fun getGregorianYear(): Int {
        return gregorianYear
    }

    /**
     * Returns the Jewish month 1-12 (or 13 years in a leap year). The month count starts with 1 for Nisan and goes to
     * 13 for Adar II
     *
     * @return the Jewish month from 1 to 12 (or 13 years in a leap year). The month count starts with 1 for Nisan and
     * goes to 13 for Adar II
     */
    fun getJewishMonth(): Int {
        return jewishMonth
    }
    /**
     * Returns the Jewish day of month.
     *
     * @return the Jewish day of the month
     */
    /**
     * sets the Jewish day of month.
     *
     * @param dayOfMonth
     * the Jewish day of month
     * @throws IllegalArgumentException
     * if the day of month is &lt; 1 or &gt; 30 is passed in
     */
    var jewishDayOfMonth: Int
        get() = jewishDay
        set(dayOfMonth) {
            setJewishDate(jewishYear, jewishMonth, dayOfMonth)
        }

    /**
     * Returns the Jewish year.
     *
     * @return the Jewish year
     */
    fun getJewishYear(): Int {
        return jewishYear
    }

    /**
     * Sets the Gregorian month.
     *
     * @param month
     * the Gregorian month
     *
     * @throws IllegalArgumentException
     * if a month &lt; 0 or &gt; 11 is passed in
     */
    fun setGregorianMonth(month: Int) {
        validateGregorianMonth(month)
        setInternalGregorianDate(gregorianYear, month + 1, gregorianDayOfMonth)
    }

    /**
     * sets the Gregorian year.
     *
     * @param year
     * the Gregorian year.
     * @throws IllegalArgumentException
     * if a year of &lt; 1 is passed in
     */
    fun setGregorianYear(year: Int) {
        validateGregorianYear(year)
        setInternalGregorianDate(year, gregorianMonth, gregorianDayOfMonth)
    }

    /**
     * sets the Gregorian Day of month.
     *
     * @param dayOfMonth
     * the Gregorian Day of month.
     * @throws IllegalArgumentException
     * if the day of month of &lt; 1 is passed in
     */
    fun setGregorianDayOfMonth(dayOfMonth: Int) {
        validateGregorianDayOfMonth(dayOfMonth)
        setInternalGregorianDate(gregorianYear, gregorianMonth, dayOfMonth)
    }

    /**
     * sets the Jewish month.
     *
     * @param month
     * the Jewish month from 1 to 12 (or 13 years in a leap year). The month count starts with 1 for Nisan
     * and goes to 13 for Adar II
     * @throws IllegalArgumentException
     * if a month &lt; 1 or &gt; 12 (or 13 on a leap year) is passed in
     */
    fun setJewishMonth(month: Int) {
        setJewishDate(jewishYear, month, jewishDay)
    }

    /**
     * sets the Jewish year.
     *
     * @param year
     * the Jewish year
     * @throws IllegalArgumentException
     * if a year of &lt; 3761 is passed in. The same will happen if the year is 3761 and the month and day
     * previously set are &lt; 18 Teves (preior to Jan 1, 1 AD)
     */
    fun setJewishYear(year: Int) {
        setJewishDate(year, jewishMonth, jewishDay)
    }

    /**
     * A method that creates a [deep copy](http://en.wikipedia.org/wiki/Object_copy#Deep_copy) of the object.
     *
     * @see Object.clone
     */
    public override fun clone(): Any {
        var clone: JewishDate? = null
        try {
            clone = super.clone() as JewishDate
        } catch (cnse: CloneNotSupportedException) {
            // Required by the compiler. Should never be reached since we implement clone()
        }
        clone!!.setInternalGregorianDate(gregorianYear, gregorianMonth, gregorianDayOfMonth)
        return clone
    }

    /**
     * Overrides [Object.hashCode].
     * @see Object.hashCode
     */
    override fun hashCode(): Int {
        var result = 17
        result = 37 * result + javaClass.hashCode() // needed or this and subclasses will return identical hash
        result += 37 * result + absDate
        return result
    }

    companion object {
        /**
         * Value of the month field indicating Nissan, the first numeric month of the year in the Jewish calendar. With the
         * year starting at [.TISHREI], it would actually be the 7th (or 8th in a [leap][.isJewishLeapYear]) month of the year.
         */
        const val NISSAN = 1

        /**
         * Value of the month field indicating Iyar, the second numeric month of the year in the Jewish calendar. With the
         * year starting at [.TISHREI], it would actually be the 8th (or 9th in a [leap][.isJewishLeapYear]) month of the year.
         */
        const val IYAR = 2

        /**
         * Value of the month field indicating Sivan, the third numeric month of the year in the Jewish calendar. With the
         * year starting at [.TISHREI], it would actually be the 9th (or 10th in a [leap][.isJewishLeapYear]) month of the year.
         */
        const val SIVAN = 3

        /**
         * Value of the month field indicating Tammuz, the fourth numeric month of the year in the Jewish calendar. With the
         * year starting at [.TISHREI], it would actually be the 10th (or 11th in a [leap][.isJewishLeapYear]) month of the year.
         */
        const val TAMMUZ = 4

        /**
         * Value of the month field indicating Av, the fifth numeric month of the year in the Jewish calendar. With the year
         * starting at [.TISHREI], it would actually be the 11th (or 12th in a [leap year][.isJewishLeapYear])
         * month of the year.
         */
        const val AV = 5

        /**
         * Value of the month field indicating Elul, the sixth numeric month of the year in the Jewish calendar. With the
         * year starting at [.TISHREI], it would actually be the 12th (or 13th in a [leap][.isJewishLeapYear]) month of the year.
         */
        const val ELUL = 6

        /**
         * Value of the month field indicating Tishrei, the seventh numeric month of the year in the Jewish calendar. With
         * the year starting at this month, it would actually be the 1st month of the year.
         */
        const val TISHREI = 7

        /**
         * Value of the month field indicating Cheshvan/marcheshvan, the eighth numeric month of the year in the Jewish
         * calendar. With the year starting at [.TISHREI], it would actually be the 2nd month of the year.
         */
        const val CHESHVAN = 8

        /**
         * Value of the month field indicating Kislev, the ninth numeric month of the year in the Jewish calendar. With the
         * year starting at [.TISHREI], it would actually be the 3rd month of the year.
         */
        const val KISLEV = 9

        /**
         * Value of the month field indicating Teves, the tenth numeric month of the year in the Jewish calendar. With the
         * year starting at [.TISHREI], it would actually be the 4th month of the year.
         */
        const val TEVES = 10

        /**
         * Value of the month field indicating Shevat, the eleventh numeric month of the year in the Jewish calendar. With
         * the year starting at [.TISHREI], it would actually be the 5th month of the year.
         */
        const val SHEVAT = 11

        /**
         * Value of the month field indicating Adar (or Adar I in a [leap year][.isJewishLeapYear]), the twelfth
         * numeric month of the year in the Jewish calendar. With the year starting at [.TISHREI], it would actually
         * be the 6th month of the year.
         */
        const val ADAR = 12

        /**
         * Value of the month field indicating Adar II, the leap (intercalary or embolismic) thirteenth (Undecimber) numeric
         * month of the year added in Jewish [leap year][.isJewishLeapYear]). The leap years are years 3, 6, 8, 11,
         * 14, 17 and 19 of a 19 year cycle. With the year starting at [.TISHREI], it would actually be the 7th month
         * of the year.
         */
        const val ADAR_II = 13

        /**
         * the Jewish epoch using the RD (Rata Die/Fixed Date or Reingold Dershowitz) day used in Calendrical Calculations.
         * Day 1 is January 1, 0001 Gregorian
         */
        private const val JEWISH_EPOCH = -1373429

        /** The number  of *chalakim* (18) in a minute. */
        private const val CHALAKIM_PER_MINUTE = 18

        /** The number  of *chalakim* (1080) in an hour. */
        private const val CHALAKIM_PER_HOUR = 1080

        /** The number of *chalakim* (25,920) in a 24 hour day . */
        private const val CHALAKIM_PER_DAY = 25920 // 24 * 1080

        /** The number  of *chalakim* in an average Jewish month. A month has 29 days, 12 hours and 793
         * *chalakim* (44 minutes and 3.3 seconds) for a total of 765,433 *chalakim* */
        private const val CHALAKIM_PER_MONTH: Long = 765433 // (29 * 24 + 12) * 1080 + 793

        /**
         * Days from the beginning of Sunday till molad BaHaRaD. Calculated as 1 day, 5 hours and 204 chalakim = (24 + 5) *
         * 1080 + 204 = 31524
         */
        private const val CHALAKIM_MOLAD_TOHU = 31524

        /**
         * A short year where both [.CHESHVAN] and [.KISLEV] are 29 days.
         *
         * @see .getCheshvanKislevKviah
         * @see HebrewDateFormatter.getFormattedKviah
         */
        const val CHASERIM = 0

        /**
         * An ordered year where [.CHESHVAN] is 29 days and [.KISLEV] is 30 days.
         *
         * @see .getCheshvanKislevKviah
         * @see HebrewDateFormatter.getFormattedKviah
         */
        const val KESIDRAN = 1

        /**
         * A long year where both [.CHESHVAN] and [.KISLEV] are 30 days.
         *
         * @see .getCheshvanKislevKviah
         * @see HebrewDateFormatter.getFormattedKviah
         */
        const val SHELAIMIM = 2

        /**
         * Returns the number of days in a given month in a given month and year.
         *
         * @param month
         * the month. As with other cases in this class, this is 1-based, not zero-based.
         * @param year
         * the year (only impacts February)
         * @return the number of days in the month in the given year
         */
        private fun getLastDayOfGregorianMonth(month: Int, year: Int): Int {
            return when (month) {
                2 -> if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                    29
                } else {
                    28
                }

                4, 6, 9, 11 -> 30
                else -> 31
            }
        }

        /**
         * Computes the absolute date from a Gregorian date. ND+ER
         *
         * @param year
         * the Gregorian year
         * @param month
         * the Gregorian month. Unlike the Java Calendar where January has the value of 0,This expects a 1 for
         * January
         * @param dayOfMonth
         * the day of the month (1st, 2nd, etc...)
         * @return the absolute Gregorian day
         */
        private fun gregorianDateToAbsDate(year: Int, month: Int, dayOfMonth: Int): Int {
            var absDate = dayOfMonth
            for (m in month - 1 downTo 1) {
                absDate += getLastDayOfGregorianMonth(m, year) // days in prior months of the year
            }
            return (absDate + 365 * (year - 1) + (year - 1) / 4 // Julian leap days before this year
                    - (year - 1) / 100 // minus prior century years
                    + (year - 1) / 400) // plus prior years divisible by 400
        }

        /**
         * Returns if the year is a Jewish leap year. Years 3, 6, 8, 11, 14, 17 and 19 in the 19 year cycle are leap years.
         *
         * @param year
         * the Jewish year.
         * @return true if it is a leap year
         * @see .isJewishLeapYear
         */
        private fun isJewishLeapYear(year: Int): Boolean {
            return (7 * year + 1) % 19 < 7
        }

        /**
         * Returns the last month of a given Jewish year. This will be 12 on a non [leap year][.isJewishLeapYear]
         * or 13 on a leap year.
         *
         * @param year
         * the Jewish year.
         * @return 12 on a non leap year or 13 on a leap year
         * @see .isJewishLeapYear
         */
        private fun getLastMonthOfJewishYear(year: Int): Int {
            return if (isJewishLeapYear(year)) ADAR_II else ADAR
        }

        /**
         * Returns the number of days elapsed from the Sunday prior to the start of the Jewish calendar to the mean
         * conjunction of Tishri of the Jewish year.
         *
         * @param year
         * the Jewish year
         * @return the number of days elapsed from prior to the molad Tohu BaHaRaD (Be = Monday, Ha= 5 hours and Rad =204
         * chalakim/parts) prior to the start of the Jewish calendar, to the mean conjunction of Tishri of the
         * Jewish year. BeHaRaD is 23:11:20 on Sunday night(5 hours 204/1080 chalakim after sunset on Sunday
         * evening).
         */
        @JvmStatic
        fun getJewishCalendarElapsedDays(year: Int): Int {
            val chalakimSince = getChalakimSinceMoladTohu(year, TISHREI)
            val moladDay = (chalakimSince / CHALAKIM_PER_DAY.toLong()).toInt()
            val moladParts = (chalakimSince - moladDay * CHALAKIM_PER_DAY.toLong()).toInt()
            // delay Rosh Hashana for the 4 dechiyos
            return addDechiyos(year, moladDay, moladParts)
        }
        // private static int getJewishCalendarElapsedDaysOLD(int year) {
        // // Jewish lunar month = 29 days, 12 hours and 793 chalakim
        // // Molad Tohu = BeHaRaD - Monday, 5 hours (11 PM) and 204 chalakim
        // final int chalakimTashTZag = 793; // chalakim in a lunar month
        // final int chalakimTohuRaD = 204; // chalakim from original molad Tohu BeHaRaD
        // final int hoursTohuHa = 5; // hours from original molad Tohu BeHaRaD
        // final int dayTohu = 1; // Monday (0 based)
        //
        // int monthsElapsed = (235 * ((year - 1) / 19)) // Months in complete 19 year lunar (Metonic) cycles so far
        // + (12 * ((year - 1) % 19)) // Regular months in this cycle
        // + ((7 * ((year - 1) % 19) + 1) / 19); // Leap months this cycle
        // // start with Molad Tohu BeHaRaD
        // // start with RaD of BeHaRaD and add TaShTzaG (793) chalakim plus elapsed chalakim
        // int partsElapsed = chalakimTohuRaD + chalakimTashTZag * (monthsElapsed % 1080);
        // // start with Ha hours of BeHaRaD, add 12 hour remainder of lunar month add hours elapsed
        // int hoursElapsed = hoursTohuHa + 12 * monthsElapsed + 793 * (monthsElapsed / 1080) + partsElapsed / 1080;
        // // start with Monday of BeHaRaD = 1 (0 based), add 29 days of the lunar months elapsed
        // int conjunctionDay = dayTohu + 29 * monthsElapsed + hoursElapsed / 24;
        // int conjunctionParts = 1080 * (hoursElapsed % 24) + partsElapsed % 1080;
        // return addDechiyos(year, conjunctionDay, conjunctionParts);
        // }
        /**
         * Adds the 4 dechiyos for molad Tishrei. These are:
         *
         *  1. Lo ADU Rosh - Rosh Hashana can't fall on a Sunday, Wednesday or Friday. If the molad fell on one of these
         * days, Rosh Hashana is delayed to the following day.
         *  1. Molad Zaken - If the molad of Tishrei falls after 12 noon, Rosh Hashana is delayed to the following day. If
         * the following day is ADU, it will be delayed an additional day.
         *  1. GaTRaD - If on a non leap year the molad of Tishrei falls on a Tuesday (Ga) on or after 9 hours (T) and 204
         * chalakim (TRaD) it is delayed till Thursday (one day delay, plus one day for Lo ADU Rosh)
         *  1. BeTuTaKFoT - if the year following a leap year falls on a Monday (Be) on or after 15 hours (Tu) and 589
         * chalakim (TaKFoT) it is delayed till Tuesday
         *
         *
         * @param year the year
         * @param moladDay the molad day
         * @param moladParts the molad parts
         * @return the number of elapsed days in the JewishCalendar adjusted for the 4 dechiyos.
         */
        private fun addDechiyos(year: Int, moladDay: Int, moladParts: Int): Int {
            var roshHashanaDay = moladDay // if no dechiyos
            // delay Rosh Hashana for the dechiyos of the Molad - new moon 1 - Molad Zaken, 2- GaTRaD 3- BeTuTaKFoT
            if (moladParts >= 19440 // Dechiya of Molad Zaken - molad is >= midday (18 hours * 1080 chalakim)
                || (moladDay % 7 == 2 && moladParts >= 9924 // TRaD = 9 hours, 204 parts or later (9 * 1080 + 204)
                        && !isJewishLeapYear(year)) // of a non-leap year - end Dechiya of GaTRaD
                || (moladDay % 7 == 1 && moladParts >= 16789 // TRaD = 15 hours, 589 parts or later (15 * 1080 + 589)
                        && isJewishLeapYear(year - 1))
            ) { // in a year following a leap year - end Dechiya of BeTuTaKFoT
                roshHashanaDay += 1 // Then postpone Rosh HaShanah one day
            }
            // start 4th Dechiya - Lo ADU Rosh - Rosh Hashana can't occur on A- sunday, D- Wednesday, U - Friday
            if (roshHashanaDay % 7 == 0 || roshHashanaDay % 7 == 3 || roshHashanaDay % 7 == 5) { // or Friday - end 4th Dechiya - Lo ADU Rosh
                roshHashanaDay = roshHashanaDay + 1 // Then postpone it one (more) day
            }
            return roshHashanaDay
        }

        /**
         * Returns the number of chalakim (parts - 1080 to the hour) from the original hypothetical Molad Tohu to the year
         * and month passed in.
         *
         * @param year
         * the Jewish year
         * @param month
         * the Jewish month the Jewish month, with the month numbers starting from Nisan. Use the JewishDate
         * constants such as [JewishDate.TISHREI].
         * @return the number of chalakim (parts - 1080 to the hour) from the original hypothetical Molad Tohu
         */
        private fun getChalakimSinceMoladTohu(year: Int, month: Int): Long {
            // Jewish lunar month = 29 days, 12 hours and 793 chalakim
            // chalakim since Molad Tohu BeHaRaD - 1 day, 5 hours and 204 chalakim
            val monthOfYear = getJewishMonthOfYear(year, month)
            val monthsElapsed =
                (235 * ((year - 1) / 19) + 12 * ((year - 1) % 19) + (7 * ((year - 1) % 19) + 1) / 19 // Leap months this cycle
                        + (monthOfYear - 1)) // add elapsed months till the start of the molad of the month
            // return chalakim prior to BeHaRaD + number of chalakim since
            return CHALAKIM_MOLAD_TOHU + CHALAKIM_PER_MONTH * monthsElapsed
        }

        /**
         * Converts the [JewishDate.NISSAN] based constants used by this class to numeric month starting from
         * [JewishDate.TISHREI]. This is required for Molad claculations.
         *
         * @param year
         * The Jewish year
         * @param month
         * The Jewish Month
         * @return the Jewish month of the year starting with Tishrei
         */
        private fun getJewishMonthOfYear(year: Int, month: Int): Int {
            val isLeapYear = isJewishLeapYear(year)
            return (month + if (isLeapYear) 6 else 5) % (if (isLeapYear) 13 else 12) + 1
        }

        /**
         * Validates the components of a Jewish date for validity. It will throw an [IllegalArgumentException] if the
         * Jewish date is earlier than 18 Teves, 3761 (1/1/1 Gregorian), a month &lt; 1 or &gt; 12 (or 13 on a
         * [leap year][.isJewishLeapYear]), the day of month is &lt; 1 or &gt; 30, an hour &lt; 0 or &gt; 23, a minute &lt; 0
         * or &gt; 59 or chalakim &lt; 0 or &gt; 17. For larger a larger number of chalakim such as 793 (TaShTzaG) break the chalakim into
         * minutes (18 chalakim per minutes, so it would be 44 minutes and 1 chelek in the case of 793/TaShTzaG).
         *
         * @param year
         * the Jewish year to validate. It will reject any year &lt;= 3761 (lower than the year 1 Gregorian).
         * @param month
         * the Jewish month to validate. It will reject a month &lt; 1 or &gt; 12 (or 13 on a leap year) .
         * @param dayOfMonth
         * the day of the Jewish month to validate. It will reject any value &lt; 1 or &gt; 30 TODO: check calling
         * methods to see if there is any reason that the class can validate that 30 is invalid for some months.
         * @param hours
         * the hours (for molad calculations). It will reject an hour &lt; 0 or &gt; 23
         * @param minutes
         * the minutes (for molad calculations). It will reject a minute &lt; 0 or &gt; 59
         * @param chalakim
         * the chalakim/parts (for molad calculations). It will reject a chalakim &lt; 0 or &gt; 17. For larger numbers
         * such as 793 (TaShTzaG) break the chalakim into minutes (18 chalakim per minutes, so it would be 44
         * minutes and 1 chelek in the case of 793/TaShTzaG)
         *
         * @throws IllegalArgumentException
         * if a A Jewish date earlier than 18 Teves, 3761 (1/1/1 Gregorian), a month &lt; 1 or &gt; 12 (or 13 on a
         * leap year), the day of month is &lt; 1 or &gt; 30, an hour &lt; 0 or &gt; 23, a minute &lt; 0 or &gt; 59 or
         * chalakim &lt; 0 or &gt; 17. For larger a larger number of chalakim such as 793 (TaShTzaG) break the chalakim
         * into minutes (18 chalakim per minutes, so it would be 44 minutes and 1 chelek in the case of 793 (TaShTzaG).
         */
        private fun validateJewishDate(year: Int, month: Int, dayOfMonth: Int, hours: Int, minutes: Int, chalakim: Int) {
            require(!(month < NISSAN || month > getLastMonthOfJewishYear(year))) {
                ("The Jewish month has to be between 1 and 12 (or 13 on a leap year). "
                        + month + " is invalid for the year " + year + ".")
            }
            require(!(dayOfMonth < 1 || dayOfMonth > 30)) {
                ("The Jewish day of month can't be < 1 or > 30.  " + dayOfMonth
                        + " is invalid.")
            }
            // reject dates prior to 18 Teves, 3761 (1/1/1 AD). This restriction can be relaxed if the date coding is
            // changed/corrected
            require((year < 3761) || (year == 3761 && (month >= TISHREI && month < TEVES)) || (year == 3761 && month == TEVES && dayOfMonth < 18)) {
                ("A Jewish date earlier than 18 Teves, 3761 (1/1/1 Gregorian) can't be set. " + year + ", " + month
                        + ", " + dayOfMonth + " is invalid.")
            }
            require(!(hours < 0 || hours > 23)) { "Hours < 0 or > 23 can't be set. $hours is invalid." }
            require(!(minutes < 0 || minutes > 59)) { "Minutes < 0 or > 59 can't be set. $minutes is invalid." }
            require(!(chalakim < 0 || chalakim > 17)) {
                ("Chalakim/parts < 0 or > 17 can't be set. "
                        + chalakim
                        + " is invalid. For larger numbers such as 793 (TaShTzaG) break the chalakim into minutes (18 chalakim per minutes, so it would be 44 minutes and 1 chelek in the case of 793 (TaShTzaG)")
            }
        }

        /**
         * Validates the components of a Gregorian date for validity. It will throw an [IllegalArgumentException] if a
         * year of &lt; 1, a month &lt; 0 or &gt; 11 or a day of month &lt; 1 is passed in.
         *
         * @param year
         * the Gregorian year to validate. It will reject any year &lt; 1.
         * @param month
         * the Gregorian month number to validate. It will enforce that the month is between 0 - 11 like a
         * [GregorianCalendar], where [Calendar.JANUARY] has a value of 0.
         * @param dayOfMonth
         * the day of the Gregorian month to validate. It will reject any value &lt; 1, but will allow values &gt; 31
         * since calling methods will simply set it to the maximum for that month. TODO: check calling methods to
         * see if there is any reason that the class needs days &gt; the maximum.
         * @throws IllegalArgumentException
         * if a year of &lt; 1, a month &lt; 0 or &gt; 11 or a day of month &lt; 1 is passed in
         * @see .validateGregorianYear
         * @see .validateGregorianMonth
         * @see .validateGregorianDayOfMonth
         */
        private fun validateGregorianDate(year: Int, month: Int, dayOfMonth: Int) {
            validateGregorianMonth(month)
            validateGregorianDayOfMonth(dayOfMonth)
            validateGregorianYear(year)
        }

        /**
         * Validates a Gregorian month for validity.
         *
         * @param month
         * the Gregorian month number to validate. It will enforce that the month is between 0 - 11 like a
         * [GregorianCalendar], where [Calendar.JANUARY] has a value of 0.
         */
        private fun validateGregorianMonth(month: Int) {
            require(!(month > 11 || month < 0)) {
                ("The Gregorian month has to be between 0 - 11. " + month
                        + " is invalid.")
            }
        }

        /**
         * Validates a Gregorian day of month for validity.
         *
         * @param dayOfMonth
         * the day of the Gregorian month to validate. It will reject any value &lt; 1, but will allow values &gt; 31
         * since calling methods will simply set it to the maximum for that month. TODO: check calling methods to
         * see if there is any reason that the class needs days &gt; the maximum.
         */
        private fun validateGregorianDayOfMonth(dayOfMonth: Int) {
            require(dayOfMonth > 0) { "The day of month can't be less than 1. $dayOfMonth is invalid." }
        }

        /**
         * Validates a Gregorian year for validity.
         *
         * @param year
         * the Gregorian year to validate. It will reject any year &lt; 1.
         */
        private fun validateGregorianYear(year: Int) {
            require(year >= 1) { "Years < 1 can't be claculated. $year is invalid." }
        }

        /**
         * Returns the number of days for a given Jewish year. ND+ER
         *
         * @param year
         * the Jewish year
         * @return the number of days for a given Jewish year.
         * @see .isCheshvanLong
         * @see .isKislevShort
         */
        fun getDaysInJewishYear(year: Int): Int {
            return getJewishCalendarElapsedDays(year + 1) - getJewishCalendarElapsedDays(year)
        }

        /**
         * Returns if Cheshvan is long in a given Jewish year. The method name isLong is done since in a Kesidran (ordered)
         * year Cheshvan is short. ND+ER
         *
         * @param year
         * the year
         * @return true if Cheshvan is long in Jewish year.
         * @see .isCheshvanLong
         * @see .getCheshvanKislevKviah
         */
        private fun isCheshvanLong(year: Int): Boolean {
            return getDaysInJewishYear(year) % 10 == 5
        }

        /**
         * Returns if Kislev is short (29 days VS 30 days) in a given Jewish year. The method name isShort is done since in
         * a Kesidran (ordered) year Kislev is long. ND+ER
         *
         * @param year
         * the Jewish year
         * @return true if Kislev is short for the given Jewish year.
         * @see .isKislevShort
         * @see .getCheshvanKislevKviah
         */
        private fun isKislevShort(year: Int): Boolean {
            return getDaysInJewishYear(year) % 10 == 3
        }

        /**
         * Returns the number of days of a Jewish month for a given month and year.
         *
         * @param month
         * the Jewish month
         * @param year
         * the Jewish Year
         * @return the number of days for a given Jewish month
         */
        private fun getDaysInJewishMonth(month: Int, year: Int): Int {
            return if (month == IYAR || month == TAMMUZ || month == ELUL || month == CHESHVAN && !isCheshvanLong(
                    year
                ) || month == KISLEV && isKislevShort(year) || month == TEVES || month == ADAR && !isJewishLeapYear(year) || month == ADAR_II
            ) {
                29
            } else {
                30
            }
        }

        /**
         * Returns the absolute date of Jewish date. ND+ER
         *
         * @param year
         * the Jewish year. The year can't be negative
         * @param month
         * the Jewish month starting with Nisan. Nisan expects a value of 1 etc till Adar with a value of 12. For
         * a leap year, 13 will be the expected value for Adar II. Use the constants [JewishDate.NISSAN]
         * etc.
         * @param dayOfMonth
         * the Jewish day of month. valid values are 1-30. If the day of month is set to 30 for a month that only
         * has 29 days, the day will be set as 29.
         * @return the absolute date of the Jewish date.
         */
        private fun jewishDateToAbsDate(year: Int, month: Int, dayOfMonth: Int): Int {
            val elapsed = getDaysSinceStartOfJewishYear(year, month, dayOfMonth)
            // add elapsed days this year + Days in prior years + Days elapsed before absolute year 1
            return elapsed + getJewishCalendarElapsedDays(year) + JEWISH_EPOCH
        }

        /**
         * Returns the number of days from the Jewish epoch from the number of chalakim from the epoch passed in.
         *
         * @param chalakim
         * the number of chalakim since the beginning of Sunday prior to BaHaRaD
         * @return the number of days from the Jewish epoch
         */
        private fun moladToAbsDate(chalakim: Long): Int {
            return (chalakim / CHALAKIM_PER_DAY).toInt() + JEWISH_EPOCH
        }

        /**
         * returns the number of days from Rosh Hashana of the date passed in, to the full date passed in.
         *
         * @param year
         * the Jewish year
         * @param month
         * the Jewish month
         * @param dayOfMonth
         * the day in the Jewish month
         * @return the number of days
         */
        private fun getDaysSinceStartOfJewishYear(year: Int, month: Int, dayOfMonth: Int): Int {
            var elapsedDays = dayOfMonth
            // Before Tishrei (from Nissan to Tishrei), add days in prior months
            if (month < TISHREI) {
                // this year before and after Nisan.
                for (m in TISHREI..getLastMonthOfJewishYear(year)) {
                    elapsedDays += getDaysInJewishMonth(m, year)
                }
                for (m in NISSAN until month) {
                    elapsedDays += getDaysInJewishMonth(m, year)
                }
            } else { // Add days in prior months this year
                for (m in TISHREI until month) {
                    elapsedDays += getDaysInJewishMonth(m, year)
                }
            }
            return elapsedDays
        }
    }
}
