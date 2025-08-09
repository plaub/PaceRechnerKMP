package de.pierrelaub.pace_rechner.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import de.pierrelaub.pace_rechner.data.PaceCalculation
import de.pierrelaub.pace_rechner.data.SportActivity
import de.pierrelaub.pace_rechner.data.SettingsRepository
import de.pierrelaub.pace_rechner.types.SportsType
import de.pierrelaub.pace_rechner.types.CompetitionType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PaceRechnerViewModel {
    // Basis-Einstellungen
    var dayTimeStart by mutableStateOf(25200) // 07:00:00 in seconds
        private set
    var preset by mutableStateOf("")
        private set
    var presetExpanded by mutableStateOf(false)
        private set

    // Sportaktivitäten in der Reihenfolge des Wettkampfs
    var activities by mutableStateOf(listOf<SportActivity>())
        private set

    // Übergangszeiten
    var transitionTimes by mutableStateOf(mutableMapOf<Int, Int>()) // Index -> Zeit in Sekunden
        private set

    private val viewModelScope = CoroutineScope(Dispatchers.Main)

    init {
        loadDefaultSettings()

        // React to competition type changes from settings
        viewModelScope.launch {
            snapshotFlow { SettingsRepository.getCompetitionType() }
                .collect { competitionType ->
                    val defaultDistance = SettingsRepository.getDefaultDistance()
                    initializeForCompetitionType(competitionType, defaultDistance)
                }
        }
    }

    private fun loadDefaultSettings() {
        viewModelScope.launch {
            val competitionType = SettingsRepository.getCompetitionType()
            val defaultDistance = SettingsRepository.getDefaultDistance()

            // Initialize activities based on competition type
            initializeForCompetitionType(competitionType, defaultDistance)
        }
    }

    private fun initializeForCompetitionType(competitionType: CompetitionType, preset: String = "") {
        // Clear existing activities and transitions
        activities = emptyList()
        transitionTimes = mutableMapOf()

        when (competitionType) {
            CompetitionType.Triathlon -> {
                if (preset.isNotEmpty()) {
                    handleTriathlonPresetChange(preset)
                } else {
                    // Default triathlon setup
                    activities = listOf(
                        SportActivity(SportsType.Swim, 3800.0, 4560, 120.0),
                        SportActivity(SportsType.Bike, 180.0, 25920, 25.0),
                        SportActivity(SportsType.Run, 42195.0, 16034, 380.0)
                    )
                    transitionTimes = mutableMapOf(0 to 120, 1 to 90)
                }
            }
            CompetitionType.Duathlon -> {
                handleDuathlonPresetChange(preset)
            }
            CompetitionType.Swimming -> {
                activities = listOf(SportActivity(SportsType.Swim, 3800.0, 4560, 120.0))
            }
            CompetitionType.Cycling -> {
                activities = listOf(SportActivity(SportsType.Bike, 180.0, 25920, 25.0))
            }
            CompetitionType.Running -> {
                activities = listOf(SportActivity(SportsType.Run, 42195.0, 16034, 380.0))
            }
            CompetitionType.Rowing -> {
                activities = listOf(SportActivity(SportsType.Rowing, 2000.0, 7200, 15.0))
            }
            CompetitionType.Hiking -> {
                activities = listOf(SportActivity(SportsType.Hiking, 21097.5, 25200, 600.0))
            }
            CompetitionType.Walking -> {
                activities = listOf(SportActivity(SportsType.Walking, 10000.0, 21600, 720.0))
            }
        }
    }

    fun handlePresetChange(selectedPreset: String) {
        val competitionType = SettingsRepository.getCompetitionType()

        when (competitionType) {
            CompetitionType.Triathlon -> handleTriathlonPresetChange(selectedPreset)
            CompetitionType.Duathlon -> handleDuathlonPresetChange(selectedPreset)
            else -> {
                // For single sports, no presets to handle
            }
        }
        preset = selectedPreset
    }

    private fun handleTriathlonPresetChange(selectedPreset: String) {
        when (selectedPreset) {
            "sprint" -> {
                activities = listOf(
                    SportActivity(SportsType.Swim, 750.0, 900, 120.0),
                    SportActivity(SportsType.Bike, 20.0, 2880, 25.0),
                    SportActivity(SportsType.Run, 5000.0, 1500, 300.0)
                )
                transitionTimes = mutableMapOf(0 to 120, 1 to 90)
            }
            "olympic" -> {
                activities = listOf(
                    SportActivity(SportsType.Swim, 1500.0, 1800, 120.0),
                    SportActivity(SportsType.Bike, 40.0, 5760, 25.0),
                    SportActivity(SportsType.Run, 10000.0, 3000, 300.0)
                )
                transitionTimes = mutableMapOf(0 to 120, 1 to 90)
            }
            "md" -> {
                activities = listOf(
                    SportActivity(SportsType.Swim, 1900.0, 2280, 120.0),
                    SportActivity(SportsType.Bike, 90.0, 12960, 25.0),
                    SportActivity(SportsType.Run, 21097.5, 8017, 380.0)
                )
                transitionTimes = mutableMapOf(0 to 120, 1 to 90)
            }
            "ld" -> {
                activities = listOf(
                    SportActivity(SportsType.Swim, 3800.0, 4560, 120.0),
                    SportActivity(SportsType.Bike, 180.0, 25920, 25.0),
                    SportActivity(SportsType.Run, 42195.0, 16034, 380.0)
                )
                transitionTimes = mutableMapOf(0 to 120, 1 to 90)
            }
        }
    }

    private fun handleDuathlonPresetChange(selectedPreset: String) {
        when (selectedPreset) {
            "sprint_du" -> {
                activities = listOf(
                    SportActivity(SportsType.Run, 5000.0, 1500, 300.0),
                    SportActivity(SportsType.Bike, 20.0, 2880, 25.0),
                    SportActivity(SportsType.Run, 2500.0, 750, 300.0)
                )
                transitionTimes = mutableMapOf(0 to 60, 1 to 60)
            }
            "standard_du" -> {
                activities = listOf(
                    SportActivity(SportsType.Run, 10000.0, 3000, 300.0),
                    SportActivity(SportsType.Bike, 40.0, 5760, 25.0),
                    SportActivity(SportsType.Run, 5000.0, 1500, 300.0)
                )
                transitionTimes = mutableMapOf(0 to 90, 1 to 90)
            }
            "long_du" -> {
                activities = listOf(
                    SportActivity(SportsType.Run, 21097.5, 8017, 380.0),
                    SportActivity(SportsType.Bike, 90.0, 12960, 25.0),
                    SportActivity(SportsType.Run, 10000.0, 3800, 380.0)
                )
                transitionTimes = mutableMapOf(0 to 120, 1 to 120)
            }
            else -> {
                // Default duathlon
                activities = listOf(
                    SportActivity(SportsType.Run, 10000.0, 3000, 300.0),
                    SportActivity(SportsType.Bike, 40.0, 5760, 25.0),
                    SportActivity(SportsType.Run, 5000.0, 1500, 300.0)
                )
                transitionTimes = mutableMapOf(0 to 90, 1 to 90)
            }
        }
    }

    // Update-Funktionen
    fun updateDayTimeStart(value: Int) {
        dayTimeStart = value
    }

    fun updatePreset(value: String) {
        preset = value
    }

    fun updatePresetExpanded(value: Boolean) {
        presetExpanded = value
    }

    fun updateActivity(index: Int, activity: SportActivity) {
        if (index < activities.size) {
            val newActivities = activities.toMutableList()
            newActivities[index] = activity
            activities = newActivities
        }
    }

    fun updateActivityDistance(index: Int, distance: Double) {
        if (index < activities.size) {
            val activity = activities[index]
            updateActivity(index, activity.copy(distance = distance))
        }
    }

    fun updateActivityTime(index: Int, time: Int) {
        if (index < activities.size) {
            val activity = activities[index]
            updateActivity(index, activity.copy(time = time))
        }
    }

    fun updateActivityPaceOrSpeed(index: Int, paceOrSpeed: Double) {
        if (index < activities.size) {
            val activity = activities[index]
            updateActivity(index, activity.copy(paceOrSpeed = paceOrSpeed))
        }
    }

    fun updateTransitionTime(index: Int, time: Int) {
        val newTransitions = transitionTimes.toMutableMap()
        newTransitions[index] = time
        transitionTimes = newTransitions
    }

    fun addActivity(activity: SportActivity) {
        activities = activities + activity
    }

    fun removeActivity(index: Int) {
        if (index < activities.size) {
            activities = activities.filterIndexed { i, _ -> i != index }
        }
    }

    // Backward compatibility - einzelne Update-Funktionen
    fun updateSwimDistance(value: Double) = updateActivityDistance(0, value)
    fun updateSwimTime(value: Int) = updateActivityTime(0, value)
    fun updateSwimPace(value: Int) = updateActivityPaceOrSpeed(0, value.toDouble())

    fun updateBikeDistance(value: Double) = updateActivityDistance(1, value)
    fun updateBikeTime(value: Int) = updateActivityTime(1, value)
    fun updateBikeSpeed(value: Double) = updateActivityPaceOrSpeed(1, value)

    fun updateRunDistance(value: Double) = updateActivityDistance(2, value)
    fun updateRunTime(value: Int) = updateActivityTime(2, value)
    fun updateRunPace(value: Int) = updateActivityPaceOrSpeed(2, value.toDouble())

    fun updateT1Time(value: Int) = updateTransitionTime(0, value)
    fun updateT2Time(value: Int) = updateTransitionTime(1, value)

    // Load calculation from PaceCalculation object
    fun loadCalculation(calculation: PaceCalculation) {
        dayTimeStart = calculation.dayTimeStart
        activities = listOf(
            SportActivity(SportsType.Swim, calculation.swimDistance, calculation.swimTime, calculation.swimPace.toDouble()),
            SportActivity(SportsType.Bike, calculation.bikeDistance, calculation.bikeTime, calculation.bikeSpeed),
            SportActivity(SportsType.Run, calculation.runDistance, calculation.runTime, calculation.runPace.toDouble())
        )
        transitionTimes = mutableMapOf(0 to calculation.t1Time, 1 to calculation.t2Time)
        preset = calculation.presetType
    }

    // Backward compatibility - Getter für bestehende Properties
    val swimDistance: Double
        get() = activities.getOrNull(0)?.distance ?: 0.0

    val swimTime: Int
        get() = activities.getOrNull(0)?.time ?: 0

    val swimPace: Int
        get() = activities.getOrNull(0)?.paceOrSpeed?.toInt() ?: 0

    val bikeDistance: Double
        get() = activities.getOrNull(1)?.distance ?: 0.0

    val bikeTime: Int
        get() = activities.getOrNull(1)?.time ?: 0

    val bikeSpeed: Double
        get() = activities.getOrNull(1)?.paceOrSpeed ?: 0.0

    val runDistance: Double
        get() = activities.getOrNull(2)?.distance ?: 0.0

    val runTime: Int
        get() = activities.getOrNull(2)?.time ?: 0

    val runPace: Int
        get() = activities.getOrNull(2)?.paceOrSpeed?.toInt() ?: 0

    val t1Time: Int
        get() = transitionTimes[0] ?: 0

    val t2Time: Int
        get() = transitionTimes[1] ?: 0

    // Berechnete Werte
    val totalTime: Int
        get() = activities.sumOf { it.time } + transitionTimes.values.sum()

    val swimCumulativeTime: Int
        get() = getCumulativeTime(0)

    val t1CumulativeTime: Int
        get() = getCumulativeTime(0) + t1Time

    val bikeCumulativeTime: Int
        get() = getCumulativeTime(1) + t1Time

    val t2CumulativeTime: Int
        get() = getCumulativeTime(1) + t1Time + t2Time

    fun getCumulativeTime(upToIndex: Int): Int {
        var cumulative = 0
        for (i in 0 until minOf(upToIndex + 1, activities.size)) {
            cumulative += activities[i].time
        }
        return cumulative
    }

    fun getClockTime(upToIndex: Int): Int {
        return dayTimeStart + getCumulativeTime(upToIndex) + getTransitionTimeSum(upToIndex)
    }

    private fun getTransitionTimeSum(upToIndex: Int): Int {
        var sum = 0
        for (i in 0 until upToIndex) {
            sum += transitionTimes[i] ?: 0
        }
        return sum
    }

    // Split-Berechnungen
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
