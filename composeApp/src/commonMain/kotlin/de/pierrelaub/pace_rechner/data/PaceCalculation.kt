package de.pierrelaub.pace_rechner.data

import kotlinx.serialization.Serializable

@Serializable
data class PaceCalculation(
    val id: String,
    val name: String,
    val timestamp: Long,
    val swimDistance: Double,
    val swimTime: Int,
    val swimPace: Int,
    val bikeDistance: Double,
    val bikeTime: Int,
    val bikeSpeed: Double,
    val runDistance: Double,
    val runTime: Int,
    val runPace: Int,
    val t1Time: Int,
    val t2Time: Int,
    val dayTimeStart: Int,
    val presetType: String
) {
    val totalTime: Int
        get() = swimTime + t1Time + bikeTime + t2Time + runTime
}
