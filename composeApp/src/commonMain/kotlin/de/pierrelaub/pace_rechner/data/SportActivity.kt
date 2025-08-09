package de.pierrelaub.pace_rechner.data

import de.pierrelaub.pace_rechner.types.SportsType

/**
 * Repräsentiert eine Sportaktivität mit den drei Hauptwerten: Distanz, Zeit und Pace/Speed
 */
data class SportActivity(
    val type: SportsType,
    val distance: Double, // in meters (für Lauf/Schwimmen) oder km (für Rad)
    val time: Int, // in seconds
    val paceOrSpeed: Double // pace in sec/100m (Schwimmen) oder sec/km (Lauf) oder speed in km/h (Rad)
) {
    /**
     * Berechnet die Zeit basierend auf Distanz und Pace/Speed
     */
    fun calculateTimeFromPace(): Int {
        return when (type) {
            SportsType.Swim -> (distance / 100.0 * paceOrSpeed).toInt()
            SportsType.Run -> (distance / 1000.0 * paceOrSpeed).toInt()
            SportsType.Bike -> (distance / paceOrSpeed * 3600).toInt()
            else -> time // Fallback für andere Sportarten
        }
    }

    /**
     * Berechnet Pace/Speed basierend auf Distanz und Zeit
     */
    fun calculatePaceFromTime(): Double {
        return when (type) {
            SportsType.Swim -> if (distance > 0) time / (distance / 100.0) else 0.0
            SportsType.Run -> if (distance > 0) time / (distance / 1000.0) else 0.0
            SportsType.Bike -> if (time > 0) distance / (time / 3600.0) else 0.0
            else -> paceOrSpeed // Fallback für andere Sportarten
        }
    }

    /**
     * Berechnet Distanz basierend auf Zeit und Pace/Speed
     */
    fun calculateDistanceFromPace(): Double {
        return when (type) {
            SportsType.Swim -> (time / paceOrSpeed) * 100.0
            SportsType.Run -> (time / paceOrSpeed) * 1000.0
            SportsType.Bike -> (paceOrSpeed * time) / 3600.0
            else -> distance // Fallback für andere Sportarten
        }
    }

    /**
     * Gibt die Einheit für Pace/Speed zurück
     */
    fun getPaceUnit(): String {
        return when (type) {
            SportsType.Swim -> "sek/100m"
            SportsType.Run, SportsType.Walking, SportsType.Hiking -> "sek/km"
            SportsType.Bike, SportsType.Rowing -> "km/h"
        }
    }

    /**
     * Gibt die Einheit für Distanz zurück
     */
    fun getDistanceUnit(): String {
        return when (type) {
            SportsType.Swim -> "m"
            SportsType.Run, SportsType.Walking, SportsType.Hiking -> "m"
            SportsType.Bike, SportsType.Rowing -> "km"
        }
    }
}
