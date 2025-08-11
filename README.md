# Pace Rechner KMP 🚀

Ein intuitiver und moderner Pace-Kalkulator, entwickelt mit Kotlin Multiplatform und Compose Multiplatform. Berechne deine Lauf-, Rad- oder Schwimmzeiten, Pace oder Distanzen – plattformübergreifend auf Android, iOS und Web!

## ✨ Features

*   **Pace berechnen:** Gib Distanz und Zeit ein, um deine Pace (z.B. min/km) zu ermitteln.
*   **Zeit berechnen:** Gib Distanz und Pace (oder Geschwindigkeit) ein, um die benötigte Zeit zu kalkulieren.
*   **Distanz berechnen:** Gib Zeit und Pace (oder Geschwindigkeit) ein, um die mögliche Distanz zu erfahren.
*   **Flexible Einheiten:**
    *   Distanz: Kilometer (km), Meilen (mi)
    *   Pace: Minuten pro Kilometer (min/km), Minuten pro Meile (min/mi)
    *   Geschwindigkeit: Kilometer pro Stunde (km/h), Meilen pro Stunde (mph) (Implementierung geplant)
*   **Intuitive Benutzeroberfläche:** Klare Eingabefelder und eine ansprechende Darstellung dank Compose Multiplatform.
*   **Anpassbar:** Einfache Adaption für verschiedene Sportarten (Laufen, Radfahren, Schwimmen etc.).

## 📱 Unterstützte Plattformen

*   **Android**
*   **iOS**
*   **Web** (via Kotlin/Wasm)

## 🛠️ Tech Stack

*   **Kotlin Multiplatform:** Für die gemeinsame Codebasis und Logik.
*   **Compose Multiplatform:** Für eine moderne, deklarative UI auf allen Plattformen.
*   **Material 3 Design:** Für ein zeitgemäßes Look-and-Feel.

## 🚀 Loslegen

Das Projekt kann direkt in Android Studio geöffnet und ausgeführt werden.

*   **Android:** Wähle das `composeApp`-Modul aus und starte es auf einem Android-Emulator oder einem physischen Gerät.
*   **iOS:**
    *   **Android Studio:** Wähle ein iOS-Ziel (z.B. `iosApp`) aus der Konfigurationsliste und starte es auf einem Simulator oder einem verbundenen Gerät.
    *   **Xcode:** Öffne die Datei `iosApp/iosApp.xcworkspace` in Xcode und baue/starte die App von dort.
*   **Web (Wasm):**
    1.  Öffne das Gradle-Toolfenster in Android Studio.
    2.  Führe den Task `:composeApp:wasmJsBrowserDevelopmentRun` unter `composeApp > Tasks > wasmJs` aus.
    3.  Öffne die im Terminal angezeigte URL (standardmäßig `http://localhost:8080`) in deinem Browser.

## 📂 Projektstruktur

*   `./composeApp`: Enthält den Hauptteil des Codes.
    *   `src/commonMain/kotlin`: Geteilte Logik (Berechnungen, ViewModels) und die gemeinsame Compose UI.
    *   `src/androidMain/kotlin`: Android-spezifischer Code.
    *   `src/iosMain/kotlin`: iOS-spezifischer Code.
    *   `src/wasmJsMain/kotlin`: WebAssembly-spezifischer Code.
*   `./iosApp`: Das Xcode-Projekt für die iOS-App.

## 🤝 Mitwirken & Feedback

Wir freuen uns über Feedback und Beiträge! Wenn du Ideen zur Verbesserung hast, Fehler findest oder mitwirken möchtest, erstelle bitte ein Issue im GitHub Repository.

Viel Spaß beim Kalkulieren deiner nächsten Bestzeit!