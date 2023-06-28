package com.kosherjava.zmanim.hebrewcalendar

import org.junit.Assert
import org.junit.Test

class UT_YerushalmiTest {
    @Test
    fun testCorrectDaf1() {
        val jewishCalendar = JewishCalendar(5777, JewishCalendar.ELUL, 10)
        Assert.assertEquals(8, jewishCalendar.dafYomiYerushalmi.daf.toLong())
        Assert.assertEquals(29, jewishCalendar.dafYomiYerushalmi.masechtaNumber.toLong())
        println(hdf.formatDafYomiYerushalmi(jewishCalendar.dafYomiYerushalmi))
    }

    @Test
    fun testCorrectDaf2() {
        val jewishCalendar = JewishCalendar(5744, JewishCalendar.KISLEV, 1)
        Assert.assertEquals(26, jewishCalendar.dafYomiYerushalmi.daf.toLong())
        Assert.assertEquals(32, jewishCalendar.dafYomiYerushalmi.masechtaNumber.toLong())
        println(hdf.formatDafYomiYerushalmi(jewishCalendar.dafYomiYerushalmi))
    }

    @Test
    fun testCorrectDaf3() {
        val jewishCalendar = JewishCalendar(5782, JewishCalendar.SIVAN, 1)
        Assert.assertEquals(15, jewishCalendar.dafYomiYerushalmi.daf.toLong())
        Assert.assertEquals(33, jewishCalendar.dafYomiYerushalmi.masechtaNumber.toLong())
        println(hdf.formatDafYomiYerushalmi(jewishCalendar.dafYomiYerushalmi))
    }

    @Test
    fun testCorrectSpecialDate() {
        var jewishCalendar = JewishCalendar(5775, JewishCalendar.TISHREI, 10)
        Assert.assertNull(jewishCalendar.dafYomiYerushalmi)
        println(hdf.formatDafYomiYerushalmi(jewishCalendar.dafYomiYerushalmi))
        jewishCalendar = JewishCalendar(5783, JewishCalendar.AV, 9)
        Assert.assertNull(jewishCalendar.dafYomiYerushalmi)
        println(hdf.formatDafYomiYerushalmi(jewishCalendar.dafYomiYerushalmi))
        jewishCalendar = JewishCalendar(5775, JewishCalendar.AV, 10) // 9 Av delayed to Sunday 10 Av
        Assert.assertNull(jewishCalendar.dafYomiYerushalmi)
        println(hdf.formatDafYomiYerushalmi(jewishCalendar.dafYomiYerushalmi))
    }

    companion object {
        private val hdf = HebrewDateFormatter()

        init {
            hdf.isHebrewFormat = true
        }
    }
}
