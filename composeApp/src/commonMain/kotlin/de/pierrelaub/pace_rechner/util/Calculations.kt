package de.pierrelaub.pace_rechner.util

import kotlin.math.roundToInt

// Helper function for zero-padding numbers
private fun Int.pad(digits: Int = 2): String = this.toString().padStart(digits, '0')

fun getEnhancedAvgSpeed(totalDistance: Double, totalDuration: Double): Double {
    return totalDistance / totalDuration
}

fun secondsToHHMMSS(s: Int, trim: Boolean): String {
    if (s < 0) return secondsToHHMMSS(0, trim) // Handle negative values, delegate to 0
    if (s == 0 && trim) return "00:00" // Special case for 0 and trim
    if (s == 0) return "00:00:00"

    val hours = s / 3600
    val minutes = (s % 3600) / 60
    val seconds = s % 60

    val formattedString = "${hours.pad()}:${minutes.pad()}:${seconds.pad()}"

    return if (trim && formattedString.startsWith("00:")) {
        formattedString.substring(3)
    } else {
        formattedString
    }
}

fun msToMinPerKmNumber(ms: Double): Double {
    if (ms == 0.0) return 0.0
    return 1 / (ms / 1000) / 60
}

fun msToKmPerHourNumber(ms: Double): Double {
    if (ms == 0.0) return 0.0
    return ms * 3.6
}

fun msToMinPerKm(ms: Double, trim: Boolean): String {
    if (ms == 0.0) return "0" // Kept original simple return for 0.0
    val minPerKm = 1 / (ms / 1000) / 60
    // Ensure the result of (minPerKm * 60) is not negative before rounding and passing
    val totalSeconds = (minPerKm * 60)
    if (totalSeconds < 0) return secondsToHHMMSS(0, trim) // Or handle error appropriately
    return secondsToHHMMSS(totalSeconds.roundToInt(), trim)
}

fun hoursValueToSecondsValue(value: Double, seconds: Double): Double {
    return (value / 3600) * seconds
}
