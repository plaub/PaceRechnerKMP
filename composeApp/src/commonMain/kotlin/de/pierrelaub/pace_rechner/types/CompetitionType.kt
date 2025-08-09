package de.pierrelaub.pace_rechner.types

import de.pierrelaub.pace_rechner.types.SportsType

enum class CompetitionType(
    val value: String,
    val displayName: String,
    val sportsTypes: List<SportsType>,
    val hasTransitions: Boolean = false
) {
    Triathlon("triathlon", "Triathlon", listOf(SportsType.Swim, SportsType.Bike, SportsType.Run), true),
    Duathlon("duathlon", "Duathlon", listOf(SportsType.Run, SportsType.Bike, SportsType.Run), true),
    Swimming("swimming", "Nur Schwimmen", listOf(SportsType.Swim)),
    Cycling("cycling", "Nur Radfahren", listOf(SportsType.Bike)),
    Running("running", "Nur Laufen", listOf(SportsType.Run)),
    Rowing("rowing", "Nur Rudern", listOf(SportsType.Rowing)),
    Hiking("hiking", "Nur Wandern", listOf(SportsType.Hiking)),
    Walking("walking", "Nur Gehen", listOf(SportsType.Walking));

    /**
     * Gibt die Anzahl der Übergänge zurück
     */
    fun getTransitionCount(): Int {
        return if (hasTransitions) maxOf(0, sportsTypes.size - 1) else 0
    }

    /**
     * Gibt die Standard-Presets für diesen Wettkampftyp zurück
     */
    fun getAvailablePresets(): List<Pair<String, String>> {
        return when (this) {
            Triathlon -> listOf(
                "sprint" to "Sprint",
                "olympic" to "Olympisch",
                "md" to "Mitteldistanz",
                "ld" to "Langdistanz"
            )
            Duathlon -> listOf(
                "sprint_du" to "Sprint Duathlon",
                "standard_du" to "Standard Duathlon",
                "long_du" to "Lang Duathlon"
            )
            else -> emptyList() // Einzelsportarten haben keine Presets
        }
    }
}
