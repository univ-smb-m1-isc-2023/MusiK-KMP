package com.github.enteraname74.strings

import androidx.compose.ui.text.intl.Locale

/**
 * The application's strings, adapted from the current language used on the device.
 */
val appStrings = when(Locale.current.language) {
    "fr" -> FrStrings
    else -> EnStrings
}

/**
 * Strings used for the applications.
 */
interface ApplicationStrings {
    val appName: String get() = "MusiK"
    val welcome: String


    /***** Fetching strings ******/
    val fetchingAllMusics: String
}