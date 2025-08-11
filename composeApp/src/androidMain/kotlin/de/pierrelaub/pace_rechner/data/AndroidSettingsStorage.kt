package de.pierrelaub.pace_rechner.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit // Import the KTX extension

class AndroidSettingsStorage(context: Context) : SettingsStorage {
    private val preferences: SharedPreferences = context.getSharedPreferences(
        "pace_rechner_settings",
        Context.MODE_PRIVATE
    )

    override fun getString(key: String, defaultValue: String): String {
        return preferences.getString(key, defaultValue) ?: defaultValue
    }

    override fun putString(key: String, value: String) {
        preferences.edit { // Use the KTX extension
            putString(key, value)
        }
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return preferences.getInt(key, defaultValue)
    }

    override fun putInt(key: String, value: Int) {
        preferences.edit { // Use the KTX extension
            putInt(key, value)
        }
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        preferences.edit { // Use the KTX extension
            putBoolean(key, value)
        }
    }
}