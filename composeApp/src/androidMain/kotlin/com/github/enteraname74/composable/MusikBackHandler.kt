package com.github.enteraname74.composable

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
actual fun MusikBackHandler(
    enabled: Boolean,
    onBack: () -> Unit
) {
    BackHandler(enabled = enabled) {
        onBack()
    }
}