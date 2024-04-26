package com.github.enteraname74.composable.playbuttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.github.enteraname74.Constants
import com.github.enteraname74.model.PlaybackController
import com.github.enteraname74.theme.MusikColorTheme
import com.github.enteraname74.type.PlayerScreenSheetStates

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MinimisedPlayButtonsComposable(
    modifier: Modifier = Modifier,
    playerViewDraggableState: SwipeableState<PlayerScreenSheetStates>,
    playbackController: PlaybackController,
    isPlaying: Boolean
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Constants.Spacing.medium)
    ) {
        Image(
            imageVector = Icons.Rounded.SkipPrevious,
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    if (playerViewDraggableState.currentValue == PlayerScreenSheetStates.MINIMISED) {
                        playbackController.previous()
                    }
                },
            colorFilter = ColorFilter.tint(color = MusikColorTheme.colorScheme.onSecondary)
        )
        if (isPlaying) {
            Image(
                imageVector = Icons.Rounded.Pause,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        if (playerViewDraggableState.currentValue == PlayerScreenSheetStates.MINIMISED) {
                            playbackController.togglePlayPause()
                        }
                    },
                colorFilter = ColorFilter.tint(color = MusikColorTheme.colorScheme.onSecondary)
            )
        } else {
            Image(
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        if (playerViewDraggableState.currentValue == PlayerScreenSheetStates.MINIMISED) {
                            playbackController.togglePlayPause()
                        }
                    },
                colorFilter = ColorFilter.tint(color = MusikColorTheme.colorScheme.onSecondary)
            )
        }
        Image(
            imageVector = Icons.Rounded.SkipNext,
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .clickable {
                    if (playerViewDraggableState.currentValue == PlayerScreenSheetStates.MINIMISED) {
                        playbackController.next()
                    }
                },
            colorFilter = ColorFilter.tint(color = MusikColorTheme.colorScheme.onSecondary)
        )
    }
}