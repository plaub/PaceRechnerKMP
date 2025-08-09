package de.pierrelaub.pace_rechner.types

enum class SportsType(
    val value: String,
    val distanceUnit: String,
    val paceSpeedUnit: String,
    val isPace: Boolean, // true für Pace (langsamer = schlechter), false für Speed (schneller = besser)
    val defaultPaceSpeed: Double
) {
    Swim("Schwimmen", "m", "sek/100m", true, 120.0),
    Bike("Radfahren", "km", "km/h", false, 25.0),
    Run("Laufen", "m", "sek/km", true, 300.0),
    Rowing("Rudern", "km", "km/h", false, 15.0),
    Hiking("Wandern", "m", "sek/km", true, 600.0),
    Walking("Gehen", "m", "sek/km", true, 720.0);

    /**
     * Berechnet die Zeit basierend auf Distanz und Pace/Speed
     */
    fun calculateTime(distance: Double, paceOrSpeed: Double): Int {
        return when (this) {
            Swim -> (distance / 100.0 * paceOrSpeed).toInt()
            Run, Hiking, Walking -> (distance / 1000.0 * paceOrSpeed).toInt()
            Bike, Rowing -> (distance / paceOrSpeed * 3600).toInt()
        }
    }

    /**
     * Berechnet Pace/Speed basierend auf Distanz und Zeit
     */
    fun calculatePaceSpeed(distance: Double, time: Int): Double {
        return when (this) {
            Swim -> if (distance > 0) time / (distance / 100.0) else 0.0
            Run, Hiking, Walking -> if (distance > 0) time / (distance / 1000.0) else 0.0
            Bike, Rowing -> if (time > 0) distance / (time / 3600.0) else 0.0
        }
    }

    /**
     * Berechnet Distanz basierend auf Zeit und Pace/Speed
     */
    fun calculateDistance(time: Int, paceOrSpeed: Double): Double {
        return when (this) {
            Swim -> (time / paceOrSpeed) * 100.0
            Run, Hiking, Walking -> (time / paceOrSpeed) * 1000.0
            Bike, Rowing -> (paceOrSpeed * time) / 3600.0
        }
    }
}