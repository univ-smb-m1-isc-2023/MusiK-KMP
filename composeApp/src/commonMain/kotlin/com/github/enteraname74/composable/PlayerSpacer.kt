package com.github.enteraname74.composable

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Spacer used to give bottom padding for an element to avoid it from behind hide behind the player
 * swipeable screen.
 */
@Composable
fun PlayerSpacer() {
    Spacer(
        modifier = Modifier
            .height(80.dp)
    )
}
