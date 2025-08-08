# Pace Rechner KMP 🚀

Ein intuitiver und moderner Pace-Kalkulator, entwickelt mit Kotlin Multiplatform und Compose Multiplatform. Berechne deine Lauf-, Rad- oder Schwimmzeiten, Pace oder Distanzen – plattformübergreifend auf Android, iOS und Web!

## ✨ Features

*   **Pace berechnen:** Gib Distanz und Zeit ein, um deine Pace (z.B. min/km) zu ermitteln.
*   **Zeit berechnen:** Gib Distanz und Pace (oder Geschwindigkeit) ein, um die benötigte Zeit zu kalkulieren.
*   **Distanz berechnen:** Gib Zeit und Pace (oder Geschwindigkeit) ein, um die mögliche Distanz zu erfahren.
*   **Unterstützung für verschiedene Einheiten:**
    *   Distanz: Kilometer (km), Meilen (mi)
    *   Pace: Minuten pro Kilometer (min/km), Minuten pro Meile (min/mi)
    *   Geschwindigkeit: Kilometer pro Stunde (km/h), Meilen pro Stunde (mph) (zukünftig)
*   **Benutzerfreundliche Oberfläche:** Klare Eingabefelder und eine ansprechende Darstellung dank Compose Multiplatform.
*   **Flexibel:** Einfache Anpassung für verschiedene Sportarten (Laufen, Radfahren etc.).

## 📱 Unterstützte Plattformen

*   **Android**
*   **iOS**
*   **Web** (via Kotlin/Wasm)

## 🛠️ Tech Stack

*   **Kotlin Multiplatform:** Für die gemeinsame Logik auf allen Plattformen.
*   **Compose Multiplatform:** Für eine moderne, deklarative UI, die auf Android, iOS und Web geteilt wird.
*   **Material 3 Design:** Für ein zeitgemäßes Look-and-Feel.

## 🚀 Loslegen

Du kannst das Projekt direkt in Android Studio öffnen.

*   **Android:** Wähle das `composeApp`-Modul aus und starte es auf einem Android-Emulator oder einem physischen Gerät.
*   **iOS:**
    *   In Android Studio: Wähle ein iOS-Ziel (z.B. `iosApp`) aus der Konfigurationsliste und starte es auf einem Simulator oder einem verbundenen Gerät.
    *   Alternativ: Öffne die Datei `iosApp/iosApp.xcworkspace` in Xcode und baue/starte die App von dort.
*   **Web (Wasm):**
    1.  Öffne das Gradle-Toolfenster in Android Studio.
    2.  Führe den Task `:composeApp:wasmJsBrowserDevelopmentRun` aus.
    3.  Öffne die im Terminal angezeigte URL (standardmäßig `http://localhost:8080`) in deinem Browser.

## 📂 Projektstruktur (Kurzübersicht)

*   `./composeApp`: Enthält den Großteil des Codes.
    *   `src/commonMain/kotlin`: Hier liegt die geteilte Logik (Berechnungen, ViewModels) und die Compose UI, die auf allen Plattformen verwendet wird.
    *   `src/androidMain/kotlin`: Android-spezifischer Code (falls nötig).
    *   `src/iosMain/kotlin`: iOS-spezifischer Code (falls nötig).
    *   `src/wasmJsMain/kotlin`: WebAssembly-spezifischer Code (falls nötig).
*   `./iosApp`: Das Xcode-Projekt für die iOS-App.

## 🤝 Feedback & Mitwirkung

Feedback ist immer willkommen! Wenn du Ideen hast oder Fehler findest, erstelle gerne ein Issue.

---

Viel Spaß beim Kalkulieren deiner nächsten Bestzeit!