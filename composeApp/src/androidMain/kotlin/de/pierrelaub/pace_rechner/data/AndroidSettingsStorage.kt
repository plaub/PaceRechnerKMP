package de.pierrelaub.pace_rechner.data

import android.content.Context
import android.content.SharedPreferences

class AndroidSettingsStorage(context: Context) : SettingsStorage {
    private val preferences: SharedPreferences = context.getSharedPreferences(
        "pace_rechner_settings",
        Context.MODE_PRIVATE
    )

    override fun getString(key: String, defaultValue: String): String {
        return preferences.getString(key, defaultValue) ?: defaultValue
    }

    override fun putString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        return preferences.getInt(key, defaultValue)
    }

    override fun putInt(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preferences.getBoolean(key, defaultValue)
    }

    override fun putBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }
}
