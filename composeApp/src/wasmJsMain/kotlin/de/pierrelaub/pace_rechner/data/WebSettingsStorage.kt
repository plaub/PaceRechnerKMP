package de.pierrelaub.pace_rechner.data

import kotlinx.browser.localStorage

class WebSettingsStorage : SettingsStorage {
    override fun getString(key: String, defaultValue: String): String {
        return localStorage.getItem(key) ?: defaultValue
    }

    override fun putString(key: String, value: String) {
        localStorage.setItem(key, value)
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        val value = localStorage.getItem(key)
        return value?.toIntOrNull() ?: defaultValue
    }

    override fun putInt(key: String, value: Int) {
        localStorage.setItem(key, value.toString())
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        val value = localStorage.getItem(key)
        return value?.toBooleanStrictOrNull() ?: defaultValue
    }

    override fun putBoolean(key: String, value: Boolean) {
        localStorage.setItem(key, value.toString())
    }
}
