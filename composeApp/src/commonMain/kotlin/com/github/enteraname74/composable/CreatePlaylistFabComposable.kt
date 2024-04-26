package com.github.enteraname74.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.PlaylistAdd
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.enteraname74.theme.MusikColorTheme
import com.github.enteraname74.type.PlayerScreenSheetStates

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreatePlaylistFabComposable(
    modifier: Modifier = Modifier,
    playerScreenSwipeableState: SwipeableState<PlayerScreenSheetStates>,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = MusikColorTheme.colorScheme.onSecondary,
        modifier = modifier.padding(bottom = if (playerScreenSwipeableState.currentValue != PlayerScreenSheetStates.COLLAPSED) 65.dp else 0.dp)
    ) {
        Icon(Icons.AutoMirrored.Rounded.PlaylistAdd, "Create playlist button.")
    }
}
