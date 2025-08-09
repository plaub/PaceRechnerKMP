package de.pierrelaub.pace_rechner.resources

interface Strings {
    // General
    val appName: String
    val save: String
    val cancel: String
    val delete: String
    val edit: String
    val ok: String

    // Navigation
    val calculator: String
    val history: String
    val settings: String

    // Calculator Screen
    val templates: String
    val saveCalculation: String
    val enterName: String
    val exampleName: String

    // Sports
    val swim: String
    val bike: String
    val run: String
    val run1: String
    val run2: String
    val rowing: String
    val hiking: String
    val walking: String

    // Transitions
    val transition1: String
    val transition2: String

    // Form Labels
    val distance: String
    val time: String
    val pace: String
    val speed: String
    val targetTime: String
    val targetPace: String
    val targetSpeed: String

    // Units
    val km: String
    val m: String
    val hours: String
    val minutes: String
    val seconds: String
    val minPerKm: String
    val minPer100m: String
    val kmh: String

    // Summary
    val summary: String
    val totalTime: String
    val averageSpeed: String
    val finishTime: String
    val startTime: String

    // History
    val historyTitle: String
    val noHistoryEntries: String
    val deleteEntry: String
    val deleteEntryConfirm: String
    val savedCalculations: String
    val saveFirstCalculation: String

    // Settings
    val settingsTitle: String
    val competition: String
    val units: String
    val theme: String
    val language: String
    val configureSettings: String
    val defaultTemplate: String
    val chooseLanguage: String
    val chooseCompetition: String
    val chooseLightDark: String
    val noPresetsAvailable: String
    val lightTheme: String
    val darkTheme: String

    // Competition Types
    val triathlon: String
    val duathlon: String
    val aquathlon: String
    val individual: String
    val swimming: String
    val cycling: String
    val running: String
    val rowingCompetition: String
    val hikingCompetition: String
    val walkingCompetition: String

    // Validation Messages
    val invalidDistance: String
    val invalidTime: String
    val invalidPace: String
    val invalidSpeed: String

    // Presets
    val sprintTriathlon: String
    val olympicTriathlon: String
    val halfIronman: String
    val ironman: String
    val sprintDuathlon: String
    val standardDuathlon: String
    val longDuathlon: String
    val sprintAquathlon: String
    val standardAquathlon: String
}

object StringsGerman : Strings {
    override val appName = "Pace Rechner"
    override val save = "Speichern"
    override val cancel = "Abbrechen"
    override val delete = "Löschen"
    override val edit = "Bearbeiten"
    override val ok = "OK"

    override val calculator = "Rechner"
    override val history = "Verlauf"
    override val settings = "Einstellungen"

    override val templates = "Vorlagen"
    override val saveCalculation = "Berechnung speichern"
    override val enterName = "Name eingeben"
    override val exampleName = "Training"

    override val swim = "Schwimmen"
    override val bike = "Radfahren"
    override val run = "Laufen"
    override val run1 = "Laufen 1"
    override val run2 = "Laufen 2"
    override val rowing = "Rudern"
    override val hiking = "Wandern"
    override val walking = "Gehen"

    override val transition1 = "T1"
    override val transition2 = "T2"

    override val distance = "Distanz"
    override val time = "Zeit"
    override val pace = "Pace"
    override val speed = "Geschwindigkeit"
    override val targetTime = "Zielzeit"
    override val targetPace = "Ziel-Pace"
    override val targetSpeed = "Zielgeschwindigkeit"

    override val km = "km"
    override val m = "m"
    override val hours = "Stunden"
    override val minutes = "Minuten"
    override val seconds = "Sekunden"
    override val minPerKm = "min/km"
    override val minPer100m = "min/100m"
    override val kmh = "km/h"

    override val summary = "Zusammenfassung"
    override val totalTime = "Gesamtzeit"
    override val averageSpeed = "Durchschnittsgeschwindigkeit"
    override val finishTime = "Zielzeit"
    override val startTime = "Startzeit"

    override val historyTitle = "Verlauf"
    override val noHistoryEntries = "Keine Einträge vorhanden"
    override val deleteEntry = "Eintrag löschen"
    override val deleteEntryConfirm = "Möchten Sie diesen Eintrag wirklich löschen?"
    override val savedCalculations = "Gespeicherte Berechnungen"
    override val saveFirstCalculation = "Speichere deine erste Berechnung im Rechner-Tab!"

    override val settingsTitle = "Einstellungen"
    override val competition = "Wettkampf"
    override val units = "Einheiten"
    override val theme = "Design"
    override val language = "Sprache"
    override val configureSettings = "Einstellungen konfigurieren"
    override val defaultTemplate = "Standardvorlage"
    override val chooseLanguage = "Sprache wählen"
    override val chooseCompetition = "Wettkampfart wählen"
    override val chooseLightDark = "Helles/Dunkles Design wählen"
    override val noPresetsAvailable = "Keine Presets verfügbar"
    override val lightTheme = "Helles Design"
    override val darkTheme = "Dunkles Design"

    override val triathlon = "Triathlon"
    override val duathlon = "Duathlon"
    override val aquathlon = "Aquathlon"
    override val individual = "Einzelsport"
    override val swimming = "Schwimmen"
    override val cycling = "Radfahren"
    override val running = "Laufen"
    override val rowingCompetition = "Rudern"
    override val hikingCompetition = "Wandern"
    override val walkingCompetition = "Gehen"

    override val invalidDistance = "Ungültige Distanz"
    override val invalidTime = "Ungültige Zeit"
    override val invalidPace = "Ungültiger Pace"
    override val invalidSpeed = "Ungültige Geschwindigkeit"

    override val sprintTriathlon = "Sprint Triathlon"
    override val olympicTriathlon = "Olympische Distanz"
    override val halfIronman = "Halb-Ironman (70.3)"
    override val ironman = "Ironman"
    override val sprintDuathlon = "Sprint Duathlon"
    override val standardDuathlon = "Standard Duathlon"
    override val longDuathlon = "Langer Duathlon"
    override val sprintAquathlon = "Sprint Aquathlon"
    override val standardAquathlon = "Standard Aquathlon"
}

object StringsEnglish : Strings {
    override val appName = "Pace Calculator"
    override val save = "Save"
    override val cancel = "Cancel"
    override val delete = "Delete"
    override val edit = "Edit"
    override val ok = "OK"

    override val calculator = "Calculator"
    override val history = "History"
    override val settings = "Settings"

    override val templates = "Templates"
    override val saveCalculation = "Save calculation"
    override val enterName = "Enter name"
    override val exampleName = "Training"

    override val swim = "Swim"
    override val bike = "Bike"
    override val run = "Run"
    override val run1 = "Run 1"
    override val run2 = "Run 2"
    override val rowing = "Rowing"
    override val hiking = "Hiking"
    override val walking = "Walking"

    override val transition1 = "T1"
    override val transition2 = "T2"

    override val distance = "Distance"
    override val time = "Time"
    override val pace = "Pace"
    override val speed = "Speed"
    override val targetTime = "Target time"
    override val targetPace = "Target pace"
    override val targetSpeed = "Target speed"

    override val km = "km"
    override val m = "m"
    override val hours = "Hours"
    override val minutes = "Minutes"
    override val seconds = "Seconds"
    override val minPerKm = "min/km"
    override val minPer100m = "min/100m"
    override val kmh = "km/h"

    override val summary = "Summary"
    override val totalTime = "Total time"
    override val averageSpeed = "Average speed"
    override val finishTime = "Finish time"
    override val startTime = "Start time"

    override val historyTitle = "History"
    override val noHistoryEntries = "No entries available"
    override val deleteEntry = "Delete entry"
    override val deleteEntryConfirm = "Do you really want to delete this entry?"
    override val savedCalculations = "Saved calculations"
    override val saveFirstCalculation = "Calculate something first to save."

    override val settingsTitle = "Settings"
    override val competition = "Competition"
    override val units = "Units"
    override val theme = "Theme"
    override val language = "Language"
    override val configureSettings = "Configure settings"
    override val defaultTemplate = "Default template"
    override val chooseLanguage = "Choose language"
    override val chooseCompetition = "Choose competition"
    override val chooseLightDark = "Choose light/dark theme"
    override val noPresetsAvailable = "No presets available"
    override val lightTheme = "Light theme"
    override val darkTheme = "Dark theme"

    override val triathlon = "Triathlon"
    override val duathlon = "Duathlon"
    override val aquathlon = "Aquathlon"
    override val individual = "Individual Sport"
    override val swimming = "Swimming"
    override val cycling = "Cycling"
    override val running = "Running"
    override val rowingCompetition = "Rowing"
    override val hikingCompetition = "Hiking"
    override val walkingCompetition = "Walking"

    override val invalidDistance = "Invalid distance"
    override val invalidTime = "Invalid time"
    override val invalidPace = "Invalid pace"
    override val invalidSpeed = "Invalid speed"

    override val sprintTriathlon = "Sprint Triathlon"
    override val olympicTriathlon = "Olympic Distance"
    override val halfIronman = "Half Ironman (70.3)"
    override val ironman = "Ironman"
    override val sprintDuathlon = "Sprint Duathlon"
    override val standardDuathlon = "Standard Duathlon"
    override val longDuathlon = "Long Duathlon"
    override val sprintAquathlon = "Sprint Aquathlon"
    override val standardAquathlon = "Standard Aquathlon"
}
