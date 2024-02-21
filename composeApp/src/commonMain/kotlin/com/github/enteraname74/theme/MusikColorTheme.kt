package com.github.enteraname74.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

/**
 * Dynamic colors used in the application.
 */
object MusikColorTheme {
    var colorScheme = MusikPalette()

    private val darkTheme = MusikPalette()
    private val lightTheme = MusikPalette(
        primary = primaryColorLight,
        secondary = secondaryColorLight,
        onPrimary = textColorLight,
        onSecondary = textColorLight,
        subText = subTextColorLight
    )

    /**
     * Retrieve the correct MusikPalette based on the device theme.
     */
    val defaultTheme: MusikPalette
        @Composable
        get() = if (isSystemInDarkTheme()) darkTheme else lightTheme
}