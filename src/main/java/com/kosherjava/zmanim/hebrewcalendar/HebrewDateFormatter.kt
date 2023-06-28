/*
 * Zmanim Java API
 * Copyright (C) 2011 - 2023 Eliyahu Hershfeld
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

import com.kosherjava.zmanim.hebrewcalendar.Daf.Companion.yerushlmiMasechtos
import com.kosherjava.zmanim.hebrewcalendar.Daf.Companion.yerushlmiMasechtosTransliterated
import com.kosherjava.zmanim.hebrewcalendar.JewishCalendar.Parsha
import java.text.SimpleDateFormat
import java.util.EnumMap

/**
 * The HebrewDateFormatter class formats a [JewishDate].
 *
 * The class formats Jewish dates, numbers, *Daf Yomi* (*Bavli* and *Yerushalmi*), the *Omer*,
 * *Parshas Hashavua* (including the special *parshiyos* of *Shekalim*, *Zachor*, *Parah*
 * and *Hachodesh*), Yomim Tovim and the Molad (experimental) in Hebrew or Latin chars, and has various settings.
 * Sample full date output includes (using various options):
 *
 *  * 21 Shevat, 5729
 *  * &#x5DB;&#x5D0; &#x5E9;&#x5D1;&#x5D8; &#x5EA;&#x5E9;&#x5DB;&#x5D8;
 *  * &#x5DB;&#x5F4;&#x5D0; &#x5E9;&#x5D1;&#x5D8; &#x5D4;&#x5F3;&#x5EA;&#x5E9;&#x5DB;&#x5F4;&#x5D8;
 *  * &#x5DB;&#x5F4;&#x5D0; &#x5E9;&#x5D1;&#x5D8; &#x5EA;&#x5E9;&#x5F4;&#x05E4; or
 * &#x5DB;&#x5F4;&#x5D0; &#x5E9;&#x5D1;&#x5D8; &#x5EA;&#x5E9;&#x5F4;&#x05E3;
 *  * &#x05DB;&#x05F3; &#x05E9;&#x05D1;&#x05D8; &#x05D5;&#x05F3; &#x05D0;&#x05DC;&#x05E4;&#x05D9;&#x05DD;
 *
 *
 * @see JewishDate
 *
 * @see JewishCalendar
 *
 *
 * @author  Eliyahu Hershfeld 2011 - 2023
 */
class HebrewDateFormatter {
    /**
     * Returns if the formatter is set to use Hebrew formatting in the various formatting methods.
     *
     * @return the hebrewFormat
     * @see .setHebrewFormat
     * @see .format
     * @see .formatDayOfWeek
     * @see .formatMonth
     * @see .formatOmer
     * @see .formatYomTov
     */
    /**
     * Sets the formatter to format in Hebrew in the various formatting methods.
     *
     * @param hebrewFormat
     * the hebrewFormat to set
     * @see .isHebrewFormat
     * @see .format
     * @see .formatDayOfWeek
     * @see .formatMonth
     * @see .formatOmer
     * @see .formatYomTov
     */
    /**
     * See [.isHebrewFormat] and [.setHebrewFormat].
     */
    var isHebrewFormat = false
    /**
     * Returns whether the class is set to use the thousands digit when formatting. When formatting a Hebrew Year,
     * traditionally the thousands digit is omitted and output for a year such as 5729 (1969 Gregorian) would be
     * calculated for 729 and format as &#x5EA;&#x5E9;&#x5DB;&#x5F4;&#x5D8;. When set to true the long format year such
     * as &#x5D4;&#x5F3; &#x5EA;&#x5E9;&#x5DB;&#x5F4;&#x5D8; for 5729/1969 is returned.
     *
     * @return true if set to use the thousands digit when formatting Hebrew dates and numbers.
     */
    /**
     * When formatting a Hebrew Year, traditionally the thousands digit is omitted and output for a year such as 5729
     * (1969 Gregorian) would be calculated for 729 and format as &#x5EA;&#x5E9;&#x5DB;&#x5F4;&#x5D8;. This method
     * allows setting this to true to return the long format year such as &#x5D4;&#x5F3;
     * &#x5EA;&#x5E9;&#x5DB;&#x5F4;&#x5D8; for 5729/1969.
     *
     * @param useLongHebrewYears
     * Set this to true to use the long formatting
     */
    /**
     * See [.isUseLongHebrewYears] and [.setUseLongHebrewYears].
     */
    var isUseLongHebrewYears = false
    /**
     * Returns whether the class is set to use the Geresh &#x5F3; and Gershayim &#x5F4; in formatting Hebrew dates and
     * numbers. When true and output would look like &#x5DB;&#x5F4;&#x5D0; &#x5E9;&#x5D1;&#x5D8; &#x5EA;&#x5E9;&#x5F4;&#x5DB;
     * (or &#x5DB;&#x5F4;&#x5D0; &#x5E9;&#x5D1;&#x5D8; &#x5EA;&#x5E9;&#x5F4;&#x5DA;). When set to false, this output
     * would display as &#x5DB;&#x5D0; &#x5E9;&#x5D1;&#x5D8; &#x5EA;&#x5E9;&#x5DB;.
     *
     * @return true if set to use the Geresh &#x5F3; and Gershayim &#x5F4; in formatting Hebrew dates and numbers.
     */
    /**
     * Sets whether to use the Geresh &#x5F3; and Gershayim &#x5F4; in formatting Hebrew dates and numbers. The default
     * value is true and output would look like &#x5DB;&#x5F4;&#x5D0; &#x5E9;&#x5D1;&#x5D8; &#x5EA;&#x5E9;&#x5F4;&#x5DB;
     * (or &#x5DB;&#x5F4;&#x5D0; &#x5E9;&#x5D1;&#x5D8; &#x5EA;&#x5E9;&#x5F4;&#x5DA;). When set to false, this output would
     * display as &#x5DB;&#x5D0; &#x5E9;&#x5D1;&#x5D8; &#x5EA;&#x5E9;&#x5DB; (or
     * &#x5DB;&#x5D0; &#x5E9;&#x5D1;&#x5D8; &#x5EA;&#x5E9;&#x5DA;). Single digit days or month or years such as &#x05DB;&#x05F3;
     * &#x05E9;&#x05D1;&#x05D8; &#x05D5;&#x05F3; &#x05D0;&#x05DC;&#x05E4;&#x05D9;&#x05DD; show the use of the Geresh.
     *
     * @param useGershGershayim
     * set to false to omit the Geresh &#x5F3; and Gershayim &#x5F4; in formatting
     */
    /**
     * See [.isUseGershGershayim] and [.setUseGershGershayim].
     */
    var isUseGershGershayim = true
    /**
     * Returns if the [.formatDayOfWeek] will use the long format such as
     * &#x05E8;&#x05D0;&#x05E9;&#x05D5;&#x05DF; or short such as &#x05D0; when formatting the day of week in
     * [Hebrew][.isHebrewFormat].
     *
     * @return the longWeekFormat
     * @see .setLongWeekFormat
     * @see .formatDayOfWeek
     */
    /**
     * Setting to control if the [.formatDayOfWeek] will use the long format such as
     * &#x05E8;&#x05D0;&#x05E9;&#x05D5;&#x05DF; or short such as &#x05D0; when formatting the day of week in
     * [Hebrew][.isHebrewFormat].
     *
     * @param longWeekFormat
     * the longWeekFormat to set
     */
    /**
     * See [.isLongWeekFormat] and [.setLongWeekFormat].
     */
    var isLongWeekFormat = true
        set(longWeekFormat) {
            field = longWeekFormat
            weekFormat = if (longWeekFormat) {
                SimpleDateFormat("EEEE")
            } else {
                SimpleDateFormat("EEE")
            }
        }
    /**
     * Returns whether the class is set to use the &#x05DE;&#x05E0;&#x05E6;&#x05E4;&#x05F4;&#x05DA; letters when
     * formatting years ending in 20, 40, 50, 80 and 90 to produce &#x05EA;&#x05E9;&#x05F4;&#x05E4; if false or
     * or &#x05EA;&#x05E9;&#x05F4;&#x05E3; if true. Traditionally non-final form letters are used, so the year
     * 5780 would be formatted as &#x05EA;&#x05E9;&#x05F4;&#x05E4; if the default false is used here. If this returns
     * true, the format &#x05EA;&#x05E9;&#x05F4;&#x05E3; would be used.
     *
     * @return true if set to use final form letters when formatting Hebrew years. The default value is false.
     */
    /**
     * When formatting a Hebrew Year, traditionally years ending in 20, 40, 50, 80 and 90 are formatted using non-final
     * form letters for example &#x05EA;&#x05E9;&#x05F4;&#x05E4; for the year 5780. Setting this to true (the default
     * is false) will use the final form letters for &#x05DE;&#x05E0;&#x05E6;&#x05E4;&#x05F4;&#x05DA; and will format
     * the year 5780 as &#x05EA;&#x05E9;&#x05F4;&#x05E3;.
     *
     * @param useFinalFormLetters
     * Set this to true to use final form letters when formatting Hebrew years.
     */
    /**
     * See [.isUseFinalFormLetters] and [.setUseFinalFormLetters].
     */
    var isUseFinalFormLetters = false

    /**
     * The internal DateFormat.&nbsp; See [.isLongWeekFormat] and [.setLongWeekFormat].
     */
    private var weekFormat: SimpleDateFormat? = null
    /**
     * Retruns the list of transliterated parshiyos used by this formatter.
     *
     * @return the list of transliterated Parshios
     */
    /**
     * Setter method to allow overriding of the default list of parshiyos transliterated into into Latin chars. The
     * default uses Ashkenazi American English transliteration.
     *
     * @param transliteratedParshaMap
     * the transliterated Parshios as an EnumMap to set
     * @see .getTransliteratedParshiosList
     */
    /**
     * List of transliterated parshiyos using the default *Ashkenazi* pronunciation.&nbsp; The formatParsha method
     * uses this for transliterated *parsha* formatting.&nbsp; This list can be overridden (for *Sephardi*
     * English transliteration for example) by setting the [.setTransliteratedParshiosList].&nbsp; The list
     * includes double and special *parshiyos* is set as "*Bereshis, Noach, Lech Lecha, Vayera, Chayei Sara,
     * Toldos, Vayetzei, Vayishlach, Vayeshev, Miketz, Vayigash, Vayechi, Shemos, Vaera, Bo, Beshalach, Yisro, Mishpatim,
     * Terumah, Tetzaveh, Ki Sisa, Vayakhel, Pekudei, Vayikra, Tzav, Shmini, Tazria, Metzora, Achrei Mos, Kedoshim, Emor,
     * Behar, Bechukosai, Bamidbar, Nasso, Beha'aloscha, Sh'lach, Korach, Chukas, Balak, Pinchas, Matos, Masei, Devarim,
     * Vaeschanan, Eikev, Re'eh, Shoftim, Ki Seitzei, Ki Savo, Nitzavim, Vayeilech, Ha'Azinu, Vezos Habracha,
     * Vayakhel Pekudei, Tazria Metzora, Achrei Mos Kedoshim, Behar Bechukosai, Chukas Balak, Matos Masei, Nitzavim Vayeilech,
     * Shekalim, Zachor, Parah, Hachodesh,Shuva, Shira, Hagadol, Chazon, Nachamu*".
     *
     * @see .formatParsha
     */
    var transliteratedParshiosList: EnumMap<Parsha, String>

    /**
     * Unicode [EnumMap] of Hebrew *parshiyos*.&nbsp; The list includes double and special *parshiyos* and
     * contains `"&#x05D1;&#x05E8;&#x05D0;&#x05E9;&#x05D9;&#x05EA;, &#x05E0;&#x05D7;, &#x05DC;&#x05DA; &#x05DC;&#x05DA;,
     * &#x05D5;&#x05D9;&#x05E8;&#x05D0;, &#x05D7;&#x05D9;&#x05D9; &#x05E9;&#x05E8;&#x05D4;,
     * &#x05EA;&#x05D5;&#x05DC;&#x05D3;&#x05D5;&#x05EA;, &#x05D5;&#x05D9;&#x05E6;&#x05D0;, &#x05D5;&#x05D9;&#x05E9;&#x05DC;&#x05D7;,
     * &#x05D5;&#x05D9;&#x05E9;&#x05D1;, &#x05DE;&#x05E7;&#x05E5;, &#x05D5;&#x05D9;&#x05D2;&#x05E9;, &#x05D5;&#x05D9;&#x05D7;&#x05D9;,
     * &#x05E9;&#x05DE;&#x05D5;&#x05EA;, &#x05D5;&#x05D0;&#x05E8;&#x05D0;, &#x05D1;&#x05D0;, &#x05D1;&#x05E9;&#x05DC;&#x05D7;,
     * &#x05D9;&#x05EA;&#x05E8;&#x05D5;, &#x05DE;&#x05E9;&#x05E4;&#x05D8;&#x05D9;&#x05DD;, &#x05EA;&#x05E8;&#x05D5;&#x05DE;&#x05D4;,
     * &#x05EA;&#x05E6;&#x05D5;&#x05D4;, &#x05DB;&#x05D9; &#x05EA;&#x05E9;&#x05D0;, &#x05D5;&#x05D9;&#x05E7;&#x05D4;&#x05DC;,
     * &#x05E4;&#x05E7;&#x05D5;&#x05D3;&#x05D9;, &#x05D5;&#x05D9;&#x05E7;&#x05E8;&#x05D0;, &#x05E6;&#x05D5;,
     * &#x05E9;&#x05DE;&#x05D9;&#x05E0;&#x05D9;, &#x05EA;&#x05D6;&#x05E8;&#x05D9;&#x05E2;, &#x05DE;&#x05E6;&#x05E8;&#x05E2;,
     * &#x05D0;&#x05D7;&#x05E8;&#x05D9; &#x05DE;&#x05D5;&#x05EA;, &#x05E7;&#x05D3;&#x05D5;&#x05E9;&#x05D9;&#x05DD;,
     * &#x05D0;&#x05DE;&#x05D5;&#x05E8;, &#x05D1;&#x05D4;&#x05E8;, &#x05D1;&#x05D7;&#x05E7;&#x05EA;&#x05D9;,
     * &#x05D1;&#x05DE;&#x05D3;&#x05D1;&#x05E8;, &#x05E0;&#x05E9;&#x05D0;, &#x05D1;&#x05D4;&#x05E2;&#x05DC;&#x05EA;&#x05DA;,
     * &#x05E9;&#x05DC;&#x05D7; &#x05DC;&#x05DA;, &#x05E7;&#x05E8;&#x05D7;, &#x05D7;&#x05D5;&#x05E7;&#x05EA;, &#x05D1;&#x05DC;&#x05E7;,
     * &#x05E4;&#x05D9;&#x05E0;&#x05D7;&#x05E1;, &#x05DE;&#x05D8;&#x05D5;&#x05EA;, &#x05DE;&#x05E1;&#x05E2;&#x05D9;,
     * &#x05D3;&#x05D1;&#x05E8;&#x05D9;&#x05DD;, &#x05D5;&#x05D0;&#x05EA;&#x05D7;&#x05E0;&#x05DF;, &#x05E2;&#x05E7;&#x05D1;,
     * &#x05E8;&#x05D0;&#x05D4;, &#x05E9;&#x05D5;&#x05E4;&#x05D8;&#x05D9;&#x05DD;, &#x05DB;&#x05D9; &#x05EA;&#x05E6;&#x05D0;,
     * &#x05DB;&#x05D9; &#x05EA;&#x05D1;&#x05D5;&#x05D0;, &#x05E0;&#x05E6;&#x05D1;&#x05D9;&#x05DD;, &#x05D5;&#x05D9;&#x05DC;&#x05DA;,
     * &#x05D4;&#x05D0;&#x05D6;&#x05D9;&#x05E0;&#x05D5;, &#x05D5;&#x05D6;&#x05D0;&#x05EA; &#x05D4;&#x05D1;&#x05E8;&#x05DB;&#x05D4;,
     * &#x05D5;&#x05D9;&#x05E7;&#x05D4;&#x05DC; &#x05E4;&#x05E7;&#x05D5;&#x05D3;&#x05D9;, &#x05EA;&#x05D6;&#x05E8;&#x05D9;&#x05E2;
     * &#x05DE;&#x05E6;&#x05E8;&#x05E2;, &#x05D0;&#x05D7;&#x05E8;&#x05D9; &#x05DE;&#x05D5;&#x05EA;
     * &#x05E7;&#x05D3;&#x05D5;&#x05E9;&#x05D9;&#x05DD;, &#x05D1;&#x05D4;&#x05E8; &#x05D1;&#x05D7;&#x05E7;&#x05EA;&#x05D9;,
     * &#x05D7;&#x05D5;&#x05E7;&#x05EA; &#x05D1;&#x05DC;&#x05E7;, &#x05DE;&#x05D8;&#x05D5;&#x05EA; &#x05DE;&#x05E1;&#x05E2;&#x05D9;,
     * &#x05E0;&#x05E6;&#x05D1;&#x05D9;&#x05DD; &#x05D5;&#x05D9;&#x05DC;&#x05DA;, &#x05E9;&#x05E7;&#x05DC;&#x05D9;&#x05DD;,
     * &#x05D6;&#x05DB;&#x05D5;&#x05E8;, &#x05E4;&#x05E8;&#x05D4;, &#x05D4;&#x05D7;&#x05D3;&#x05E9;,
     * &#x05E9;&#x05D5;&#x05D1;&#x05D4;,&#x05E9;&#x05D9;&#x05E8;&#x05D4;,&#x05D4;&#x05D2;&#x05D3;&#x05D5;&#x05DC;,
     * &#x05D7;&#x05D6;&#x05D5;&#x05DF;,&#x05E0;&#x05D7;&#x05DE;&#x05D5;"`
     */
    private val hebrewParshaMap: EnumMap<Parsha, String>
    /**
     * Returns the list of months transliterated into Latin chars. The default list of months uses Ashkenazi
     * pronunciation in typical American English spelling. This list has a length of 14 with 3 variations for Adar -
     * "Adar", "Adar II", "Adar I"
     *
     * @return the list of months beginning in Nissan and ending in in "Adar", "Adar II", "Adar I". The default list is
     * currently ["Nissan", "Iyar", "Sivan", "Tammuz", "Av", "Elul", "Tishrei", "Cheshvan", "Kislev", "Teves",
     * "Shevat", "Adar", "Adar II", "Adar I"].
     * @see .setTransliteratedMonthList
     */
    /**
     * Setter method to allow overriding of the default list of months transliterated into into Latin chars. The default
     * uses Ashkenazi American English transliteration.
     *
     * @param transliteratedMonths
     * an array of 14 month names that defaults to ["Nissan", "Iyar", "Sivan", "Tamuz", "Av", "Elul", "Tishrei",
     * "Heshvan", "Kislev", "Tevet", "Shevat", "Adar", "Adar II", "Adar I"].
     * @see .getTransliteratedMonthList
     */
    /**
     * Transliterated month names.&nbsp; Defaults to ["Nissan", "Iyar", "Sivan", "Tammuz", "Av", "Elul", "Tishrei", "Cheshvan",
     * "Kislev", "Teves", "Shevat", "Adar", "Adar II", "Adar I" ].
     * @see .getTransliteratedMonthList
     * @see .setTransliteratedMonthList
     */
    var transliteratedMonthList = arrayOf(
        "Nissan", "Iyar", "Sivan", "Tammuz", "Av", "Elul", "Tishrei", "Cheshvan",
        "Kislev", "Teves", "Shevat", "Adar", "Adar II", "Adar I"
    )
    /**
     * Returns the Hebrew Omer prefix.&nbsp; By default it is the letter &#x05D1; producing
     * &#x05D1;&#x05E2;&#x05D5;&#x05DE;&#x05E8;, but it can be set to &#x05DC; to produce
     * &#x05DC;&#x05E2;&#x05D5;&#x05DE;&#x05E8; (or any other prefix) using the [.setHebrewOmerPrefix].
     *
     * @return the hebrewOmerPrefix
     *
     * @see .hebrewOmerPrefix
     *
     * @see .setHebrewOmerPrefix
     * @see .formatOmer
     */
    /**
     * Method to set the Hebrew Omer prefix.&nbsp; By default it is the letter &#x05D1; producing
     * &#x05D1;&#x05E2;&#x05D5;&#x05DE;&#x05E8;, but it can be set to &#x05DC; to produce
     * &#x05DC;&#x05E2;&#x05D5;&#x05DE;&#x05E8; (or any other prefix)
     * @param hebrewOmerPrefix
     * the hebrewOmerPrefix to set. You can use the Unicode &#92;u05DC to set it to &#x5DC;.
     * @see .hebrewOmerPrefix
     *
     * @see .getHebrewOmerPrefix
     * @see .formatOmer
     */
    /**
     * The Hebrew omer prefix charachter. It defaults to &#x05D1; producing &#x05D1;&#x05E2;&#x05D5;&#x05DE;&#x05E8;,
     * but can be set to &#x05DC; to produce &#x05DC;&#x05E2;&#x05D5;&#x05DE;&#x05E8; (or any other prefix).
     * @see .getHebrewOmerPrefix
     * @see .setHebrewOmerPrefix
     */
    var hebrewOmerPrefix = "\u05D1"
    /**
     * Returns the day of Shabbos transliterated into Latin chars. The default uses Ashkenazi pronunciation "Shabbos".
     * This can be overwritten using the [.setTransliteratedShabbosDayOfWeek]
     *
     * @return the transliteratedShabbos. The default list of months uses Ashkenazi pronunciation "Shabbos".
     * @see .setTransliteratedShabbosDayOfWeek
     * @see .formatDayOfWeek
     */
    /**
     * Setter to override the default transliterated name of "Shabbos" to alternate spelling such as "Shabbat" used by
     * the [.formatDayOfWeek]
     *
     * @param transliteratedShabbos
     * the transliteratedShabbos to set
     *
     * @see .getTransliteratedShabbosDayOfWeek
     * @see .formatDayOfWeek
     */
    /**
     * The default value for formatting Shabbos (Saturday).&nbsp; Defaults to Shabbos.
     * @see .getTransliteratedShabbosDayOfWeek
     * @see .setTransliteratedShabbosDayOfWeek
     */
    var transliteratedShabbosDayOfWeek = "Shabbos"
    /**
     * Returns the list of holidays transliterated into Latin chars. This is used by the
     * [.formatYomTov] when formatting the Yom Tov String. The default list of months uses
     * Ashkenazi pronunciation in typical American English spelling.
     *
     * @return the list of transliterated holidays. The default list is currently ["Erev Pesach", "Pesach",
     * "Chol Hamoed Pesach", "Pesach Sheni", "Erev Shavuos", "Shavuos", "Seventeenth of Tammuz", "Tishah B'Av",
     * "Tu B'Av", "Erev Rosh Hashana", "Rosh Hashana", "Fast of Gedalyah", "Erev Yom Kippur", "Yom Kippur",
     * "Erev Succos", "Succos", "Chol Hamoed Succos", "Hoshana Rabbah", "Shemini Atzeres", "Simchas Torah",
     * "Erev Chanukah", "Chanukah", "Tenth of Teves", "Tu B'Shvat", "Fast of Esther", "Purim", "Shushan Purim",
     * "Purim Katan", "Rosh Chodesh", "Yom HaShoah", "Yom Hazikaron", "Yom Ha'atzmaut", "Yom Yerushalayim",
     * "Lag B'Omer","Shushan Purim Katan","Isru Chag"].
     *
     * @see .setTransliteratedMonthList
     * @see .formatYomTov
     * @see .isHebrewFormat
     */
    /**
     * Sets the list of holidays transliterated into Latin chars. This is used by the
     * [.formatYomTov] when formatting the Yom Tov String.
     *
     * @param transliteratedHolidays
     * the transliteratedHolidays to set. Ensure that the sequence exactly matches the list returned by the
     * default
     */
    /**
     * See [.getTransliteratedHolidayList] and [.setTransliteratedHolidayList].
     */
    var transliteratedHolidayList = arrayOf(
        "Erev Pesach", "Pesach", "Chol Hamoed Pesach", "Pesach Sheni",
        "Erev Shavuos", "Shavuos", "Seventeenth of Tammuz", "Tishah B'Av", "Tu B'Av", "Erev Rosh Hashana",
        "Rosh Hashana", "Fast of Gedalyah", "Erev Yom Kippur", "Yom Kippur", "Erev Succos", "Succos",
        "Chol Hamoed Succos", "Hoshana Rabbah", "Shemini Atzeres", "Simchas Torah", "Erev Chanukah", "Chanukah",
        "Tenth of Teves", "Tu B'Shvat", "Fast of Esther", "Purim", "Shushan Purim", "Purim Katan", "Rosh Chodesh",
        "Yom HaShoah", "Yom Hazikaron", "Yom Ha'atzmaut", "Yom Yerushalayim", "Lag B'Omer", "Shushan Purim Katan",
        "Isru Chag"
    )

    /**
     * Hebrew holiday array in the following format.<br></br>`["&#x05E2;&#x05E8;&#x05D1; &#x05E4;&#x05E1;&#x05D7;",
     * "&#x05E4;&#x05E1;&#x05D7;", "&#x05D7;&#x05D5;&#x05DC; &#x05D4;&#x05DE;&#x05D5;&#x05E2;&#x05D3;
     * &#x05E4;&#x05E1;&#x05D7;", "&#x05E4;&#x05E1;&#x05D7; &#x05E9;&#x05E0;&#x05D9;", "&#x05E2;&#x05E8;&#x05D1;
     * &#x05E9;&#x05D1;&#x05D5;&#x05E2;&#x05D5;&#x05EA;", "&#x05E9;&#x05D1;&#x05D5;&#x05E2;&#x05D5;&#x05EA;",
     * "&#x05E9;&#x05D1;&#x05E2;&#x05D4; &#x05E2;&#x05E9;&#x05E8; &#x05D1;&#x05EA;&#x05DE;&#x05D5;&#x05D6;",
     * "&#x05EA;&#x05E9;&#x05E2;&#x05D4; &#x05D1;&#x05D0;&#x05D1;",
     * "&#x05D8;&#x05F4;&#x05D5; &#x05D1;&#x05D0;&#x05D1;",
     * "&#x05E2;&#x05E8;&#x05D1; &#x05E8;&#x05D0;&#x05E9; &#x05D4;&#x05E9;&#x05E0;&#x05D4;",
     * "&#x05E8;&#x05D0;&#x05E9; &#x05D4;&#x05E9;&#x05E0;&#x05D4;",
     * "&#x05E6;&#x05D5;&#x05DD; &#x05D2;&#x05D3;&#x05DC;&#x05D9;&#x05D4;",
     * "&#x05E2;&#x05E8;&#x05D1; &#x05D9;&#x05D5;&#x05DD; &#x05DB;&#x05D9;&#x05E4;&#x05D5;&#x05E8;",
     * "&#x05D9;&#x05D5;&#x05DD; &#x05DB;&#x05D9;&#x05E4;&#x05D5;&#x05E8;",
     * "&#x05E2;&#x05E8;&#x05D1; &#x05E1;&#x05D5;&#x05DB;&#x05D5;&#x05EA;",
     * "&#x05E1;&#x05D5;&#x05DB;&#x05D5;&#x05EA;",
     * "&#x05D7;&#x05D5;&#x05DC; &#x05D4;&#x05DE;&#x05D5;&#x05E2;&#x05D3; &#x05E1;&#x05D5;&#x05DB;&#x05D5;&#x05EA;",
     * "&#x05D4;&#x05D5;&#x05E9;&#x05E2;&#x05E0;&#x05D0; &#x05E8;&#x05D1;&#x05D4;",
     * "&#x05E9;&#x05DE;&#x05D9;&#x05E0;&#x05D9; &#x05E2;&#x05E6;&#x05E8;&#x05EA;",
     * "&#x05E9;&#x05DE;&#x05D7;&#x05EA; &#x05EA;&#x05D5;&#x05E8;&#x05D4;",
     * "&#x05E2;&#x05E8;&#x05D1; &#x05D7;&#x05E0;&#x05D5;&#x05DB;&#x05D4;",
     * "&#x05D7;&#x05E0;&#x05D5;&#x05DB;&#x05D4;", "&#x05E2;&#x05E9;&#x05E8;&#x05D4; &#x05D1;&#x05D8;&#x05D1;&#x05EA;",
     * "&#x05D8;&#x05F4;&#x05D5; &#x05D1;&#x05E9;&#x05D1;&#x05D8;",
     * "&#x05EA;&#x05E2;&#x05E0;&#x05D9;&#x05EA; &#x05D0;&#x05E1;&#x05EA;&#x05E8;",
     * "&#x05E4;&#x05D5;&#x05E8;&#x05D9;&#x05DD;",
     * "&#x05E4;&#x05D5;&#x05E8;&#x05D9;&#x05DD; &#x05E9;&#x05D5;&#x05E9;&#x05DF;",
     * "&#x05E4;&#x05D5;&#x05E8;&#x05D9;&#x05DD; &#x05E7;&#x05D8;&#x05DF;",
     * "&#x05E8;&#x05D0;&#x05E9; &#x05D7;&#x05D5;&#x05D3;&#x05E9;",
     * "&#x05D9;&#x05D5;&#x05DD; &#x05D4;&#x05E9;&#x05D5;&#x05D0;&#x05D4;",
     * "&#x05D9;&#x05D5;&#x05DD; &#x05D4;&#x05D6;&#x05D9;&#x05DB;&#x05E8;&#x05D5;&#x05DF;",
     * "&#x05D9;&#x05D5;&#x05DD; &#x05D4;&#x05E2;&#x05E6;&#x05DE;&#x05D0;&#x05D5;&#x05EA;",
     * "&#x05D9;&#x05D5;&#x05DD; &#x05D9;&#x05E8;&#x05D5;&#x05E9;&#x05DC;&#x05D9;&#x05DD;",
     * "&#x05DC;&#x05F4;&#x05D2; &#x05D1;&#x05E2;&#x05D5;&#x05DE;&#x05E8;",
     * "&#x05E4;&#x05D5;&#x05E8;&#x05D9;&#x05DD; &#x05E9;&#x05D5;&#x05E9;&#x05DF; &#x05E7;&#x05D8;&#x05DF;"]`
     */
    private val hebrewHolidays = arrayOf(
        "\u05E2\u05E8\u05D1 \u05E4\u05E1\u05D7", "\u05E4\u05E1\u05D7",
        "\u05D7\u05D5\u05DC \u05D4\u05DE\u05D5\u05E2\u05D3 \u05E4\u05E1\u05D7",
        "\u05E4\u05E1\u05D7 \u05E9\u05E0\u05D9", "\u05E2\u05E8\u05D1 \u05E9\u05D1\u05D5\u05E2\u05D5\u05EA",
        "\u05E9\u05D1\u05D5\u05E2\u05D5\u05EA",
        "\u05E9\u05D1\u05E2\u05D4 \u05E2\u05E9\u05E8 \u05D1\u05EA\u05DE\u05D5\u05D6",
        "\u05EA\u05E9\u05E2\u05D4 \u05D1\u05D0\u05D1", "\u05D8\u05F4\u05D5 \u05D1\u05D0\u05D1",
        "\u05E2\u05E8\u05D1 \u05E8\u05D0\u05E9 \u05D4\u05E9\u05E0\u05D4",
        "\u05E8\u05D0\u05E9 \u05D4\u05E9\u05E0\u05D4", "\u05E6\u05D5\u05DD \u05D2\u05D3\u05DC\u05D9\u05D4",
        "\u05E2\u05E8\u05D1 \u05D9\u05D5\u05DD \u05DB\u05D9\u05E4\u05D5\u05E8",
        "\u05D9\u05D5\u05DD \u05DB\u05D9\u05E4\u05D5\u05E8", "\u05E2\u05E8\u05D1 \u05E1\u05D5\u05DB\u05D5\u05EA",
        "\u05E1\u05D5\u05DB\u05D5\u05EA",
        "\u05D7\u05D5\u05DC \u05D4\u05DE\u05D5\u05E2\u05D3 \u05E1\u05D5\u05DB\u05D5\u05EA",
        "\u05D4\u05D5\u05E9\u05E2\u05E0\u05D0 \u05E8\u05D1\u05D4",
        "\u05E9\u05DE\u05D9\u05E0\u05D9 \u05E2\u05E6\u05E8\u05EA",
        "\u05E9\u05DE\u05D7\u05EA \u05EA\u05D5\u05E8\u05D4", "\u05E2\u05E8\u05D1 \u05D7\u05E0\u05D5\u05DB\u05D4",
        "\u05D7\u05E0\u05D5\u05DB\u05D4", "\u05E2\u05E9\u05E8\u05D4 \u05D1\u05D8\u05D1\u05EA",
        "\u05D8\u05F4\u05D5 \u05D1\u05E9\u05D1\u05D8", "\u05EA\u05E2\u05E0\u05D9\u05EA \u05D0\u05E1\u05EA\u05E8",
        "\u05E4\u05D5\u05E8\u05D9\u05DD", "\u05E4\u05D5\u05E8\u05D9\u05DD \u05E9\u05D5\u05E9\u05DF",
        "\u05E4\u05D5\u05E8\u05D9\u05DD \u05E7\u05D8\u05DF", "\u05E8\u05D0\u05E9 \u05D7\u05D5\u05D3\u05E9",
        "\u05D9\u05D5\u05DD \u05D4\u05E9\u05D5\u05D0\u05D4",
        "\u05D9\u05D5\u05DD \u05D4\u05D6\u05D9\u05DB\u05E8\u05D5\u05DF",
        "\u05D9\u05D5\u05DD \u05D4\u05E2\u05E6\u05DE\u05D0\u05D5\u05EA",
        "\u05D9\u05D5\u05DD \u05D9\u05E8\u05D5\u05E9\u05DC\u05D9\u05DD",
        "\u05DC\u05F4\u05D2 \u05D1\u05E2\u05D5\u05DE\u05E8",
        "\u05E4\u05D5\u05E8\u05D9\u05DD \u05E9\u05D5\u05E9\u05DF \u05E7\u05D8\u05DF",
        "\u05D0\u05E1\u05E8\u05D5 \u05D7\u05D2"
    )

    /**
     * Formats the Yom Tov (holiday) in Hebrew or transliterated Latin characters.
     *
     * @param jewishCalendar the JewishCalendar
     * @return the formatted holiday or an empty String if the day is not a holiday.
     * @see .isHebrewFormat
     */
    fun formatYomTov(jewishCalendar: JewishCalendar): String {
        val index = jewishCalendar.yomTovIndex
        if (index == JewishCalendar.CHANUKAH) {
            val dayOfChanukah = jewishCalendar.dayOfChanukah
            return if (isHebrewFormat) formatHebrewNumber(dayOfChanukah) + " " + hebrewHolidays[index] else transliteratedHolidayList[index] + " " + dayOfChanukah
        }
        return if (index == -1) "" else if (isHebrewFormat) hebrewHolidays[index] else transliteratedHolidayList[index]
    }

    /**
     * Formats a day as Rosh Chodesh in the format of in the format of &#x05E8;&#x05D0;&#x05E9;
     * &#x05D7;&#x05D5;&#x05D3;&#x05E9; &#x05E9;&#x05D1;&#x05D8; or Rosh Chodesh Shevat. If it
     * is not Rosh Chodesh, an empty `String` will be returned.
     * @param jewishCalendar the JewishCalendar
     * @return The formatted `String` in the format of &#x05E8;&#x05D0;&#x05E9;
     * &#x05D7;&#x05D5;&#x05D3;&#x05E9; &#x05E9;&#x05D1;&#x05D8; or Rosh Chodesh Shevat. If it
     * is not Rosh Chodesh, an empty `String` will be returned.
     */
    fun formatRoshChodesh(jewishCalendar: JewishCalendar): String {
        var jewishCalendar = jewishCalendar
        if (!jewishCalendar.isRoshChodesh) {
            return ""
        }
        var formattedRoshChodesh = ""
        var month = jewishCalendar.jewishMonth
        if (jewishCalendar.jewishDayOfMonth == 30) {
            if (month < JewishDate.ADAR || month == JewishDate.ADAR && jewishCalendar.isJewishLeapYear) {
                month++
            } else { // roll to Nissan
                month = JewishDate.NISSAN
            }
        }

        // This method is only about formatting, so we shouldn't make any changes to the params passed in...
        jewishCalendar = jewishCalendar.clone() as JewishCalendar
        jewishCalendar.jewishMonth = month
        formattedRoshChodesh =
            if (isHebrewFormat) hebrewHolidays[JewishCalendar.ROSH_CHODESH] else transliteratedHolidayList[JewishCalendar.ROSH_CHODESH]
        formattedRoshChodesh += " " + formatMonth(jewishCalendar)
        return formattedRoshChodesh
    }

    /**
     * Default constructor sets the [EnumMap]s of Hebrew and default transliterated parshiyos.
     */
    init {
        transliteratedParshiosList = EnumMap(Parsha::class.java)
        transliteratedParshiosList[Parsha.NONE] = ""
        transliteratedParshiosList[Parsha.BERESHIS] = "Bereshis"
        transliteratedParshiosList[Parsha.NOACH] = "Noach"
        transliteratedParshiosList[Parsha.LECH_LECHA] = "Lech Lecha"
        transliteratedParshiosList[Parsha.VAYERA] = "Vayera"
        transliteratedParshiosList[Parsha.CHAYEI_SARA] = "Chayei Sara"
        transliteratedParshiosList[Parsha.TOLDOS] = "Toldos"
        transliteratedParshiosList[Parsha.VAYETZEI] = "Vayetzei"
        transliteratedParshiosList[Parsha.VAYISHLACH] = "Vayishlach"
        transliteratedParshiosList[Parsha.VAYESHEV] = "Vayeshev"
        transliteratedParshiosList[Parsha.MIKETZ] = "Miketz"
        transliteratedParshiosList[Parsha.VAYIGASH] = "Vayigash"
        transliteratedParshiosList[Parsha.VAYECHI] = "Vayechi"
        transliteratedParshiosList[Parsha.SHEMOS] = "Shemos"
        transliteratedParshiosList[Parsha.VAERA] = "Vaera"
        transliteratedParshiosList[Parsha.BO] = "Bo"
        transliteratedParshiosList[Parsha.BESHALACH] = "Beshalach"
        transliteratedParshiosList[Parsha.YISRO] = "Yisro"
        transliteratedParshiosList[Parsha.MISHPATIM] = "Mishpatim"
        transliteratedParshiosList[Parsha.TERUMAH] = "Terumah"
        transliteratedParshiosList[Parsha.TETZAVEH] = "Tetzaveh"
        transliteratedParshiosList[Parsha.KI_SISA] = "Ki Sisa"
        transliteratedParshiosList[Parsha.VAYAKHEL] = "Vayakhel"
        transliteratedParshiosList[Parsha.PEKUDEI] = "Pekudei"
        transliteratedParshiosList[Parsha.VAYIKRA] = "Vayikra"
        transliteratedParshiosList[Parsha.TZAV] = "Tzav"
        transliteratedParshiosList[Parsha.SHMINI] = "Shmini"
        transliteratedParshiosList[Parsha.TAZRIA] = "Tazria"
        transliteratedParshiosList[Parsha.METZORA] = "Metzora"
        transliteratedParshiosList[Parsha.ACHREI_MOS] = "Achrei Mos"
        transliteratedParshiosList[Parsha.KEDOSHIM] = "Kedoshim"
        transliteratedParshiosList[Parsha.EMOR] = "Emor"
        transliteratedParshiosList[Parsha.BEHAR] = "Behar"
        transliteratedParshiosList[Parsha.BECHUKOSAI] = "Bechukosai"
        transliteratedParshiosList[Parsha.BAMIDBAR] = "Bamidbar"
        transliteratedParshiosList[Parsha.NASSO] = "Nasso"
        transliteratedParshiosList[Parsha.BEHAALOSCHA] = "Beha'aloscha"
        transliteratedParshiosList[Parsha.SHLACH] = "Sh'lach"
        transliteratedParshiosList[Parsha.KORACH] = "Korach"
        transliteratedParshiosList[Parsha.CHUKAS] = "Chukas"
        transliteratedParshiosList[Parsha.BALAK] = "Balak"
        transliteratedParshiosList[Parsha.PINCHAS] = "Pinchas"
        transliteratedParshiosList[Parsha.MATOS] = "Matos"
        transliteratedParshiosList[Parsha.MASEI] = "Masei"
        transliteratedParshiosList[Parsha.DEVARIM] = "Devarim"
        transliteratedParshiosList[Parsha.VAESCHANAN] = "Vaeschanan"
        transliteratedParshiosList[Parsha.EIKEV] = "Eikev"
        transliteratedParshiosList[Parsha.REEH] = "Re'eh"
        transliteratedParshiosList[Parsha.SHOFTIM] = "Shoftim"
        transliteratedParshiosList[Parsha.KI_SEITZEI] = "Ki Seitzei"
        transliteratedParshiosList[Parsha.KI_SAVO] = "Ki Savo"
        transliteratedParshiosList[Parsha.NITZAVIM] = "Nitzavim"
        transliteratedParshiosList[Parsha.VAYEILECH] = "Vayeilech"
        transliteratedParshiosList[Parsha.HAAZINU] = "Ha'Azinu"
        transliteratedParshiosList[Parsha.VZOS_HABERACHA] = "Vezos Habracha"
        transliteratedParshiosList[Parsha.VAYAKHEL_PEKUDEI] = "Vayakhel Pekudei"
        transliteratedParshiosList[Parsha.TAZRIA_METZORA] = "Tazria Metzora"
        transliteratedParshiosList[Parsha.ACHREI_MOS_KEDOSHIM] = "Achrei Mos Kedoshim"
        transliteratedParshiosList[Parsha.BEHAR_BECHUKOSAI] = "Behar Bechukosai"
        transliteratedParshiosList[Parsha.CHUKAS_BALAK] = "Chukas Balak"
        transliteratedParshiosList[Parsha.MATOS_MASEI] = "Matos Masei"
        transliteratedParshiosList[Parsha.NITZAVIM_VAYEILECH] = "Nitzavim Vayeilech"
        transliteratedParshiosList[Parsha.SHKALIM] = "Shekalim"
        transliteratedParshiosList[Parsha.ZACHOR] = "Zachor"
        transliteratedParshiosList[Parsha.PARA] = "Parah"
        transliteratedParshiosList[Parsha.HACHODESH] = "Hachodesh"
        transliteratedParshiosList[Parsha.SHUVA] = "Shuva"
        transliteratedParshiosList[Parsha.SHIRA] = "Shira"
        transliteratedParshiosList[Parsha.HAGADOL] = "Hagadol"
        transliteratedParshiosList[Parsha.CHAZON] = "Chazon"
        transliteratedParshiosList[Parsha.NACHAMU] = "Nachamu"
        hebrewParshaMap = EnumMap(Parsha::class.java)
        hebrewParshaMap[Parsha.NONE] = ""
        hebrewParshaMap[Parsha.BERESHIS] = "\u05D1\u05E8\u05D0\u05E9\u05D9\u05EA"
        hebrewParshaMap[Parsha.NOACH] = "\u05E0\u05D7"
        hebrewParshaMap[Parsha.LECH_LECHA] = "\u05DC\u05DA \u05DC\u05DA"
        hebrewParshaMap[Parsha.VAYERA] = "\u05D5\u05D9\u05E8\u05D0"
        hebrewParshaMap[Parsha.CHAYEI_SARA] = "\u05D7\u05D9\u05D9 \u05E9\u05E8\u05D4"
        hebrewParshaMap[Parsha.TOLDOS] = "\u05EA\u05D5\u05DC\u05D3\u05D5\u05EA"
        hebrewParshaMap[Parsha.VAYETZEI] = "\u05D5\u05D9\u05E6\u05D0"
        hebrewParshaMap[Parsha.VAYISHLACH] = "\u05D5\u05D9\u05E9\u05DC\u05D7"
        hebrewParshaMap[Parsha.VAYESHEV] = "\u05D5\u05D9\u05E9\u05D1"
        hebrewParshaMap[Parsha.MIKETZ] = "\u05DE\u05E7\u05E5"
        hebrewParshaMap[Parsha.VAYIGASH] = "\u05D5\u05D9\u05D2\u05E9"
        hebrewParshaMap[Parsha.VAYECHI] = "\u05D5\u05D9\u05D7\u05D9"
        hebrewParshaMap[Parsha.SHEMOS] = "\u05E9\u05DE\u05D5\u05EA"
        hebrewParshaMap[Parsha.VAERA] = "\u05D5\u05D0\u05E8\u05D0"
        hebrewParshaMap[Parsha.BO] = "\u05D1\u05D0"
        hebrewParshaMap[Parsha.BESHALACH] = "\u05D1\u05E9\u05DC\u05D7"
        hebrewParshaMap[Parsha.YISRO] = "\u05D9\u05EA\u05E8\u05D5"
        hebrewParshaMap[Parsha.MISHPATIM] = "\u05DE\u05E9\u05E4\u05D8\u05D9\u05DD"
        hebrewParshaMap[Parsha.TERUMAH] = "\u05EA\u05E8\u05D5\u05DE\u05D4"
        hebrewParshaMap[Parsha.TETZAVEH] = "\u05EA\u05E6\u05D5\u05D4"
        hebrewParshaMap[Parsha.KI_SISA] = "\u05DB\u05D9 \u05EA\u05E9\u05D0"
        hebrewParshaMap[Parsha.VAYAKHEL] = "\u05D5\u05D9\u05E7\u05D4\u05DC"
        hebrewParshaMap[Parsha.PEKUDEI] = "\u05E4\u05E7\u05D5\u05D3\u05D9"
        hebrewParshaMap[Parsha.VAYIKRA] = "\u05D5\u05D9\u05E7\u05E8\u05D0"
        hebrewParshaMap[Parsha.TZAV] = "\u05E6\u05D5"
        hebrewParshaMap[Parsha.SHMINI] = "\u05E9\u05DE\u05D9\u05E0\u05D9"
        hebrewParshaMap[Parsha.TAZRIA] = "\u05EA\u05D6\u05E8\u05D9\u05E2"
        hebrewParshaMap[Parsha.METZORA] = "\u05DE\u05E6\u05E8\u05E2"
        hebrewParshaMap[Parsha.ACHREI_MOS] = "\u05D0\u05D7\u05E8\u05D9 \u05DE\u05D5\u05EA"
        hebrewParshaMap[Parsha.KEDOSHIM] = "\u05E7\u05D3\u05D5\u05E9\u05D9\u05DD"
        hebrewParshaMap[Parsha.EMOR] = "\u05D0\u05DE\u05D5\u05E8"
        hebrewParshaMap[Parsha.BEHAR] = "\u05D1\u05D4\u05E8"
        hebrewParshaMap[Parsha.BECHUKOSAI] = "\u05D1\u05D7\u05E7\u05EA\u05D9"
        hebrewParshaMap[Parsha.BAMIDBAR] = "\u05D1\u05DE\u05D3\u05D1\u05E8"
        hebrewParshaMap[Parsha.NASSO] = "\u05E0\u05E9\u05D0"
        hebrewParshaMap[Parsha.BEHAALOSCHA] = "\u05D1\u05D4\u05E2\u05DC\u05EA\u05DA"
        hebrewParshaMap[Parsha.SHLACH] = "\u05E9\u05DC\u05D7 \u05DC\u05DA"
        hebrewParshaMap[Parsha.KORACH] = "\u05E7\u05E8\u05D7"
        hebrewParshaMap[Parsha.CHUKAS] = "\u05D7\u05D5\u05E7\u05EA"
        hebrewParshaMap[Parsha.BALAK] = "\u05D1\u05DC\u05E7"
        hebrewParshaMap[Parsha.PINCHAS] = "\u05E4\u05D9\u05E0\u05D7\u05E1"
        hebrewParshaMap[Parsha.MATOS] = "\u05DE\u05D8\u05D5\u05EA"
        hebrewParshaMap[Parsha.MASEI] = "\u05DE\u05E1\u05E2\u05D9"
        hebrewParshaMap[Parsha.DEVARIM] = "\u05D3\u05D1\u05E8\u05D9\u05DD"
        hebrewParshaMap[Parsha.VAESCHANAN] = "\u05D5\u05D0\u05EA\u05D7\u05E0\u05DF"
        hebrewParshaMap[Parsha.EIKEV] = "\u05E2\u05E7\u05D1"
        hebrewParshaMap[Parsha.REEH] = "\u05E8\u05D0\u05D4"
        hebrewParshaMap[Parsha.SHOFTIM] = "\u05E9\u05D5\u05E4\u05D8\u05D9\u05DD"
        hebrewParshaMap[Parsha.KI_SEITZEI] = "\u05DB\u05D9 \u05EA\u05E6\u05D0"
        hebrewParshaMap[Parsha.KI_SAVO] = "\u05DB\u05D9 \u05EA\u05D1\u05D5\u05D0"
        hebrewParshaMap[Parsha.NITZAVIM] = "\u05E0\u05E6\u05D1\u05D9\u05DD"
        hebrewParshaMap[Parsha.VAYEILECH] = "\u05D5\u05D9\u05DC\u05DA"
        hebrewParshaMap[Parsha.HAAZINU] = "\u05D4\u05D0\u05D6\u05D9\u05E0\u05D5"
        hebrewParshaMap[Parsha.VZOS_HABERACHA] = "\u05D5\u05D6\u05D0\u05EA \u05D4\u05D1\u05E8\u05DB\u05D4 "
        hebrewParshaMap[Parsha.VAYAKHEL_PEKUDEI] = "\u05D5\u05D9\u05E7\u05D4\u05DC \u05E4\u05E7\u05D5\u05D3\u05D9"
        hebrewParshaMap[Parsha.TAZRIA_METZORA] = "\u05EA\u05D6\u05E8\u05D9\u05E2 \u05DE\u05E6\u05E8\u05E2"
        hebrewParshaMap[Parsha.ACHREI_MOS_KEDOSHIM] = "\u05D0\u05D7\u05E8\u05D9 \u05DE\u05D5\u05EA \u05E7\u05D3\u05D5\u05E9\u05D9\u05DD"
        hebrewParshaMap[Parsha.BEHAR_BECHUKOSAI] = "\u05D1\u05D4\u05E8 \u05D1\u05D7\u05E7\u05EA\u05D9"
        hebrewParshaMap[Parsha.CHUKAS_BALAK] = "\u05D7\u05D5\u05E7\u05EA \u05D1\u05DC\u05E7"
        hebrewParshaMap[Parsha.MATOS_MASEI] = "\u05DE\u05D8\u05D5\u05EA \u05DE\u05E1\u05E2\u05D9"
        hebrewParshaMap[Parsha.NITZAVIM_VAYEILECH] = "\u05E0\u05E6\u05D1\u05D9\u05DD \u05D5\u05D9\u05DC\u05DA"
        hebrewParshaMap[Parsha.SHKALIM] = "\u05E9\u05E7\u05DC\u05D9\u05DD"
        hebrewParshaMap[Parsha.ZACHOR] = "\u05D6\u05DB\u05D5\u05E8"
        hebrewParshaMap[Parsha.PARA] = "\u05E4\u05E8\u05D4"
        hebrewParshaMap[Parsha.HACHODESH] = "\u05D4\u05D7\u05D3\u05E9"
        hebrewParshaMap[Parsha.SHUVA] = "\u05E9\u05D5\u05D1\u05D4"
        hebrewParshaMap[Parsha.SHIRA] = "\u05E9\u05D9\u05E8\u05D4"
        hebrewParshaMap[Parsha.HAGADOL] = "\u05D4\u05D2\u05D3\u05D5\u05DC"
        hebrewParshaMap[Parsha.CHAZON] = "\u05D7\u05D6\u05D5\u05DF"
        hebrewParshaMap[Parsha.NACHAMU] = "\u05E0\u05D7\u05DE\u05D5"
    }

    /**
     * Formats the day of week. If [Hebrew formatting][.isHebrewFormat] is set, it will display in the format
     * &#x05E8;&#x05D0;&#x05E9;&#x05D5;&#x05DF; etc. If Hebrew formatting is not in use it will return it in the format
     * of Sunday etc. There are various formatting options that will affect the output.
     *
     * @param jewishDate the JewishDate Object
     * @return the formatted day of week
     * @see .isHebrewFormat
     * @see .isLongWeekFormat
     */
    fun formatDayOfWeek(jewishDate: JewishDate): String {
        return if (isHebrewFormat) {
            if (isLongWeekFormat) {
                hebrewDaysOfWeek[jewishDate.dayOfWeek - 1]
            } else {
                if (jewishDate.dayOfWeek == 7) {
                    formatHebrewNumber(300)
                } else {
                    formatHebrewNumber(jewishDate.dayOfWeek)
                }
            }
        } else {
            if (jewishDate.dayOfWeek == 7) {
                if (isLongWeekFormat) {
                    transliteratedShabbosDayOfWeek
                } else {
                    transliteratedShabbosDayOfWeek.substring(0, 3)
                }
            } else {
                weekFormat!!.format(jewishDate.gregorianCalendar.time)
            }
        }
    }

    /**
     * Formats the Jewish date. If the formatter is set to Hebrew, it will format in the form, "day Month year" for
     * example &#x5DB;&#x5F4;&#x5D0; &#x5E9;&#x5D1;&#x5D8; &#x5EA;&#x5E9;&#x5DB;&#x5F4;&#x5D8;, and the format
     * "21 Shevat, 5729" if not.
     *
     * @param jewishDate
     * the JewishDate to be formatted
     * @return the formatted date. If the formatter is set to Hebrew, it will format in the form, "day Month year" for
     * example &#x5DB;&#x5F4;&#x5D0; &#x5E9;&#x5D1;&#x5D8; &#x5EA;&#x5E9;&#x5DB;&#x5F4;&#x5D8;, and the format
     * "21 Shevat, 5729" if not.
     */
    fun format(jewishDate: JewishDate): String {
        return if (isHebrewFormat) {
            (formatHebrewNumber(jewishDate.jewishDayOfMonth) + " " + formatMonth(jewishDate) + " "
                    + formatHebrewNumber(jewishDate.getJewishYear()))
        } else {
            jewishDate.jewishDayOfMonth.toString() + " " + formatMonth(jewishDate) + ", " + jewishDate.getJewishYear()
        }
    }

    /**
     * Returns a string of the current Hebrew month such as "Tishrei". Returns a string of the current Hebrew month such
     * as "&#x5D0;&#x5D3;&#x5E8; &#x5D1;&#x5F3;".
     *
     * @param jewishDate
     * the JewishDate to format
     * @return the formatted month name
     * @see .isHebrewFormat
     * @see .setHebrewFormat
     * @see .getTransliteratedMonthList
     * @see .setTransliteratedMonthList
     */
    fun formatMonth(jewishDate: JewishDate): String {
        val month = jewishDate.getJewishMonth()
        return if (isHebrewFormat) {
            if (jewishDate.isJewishLeapYear && month == JewishDate.ADAR) {
                hebrewMonths[13] + if (isUseGershGershayim) GERESH else "" // return Adar I, not Adar in a leap year
            } else if (jewishDate.isJewishLeapYear && month == JewishDate.ADAR_II) {
                hebrewMonths[12] + if (isUseGershGershayim) GERESH else ""
            } else {
                hebrewMonths[month - 1]
            }
        } else {
            if (jewishDate.isJewishLeapYear && month == JewishDate.ADAR) {
                transliteratedMonthList[13] // return Adar I, not Adar in a leap year
            } else {
                transliteratedMonthList[month - 1]
            }
        }
    }

    /**
     * Returns a String of the Omer day in the form &#x5DC;&#x5F4;&#x5D2; &#x5D1;&#x05E2;&#x05D5;&#x05DE;&#x5E8; if
     * Hebrew Format is set, or "Omer X" or "Lag B'Omer" if not. An empty string if there is no Omer this day.
     *
     * @param jewishCalendar
     * the JewishCalendar to be formatted
     *
     * @return a String of the Omer day in the form or an empty string if there is no Omer this day. The default
     * formatting has a &#x5D1;&#x5F3; prefix that would output &#x5D1;&#x05E2;&#x05D5;&#x05DE;&#x5E8;, but this
     * can be set via the [.setHebrewOmerPrefix] method to use a &#x5DC; and output
     * &#x5DC;&#x5F4;&#x5D2; &#x5DC;&#x05E2;&#x05D5;&#x05DE;&#x5E8;.
     * @see .isHebrewFormat
     * @see .getHebrewOmerPrefix
     * @see .setHebrewOmerPrefix
     */
    fun formatOmer(jewishCalendar: JewishCalendar): String {
        val omer = jewishCalendar.dayOfOmer
        if (omer == -1) {
            return ""
        }
        return if (isHebrewFormat) {
            formatHebrewNumber(omer) + " " + hebrewOmerPrefix + "\u05E2\u05D5\u05DE\u05E8"
        } else {
            if (omer == 33) { // if Lag B'Omer
                transliteratedHolidayList[33]
            } else {
                "Omer $omer"
            }
        }
    }

    /**
     * Returns the kviah in the traditional 3 letter Hebrew format where the first letter represents the day of week of
     * Rosh Hashana, the second letter represents the lengths of Cheshvan and Kislev ([ Shelaimim][JewishDate.SHELAIMIM] , [Kesidran][JewishDate.KESIDRAN] or [Chaserim][JewishDate.CHASERIM]) and the 3rd letter
     * represents the day of week of Pesach. For example 5729 (1969) would return &#x5D1;&#x5E9;&#x5D4; (Rosh Hashana on
     * Monday, Shelaimim, and Pesach on Thursday), while 5771 (2011) would return &#x5D4;&#x5E9;&#x5D2; (Rosh Hashana on
     * Thursday, Shelaimim, and Pesach on Tuesday).
     *
     * @param jewishYear
     * the Jewish year
     * @return the Hebrew String such as &#x5D1;&#x5E9;&#x5D4; for 5729 (1969) and &#x5D4;&#x5E9;&#x5D2; for 5771
     * (2011).
     */
    fun getFormattedKviah(jewishYear: Int): String {
        val jewishDate = JewishDate(jewishYear, JewishDate.TISHREI, 1) // set date to Rosh Hashana
        val kviah = jewishDate.cheshvanKislevKviah
        val roshHashanaDayOfweek = jewishDate.dayOfWeek
        var returnValue = formatHebrewNumber(roshHashanaDayOfweek)
        returnValue += if (kviah == JewishDate.CHASERIM) "\u05D7" else if (kviah == JewishDate.SHELAIMIM) "\u05E9" else "\u05DB"
        jewishDate.setJewishDate(jewishYear, JewishDate.NISSAN, 15) // set to Pesach of the given year
        val pesachDayOfweek = jewishDate.dayOfWeek
        returnValue += formatHebrewNumber(pesachDayOfweek)
        returnValue = returnValue.replace(GERESH.toRegex(), "") // geresh is never used in the kviah format
        // boolean isLeapYear = JewishDate.isJewishLeapYear(jewishYear);
        // for efficiency we can avoid the expensive recalculation of the pesach day of week by adding 1 day to Rosh
        // Hashana for a 353 day year, 2 for a 354 day year, 3 for a 355 or 383 day year, 4 for a 384 day year and 5 for
        // a 385 day year
        return returnValue
    }

    /**
     * Formats the [Daf Yomi](https://en.wikipedia.org/wiki/Daf_Yomi) Bavli in the format of
     * "&#x05E2;&#x05D9;&#x05E8;&#x05D5;&#x05D1;&#x05D9;&#x05DF; &#x05E0;&#x05F4;&#x05D1;" in [Hebrew][.isHebrewFormat],
     * or the transliterated format of "Eruvin 52".
     * @param daf the Daf to be formatted.
     * @return the formatted daf.
     */
    fun formatDafYomiBavli(daf: Daf): String {
        return if (isHebrewFormat) {
            daf.masechta + " " + formatHebrewNumber(daf.daf)
        } else {
            daf.masechtaTransliterated + " " + daf.daf
        }
    }

    /**
     * Formats the [Daf Yomi Yerushalmi](https://en.wikipedia.org/wiki/Jerusalem_Talmud#Daf_Yomi_Yerushalmi) in the format
     * of "&#x05E2;&#x05D9;&#x05E8;&#x05D5;&#x05D1;&#x05D9;&#x05DF; &#x05E0;&#x05F4;&#x05D1;" in [Hebrew][.isHebrewFormat], or
     * the transliterated format of "Eruvin 52".
     *
     * @param daf the Daf to be formatted.
     * @return the formatted daf.
     */
    fun formatDafYomiYerushalmi(daf: Daf?): String {
        if (daf == null) {
            return if (isHebrewFormat) {
                yerushlmiMasechtos[39]
            } else {
                yerushlmiMasechtosTransliterated[39]
            }
        }
        return if (isHebrewFormat) {
            daf.yerushalmiMasechta + " " + formatHebrewNumber(daf.daf)
        } else {
            daf.yerushlmiMasechtaTransliterated + " " + daf.daf
        }
    }

    /**
     * Returns a Hebrew formatted string of a number. The method can calculate from 0 - 9999.
     *
     *  * Single digit numbers such as 3, 30 and 100 will be returned with a &#x5F3; ([Geresh](http://en.wikipedia.org/wiki/Geresh)) appended as at the end. For example &#x5D2;&#x5F3;,
     * &#x5DC;&#x5F3; and &#x5E7;&#x5F3;
     *  * multi digit numbers such as 21 and 769 will be returned with a &#x5F4; ([Gershayim](http://en.wikipedia.org/wiki/Gershayim)) between the second to last and last letters. For
     * example &#x5DB;&#x5F4;&#x5D0;, &#x5EA;&#x5E9;&#x5DB;&#x5F4;&#x5D8;
     *  * 15 and 16 will be returned as &#x5D8;&#x5F4;&#x5D5; and &#x5D8;&#x5F4;&#x5D6;
     *  * Single digit numbers (years assumed) such as 6000 (%1000=0) will be returned as &#x5D5;&#x5F3;
     * &#x5D0;&#x5DC;&#x5E4;&#x5D9;&#x5DD;
     *  * 0 will return &#x5D0;&#x5E4;&#x05E1;
     *
     *
     * @param number
     * the number to be formatted. It will trow an IllegalArgumentException if the number is &lt; 0 or &gt; 9999.
     * @return the Hebrew formatted number such as &#x5EA;&#x5E9;&#x5DB;&#x5F4;&#x5D8;
     * @see .isUseFinalFormLetters
     * @see .isUseGershGershayim
     * @see .isHebrewFormat
     */
    fun formatHebrewNumber(number: Int): String {
        var number = number
        require(number >= 0) { "negative numbers can't be formatted" }
        require(number <= 9999) { "numbers > 9999 can't be formatted" }
        val ALAFIM = "\u05D0\u05DC\u05E4\u05D9\u05DD"
        val EFES = "\u05D0\u05E4\u05E1"
        val jHundreds = arrayOf(
            "", "\u05E7", "\u05E8", "\u05E9", "\u05EA", "\u05EA\u05E7", "\u05EA\u05E8",
            "\u05EA\u05E9", "\u05EA\u05EA", "\u05EA\u05EA\u05E7"
        )
        val jTens = arrayOf(
            "", "\u05D9", "\u05DB", "\u05DC", "\u05DE", "\u05E0", "\u05E1", "\u05E2",
            "\u05E4", "\u05E6"
        )
        val jTenEnds = arrayOf(
            "", "\u05D9", "\u05DA", "\u05DC", "\u05DD", "\u05DF", "\u05E1", "\u05E2",
            "\u05E3", "\u05E5"
        )
        val tavTaz = arrayOf("\u05D8\u05D5", "\u05D8\u05D6")
        val jOnes = arrayOf(
            "", "\u05D0", "\u05D1", "\u05D2", "\u05D3", "\u05D4", "\u05D5", "\u05D6",
            "\u05D7", "\u05D8"
        )
        if (number == 0) { // do we really need this? Should it be applicable to a date?
            return EFES
        }
        val shortNumber = number % 1000 // discard thousands
        // next check for all possible single Hebrew digit years
        val singleDigitNumber =
            (shortNumber < 11 || shortNumber < 100 && shortNumber % 10 == 0 || shortNumber <= 400) && shortNumber % 100 == 0
        val thousands = number / 1000 // get # thousands
        val sb = StringBuilder()
        // append thousands to String
        if (number % 1000 == 0) { // in year is 5000, 4000 etc
            sb.append(jOnes[thousands])
            if (isUseGershGershayim) {
                sb.append(GERESH)
            }
            sb.append(" ")
            sb.append(ALAFIM) // add # of thousands plus word thousand (overide alafim boolean)
            return sb.toString()
        } else if (isUseLongHebrewYears && number >= 1000) { // if alafim boolean display thousands
            sb.append(jOnes[thousands])
            if (isUseGershGershayim) {
                sb.append(GERESH) // append thousands quote
            }
            sb.append(" ")
        }
        number = number % 1000 // remove 1000s
        val hundreds = number / 100 // # of hundreds
        sb.append(jHundreds[hundreds]) // add hundreds to String
        number = number % 100 // remove 100s
        if (number == 15) { // special case 15
            sb.append(tavTaz[0])
        } else if (number == 16) { // special case 16
            sb.append(tavTaz[1])
        } else {
            val tens = number / 10
            if (number % 10 == 0) { // if evenly divisable by 10
                if (!singleDigitNumber) {
                    if (isUseFinalFormLetters) {
                        sb.append(jTenEnds[tens]) // years like 5780 will end with a final form &#x05E3;
                    } else {
                        sb.append(jTens[tens]) // years like 5780 will end with a regular &#x05E4;
                    }
                } else {
                    sb.append(jTens[tens]) // standard letters so years like 5050 will end with a regular nun
                }
            } else {
                sb.append(jTens[tens])
                number = number % 10
                sb.append(jOnes[number])
            }
        }
        if (isUseGershGershayim) {
            if (singleDigitNumber) {
                sb.append(GERESH) // append single quote
            } else { // append double quote before last digit
                sb.insert(sb.length - 1, GERSHAYIM)
            }
        }
        return sb.toString()
    }

    /**
     * Returns a String with the name of the current parsha(ios). If the formatter is set to format in Hebrew, returns
     * a string of the current parsha(ios) in Hebrew for example &#x05D1;&#x05E8;&#x05D0;&#x05E9;&#x05D9;&#x05EA; or
     * &#x05E0;&#x05E6;&#x05D1;&#x05D9;&#x05DD; &#x05D5;&#x05D9;&#x05DC;&#x05DA; or an empty string if there
     * are none. If not set to Hebrew, it returns a string of the parsha(ios) transliterated into Latin chars. The
     * default uses Ashkenazi pronunciation in typical American English spelling, for example Bereshis or
     * Nitzavim Vayeilech or an empty string if there are none.
     *
     * @param jewishCalendar the JewishCalendar Object
     * @return today's parsha(ios) in Hebrew for example, if the formatter is set to format in Hebrew, returns a string
     * of the current parsha(ios) in Hebrew for example &#x05D1;&#x05E8;&#x05D0;&#x05E9;&#x05D9;&#x05EA; or
     * &#x05E0;&#x05E6;&#x05D1;&#x05D9;&#x05DD; &#x05D5;&#x05D9;&#x05DC;&#x05DA; or an empty string if
     * there are none. If not set to Hebrew, it returns a string of the parsha(ios) transliterated into Latin
     * chars. The default uses Ashkenazi pronunciation in typical American English spelling, for example
     * Bereshis or Nitzavim Vayeilech or an empty string if there are none.
     */
    fun formatParsha(jewishCalendar: JewishCalendar): String? {
        val parsha = jewishCalendar.parshah
        return if (isHebrewFormat) hebrewParshaMap[parsha] else transliteratedParshiosList[parsha]
    }

    /**
     * Returns a String with the name of the current special parsha of Shekalim, Zachor, Parah or Hachodesh or an
     * empty String for a non-special parsha. If the formatter is set to format in Hebrew, it returns a string of
     * the current special parsha in Hebrew, for example &#x05E9;&#x05E7;&#x05DC;&#x05D9;&#x05DD;,
     * &#x05D6;&#x05DB;&#x05D5;&#x05E8;, &#x05E4;&#x05E8;&#x05D4; or &#x05D4;&#x05D7;&#x05D3;&#x05E9;. An empty
     * string if the date is not a special parsha. If not set to Hebrew, it returns a string of the special parsha
     * transliterated into Latin chars. The default uses Ashkenazi pronunciation in typical American English spelling
     * Shekalim, Zachor, Parah or Hachodesh.
     *
     * @param jewishCalendar the JewishCalendar Object
     * @return today's special parsha. If the formatter is set to format in Hebrew, returns a string
     * of the current special parsha  in Hebrew for in the format of &#x05E9;&#x05E7;&#x05DC;&#x05D9;&#x05DD;,
     * &#x05D6;&#x05DB;&#x05D5;&#x05E8;, &#x05E4;&#x05E8;&#x05D4; or &#x05D4;&#x05D7;&#x05D3;&#x05E9; or an empty
     * string if there are none. If not set to Hebrew, it returns a string of the special parsha transliterated
     * into Latin chars. The default uses Ashkenazi pronunciation in typical American English spelling of Shekalim,
     * Zachor, Parah or Hachodesh. An empty string if there are none.
     */
    fun formatSpecialParsha(jewishCalendar: JewishCalendar): String? {
        val specialParsha = jewishCalendar.specialShabbos
        return if (isHebrewFormat) hebrewParshaMap[specialParsha] else transliteratedParshiosList[specialParsha]
    }

    companion object {
        /**
         * The [gersh](https://en.wikipedia.org/wiki/Geresh#Punctuation_mark) character is the &#x05F3; char
         * that is similar to a single quote and is used in formatting Hebrew numbers.
         */
        private const val GERESH = "\u05F3"

        /**
         * The [gershyim](https://en.wikipedia.org/wiki/Gershayim#Punctuation_mark) character is the &#x05F4; char
         * that is similar to a double quote and is used in formatting Hebrew numbers.
         */
        private const val GERSHAYIM = "\u05F4"

        /**
         * Unicode list of Hebrew months in the following format `["\u05E0\u05D9\u05E1\u05DF","\u05D0\u05D9\u05D9\u05E8",
         * "\u05E1\u05D9\u05D5\u05DF","\u05EA\u05DE\u05D5\u05D6","\u05D0\u05D1","\u05D0\u05DC\u05D5\u05DC",
         * "\u05EA\u05E9\u05E8\u05D9","\u05D7\u05E9\u05D5\u05DF","\u05DB\u05E1\u05DC\u05D5","\u05D8\u05D1\u05EA",
         * "\u05E9\u05D1\u05D8","\u05D0\u05D3\u05E8","\u05D0\u05D3\u05E8 \u05D1","\u05D0\u05D3\u05E8 \u05D0"]`
         *
         * @see .formatMonth
         */
        private val hebrewMonths = arrayOf(
            "\u05E0\u05D9\u05E1\u05DF", "\u05D0\u05D9\u05D9\u05E8",
            "\u05E1\u05D9\u05D5\u05DF", "\u05EA\u05DE\u05D5\u05D6", "\u05D0\u05D1", "\u05D0\u05DC\u05D5\u05DC",
            "\u05EA\u05E9\u05E8\u05D9", "\u05D7\u05E9\u05D5\u05DF", "\u05DB\u05E1\u05DC\u05D5",
            "\u05D8\u05D1\u05EA", "\u05E9\u05D1\u05D8", "\u05D0\u05D3\u05E8", "\u05D0\u05D3\u05E8 \u05D1",
            "\u05D0\u05D3\u05E8 \u05D0"
        )

        /**
         * Unicode list of Hebrew days of week in the format of `["&#x05E8;&#x05D0;&#x05E9;&#x05D5;&#x05DF;",
         * "&#x05E9;&#x05E0;&#x05D9;","&#x05E9;&#x05DC;&#x05D9;&#x05E9;&#x05D9;","&#x05E8;&#x05D1;&#x05D9;&#x05E2;&#x05D9;",
         * "&#x05D7;&#x05DE;&#x05D9;&#x05E9;&#x05D9;","&#x05E9;&#x05E9;&#x05D9;","&#x05E9;&#x05D1;&#x05EA;"]`
         */
        private val hebrewDaysOfWeek = arrayOf(
            "\u05E8\u05D0\u05E9\u05D5\u05DF", "\u05E9\u05E0\u05D9",
            "\u05E9\u05DC\u05D9\u05E9\u05D9", "\u05E8\u05D1\u05D9\u05E2\u05D9", "\u05D7\u05DE\u05D9\u05E9\u05D9",
            "\u05E9\u05E9\u05D9", "\u05E9\u05D1\u05EA"
        )

        /**
         * Formats a molad.
         * @todo Experimental and incomplete.
         *
         * @param moladChalakim the chalakim of the molad
         * @return the formatted molad. FIXME: define proper format in English and Hebrew.
         */
        private fun formatMolad(moladChalakim: Long): String {
            var adjustedChalakim = moladChalakim
            val MINUTE_CHALAKIM = 18
            val HOUR_CHALAKIM = 1080
            val DAY_CHALAKIM = 24 * HOUR_CHALAKIM
            var days = adjustedChalakim / DAY_CHALAKIM
            adjustedChalakim = adjustedChalakim - days * DAY_CHALAKIM
            val hours = (adjustedChalakim / HOUR_CHALAKIM).toInt()
            if (hours >= 6) {
                days += 1
            }
            adjustedChalakim = adjustedChalakim - hours * HOUR_CHALAKIM.toLong()
            val minutes = (adjustedChalakim / MINUTE_CHALAKIM).toInt()
            adjustedChalakim = adjustedChalakim - minutes * MINUTE_CHALAKIM.toLong()
            return "Day: " + (days % 7) + " hours: " + hours + ", minutes " + minutes + ", chalakim: " + adjustedChalakim
        }
    }
}
