package com.github.enteraname74

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.github.enteraname74.type.ScreenOrientation

/**
 * Contains all elements related to a specific context of a MusiK application.
 */
expect object MusikContext {
    @get:Composable
    val orientation: ScreenOrientation

    /**
     * Painter to use for accessing drawable resources.
     */
    @Composable
    fun appPainterResource(resourcePath: String): Painter
}