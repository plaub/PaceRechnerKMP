package de.pierrelaub.pace_rechner.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.pierrelaub.pace_rechner.data.PaceCalculation
import de.pierrelaub.pace_rechner.data.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaceRechnerViewModel {
    // State variables for all the data - initialized based on settings
    var dayTimeStart by mutableStateOf(25200) // 07:00:00 in seconds
        private set
    var swimDistance by mutableStateOf(3800.0)
        private set
    var swimTime by mutableStateOf(4560)
        private set
    var swimPace by mutableStateOf(120)
        private set
    var bikeDistance by mutableStateOf(180.0)
        private set
    var bikeTime by mutableStateOf(25920)
        private set
    var bikeSpeed by mutableStateOf(25.0)
        private set
    var runDistance by mutableStateOf(42195.0)
        private set
    var runTime by mutableStateOf(16034)
        private set
    var runPace by mutableStateOf(380)
        private set
    var t1Time by mutableStateOf(120) // 2 minutes
        private set
    var t2Time by mutableStateOf(90) // 1.5 minutes
        private set

    var preset by mutableStateOf("")
        private set
    var presetExpanded by mutableStateOf(false)
        private set

    // Coroutine scope for async operations
    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    init {
        loadDefaultSettings()
    }

    private fun loadDefaultSettings() {
        viewModelScope.launch {
            val defaultDistance = SettingsRepository.getDefaultDistance()
            handlePresetChange(defaultDistance)
        }
    }

    // Update functions for all state variables
    fun updateDayTimeStart(value: Int) {
        dayTimeStart = value
    }

    fun updateSwimDistance(value: Double) {
        swimDistance = value
    }

    fun updateSwimTime(value: Int) {
        swimTime = value
    }

    fun updateSwimPace(value: Int) {
        swimPace = value
    }

    fun updateBikeDistance(value: Double) {
        bikeDistance = value
    }

    fun updateBikeTime(value: Int) {
        bikeTime = value
    }

    fun updateBikeSpeed(value: Double) {
        bikeSpeed = value
    }

    fun updateRunDistance(value: Double) {
        runDistance = value
    }

    fun updateRunTime(value: Int) {
        runTime = value
    }

    fun updateRunPace(value: Int) {
        runPace = value
    }

    fun updateT1Time(value: Int) {
        t1Time = value
    }

    fun updateT2Time(value: Int) {
        t2Time = value
    }

    fun updatePreset(value: String) {
        preset = value
    }

    fun updatePresetExpanded(value: Boolean) {
        presetExpanded = value
    }

    fun handlePresetChange(selectedPreset: String) {
        when (selectedPreset) {
            "sprint" -> {
                swimDistance = 750.0
                swimPace = 120
                swimTime = 900

                bikeDistance = 20.0
                bikeSpeed = 25.0
                bikeTime = 2880

                runDistance = 5000.0
                runPace = 300
                runTime = 1500
            }
            "olympic" -> {
                swimDistance = 1500.0
                swimPace = 120
                swimTime = 1800

                bikeDistance = 40.0
                bikeSpeed = 25.0
                bikeTime = 5760

                runDistance = 10000.0
                runPace = 300
                runTime = 3000
            }
            "md" -> {
                swimDistance = 1900.0
                swimPace = 120
                swimTime = 2280

                bikeDistance = 90.0
                bikeSpeed = 25.0
                bikeTime = 12960

                runDistance = 21097.5
                runPace = 380
                runTime = 8017
            }
            "ld" -> {
                swimDistance = 3800.0
                swimPace = 120
                swimTime = 4560

                bikeDistance = 180.0
                bikeSpeed = 25.0
                bikeTime = 25920

                runDistance = 42195.0
                runPace = 380
                runTime = 16034
            }
        }
    }

    // Load calculation from PaceCalculation object
    fun loadCalculation(calculation: PaceCalculation) {
        dayTimeStart = calculation.dayTimeStart
        swimDistance = calculation.swimDistance
        swimTime = calculation.swimTime
        swimPace = calculation.swimPace
        bikeDistance = calculation.bikeDistance
        bikeTime = calculation.bikeTime
        bikeSpeed = calculation.bikeSpeed
        runDistance = calculation.runDistance
        runTime = calculation.runTime
        runPace = calculation.runPace
        t1Time = calculation.t1Time
        t2Time = calculation.t2Time
        preset = calculation.presetType
    }

    // Calculated values
    val totalTime: Int
        get() = swimTime + t1Time + bikeTime + t2Time + runTime

    val swimCumulativeTime: Int
        get() = swimTime

    val t1CumulativeTime: Int
        get() = swimTime + t1Time

    val bikeCumulativeTime: Int
        get() = swimTime + t1Time + bikeTime

    val t2CumulativeTime: Int
        get() = swimTime + t1Time + bikeTime + t2Time

    // Time calculations for splits
    val bikeQuarter1Km: Double
        get() = bikeDistance * 0.25

    val bikeHalfKm: Double
        get() = bikeDistance * 0.5

    val bikeThreeQuarterKm: Double
        get() = bikeDistance * 0.75

    val runQuarter1Km: Double
        get() = runDistance * 0.25 / 1000.0

    val runHalfKm: Double
        get() = runDistance * 0.5 / 1000.0

    val runThreeQuarterKm: Double
        get() = runDistance * 0.75 / 1000.0

    val bike25Time: Int
        get() = (bikeTime * 0.25).toInt()

    val bike50Time: Int
        get() = (bikeTime * 0.5).toInt()

    val bike75Time: Int
        get() = (bikeTime * 0.75).toInt()

    val run25Time: Int
        get() = (runTime * 0.25).toInt()

    val run50Time: Int
        get() = (runTime * 0.5).toInt()

    val run75Time: Int
        get() = (runTime * 0.75).toInt()

    // Clock times
    val totalTimeAfterSwim: Int
        get() = dayTimeStart + swimCumulativeTime

    val timeAfterT1: Int
        get() = dayTimeStart + t1CumulativeTime

    val totalTimeAfterBike: Int
        get() = dayTimeStart + bikeCumulativeTime

    val timeAfterT2: Int
        get() = dayTimeStart + t2CumulativeTime

    val dayTimeFinish: Int
        get() = dayTimeStart + totalTime
}
