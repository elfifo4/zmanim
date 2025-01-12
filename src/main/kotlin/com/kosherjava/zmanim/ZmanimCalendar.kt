/*
 * Zmanim Java API
 * Copyright (C) 2004-2022 Eliyahu Hershfeld
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful,but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to
 * the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA,
 * or connect to: https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 */
package com.kosherjava.zmanim

import com.kosherjava.zmanim.AstronomicalCalendar
import com.kosherjava.zmanim.hebrewcalendar.JewishCalendar
import com.kosherjava.zmanim.util.GeoLocation
import java.util.Calendar
import java.util.Date

/**
 * The ZmanimCalendar is a specialized calendar that can calculate sunrise, sunset and Jewish *zmanim*
 * (religious times) for prayers and other Jewish religious duties. This class contains the main functionality of the
 * *Zmanim* library. For a much more extensive list of *zmanim*, use the [ComplexZmanimCalendar] that
 * extends this class. See documentation for the [ComplexZmanimCalendar] and [AstronomicalCalendar] for
 * simple examples on using the API.
 * **Elevation based *zmanim* (even sunrise and sunset) should not be used *lekula* without the guidance
 * of a *posek***. According to Rabbi Dovid Yehudah Bursztyn in his
 * [Zmanim Kehilchasam, 7th edition](https://www.worldcat.org/oclc/1158574217) chapter 2, section 7 (pages 181-182)
 * and section 9 (pages 186-187), no *zmanim* besides sunrise and sunset should use elevation. However, Rabbi Yechiel
 * Avrahom Zilber in the [Birur Halacha Vol. 6](https://hebrewbooks.org/51654) Ch. 58 Pages
 * [&amp;34](https://hebrewbooks.org/pdfpager.aspx?req=51654&amp;pgnum=42) and
 * [&amp;42](https://hebrewbooks.org/pdfpager.aspx?req=51654&amp;pgnum=50) is of the opinion that elevation should be
 * accounted for in *zmanim* calculations. Related to this, Rabbi Yaakov Karp in [Shimush Zekeinim](https://www.worldcat.org/oclc/919472094), Ch. 1, page 17 states that obstructing horizons should
 * be factored into *zmanim* calculations. The setting defaults to false (elevation will not be used for
 * *zmanim* calculations besides sunrise and sunset), unless the setting is changed to true in [ ][.setUseElevation]. This will impact sunrise and sunset-based *zmanim* such as [.getSunrise],
 * [.getSunset], [.getSofZmanShmaGRA], *alos*-based *zmanim* such as [.getSofZmanShmaMGA]
 * that are based on a fixed offset of sunrise or sunset and *zmanim* based on a percentage of the day such as
 * [ComplexZmanimCalendar.getSofZmanShmaMGA90MinutesZmanis] that are based on sunrise and sunset. Even when set to
 * true it will not impact *zmanim* that are a degree-based offset of sunrise and sunset, such as [ ][ComplexZmanimCalendar.getSofZmanShmaMGA16Point1Degrees] or [ComplexZmanimCalendar.getSofZmanShmaBaalHatanya] since
 * these *zmanim* are not linked to sunrise or sunset times (the calculations are based on the astronomical definition of
 * sunrise and sunset calculated in a vacuum with the solar radius above the horizon), and are therefore not impacted by the use
 * of elevation.
 * For additional information on the *halachic* impact of elevation on *zmanim* see:
 *
 *  * [Zmanei Halacha Lema'aseh](https://www.nli.org.il/en/books/NNL_ALEPH002542826/NLI) 4th edition by [Rabbi Yedidya Manat](http://beinenu.com/rabbis/%D7%94%D7%A8%D7%91-%D7%99%D7%93%D7%99%D7%93%D7%99%D7%94-%D7%9E%D7%A0%D7%AA).
 * See section 1, pages 11-12 for a very concise write-up, with details in section 2, pages 37 - 63 and 133 - 151.
 *  * [Zmanim Kehilchasam](https://www.worldcat.org/oclc/1158574217) 7th edition, by Rabbi Dovid Yehuda Burstein,  vol 1,
 * chapter 2, pages 95 - 188.
 *  * [Hazmanim Bahalacha](https://www.worldcat.org/oclc/36089452) by Rabbi Chaim Banish , perek 7, pages 53 - 63.
 *
 *
 *
 * **Note:** It is important to read the technical notes on top of the [AstronomicalCalculator] documentation
 * before using this code.
 *
 * I would like to thank [Rabbi Yaakov Shakow](https://www.worldcat.org/search?q=au%3AShakow%2C+Yaakov), the
 * author of Luach Ikvei Hayom who spent a considerable amount of time reviewing, correcting and making suggestions on the
 * documentation in this library.
 * <h2>Disclaimer:</h2> I did my best to get accurate results, but please double-check before relying on these
 * *zmanim* for *halacha lema'aseh*.
 *
 *
 * @author  Eliyahu Hershfeld 2004 - 2022
 */
open class ZmanimCalendar : AstronomicalCalendar {
    /**
     * Is elevation above sea level calculated for times besides sunrise and sunset. According to Rabbi Dovid Yehuda
     * Bursztyn in his [Zmanim Kehilchasam (second edition published
 * in 2007)](https://www.worldcat.org/oclc/659793988) chapter 2 (pages 186-187) no *zmanim* besides sunrise and sunset should use elevation. However
     * Rabbi Yechiel Avrahom Zilber in the [Birur Halacha Vol. 6](https://hebrewbooks.org/51654) Ch. 58 Pages
     * [&amp;34](https://hebrewbooks.org/pdfpager.aspx?req=51654&amp;pgnum=42) and [&amp;42](https://hebrewbooks.org/pdfpager.aspx?req=51654&amp;pgnum=50) is of the opinion that elevation should be
     * accounted for in *zmanim* calculations. Related to this, Rabbi Yaakov Karp in [Shimush Zekeinim](https://www.worldcat.org/oclc/919472094), Ch. 1, page 17 states that obstructing horizons
     * should be factored into *zmanim* calculations.The setting defaults to false (elevation will not be used for
     * *zmanim* calculations), unless the setting is changed to true in [.setUseElevation]. This will
     * impact sunrise and sunset based *zmanim* such as [.getSunrise], [.getSunset],
     * [.getSofZmanShmaGRA], alos based *zmanim* such as [.getSofZmanShmaMGA] that are based on a
     * fixed offset of sunrise or sunset and *zmanim* based on a percentage of the day such as [ ][ComplexZmanimCalendar.getSofZmanShmaMGA90MinutesZmanis] that are based on sunrise and sunset. It will not impact
     * *zmanim* that are a degree based offset of sunrise and sunset, such as
     * [ComplexZmanimCalendar.getSofZmanShmaMGA16Point1Degrees] or [ComplexZmanimCalendar.getSofZmanShmaBaalHatanya].
     *
     * @return if the use of elevation is active
     *
     * @see .setUseElevation
     */
    /**
     * Sets whether elevation above sea level is factored into *zmanim* calculations for times besides sunrise and sunset.
     * See [.isUseElevation] for more details.
     * @see .isUseElevation
     * @param useElevation set to true to use elevation in *zmanim* calculations
     */
    /**
     * Is elevation factored in for some zmanim (see [.isUseElevation] for additional information).
     * @see .isUseElevation
     * @see .setUseElevation
     */
    var isUseElevation = false
    /**
     * A method to get the offset in minutes before [sea level sunset][AstronomicalCalendar.getSeaLevelSunset] which
     * is used in calculating candle lighting time. The default time used is 18 minutes before sea level sunset. Some
     * calendars use 15 minutes, while the custom in Jerusalem is to use a 40 minute offset. Please check the local custom
     * for candle lighting time.
     *
     * @return Returns the currently set candle lighting offset in minutes.
     * @see .getCandleLighting
     * @see .setCandleLightingOffset
     */
    /**
     * A method to set the offset in minutes before [sea level sunset][AstronomicalCalendar.getSeaLevelSunset] that is
     * used in calculating candle lighting time. The default time used is 18 minutes before sunset. Some calendars use 15
     * minutes, while the custom in Jerusalem is to use a 40 minute offset.
     *
     * @param candleLightingOffset
     * The candle lighting offset to set in minutes.
     * @see .getCandleLighting
     * @see .getCandleLightingOffset
     */
    /**
     * The default *Shabbos* candle lighting offset is 18 minutes. This can be changed via the
     * [.setCandleLightingOffset] and retrieved by the [.getCandleLightingOffset].
     */
    var candleLightingOffset = 18.0

    /**
     * This method will return [sea level sunrise][.getSeaLevelSunrise] if [.isUseElevation] is false (the
     * default), or elevation adjusted [AstronomicalCalendar.getSunrise] if it is true. This allows relevant *zmanim*
     * in this and extending classes (such as the [ComplexZmanimCalendar]) to automatically adjust to the elevation setting.
     *
     * @return [.getSeaLevelSunrise] if [.isUseElevation] is false (the default), or elevation adjusted
     * [AstronomicalCalendar.getSunrise] if it is true.
     * @see com.kosherjava.zmanim.AstronomicalCalendar.getSunrise
     */
    protected val elevationAdjustedSunrise: Date?
        protected get() = if (isUseElevation) {
            super.sunrise
        } else seaLevelSunrise

    /**
     * This method will return [sea level sunrise][.getSeaLevelSunrise] if [.isUseElevation] is false (the default),
     * or elevation adjusted [AstronomicalCalendar.getSunrise] if it is true. This allows relevant *zmanim*
     * in this and extending classes (such as the [ComplexZmanimCalendar]) to automatically adjust to the elevation setting.
     *
     * @return [.getSeaLevelSunset] if [.isUseElevation] is false (the default), or elevation adjusted
     * [AstronomicalCalendar.getSunset] if it is true.
     * @see com.kosherjava.zmanim.AstronomicalCalendar.getSunset
     */
    protected val elevationAdjustedSunset: Date?
        protected get() = if (isUseElevation) {
            super.sunset
        } else seaLevelSunset

    /**
     * A method that returns *tzais* (nightfall) when the sun is [8.5&amp;deg;][.ZENITH_8_POINT_5] below the
     * [geometric horizon][.GEOMETRIC_ZENITH] (90) after [sunset][.getSunset], a time that Rabbi Meir
     * Posen in his the *[Ohr Meir](https://www.worldcat.org/oclc/29283612)* calculated that 3 small
     * stars are visible, which is later than the required 3 medium stars. See the [.ZENITH_8_POINT_5] constant.
     *
     * @see .ZENITH_8_POINT_5
     *
     *
     * @return The `Date` of nightfall. If the calculation can't be computed such as northern and southern
     * locations even south of the Arctic Circle and north of the Antarctic Circle where the sun may not reach
     * low enough below the horizon for this calculation, a null will be returned. See detailed explanation on
     * top of the [AstronomicalCalendar] documentation.
     * @see .ZENITH_8_POINT_5
     * ComplexZmanimCalendar.getTzaisGeonim8Point5Degrees
     */
    val tzais: Date?
        get() = getSunsetOffsetByDegrees(ZENITH_8_POINT_5)

    /**
     * Returns *alos* (dawn) based on the time when the sun is [16.1&amp;deg;][.ZENITH_16_POINT_1] below the
     * eastern [geometric horizon][.GEOMETRIC_ZENITH] before [sunrise][.getSunrise]. This is based on the
     * calculation that the time between dawn and sunrise (and sunset to nightfall) is 72 minutes, the time that is
     * takes to walk 4 *mil* at 18 minutes a mil (*[Rambam](https://en.wikipedia.org/wiki/Maimonides)* and others). The sun's position at 72 minutes before [sunrise][.getSunrise] in Jerusalem
     * on the [around the equinox /
 * equilux](https://kosherjava.com/2022/01/12/equinox-vs-equilux-zmanim-calculations/) is 16.1 below [.GEOMETRIC_ZENITH].
     *
     * @see .ZENITH_16_POINT_1
     *
     * @see ComplexZmanimCalendar.getAlos16Point1Degrees
     * @return The `Date` of dawn. If the calculation can't be computed such as northern and southern
     * locations even south of the Arctic Circle and north of the Antarctic Circle where the sun may not reach
     * low enough below the horizon for this calculation, a null will be returned. See detailed explanation on
     * top of the [AstronomicalCalendar] documentation.
     */
    val alosHashachar: Date?
        get() = getSunriseOffsetByDegrees(ZENITH_16_POINT_1)

    /**
     * Method to return *alos* (dawn) calculated using 72 minutes before [sunrise][.getSunrise] or
     * [sea level sunrise][.getSeaLevelSunrise] (depending on the [.isUseElevation] setting). This time
     * is based on the time to walk the distance of 4 *Mil* at 18 minutes a *Mil*. The 72 minute time (but
     * not the concept of fixed minutes) is based on the opinion that the time of the *Neshef* (twilight between
     * dawn and sunrise) does not vary by the time of year or location but depends on the time it takes to walk the
     * distance of 4 *Mil*.
     *
     * @return the `Date` representing the time. If the calculation can't be computed such as in the Arctic
     * Circle where there is at least one day a year where the sun does not rise, and one where it does not set,
     * a null will be returned. See detailed explanation on top of the [AstronomicalCalendar]
     * documentation.
     */
    val alos72: Date?
        get() = getTimeOffset(elevationAdjustedSunrise, -72 * MINUTE_MILLIS)

    /**
     * This method returns *chatzos* (midday) following most opinions that *chatzos* is the midpoint
     * between [sea level sunrise][.getSeaLevelSunrise] and [sea level sunset][.getSeaLevelSunset]. A day
     * starting at *alos* and ending at *tzais* using the same time or degree offset will also return
     * the same time. The returned value is identical to [.getSunTransit]. In reality due to lengthening or
     * shortening of day, this is not necessarily the exact midpoint of the day, but it is very close.
     *
     * @see AstronomicalCalendar.getSunTransit
     * @return the `Date` of chatzos. If the calculation can't be computed such as in the Arctic Circle
     * where there is at least one day where the sun does not rise, and one where it does not set, a null will
     * be returned. See detailed explanation on top of the [AstronomicalCalendar] documentation.
     */
    val chatzos: Date
        get() = sunTransit!!

    /**
     * A generic method for calculating the latest *zman krias shema* (time to recite shema in the morning)
     * that is 3 * *shaos zmaniyos* (temporal hours) after the start of the day, calculated using the start and
     * end of the day passed to this method.
     * The time from the start of day to the end of day are divided into 12 *shaos zmaniyos* (temporal hours),
     * and the latest *zman krias shema* is calculated as 3 of those *shaos zmaniyos* after the beginning of
     * the day. As an example, passing [sunrise][.getSunrise] and [sunset][.getSunset] or [ sea level sunrise][.getSeaLevelSunrise] and [sea level sunset][.getSeaLevelSunset] (depending on the [.isUseElevation]
     * elevation setting) to this method will return *sof zman krias shema* according to the opinion of the
     * *[GRA](https://en.wikipedia.org/wiki/Vilna_Gaon)*.
     *
     * @param startOfDay
     * the start of day for calculating *zman krias shema*. This can be sunrise or any *alos* passed
     * to this method.
     * @param endOfDay
     * the end of day for calculating *zman krias shema*. This can be sunset or any *tzais* passed to
     * this method.
     * @return the `Date` of the latest *zman shema* based on the start and end of day times passed to this
     * method. If the calculation can't be computed such as in the Arctic Circle where there is at least one day
     * a year where the sun does not rise, and one where it does not set, a null will be returned. See detailed
     * explanation on top of the [AstronomicalCalendar] documentation.
     */
    fun getSofZmanShma(startOfDay: Date?, endOfDay: Date?): Date? {
        return getShaahZmanisBasedZman(startOfDay, endOfDay, 3.0)
    }

    /**
     * This method returns the latest *zman krias shema* (time to recite shema in the morning) that is 3 *
     * [&lt;em&gt;shaos zmaniyos&lt;/em&gt;][.getShaahZmanisGra] (solar hours) after [sunrise][.getSunrise] or
     * [sea level sunrise][.getSeaLevelSunrise] (depending on the [.isUseElevation] setting), according
     * to the [GRA](https://en.wikipedia.org/wiki/Vilna_Gaon).
     * The day is calculated from [sea level sunrise][.getSeaLevelSunrise] to [sea level][.getSeaLevelSunrise] or [sunrise][.getSunrise] to [sunset][.getSunset] (depending on the [.isUseElevation]
     * setting).
     *
     * @see .getSofZmanShma
     * @see .getShaahZmanisGra
     * @see .isUseElevation
     * @see ComplexZmanimCalendar.getSofZmanShmaBaalHatanya
     * @return the `Date` of the latest *zman shema* according to the GRA. If the calculation can't be
     * computed such as in the Arctic Circle where there is at least one day a year where the sun does not rise,
     * and one where it does not set, a null will be returned. See the detailed explanation on top of the [         ] documentation.
     */
    val sofZmanShmaGRA: Date?
        get() = getSofZmanShma(elevationAdjustedSunrise, elevationAdjustedSunset)

    /**
     * This method returns the latest *zman krias shema* (time to recite shema in the morning) that is 3 *
     * [&lt;em&gt;shaos zmaniyos&lt;/em&gt;][.getShaahZmanisMGA] (solar hours) after [.getAlos72], according to the
     * [Magen Avraham (MGA)](https://en.wikipedia.org/wiki/Avraham_Gombinern). The day is calculated
     * from 72 minutes before [sea level sunrise][.getSeaLevelSunrise] to 72 minutes after [ ][.getSeaLevelSunrise] or from 72 minutes before [sunrise][.getSunrise] to [ sunset][.getSunset] (depending on the [.isUseElevation] setting).
     *
     * @return the `Date` of the latest *zman shema*. If the calculation can't be computed such as in
     * the Arctic Circle where there is at least one day a year where the sun does not rise, and one where it
     * does not set, a null will be returned. See detailed explanation on top of the
     * [AstronomicalCalendar] documentation.
     * @see .getSofZmanShma
     * @see ComplexZmanimCalendar.getShaahZmanis72Minutes
     * @see ComplexZmanimCalendar.getAlos72
     * @see ComplexZmanimCalendar.getSofZmanShmaMGA72Minutes
     */
    val sofZmanShmaMGA: Date?
        get() = getSofZmanShma(alos72, tzais72)

    /**
     * This method returns the *tzais* (nightfall) based on the opinion of *Rabbeinu Tam* that
     * *tzais hakochavim* is calculated as 72 minutes, the time it takes to walk 4 *Mil* at 18 minutes
     * a *Mil*. According to the [Machtzis Hashekel](https://en.wikipedia.org/wiki/Samuel_Loew) in
     * Orach Chaim 235:3, the [Pri Megadim](https://en.wikipedia.org/wiki/Joseph_ben_Meir_Teomim) in Orach
     * Chaim 261:2 (see the Biur Halacha) and others (see Hazmanim Bahalacha 17:3 and 17:5) the 72 minutes are standard
     * clock minutes any time of the year in any location. Depending on the [.isUseElevation] setting) a 72
     * minute offset from  either [sunset][.getSunset] or [sea level sunset][.getSeaLevelSunset] is used.
     *
     * @see ComplexZmanimCalendar.getTzais16Point1Degrees
     * @return the `Date` representing 72 minutes after sunset. If the calculation can't be
     * computed such as in the Arctic Circle where there is at least one day a year where the sun does not rise,
     * and one where it does not set, a null will be returned See detailed explanation on top of the
     * [AstronomicalCalendar] documentation.
     */
    val tzais72: Date?
        get() = getTimeOffset(elevationAdjustedSunset, 72 * MINUTE_MILLIS)

    /**
     * A method to return candle lighting time, calculated as [.getCandleLightingOffset] minutes before
     * [sea level sunset][.getSeaLevelSunset]. This will return the time for any day of the week, since it can be
     * used to calculate candle lighting time for *Yom Tov* (mid-week holidays) as well. Elevation adjustments
     * are intentionally not performed by this method, but you can calculate it by passing the elevation adjusted sunset
     * to [.getTimeOffset].
     *
     * @return candle lighting time. If the calculation can't be computed such as in the Arctic Circle where there is at
     * least one day a year where the sun does not rise, and one where it does not set, a null will be returned.
     * See detailed explanation on top of the [AstronomicalCalendar] documentation.
     *
     * @see .getSeaLevelSunset
     * @see .getCandleLightingOffset
     * @see .setCandleLightingOffset
     */
    val candleLighting: Date?
        get() = getTimeOffset(seaLevelSunset, (-candleLightingOffset * MINUTE_MILLIS).toLong())

    /**
     * A generic method for calculating the latest *zman tfilah* (time to recite the morning prayers)
     * that is 4 * *shaos zmaniyos* (temporal hours) after the start of the day, calculated using the start and
     * end of the day passed to this method.
     * The time from the start of day to the end of day are divided into 12 *shaos zmaniyos* (temporal hours),
     * and *sof zman tfila* is calculated as 4 of those *shaos zmaniyos* after the beginning of the day.
     * As an example, passing [sunrise][.getSunrise] and [sunset][.getSunset] or [ sea level sunrise][.getSeaLevelSunrise] and [sea level sunset][.getSeaLevelSunset] (depending on the [.isUseElevation]
     * elevation setting) to this method will return *zman tfilah* according to the opinion of the *[GRA](https://en.wikipedia.org/wiki/Vilna_Gaon)*.
     *
     * @param startOfDay
     * the start of day for calculating *zman tfilah*. This can be sunrise or any *alos* passed
     * to this method.
     * @param endOfDay
     * the end of day for calculating *zman tfilah*. This can be sunset or any *tzais* passed
     * to this method.
     * @return the `Date` of the latest *zman tfilah* based on the start and end of day times passed
     * to this method. If the calculation can't be computed such as in the Arctic Circle where there is at least
     * one day a year where the sun does not rise, and one where it does not set, a null will be returned. See
     * detailed explanation on top of the [AstronomicalCalendar] documentation.
     */
    fun getSofZmanTfila(startOfDay: Date?, endOfDay: Date?): Date? {
        return getShaahZmanisBasedZman(startOfDay, endOfDay, 4.0)
    }

    /**
     * This method returns the latest *zman tfila* (time to recite shema in the morning) that is 4 *
     * [&lt;em&gt;shaos zmaniyos&lt;/em&gt; ][.getShaahZmanisGra](solar hours) after [sunrise][.getSunrise] or
     * [sea level sunrise][.getSeaLevelSunrise] (depending on the [.isUseElevation] setting), according
     * to the [GRA](https://en.wikipedia.org/wiki/Vilna_Gaon).
     * The day is calculated from [sea level sunrise][.getSeaLevelSunrise] to [sea level][.getSeaLevelSunrise] or [sunrise][.getSunrise] to [sunset][.getSunset] (depending on the [.isUseElevation]
     * setting).
     *
     * @see .getSofZmanTfila
     * @see .getShaahZmanisGra
     * @see ComplexZmanimCalendar.getSofZmanTfilaBaalHatanya
     * @return the `Date` of the latest *zman tfilah*. If the calculation can't be computed such as in
     * the Arctic Circle where there is at least one day a year where the sun does not rise, and one where it
     * does not set, a null will be returned. See detailed explanation on top of the [AstronomicalCalendar]
     * documentation.
     */
    val sofZmanTfilaGRA: Date?
        get() = getSofZmanTfila(elevationAdjustedSunrise, elevationAdjustedSunset)

    /**
     * This method returns the latest *zman tfila* (time to recite shema in the morning) that is 4 *
     * [&lt;em&gt;shaos zmaniyos&lt;/em&gt;][.getShaahZmanisMGA] (solar hours) after [.getAlos72], according to the
     * *[Magen Avraham (MGA)](https://en.wikipedia.org/wiki/Avraham_Gombinern)*. The day is calculated
     * from 72 minutes before [sea level sunrise][.getSeaLevelSunrise] to 72 minutes after [ ][.getSeaLevelSunrise] or from 72 minutes before [sunrise][.getSunrise] to [ sunset][.getSunset] (depending on the [.isUseElevation] setting).
     *
     * @return the `Date` of the latest *zman tfila*. If the calculation can't be computed such as in
     * the Arctic Circle where there is at least one day a year where the sun does not rise, and one where it
     * does not set), a null will be returned. See detailed explanation on top of the [AstronomicalCalendar]
     * documentation.
     * @see .getSofZmanTfila
     * @see .getShaahZmanisMGA
     * @see .getAlos72
     */
    val sofZmanTfilaMGA: Date?
        get() = getSofZmanTfila(alos72, tzais72)

    /**
     * A generic method for calculating the latest *mincha gedola* (the earliest time to recite the mincha  prayers)
     * that is 6.5 * *shaos zmaniyos* (temporal hours) after the start of the day, calculated using the start and end
     * of the day passed to this method.
     * The time from the start of day to the end of day are divided into 12 *shaos zmaniyos* (temporal hours), and
     * *mincha gedola* is calculated as 6.5 of those *shaos zmaniyos* after the beginning of the day. As an
     * example, passing [sunrise][.getSunrise] and [sunset][.getSunset] or [sea level][.getSeaLevelSunrise] and [sea level sunset][.getSeaLevelSunset] (depending on the [.isUseElevation] elevation
     * setting) to this method will return *mincha gedola* according to the opinion of the
     * *[GRA](https://en.wikipedia.org/wiki/Vilna_Gaon)*.
     *
     * @param startOfDay
     * the start of day for calculating *Mincha gedola*. This can be sunrise or any *alos* passed
     * to this method.
     * @param endOfDay
     * the end of day for calculating *Mincha gedola*. This can be sunset or any *tzais* passed
     * to this method.
     * @return the `Date` of the time of *Mincha gedola* based on the start and end of day times
     * passed to this method. If the calculation can't be computed such as in the Arctic Circle where there is
     * at least one day a year where the sun does not rise, and one where it does not set, a null will be
     * returned. See detailed explanation on top of the [AstronomicalCalendar] documentation.
     */
    fun getMinchaGedola(startOfDay: Date?, endOfDay: Date?): Date? {
        return getShaahZmanisBasedZman(startOfDay, endOfDay, 6.5)
    }

    /**
     * This method returns the latest *mincha gedola*,the earliest time one can pray *mincha* that is 6.5 *
     * [&lt;em&gt;shaos zmaniyos&lt;/em&gt;][.getShaahZmanisGra] (solar hours) after [sunrise][.getSunrise] or
     * [sea level sunrise][.getSeaLevelSunrise] (depending on the [.isUseElevation] setting), according
     * to the *[GRA](https://en.wikipedia.org/wiki/Vilna_Gaon)*. *Mincha gedola* is the earliest
     * time one can pray *mincha*. The Ramba"m is of the opinion that it is better to delay *mincha* until
     * [&lt;em&gt;mincha ketana&lt;/em&gt;][.getMinchaKetana] while the *Ra"sh, Tur, GRA* and others are of the
     * opinion that *mincha* can be prayed *lechatchila* starting at *mincha gedola*.
     * The day is calculated from [sea level sunrise][.getSeaLevelSunrise] to [sea level][.getSeaLevelSunrise] or [sunrise][.getSunrise] to [sunset][.getSunset] (depending on the [.isUseElevation]
     * setting).
     *
     * @see .getMinchaGedola
     * @see .getShaahZmanisGra
     * @see .getMinchaKetana
     * @see ComplexZmanimCalendar.getMinchaGedolaBaalHatanya
     * @return the `Date` of the time of mincha gedola. If the calculation can't be computed such as in the
     * Arctic Circle where there is at least one day a year where the sun does not rise, and one where it does
     * not set, a null will be returned. See detailed explanation on top of the [AstronomicalCalendar]
     * documentation.
     */
    val minchaGedola: Date?
        get() = getMinchaGedola(elevationAdjustedSunrise, elevationAdjustedSunset)

    /**
     * A generic method for calculating *samuch lemincha ketana*, / near *mincha ketana* time that is half
     * an hour before [.getMinchaKetana]  or 9 * *shaos zmaniyos* (temporal hours) after the
     * start of the day, calculated using the start and end of the day passed to this method.
     * The time from the start of day to the end of day are divided into 12 *shaos zmaniyos* (temporal hours), and
     * *samuch lemincha ketana* is calculated as 9 of those *shaos zmaniyos* after the beginning of the day.
     * For example, passing [sunrise][.getSunrise] and [sunset][.getSunset] or [sea][.getSeaLevelSunrise] and [sea level sunset][.getSeaLevelSunset] (depending on the [.isUseElevation] elevation
     * setting) to this method will return *samuch lemincha ketana* according to the opinion of the
     * [GRA](https://en.wikipedia.org/wiki/Vilna_Gaon).
     *
     * @param startOfDay
     * the start of day for calculating *samuch lemincha ketana*. This can be sunrise or any *alos*
     * passed to to this method.
     * @param endOfDay
     * the end of day for calculating *samuch lemincha ketana*. This can be sunset or any *tzais*
     * passed to this method.
     * @return the `Date` of the time of *Mincha ketana* based on the start and end of day times
     * passed to this method. If the calculation can't be computed such as in the Arctic Circle where there is
     * at least one day a year where the sun does not rise, and one where it does not set, a null will be
     * returned. See detailed explanation on top of the [AstronomicalCalendar] documentation.
     *
     * @see ComplexZmanimCalendar.getSamuchLeMinchaKetanaGRA
     * @see ComplexZmanimCalendar.getSamuchLeMinchaKetana16Point1Degrees
     * @see ComplexZmanimCalendar.getSamuchLeMinchaKetana72Minutes
     */
    fun getSamuchLeMinchaKetana(startOfDay: Date?, endOfDay: Date?): Date? {
        return getShaahZmanisBasedZman(startOfDay, endOfDay, 9.0)
    }

    /**
     * A generic method for calculating *mincha ketana*, (the preferred time to recite the mincha prayers in
     * the opinion of the *[Rambam](https://en.wikipedia.org/wiki/Maimonides)* and others) that is
     * 9.5 * *shaos zmaniyos* (temporal hours) after the start of the day, calculated using the start and end
     * of the day passed to this method.
     * The time from the start of day to the end of day are divided into 12 *shaos zmaniyos* (temporal hours), and
     * *mincha ketana* is calculated as 9.5 of those *shaos zmaniyos* after the beginning of the day. As an
     * example, passing [sunrise][.getSunrise] and [sunset][.getSunset] or [sea][.getSeaLevelSunrise] and [sea level sunset][.getSeaLevelSunset] (depending on the [.isUseElevation]
     * elevation setting) to this method will return *mincha ketana* according to the opinion of the
     * [GRA](https://en.wikipedia.org/wiki/Vilna_Gaon).
     *
     * @param startOfDay
     * the start of day for calculating *Mincha ketana*. This can be sunrise or any *alos* passed
     * to this method.
     * @param endOfDay
     * the end of day for calculating *Mincha ketana*. This can be sunset or any *tzais* passed to
     * this method.
     * @return the `Date` of the time of *Mincha ketana* based on the start and end of day times
     * passed to this method. If the calculation can't be computed such as in the Arctic Circle where there is
     * at least one day a year where the sun does not rise, and one where it does not set, a null will be
     * returned. See detailed explanation on top of the [AstronomicalCalendar] documentation.
     */
    fun getMinchaKetana(startOfDay: Date?, endOfDay: Date?): Date? {
        return getShaahZmanisBasedZman(startOfDay, endOfDay, 9.5)
    }

    /**
     * This method returns *mincha ketana*,the preferred earliest time to pray *mincha* in the
     * opinion of the *[Rambam](https://en.wikipedia.org/wiki/Maimonides)* and others, that is 9.5
     * * [&lt;em&gt;shaos zmaniyos&lt;/em&gt;][.getShaahZmanisGra] (solar hours) after [sunrise][.getSunrise] or
     * [sea level sunrise][.getSeaLevelSunrise] (depending on the [.isUseElevation] setting), according
     * to the [GRA](https://en.wikipedia.org/wiki/Vilna_Gaon). For more information on this see the
     * documentation on [&lt;em&gt;mincha gedola&lt;/em&gt;][.getMinchaGedola].
     * The day is calculated from [sea level sunrise][.getSeaLevelSunrise] to [sea level][.getSeaLevelSunrise] or [sunrise][.getSunrise] to [sunset][.getSunset] (depending on the [.isUseElevation]
     * setting.
     *
     * @see .getMinchaKetana
     * @see .getShaahZmanisGra
     * @see .getMinchaGedola
     * @see ComplexZmanimCalendar.getMinchaKetanaBaalHatanya
     * @return the `Date` of the time of mincha ketana. If the calculation can't be computed such as in the
     * Arctic Circle where there is at least one day a year where the sun does not rise, and one where it does
     * not set, a null will be returned. See detailed explanation on top of the [AstronomicalCalendar]
     * documentation.
     */
    val minchaKetana: Date?
        get() = getMinchaKetana(elevationAdjustedSunrise, elevationAdjustedSunset)

    /**
     * A generic method for calculating *plag hamincha* (the earliest time that Shabbos can be started) that is
     * 10.75 hours after the start of the day, (or 1.25 hours before the end of the day) based on the start and end of
     * the day passed to the method.
     * The time from the start of day to the end of day are divided into 12 *shaos zmaniyos* (temporal hours), and
     * *plag hamincha* is calculated as 10.75 of those *shaos zmaniyos* after the beginning of the day. As an
     * example, passing [sunrise][.getSunrise] and [sunset][.getSunset] or [sea level][.getSeaLevelSunrise] and [sea level sunset][.getSeaLevelSunset] (depending on the [.isUseElevation] elevation
     * setting) to this method will return *plag mincha* according to the opinion of the
     * *[GRA](https://en.wikipedia.org/wiki/Vilna_Gaon)*.
     *
     * @param startOfDay
     * the start of day for calculating plag. This can be sunrise or any *alos* passed to this method.
     * @param endOfDay
     * the end of day for calculating plag. This can be sunset or any *tzais* passed to this method.
     * @return the `Date` of the time of *plag hamincha* based on the start and end of day times
     * passed to this method. If the calculation can't be computed such as in the Arctic Circle where there is
     * at least one day a year where the sun does not rise, and one where it does not set, a null will be
     * returned. See detailed explanation on top of the [AstronomicalCalendar] documentation.
     */
    fun getPlagHamincha(startOfDay: Date?, endOfDay: Date?): Date? {
        return getShaahZmanisBasedZman(startOfDay, endOfDay, 10.75)
    }

    /**
     * This method returns *plag hamincha*, that is 10.75 * [&lt;em&gt;shaos zmaniyos&lt;/em&gt;][.getShaahZmanisGra]
     * (solar hours) after [sunrise][.getSunrise] or [sea level sunrise][.getSeaLevelSunrise] (depending on
     * the [.isUseElevation] setting), according to the *[GRA](https://en.wikipedia.org/wiki/Vilna_Gaon)*. Plag hamincha is the earliest time that *Shabbos* can be started.
     * The day is calculated from [sea level sunrise][.getSeaLevelSunrise] to [sea level][.getSeaLevelSunrise] or [sunrise][.getSunrise] to [sunset][.getSunset] (depending on the [.isUseElevation]
     *
     * @see .getPlagHamincha
     * @see ComplexZmanimCalendar.getPlagHaminchaBaalHatanya
     * @return the `Date` of the time of *plag hamincha*. If the calculation can't be computed such as
     * in the Arctic Circle where there is at least one day a year where the sun does not rise, and one where it
     * does not set, a null will be returned. See detailed explanation on top of the
     * [AstronomicalCalendar] documentation.
     */
    val plagHamincha: Date?
        get() = getPlagHamincha(elevationAdjustedSunrise, elevationAdjustedSunset)

    /**
     * A method that returns a *shaah zmanis* ([temporal hour][.getTemporalHour]) according to
     * the opinion of the *[GRA](https://en.wikipedia.org/wiki/Vilna_Gaon)*. This calculation divides
     * the day based on the opinion of the *GRA* that the day runs from from [sea][.getSeaLevelSunrise] to [sea level sunset][.getSeaLevelSunrise] or [sunrise][.getSunrise] to
     * [sunset][.getSunset] (depending on the [.isUseElevation] setting). The day is split into 12 equal
     * parts with each one being a *shaah zmanis*. This method is similar to [.getTemporalHour], but can
     * account for elevation.
     *
     * @return the `long` millisecond length of a *shaah zmanis* calculated from sunrise to sunset.
     * If the calculation can't be computed such as in the Arctic Circle where there is at least one day a year
     * where the sun does not rise, and one where it does not set, [Long.MIN_VALUE] will be returned. See
     * detailed explanation on top of the [AstronomicalCalendar] documentation.
     * @see .getTemporalHour
     * @see .getSeaLevelSunrise
     * @see .getSeaLevelSunset
     * @see ComplexZmanimCalendar.getShaahZmanisBaalHatanya
     */
    val shaahZmanisGra: Long
        get() = getTemporalHour(elevationAdjustedSunrise, elevationAdjustedSunset)

    /**
     * A method that returns a *shaah zmanis* (temporal hour) according to the opinion of the *[Magen Avraham (MGA)](https://en.wikipedia.org/wiki/Avraham_Gombinern)* based on a 72 minutes *alos*
     * and *tzais*. This calculation divides the day that runs from dawn to dusk (for *sof zman krias shema* and
     * *tfila*). Dawn for this calculation is 72 minutes before [sunrise][.getSunrise] or [ sea level sunrise][.getSeaLevelSunrise] (depending on the [.isUseElevation] elevation setting) and dusk is 72 minutes after [ ][.getSunset] or [sea level sunset][.getSeaLevelSunset] (depending on the [.isUseElevation] elevation
     * setting). This day is split into 12 equal parts with each part being a *shaah zmanis*. Alternate methods of calculating
     * a *shaah zmanis* according to the Magen Avraham (MGA) are available in the subclass [ComplexZmanimCalendar].
     *
     * @return the `long` millisecond length of a *shaah zmanis*. If the calculation can't be computed
     * such as in the Arctic Circle where there is at least one day a year where the sun does not rise, and one
     * where it does not set, [Long.MIN_VALUE] will be returned. See detailed explanation on top of the
     * [AstronomicalCalendar] documentation.
     */
    val shaahZmanisMGA: Long
        get() = getTemporalHour(alos72, tzais72)

    /**
     * Default constructor will set a default [GeoLocation.GeoLocation], a default
     * [AstronomicalCalculator][AstronomicalCalculator.getDefault] and default the calendar to the current date.
     *
     * @see AstronomicalCalendar.AstronomicalCalendar
     */
    constructor() : super() {}

    /**
     * A constructor that takes a [GeoLocation] as a parameter.
     *
     * @param location
     * the location
     */
    constructor(location: GeoLocation?) : super(location!!) {}

    /**
     * This is a utility method to determine if the current Date (date-time) passed in has a *melacha* (work) prohibition.
     * Since there are many opinions on the time of *tzais*, the *tzais* for the current day has to be passed to this
     * class. Sunset is the classes current day's [elevation adjusted sunset][.getElevationAdjustedSunset] that observes the
     * [.isUseElevation] settings. The [JewishCalendar.getInIsrael] will be set by the inIsrael parameter.
     *
     * @param currentTime the current time
     * @param tzais the time of tzais
     * @param inIsrael whether to use Israel holiday scheme or not
     *
     * @return true if *melacha* is prohibited or false if it is not.
     *
     * @see JewishCalendar.isAssurBemelacha
     * @see JewishCalendar.hasCandleLighting
     * @see JewishCalendar.setInIsrael
     */
    fun isAssurBemlacha(currentTime: Date, tzais: Date?, inIsrael: Boolean): Boolean {
        val jewishCalendar = JewishCalendar()
        jewishCalendar.setGregorianDate(
            getCalendar()!![Calendar.YEAR], getCalendar()!![Calendar.MONTH],
            getCalendar()!![Calendar.DAY_OF_MONTH]
        )
        jewishCalendar.inIsrael = inIsrael
        if (jewishCalendar.hasCandleLighting() && currentTime.compareTo(elevationAdjustedSunset) >= 0) { //erev shabbos, YT or YT sheni and after shkiah
            return true
        }
        return if (jewishCalendar.isAssurBemelacha && currentTime.compareTo(tzais) <= 0) { //is shabbos or YT and it is before tzais
            true
        } else false
    }

    /**
     * A generic utility method for calculating any *shaah zmanis* (temporal hour) based *zman* with the
     * day defined as the start and end of day (or night) and the number of *shaahos zmaniyos* passed to the
     * method. This simplifies the code in other methods such as [.getPlagHamincha] and cuts down on
     * code replication. As an example, passing [sunrise][.getSunrise] and [sunset][.getSunset] or [ ][.getSeaLevelSunrise] and [sea level sunset][.getSeaLevelSunset] (depending on the
     * [.isUseElevation] elevation setting) and 10.75 hours to this method will return *plag mincha*
     * according to the opinion of the *[GRA](https://en.wikipedia.org/wiki/Vilna_Gaon)*.
     *
     * @param startOfDay
     * the start of day for calculating the *zman*. This can be sunrise or any *alos* passed
     * to this method.
     * @param endOfDay
     * the end of day for calculating the *zman*. This can be sunset or any *tzais* passed to
     * this method.
     * @param hours
     * the number of *shaahos zmaniyos* (temporal hours) to offset from the start of day
     * @return the `Date` of the time of *zman* with the *shaahos zmaniyos* (temporal hours)
     * in the day offset from the start of day passed to this method. If the calculation can't be computed such
     * as in the Arctic Circle where there is at least one day a year where the sun does not rise, and one
     * where it does not set, a null will be  returned. See detailed explanation on top of the [         ] documentation.
     */
    fun getShaahZmanisBasedZman(startOfDay: Date?, endOfDay: Date?, hours: Double): Date? {
        val shaahZmanis = getTemporalHour(startOfDay, endOfDay)
        return getTimeOffset(startOfDay, (shaahZmanis * hours).toLong())
    }

    companion object {
        /**
         * The zenith of 16.1 below geometric zenith (90). This calculation is used for determining *alos*
         * (dawn) and *tzais* (nightfall) in some opinions. It is based on the calculation that the time between dawn
         * and sunrise (and sunset to nightfall) is 72 minutes, the time that is takes to walk 4 *mil* at 18 minutes
         * a mil (*[Rambam](https://en.wikipedia.org/wiki/Maimonides)* and others). The sun's position at
         * 72 minutes before [sunrise][.getSunrise] in Jerusalem [around the equinox / equilux](https://kosherjava.com/2022/01/12/equinox-vs-equilux-zmanim-calculations/) is
         * 16.1 below [geometric zenith][.GEOMETRIC_ZENITH].
         *
         * @see .getAlosHashachar
         * @see ComplexZmanimCalendar.getAlos16Point1Degrees
         * @see ComplexZmanimCalendar.getTzais16Point1Degrees
         * @see ComplexZmanimCalendar.getSofZmanShmaMGA16Point1Degrees
         * @see ComplexZmanimCalendar.getSofZmanTfilaMGA16Point1Degrees
         * @see ComplexZmanimCalendar.getMinchaGedola16Point1Degrees
         * @see ComplexZmanimCalendar.getMinchaKetana16Point1Degrees
         * @see ComplexZmanimCalendar.getPlagHamincha16Point1Degrees
         * @see ComplexZmanimCalendar.getPlagAlos16Point1ToTzaisGeonim7Point083Degrees
         * @see ComplexZmanimCalendar.getSofZmanShmaAlos16Point1ToSunset
         */
        protected const val ZENITH_16_POINT_1 = GEOMETRIC_ZENITH + 16.1

        /**
         * The zenith of 8.5 below geometric zenith (90). This calculation is used for calculating *alos*
         * (dawn) and *tzais* (nightfall) in some opinions. This calculation is based on the position of the sun 36
         * minutes after [sunset][.getSunset] in Jerusalem [around the equinox / equilux](https://kosherjava.com/2022/01/12/equinox-vs-equilux-zmanim-calculations/), which
         * is 8.5 below [geometric zenith][.GEOMETRIC_ZENITH]. The *[Ohr Meir](https://www.worldcat.org/oclc/29283612)* considers this the time that 3 small stars are visible,
         * which is later than the required 3 medium stars.
         *
         * @see .getTzais
         * @see ComplexZmanimCalendar.getTzaisGeonim8Point5Degrees
         */
        protected const val ZENITH_8_POINT_5 = GEOMETRIC_ZENITH + 8.5
    }
}
