package de.pierrelaub.pace_rechner

import de.pierrelaub.pace_rechner.util.getEnhancedAvgSpeed
import de.pierrelaub.pace_rechner.util.hoursValueToSecondsValue
import de.pierrelaub.pace_rechner.util.msToKmPerHourNumber
import de.pierrelaub.pace_rechner.util.msToMinPerKm
import de.pierrelaub.pace_rechner.util.msToMinPerKmNumber
import de.pierrelaub.pace_rechner.util.secondsToHHMMSS
import kotlin.test.Test
import kotlin.test.assertEquals

class CalculationsTest {

    @Test
    fun testMsToKmPerHourNumber_conversion() {
        // Test with a known value: 10 m/s should be 36 km/h
        val inputMs = 10.0
        val expectedKmPerHour = 36.0
        val actualKmPerHour = msToKmPerHourNumber(inputMs)
        assertEquals(expectedKmPerHour, actualKmPerHour, 0.001, "Conversion for 10 m/s to km/h failed")
    }

    @Test
    fun testMsToKmPerHourNumber_zeroInput() {
        // Test with zero input
        val inputMs = 0.0
        val expectedKmPerHour = 0.0
        val actualKmPerHour = msToKmPerHourNumber(inputMs)
        assertEquals(expectedKmPerHour, actualKmPerHour, 0.001, "Conversion for 0 m/s should be 0 km/h")
    }

    @Test
    fun testSecondsToHHMMSS_noTrim() {
        assertEquals("00:00:00", secondsToHHMMSS(0, false))
        assertEquals("00:00:01", secondsToHHMMSS(1, false))
        assertEquals("00:01:00", secondsToHHMMSS(60, false))
        assertEquals("01:00:00", secondsToHHMMSS(3600, false))
        assertEquals("01:01:01", secondsToHHMMSS(3661, false))
        assertEquals("10:00:00", secondsToHHMMSS(36000, false))
    }

    @Test
    fun testSecondsToHHMMSS_withTrim() {
        assertEquals("00:00", secondsToHHMMSS(0, true))
        assertEquals("00:01", secondsToHHMMSS(1, true))
        assertEquals("01:00", secondsToHHMMSS(60, true))
        assertEquals("01:00:00", secondsToHHMMSS(3600, true)) // Hours are not trimmed
        assertEquals("01:01:01", secondsToHHMMSS(3661, true))
    }

    @Test
    fun testSecondsToHHMMSS_negativeInput() {
        assertEquals("00:00:00", secondsToHHMMSS(-100, false))
        assertEquals("00:00", secondsToHHMMSS(-100, true))
    }

    @Test
    fun testGetEnhancedAvgSpeed_typicalValues() {
        assertEquals(10.0, getEnhancedAvgSpeed(100.0, 10.0), 0.001)
        assertEquals(0.0, getEnhancedAvgSpeed(0.0, 10.0), 0.001)
    }

    @Test
    fun testGetEnhancedAvgSpeed_divisionByZero() {
        // Depending on desired behavior for division by zero, this might need adjustment
        // For Double, it results in Infinity
        assertEquals(Double.POSITIVE_INFINITY, getEnhancedAvgSpeed(100.0, 0.0))
    }

    @Test
    fun testMsToMinPerKmNumber_typicalValues() {
        // 10 m/s = 36 km/h. Pace = 60 min / 36 km = 1.666... min/km
        assertEquals(1.666666, msToMinPerKmNumber(10.0), 0.000001)
        // 5 m/s = 18 km/h. Pace = 60 min / 18 km = 3.333... min/km
        assertEquals(3.333333, msToMinPerKmNumber(5.0), 0.000001)
    }

    @Test
    fun testMsToMinPerKmNumber_zeroInput() {
        assertEquals(0.0, msToMinPerKmNumber(0.0), 0.001)
    }

    @Test
    fun testMsToMinPerKm_typicalValuesNoTrim() {
        // 10 m/s -> 1.666... min/km -> 1 min 40 seconds -> "00:01:40"
        assertEquals("00:01:40", msToMinPerKm(10.0, false))
        // 5 m/s -> 3.333... min/km -> 3 min 20 seconds -> "00:03:20"
        assertEquals("00:03:20", msToMinPerKm(5.0, false))
    }

    @Test
    fun testMsToMinPerKm_typicalValuesWithTrim() {
        // 10 m/s -> 1.666... min/km -> 1 min 40 seconds -> "01:40"
        assertEquals("01:40", msToMinPerKm(10.0, true))
         // 2.777... m/s (approx 10km/h) -> 6 min/km -> "06:00"
        assertEquals("06:00", msToMinPerKm(2.7777777777777777, true))
    }

    @Test
    fun testMsToMinPerKm_zeroInput() {
        assertEquals("0", msToMinPerKm(0.0, false))
        assertEquals("0", msToMinPerKm(0.0, true))
    }
    
    @Test
    fun testMsToMinPerKm_slowSpeedNoTrim() {
        // 0.1 m/s -> 166.66... min/km -> 2h 46m 40s -> "02:46:40"
        assertEquals("02:46:40", msToMinPerKm(0.1, false))
    }

    @Test
    fun testMsToMinPerKm_slowSpeedWithTrim() {
        // 0.1 m/s -> 166.66... min/km -> 2h 46m 40s -> "02:46:40" (hours not trimmed)
        assertEquals("02:46:40", msToMinPerKm(0.1, true))
    }


    @Test
    fun testHoursValueToSecondsValue_typicalValues() {
        // According to the ported TS logic: (value / 3600) * seconds
        // (1.0 / 3600.0) * 3600.0 = 1.0
        assertEquals(1.0, hoursValueToSecondsValue(1.0, 3600.0), 0.001)
        // (0.5 / 3600.0) * 3600.0 = 0.5
        assertEquals(0.5, hoursValueToSecondsValue(0.5, 3600.0), 0.001)
        // (1.0 / 3600.0) * 60.0 = 1.0 / 60.0
        assertEquals(1.0 / 60.0, hoursValueToSecondsValue(1.0, 60.0), 0.000001)
    }

    @Test
    fun testHoursValueToSecondsValue_zeroInput() {
        assertEquals(0.0, hoursValueToSecondsValue(0.0, 3600.0), 0.001)
        assertEquals(0.0, hoursValueToSecondsValue(1.0, 0.0), 0.001)
        assertEquals(0.0, hoursValueToSecondsValue(0.0, 0.0), 0.001)
    }
}
