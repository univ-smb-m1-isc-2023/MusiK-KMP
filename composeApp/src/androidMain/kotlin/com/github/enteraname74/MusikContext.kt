package com.github.enteraname74

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.github.enteraname74.type.ScreenOrientation
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Contains all elements related to a specific context of a MusiK application.
 */
actual object MusikContext {
    actual val orientation: ScreenOrientation
    @Composable
    get() {
        val orientation = LocalConfiguration.current.orientation
        return if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ScreenOrientation.HORIZONTAL
        } else {
            ScreenOrientation.VERTICAL
        }
    }

    /**
     * Painter to use for accessing drawable resources.
     */
    @SuppressLint("DiscouragedApi")
    @Composable
    actual fun appPainterResource(resourcePath: String): Painter {
        val context = LocalContext.current
        val drawableId = remember(resourcePath) {
            context.resources.getIdentifier(
                resourcePath,
                "drawable",
                context.packageName
            )
        }
        return painterResource(id = drawableId)
    }

    /**
     * Define the system bars color if there is any.
     */
    @Composable
    actual fun setSystemBarsColor(
        statusBarColor: Color,
        navigationBarColor: Color,
        isUsingDarkIcons: Boolean
    ) {
        val systemUiController = rememberSystemUiController()

        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = isUsingDarkIcons
        )

        systemUiController.setNavigationBarColor(
            color = navigationBarColor,
            darkIcons = isUsingDarkIcons
        )
    }

}