/*
 * Copyright (c) 2011. Jay R. Gindin
 */
package com.kosherjava.zmanim.hebrewcalendar

import org.junit.Assert
import org.junit.Test
import java.util.Calendar

/**
 * Checks that we can roll forward & backward the gregorian dates...
 */
class UT_GregorianDateNavigation {

    @Test
    fun gregorianForwardMonthToMonth() {
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = 2011
        cal[Calendar.MONTH] = Calendar.JANUARY
        cal[Calendar.DATE] = 31
        val hebrewDate = JewishDate(cal)
        Assert.assertEquals(5771, hebrewDate.getJewishYear().toLong())
        Assert.assertEquals(11, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(26, hebrewDate.jewishDayOfMonth.toLong())
        hebrewDate.forward(Calendar.DATE, 1)
        Assert.assertEquals(1, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(1, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(11, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(27, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.MONTH] = Calendar.FEBRUARY
        cal[Calendar.DATE] = 28
        hebrewDate.setDate(cal)
        Assert.assertEquals(1, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(28, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(12, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(24, hebrewDate.jewishDayOfMonth.toLong())
        hebrewDate.forward(Calendar.DATE, 1)
        Assert.assertEquals(2, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(1, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(12, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(25, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.MONTH] = Calendar.MARCH
        cal[Calendar.DATE] = 31
        hebrewDate.setDate(cal)
        hebrewDate.forward(Calendar.DATE, 1)
        Assert.assertEquals(3, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(1, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(13, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(26, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.MONTH] = Calendar.APRIL
        cal[Calendar.DATE] = 30
        hebrewDate.setDate(cal)
        hebrewDate.forward(Calendar.DATE, 1)
        Assert.assertEquals(4, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(1, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(1, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(27, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.MONTH] = Calendar.MAY
        cal[Calendar.DATE] = 31
        hebrewDate.setDate(cal)
        hebrewDate.forward(Calendar.DATE, 1)
        Assert.assertEquals(5, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(1, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(2, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(28, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.MONTH] = Calendar.JUNE
        cal[Calendar.DATE] = 30
        hebrewDate.setDate(cal)
        hebrewDate.forward(Calendar.DATE, 1)
        Assert.assertEquals(6, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(1, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(3, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(29, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.MONTH] = Calendar.JULY
        cal[Calendar.DATE] = 31
        hebrewDate.setDate(cal)
        hebrewDate.forward(Calendar.DATE, 1)
        Assert.assertEquals(7, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(1, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(5, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(1, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.MONTH] = Calendar.AUGUST
        cal[Calendar.DATE] = 31
        hebrewDate.setDate(cal)
        hebrewDate.forward(Calendar.DATE, 1)
        Assert.assertEquals(8, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(1, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(6, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(2, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.MONTH] = Calendar.SEPTEMBER
        cal[Calendar.DATE] = 30
        hebrewDate.setDate(cal)
        hebrewDate.forward(Calendar.DATE, 1)
        Assert.assertEquals(9, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(1, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(7, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(3, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.MONTH] = Calendar.OCTOBER
        cal[Calendar.DATE] = 31
        hebrewDate.setDate(cal)
        hebrewDate.forward(Calendar.DATE, 1)
        Assert.assertEquals(10, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(1, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(5772, hebrewDate.getJewishYear().toLong())
        Assert.assertEquals(8, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(4, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.MONTH] = Calendar.NOVEMBER
        cal[Calendar.DATE] = 30
        hebrewDate.setDate(cal)
        hebrewDate.forward(Calendar.DATE, 1)
        Assert.assertEquals(11, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(1, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(9, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(5, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.MONTH] = Calendar.DECEMBER
        cal[Calendar.DATE] = 31
        hebrewDate.setDate(cal)
        hebrewDate.forward(Calendar.DATE, 1)
        Assert.assertEquals(2012, hebrewDate.getGregorianYear().toLong())
        Assert.assertEquals(0, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(1, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(10, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(6, hebrewDate.jewishDayOfMonth.toLong())
    }

    @Test
    fun gregorianBackwardMonthToMonth() {
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = 2011
        cal[Calendar.MONTH] = Calendar.JANUARY
        cal[Calendar.DATE] = 1
        val hebrewDate = JewishDate(cal)
        hebrewDate.back()
        Assert.assertEquals(2010, hebrewDate.getGregorianYear().toLong())
        Assert.assertEquals(11, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(31, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(10, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(24, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.DATE] = 1
        cal[Calendar.MONTH] = Calendar.DECEMBER
        cal[Calendar.YEAR] = 2010
        hebrewDate.setDate(cal)
        hebrewDate.back()
        Assert.assertEquals(10, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(30, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(9, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(23, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.DATE] = 1
        cal[Calendar.MONTH] = Calendar.NOVEMBER
        hebrewDate.setDate(cal)
        hebrewDate.back()
        Assert.assertEquals(9, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(31, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(8, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(23, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.DATE] = 1
        cal[Calendar.MONTH] = Calendar.OCTOBER
        hebrewDate.setDate(cal)
        hebrewDate.back()
        Assert.assertEquals(8, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(30, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(7, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(22, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.DATE] = 1
        cal[Calendar.MONTH] = Calendar.SEPTEMBER
        hebrewDate.setDate(cal)
        hebrewDate.back()
        Assert.assertEquals(7, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(31, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(5770, hebrewDate.getJewishYear().toLong())
        Assert.assertEquals(6, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(21, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.DATE] = 1
        cal[Calendar.MONTH] = Calendar.AUGUST
        hebrewDate.setDate(cal)
        hebrewDate.back()
        Assert.assertEquals(6, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(31, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(5, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(20, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.DATE] = 1
        cal[Calendar.MONTH] = Calendar.JULY
        hebrewDate.setDate(cal)
        hebrewDate.back()
        Assert.assertEquals(5, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(30, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(4, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(18, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.DATE] = 1
        cal[Calendar.MONTH] = Calendar.JUNE
        hebrewDate.setDate(cal)
        hebrewDate.back()
        Assert.assertEquals(4, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(31, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(3, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(18, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.DATE] = 1
        cal[Calendar.MONTH] = Calendar.MAY
        hebrewDate.setDate(cal)
        hebrewDate.back()
        Assert.assertEquals(3, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(30, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(2, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(16, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.DATE] = 1
        cal[Calendar.MONTH] = Calendar.APRIL
        hebrewDate.setDate(cal)
        hebrewDate.back()
        Assert.assertEquals(2, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(31, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(1, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(16, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.DATE] = 1
        cal[Calendar.MONTH] = Calendar.MARCH
        hebrewDate.setDate(cal)
        hebrewDate.back()
        Assert.assertEquals(1, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(28, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(12, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(14, hebrewDate.jewishDayOfMonth.toLong())
        cal[Calendar.DATE] = 1
        cal[Calendar.MONTH] = Calendar.FEBRUARY
        hebrewDate.setDate(cal)
        hebrewDate.back()
        Assert.assertEquals(0, hebrewDate.getGregorianMonth().toLong())
        Assert.assertEquals(31, hebrewDate.getGregorianDayOfMonth().toLong())
        Assert.assertEquals(11, hebrewDate.getJewishMonth().toLong())
        Assert.assertEquals(16, hebrewDate.jewishDayOfMonth.toLong())
    }

} // End of UT_GregorianDateNavigation class
