/*
 * Copyright (c) 2011. Jay R. Gindin
 */
package com.kosherjava.zmanim.hebrewcalendar

import org.junit.Assert
import org.junit.Test
import java.util.Calendar

/**
 * Verify the calculation of the number of days in a month. Not too hard...just the rules about when February
 * has 28 or 29 days...
 */
class UT_DaysInGregorianMonth {

    @Test
    fun testDaysInMonth() {
        val hebrewDate = JewishDate()
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = 2011
        cal[Calendar.MONTH] = Calendar.JANUARY
        hebrewDate.setDate(cal)
        assertDaysInMonth(false, hebrewDate)
    }

    @Test
    fun testDaysInMonthLeapYear() {
        val hebrewDate = JewishDate()
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = 2012
        cal[Calendar.MONTH] = Calendar.JANUARY
        hebrewDate.setDate(cal)
        assertDaysInMonth(true, hebrewDate)
    }

    @Test
    fun testDaysInMonth100Year() {
        val hebrewDate = JewishDate()
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = 2100
        cal[Calendar.MONTH] = Calendar.JANUARY
        hebrewDate.setDate(cal)
        assertDaysInMonth(false, hebrewDate)
    }

    @Test
    fun testDaysInMonth400Year() {
        val hebrewDate = JewishDate()
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = 2000
        cal[Calendar.MONTH] = Calendar.JANUARY
        hebrewDate.setDate(cal)
        assertDaysInMonth(true, hebrewDate)
    }

    private fun assertDaysInMonth(
        febIsLeap: Boolean,
        hebrewDate: JewishDate,
    ) {
        Assert.assertEquals(31, hebrewDate.getLastDayOfGregorianMonth(1).toLong())
        Assert.assertEquals((if (febIsLeap) 29 else 28).toLong(), hebrewDate.getLastDayOfGregorianMonth(2).toLong())
        Assert.assertEquals(31, hebrewDate.getLastDayOfGregorianMonth(3).toLong())
        Assert.assertEquals(30, hebrewDate.getLastDayOfGregorianMonth(4).toLong())
        Assert.assertEquals(31, hebrewDate.getLastDayOfGregorianMonth(5).toLong())
        Assert.assertEquals(30, hebrewDate.getLastDayOfGregorianMonth(6).toLong())
        Assert.assertEquals(31, hebrewDate.getLastDayOfGregorianMonth(7).toLong())
        Assert.assertEquals(31, hebrewDate.getLastDayOfGregorianMonth(8).toLong())
        Assert.assertEquals(30, hebrewDate.getLastDayOfGregorianMonth(9).toLong())
        Assert.assertEquals(31, hebrewDate.getLastDayOfGregorianMonth(10).toLong())
        Assert.assertEquals(30, hebrewDate.getLastDayOfGregorianMonth(11).toLong())
        Assert.assertEquals(31, hebrewDate.getLastDayOfGregorianMonth(12).toLong())
    }

} // End of UT_DaysInGregorianMonth class
