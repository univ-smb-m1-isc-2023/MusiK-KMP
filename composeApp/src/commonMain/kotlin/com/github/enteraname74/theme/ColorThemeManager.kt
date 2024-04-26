package com.github.enteraname74.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.github.enteraname74.Constants

/**
 * Manage the color theme of the application.
 */
class ColorThemeManager {
    private val primary: Color
    @Composable
    get() = animateColorAsState(
        targetValue = MusikColorTheme.defaultTheme.primary,
        animationSpec = tween(Constants.AnimationDuration.short),
        label = "PRIMARY_DYNAMIC_COLOR"
    ).value

    private val onPrimary: Color
        @Composable
        get() = animateColorAsState(
            targetValue = MusikColorTheme.defaultTheme.onPrimary,
            animationSpec = tween(Constants.AnimationDuration.short),
            label = "ON_PRIMARY_DYNAMIC_COLOR"
        ).value

    private val secondary: Color
        @Composable
        get() = animateColorAsState(
            targetValue = MusikColorTheme.defaultTheme.secondary,
            animationSpec = tween(Constants.AnimationDuration.short),
            label = "SECONDARY_DYNAMIC_COLOR"
        ).value

    private val onSecondary: Color
        @Composable
        get() = animateColorAsState(
            targetValue = MusikColorTheme.defaultTheme.onSecondary,
            animationSpec = tween(Constants.AnimationDuration.short),
            label = "ON_SECONDARY_DYNAMIC_COLOR"
        ).value

    private val subText: Color
        @Composable
        get() = animateColorAsState(
            targetValue = MusikColorTheme.defaultTheme.subText,
            animationSpec = tween(Constants.AnimationDuration.short),
            label = "SUB_TEXT_DYNAMIC_COLOR"
        ).value

    /**
    * Retrieve the color theme of the application.
    */
    @Composable
    fun getColorTheme(): MusikPalette = MusikPalette(
        primary = primary,
        secondary = secondary,
        onPrimary = onPrimary,
        onSecondary = onSecondary,
        subText = subText
    )
}