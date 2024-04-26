package com.github.enteraname74.composable

import androidx.compose.runtime.Composable

/**
 * Back handler for the application.
 */
@Composable
expect fun MusikBackHandler(
    enabled: Boolean = true,
    onBack: () -> Unit
)