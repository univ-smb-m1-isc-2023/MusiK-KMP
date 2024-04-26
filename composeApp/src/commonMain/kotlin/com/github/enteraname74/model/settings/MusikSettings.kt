package com.github.enteraname74.model.settings

/**
 * Represent the settings of a Musik application where we can save key-value elements.
 */
interface MusikSettings {
    /**
     * Save an Integer related to a given key.
     */
    fun setInt(key: String, value: Int)

    /**
     * Save a String related to a given key.
     */
    fun setString(key: String, value: String)

    /**
     * Save a Boolean related to a given key.
     */
    fun setBoolean(key: String, value: Boolean)

    /**
     * Tries to retrieve an Integer related to a given key.
     */
    fun getInt(key: String, defaultValue: Int): Int

    /**
     * Tries to retrieve a Boolean related to a given key.
     */
    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    /**
     * Tries to retrieve a String related to a given key.
     */
    fun getString(key: String, defaultValue: String): String

    companion object {
        const val SETTINGS_KEY = "MUSIK_SETTINGS_KEY"

        const val USERNAME = "USERNAME"
        const val PASSWORD = "PASSWORD"
    }
}