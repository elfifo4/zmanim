/*
 * Copyright (c) 2011. Jay R. Gindin
 */
package com.kosherjava.zmanim.hebrewcalendar

import org.junit.Assert
import org.junit.Test

/**
 *
 */
class UT_JewishDateNavigation {
    @Test
    fun jewishForwardMonthToMonth() {
        val jewishDate = JewishDate()
        jewishDate.setJewishDate(5771, 1, 1)
        Assert.assertEquals(5, jewishDate.gregorianDayOfMonth.toLong())
        Assert.assertEquals(3, jewishDate.gregorianMonth.toLong())
        Assert.assertEquals(2011, jewishDate.gregorianYear.toLong())
    }

    @Test
    fun computeRoshHashana5771() {

        // At one point, this test was failing as the JewishDate class spun through a never-ending loop...
        val jewishDate = JewishDate()
        jewishDate.setJewishDate(5771, 7, 1)
        Assert.assertEquals(9, jewishDate.gregorianDayOfMonth.toLong())
        Assert.assertEquals(8, jewishDate.gregorianMonth.toLong())
        Assert.assertEquals(2010, jewishDate.gregorianYear.toLong())
    }
} // End of UT_JewishDateNavigation class
