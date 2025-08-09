package de.pierrelaub.pace_rechner.utils

object TimeFormatter {
    /**
     * Formatiert Sekunden zu einem HH:MM:SS String
     */
    fun formatSecondsToTimeString(totalSeconds: Int): String {
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
    }

    /**
     * Formatiert Sekunden zu einer Tageszeit (HH:MM:SS)
     * Behandelt Überlauf über 24 Stunden
     */
    fun formatSecondsToDayTime(totalSeconds: Int): String {
        val hours = (totalSeconds / 3600) % 24
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60
        return "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}"
    }
}
