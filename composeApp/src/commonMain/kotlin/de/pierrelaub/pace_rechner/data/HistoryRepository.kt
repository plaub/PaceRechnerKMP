package de.pierrelaub.pace_rechner.data

import androidx.compose.runtime.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.random.Random

object HistoryRepository {
    private const val KEY_HISTORY_LIST = "pace_calculations_history"

    private var storage: SettingsStorage? = null
    private var _calculations = mutableStateListOf<PaceCalculation>()
    val calculations: List<PaceCalculation> = _calculations

    fun initialize(settingsStorage: SettingsStorage) {
        storage = settingsStorage
        loadCalculations()
    }

    fun saveCalculation(
        name: String,
        swimDistance: Double,
        swimTime: Int,
        swimPace: Int,
        bikeDistance: Double,
        bikeTime: Int,
        bikeSpeed: Double,
        runDistance: Double,
        runTime: Int,
        runPace: Int,
        t1Time: Int,
        t2Time: Int,
        dayTimeStart: Int,
        presetType: String
    ) {
        val calculation = PaceCalculation(
            id = generateId(),
            name = name,
            timestamp = currentTimeMillis(),
            swimDistance = swimDistance,
            swimTime = swimTime,
            swimPace = swimPace,
            bikeDistance = bikeDistance,
            bikeTime = bikeTime,
            bikeSpeed = bikeSpeed,
            runDistance = runDistance,
            runTime = runTime,
            runPace = runPace,
            t1Time = t1Time,
            t2Time = t2Time,
            dayTimeStart = dayTimeStart,
            presetType = presetType
        )

        _calculations.add(0, calculation) // Neuste zuerst
        saveToStorage()
    }

    fun deleteCalculation(id: String) {
        _calculations.removeAll { it.id == id }
        saveToStorage()
    }

    private fun loadCalculations() {
        try {
            val jsonString = storage?.getString(KEY_HISTORY_LIST, "[]") ?: "[]"
            val loadedCalculations = Json.decodeFromString<List<PaceCalculation>>(jsonString)
            _calculations.clear()
            _calculations.addAll(loadedCalculations.sortedByDescending { it.timestamp })
        } catch (e: Exception) {
            // Falls JSON nicht parsebar ist, einfach leere Liste verwenden
            _calculations.clear()
        }
    }

    private fun saveToStorage() {
        try {
            val jsonString = Json.encodeToString(_calculations.toList())
            storage?.putString(KEY_HISTORY_LIST, jsonString)
        } catch (e: Exception) {
            // Fehler beim Speichern ignorieren
        }
    }

    private fun generateId(): String {
        // Einfache ID-Generierung ohne UUID
        return "${currentTimeMillis()}_${Random.nextInt(1000, 9999)}"
    }

    private fun currentTimeMillis(): Long {
        // Plattformunabhängige Zeit
        return kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
    }
}
