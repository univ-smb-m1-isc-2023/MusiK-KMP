package com.github.enteraname74.model.settings

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get

/**
 * Implementation of the MusikSettings.
 */
class MusikSettingsImpl(
    private val settings: Settings
): MusikSettings {
    override fun setInt(key: String, value: Int) = settings.putInt(key, value)

    override fun setString(key: String, value: String) = settings.putString(key, value)

    override fun setBoolean(key: String, value: Boolean) = settings.putBoolean(key, value)

    override fun getInt(key: String, defaultValue: Int) = settings[key, defaultValue]

    override fun getBoolean(key: String, defaultValue: Boolean) = settings[key, defaultValue]

    override fun getString(key: String, defaultValue: String) =  settings[key, defaultValue]
}