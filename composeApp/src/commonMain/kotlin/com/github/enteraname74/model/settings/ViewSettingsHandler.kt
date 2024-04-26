package com.github.enteraname74.model.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.github.enteraname74.domain.model.User

/**
 * Handle view elements related to settings.
 */
class ViewSettingsHandler(
    private val settings: MusikSettings
) {

    var user by mutableStateOf<User?>(null)
        private set

    init {
        initSettings()
    }

    /**
     * Initialize the settings.
     */
    private fun initSettings() {
        with(settings) {
            val username = getString(
                key = MusikSettings.USERNAME,
                defaultValue = ""
            )
            val password = getString(
                key = MusikSettings.PASSWORD,
                defaultValue = ""
            )
            println("username: $username, password: $password")
            user = if (username.isBlank() || password.isBlank()) null else User(username, password)
        }
    }

    /**
     * Set the information of a user.
     */
    fun setUserInformation(username: String, password: String) {
        if (username.isNotBlank() && password.isNotBlank()) {
            user = User(username, password)
            settings.setString(
                key = MusikSettings.USERNAME,
                value = username
            )
            settings.setString(
                key = MusikSettings.PASSWORD,
                value = password
            )
        }
    }
}