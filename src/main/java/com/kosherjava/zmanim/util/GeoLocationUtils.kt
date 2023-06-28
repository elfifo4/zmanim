/*
 * Zmanim Java API
 * Copyright (C) 2004-2020 Eliyahu Hershfeld
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
package com.kosherjava.zmanim.util

/**
 * A class for various location calculations
 * Most of the code in this class is ported from [Chris Veness'](http://www.movable-type.co.uk/)
 * [LGPL](http://www.fsf.org/licensing/licenses/lgpl.html) Javascript Implementation
 *
 * @author  Eliyahu Hershfeld 2009 - 2020
 */
@Deprecated(
    """All methods in this call are available in the {@link GeoLocation} class, and this class that duplicates that
  code will be removed in release 3.0."""
)
object GeoLocationUtils {
    /**
     * Constant for a distance type calculation.
     * @see .getGeodesicDistance
     */
    private const val DISTANCE = 0

    /**
     * Constant for a initial bearing type calculation.
     * @see .getGeodesicInitialBearing
     */
    private const val INITIAL_BEARING = 1

    /**
     * Constant for a final bearing type calculation.
     * @see .getGeodesicFinalBearing
     */
    private const val FINAL_BEARING = 2

    /**
     * Calculate the [geodesic](http://en.wikipedia.org/wiki/Great_circle) initial bearing between this Object and
     * a second Object passed to this method using [Thaddeus
 * Vincenty's](http://en.wikipedia.org/wiki/Thaddeus_Vincenty) inverse formula See T Vincenty, "[Direct and
 * Inverse Solutions of Geodesics on the Ellipsoid with application of nested equations](http://www.ngs.noaa.gov/PUBS_LIB/inverse.pdf)", Survey Review, vol XXII
     * no 176, 1975.
     *
     * @param location
     * the initial location
     * @param destination
     * the destination location
     * @return the geodesic bearing
     */
    fun getGeodesicInitialBearing(location: GeoLocation, destination: GeoLocation): Double {
        return vincentyFormula(location, destination, INITIAL_BEARING)
    }

    /**
     * Calculate the [geodesic](http://en.wikipedia.org/wiki/Great_circle) final bearing between this Object
     * and a second Object passed to this method using [Thaddeus Vincenty's](http://en.wikipedia.org/wiki/Thaddeus_Vincenty)
     * inverse formula See T Vincenty, "[Direct and Inverse Solutions of Geodesics
 * on the Ellipsoid with application of nested equations](http://www.ngs.noaa.gov/PUBS_LIB/inverse.pdf)", Survey Review, vol XXII no 176, 1975.
     *
     * @param location
     * the initial location
     * @param destination
     * the destination location
     * @return the geodesic bearing
     */
    fun getGeodesicFinalBearing(location: GeoLocation, destination: GeoLocation): Double {
        return vincentyFormula(location, destination, FINAL_BEARING)
    }

    /**
     * Calculate [geodesic distance](http://en.wikipedia.org/wiki/Great-circle_distance) in Meters
     * between this Object and a second Object passed to this method using [Thaddeus Vincenty's](http://en.wikipedia.org/wiki/Thaddeus_Vincenty) inverse formula See T Vincenty,
     * "[Direct and Inverse Solutions of Geodesics on the
 * Ellipsoid with application of nested equations](http://www.ngs.noaa.gov/PUBS_LIB/inverse.pdf)", Survey Review, vol XXII no 176, 1975. This uses the
     * WGS-84 geodetic model.
     *
     * @param location
     * the initial location
     * @param destination
     * the destination location
     * @return the geodesic distance in Meters
     */
    fun getGeodesicDistance(location: GeoLocation, destination: GeoLocation): Double {
        return vincentyFormula(location, destination, DISTANCE)
    }

    /**
     * Calculates the initial [geodesic](http://en.wikipedia.org/wiki/Great_circle) bearing, final bearing or
     * [geodesic distance](http://en.wikipedia.org/wiki/Great-circle_distance) using [Thaddeus Vincenty's](http://en.wikipedia.org/wiki/Thaddeus_Vincenty) inverse formula See T Vincenty, "[Direct and Inverse Solutions of Geodesics on the Ellipsoid
 * with application of nested equations](http://www.ngs.noaa.gov/PUBS_LIB/inverse.pdf)", Survey Review, vol XXII no 176, 1975.
     *
     * @param location
     * the initial location
     * @param destination
     * the destination location
     * @param formula
     * This formula calculates initial bearing ([.INITIAL_BEARING]),
     * final bearing ([.FINAL_BEARING]) and distance ([.DISTANCE]).
     * @return
     * the geodesic distance, initial or final bearing (based on the formula passed in) between the location
     * and destination in Meters
     * @see .getGeodesicDistance
     * @see .getGeodesicInitialBearing
     * @see .getGeodesicFinalBearing
     */
    private fun vincentyFormula(location: GeoLocation, destination: GeoLocation, formula: Int): Double {
        val a = 6378137.0 // length of semi-major axis of the ellipsoid (radius at equator) in metres based on WGS-84
        val b = 6356752.3142 // length of semi-minor axis of the ellipsoid (radius at the poles) in meters based on WGS-84
        val f = 1 / 298.257223563 // flattening of the ellipsoid based on WGS-84
        val L = Math.toRadians(destination.longitude - location.longitude) //difference in longitude of two points;
        val U1 = Math.atan((1 - f) * Math.tan(Math.toRadians(location.latitude))) // reduced latitude (latitude on the auxiliary sphere)
        val U2 = Math.atan((1 - f) * Math.tan(Math.toRadians(destination.latitude))) // reduced latitude (latitude on the auxiliary sphere)
        val sinU1 = Math.sin(U1)
        val cosU1 = Math.cos(U1)
        val sinU2 = Math.sin(U2)
        val cosU2 = Math.cos(U2)
        var lambda = L
        var lambdaP = 2 * Math.PI
        var iterLimit = 20.0
        var sinLambda = 0.0
        var cosLambda = 0.0
        var sinSigma = 0.0
        var cosSigma = 0.0
        var sigma = 0.0
        var sinAlpha = 0.0
        var cosSqAlpha = 0.0
        var cos2SigmaM = 0.0
        var C: Double
        while (Math.abs(lambda - lambdaP) > 1e-12 && --iterLimit > 0) {
            sinLambda = Math.sin(lambda)
            cosLambda = Math.cos(lambda)
            sinSigma = Math.sqrt(
                cosU2 * sinLambda * (cosU2 * sinLambda)
                        + (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda)
                        * (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda)
            )
            if (sinSigma == 0.0) return 0.0 // co-incident points
            cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda
            sigma = Math.atan2(sinSigma, cosSigma)
            sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma
            cosSqAlpha = 1 - sinAlpha * sinAlpha
            cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha
            if (java.lang.Double.isNaN(cos2SigmaM)) cos2SigmaM = 0.0 // equatorial line: cosSqAlpha=0 (§6)
            C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha))
            lambdaP = lambda
            lambda = (L
                    + ((1 - C)
                    * f
                    * sinAlpha
                    * (sigma + (C
                    * sinSigma
                    * (cos2SigmaM + (C * cosSigma
                    * (-1 + 2 * cos2SigmaM * cos2SigmaM)))))))
        }
        if (iterLimit == 0.0) return Double.NaN // formula failed to converge
        val uSq = cosSqAlpha * (a * a - b * b) / (b * b)
        val A = 1 + uSq / 16384 * 4096 + uSq * (-768 + uSq * (320 - 175 * uSq))
        val B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)))
        val deltaSigma = (B
                * sinSigma
                * (cos2SigmaM + B
                / 4
                * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) - ((B
                / 6) * cos2SigmaM
                * (-3 + 4 * sinSigma * sinSigma)
                * (-3 + 4 * cos2SigmaM * cos2SigmaM)))))
        val distance = b * A * (sigma - deltaSigma)

        // initial bearing
        val fwdAz = Math.toDegrees(
            Math.atan2(
                cosU2 * sinLambda, cosU1
                        * sinU2 - sinU1 * cosU2 * cosLambda
            )
        )
        // final bearing
        val revAz = Math.toDegrees(
            Math.atan2(
                cosU1 * sinLambda, -sinU1
                        * cosU2 + cosU1 * sinU2 * cosLambda
            )
        )
        return if (formula == DISTANCE) {
            distance
        } else if (formula == INITIAL_BEARING) {
            fwdAz
        } else if (formula == FINAL_BEARING) {
            revAz
        } else { // should never happen
            Double.NaN
        }
    }

    /**
     * Returns the [rhumb line](http://en.wikipedia.org/wiki/Rhumb_line)
     * bearing from the current location to the GeoLocation passed in.
     *
     * @param location
     * the initial location
     * @param destination
     * the destination location
     * @return the bearing in degrees
     */
    fun getRhumbLineBearing(location: GeoLocation, destination: GeoLocation): Double {
        var dLon = Math.toRadians(destination.longitude - location.longitude)
        val dPhi = Math.log(
            Math.tan(
                Math.toRadians(destination.latitude)
                        / 2 + Math.PI / 4
            )
                    / Math.tan(Math.toRadians(location.latitude) / 2 + Math.PI / 4)
        )
        if (Math.abs(dLon) > Math.PI) dLon = if (dLon > 0) -(2 * Math.PI - dLon) else 2 * Math.PI + dLon
        return Math.toDegrees(Math.atan2(dLon, dPhi))
    }

    /**
     * Returns the [rhumb line](http://en.wikipedia.org/wiki/Rhumb_line) distance between two GeoLocations
     * passed in. Ported from [Chris Veness'](http://www.movable-type.co.uk/) Javascript Implementation.
     *
     * @param location
     * the initial location
     * @param destination
     * the destination location
     * @return the distance in Meters
     */
    fun getRhumbLineDistance(location: GeoLocation, destination: GeoLocation): Double {
        val earthRadius = 6378137.0 // Earth's radius in meters (WGS-84)
        val dLat = Math.toRadians(location.latitude) - Math.toRadians(destination.latitude)
        var dLon = Math.abs(Math.toRadians(location.longitude) - Math.toRadians(destination.longitude))
        val dPhi = Math.log(
            Math.tan(Math.toRadians(location.latitude) / 2 + Math.PI / 4)
                    / Math.tan(Math.toRadians(destination.latitude) / 2 + Math.PI / 4)
        )
        var q = dLat / dPhi
        if (Math.abs(q) > Double.MAX_VALUE) {
            q = Math.cos(Math.toRadians(destination.latitude))
        }
        // if dLon over 180° take shorter rhumb across 180° meridian:
        if (dLon > Math.PI) {
            dLon = 2 * Math.PI - dLon
        }
        val d = Math.sqrt(dLat * dLat + q * q * dLon * dLon)
        return d * earthRadius
    }
}
