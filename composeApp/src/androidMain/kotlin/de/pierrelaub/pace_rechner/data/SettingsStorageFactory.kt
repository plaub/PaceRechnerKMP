package de.pierrelaub.pace_rechner.data

import android.content.Context

fun createSettingsStorage(context: Context): SettingsStorage {
    return AndroidSettingsStorage(context)
}
