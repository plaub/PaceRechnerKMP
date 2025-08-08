package de.pierrelaub.pace_rechner.data

import platform.Foundation.NSUserDefaults

class IOSSettingsStorage : SettingsStorage {
    private val userDefaults = NSUserDefaults.standardUserDefaults

    override fun getString(key: String, defaultValue: String): String {
        return userDefaults.stringForKey(key) ?: defaultValue
    }

    override fun putString(key: String, value: String) {
        userDefaults.setObject(value, key)
        userDefaults.synchronize()
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        val value = userDefaults.integerForKey(key)
        return if (userDefaults.objectForKey(key) != null) value.toInt() else defaultValue
    }

    override fun putInt(key: String, value: Int) {
        userDefaults.setInteger(value.toLong(), key)
        userDefaults.synchronize()
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return if (userDefaults.objectForKey(key) != null) {
            userDefaults.boolForKey(key)
        } else {
            defaultValue
        }
    }

    override fun putBoolean(key: String, value: Boolean) {
        userDefaults.setBool(value, key)
        userDefaults.synchronize()
    }
}
