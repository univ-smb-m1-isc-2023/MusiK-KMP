package com.github.enteraname74.composable.playbuttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.SkipNext
import androidx.compose.material.icons.rounded.SkipPrevious
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.enteraname74.Constants
import com.github.enteraname74.model.PlaybackController
import com.github.enteraname74.theme.MusikColorTheme


@Composable
fun ExpandedPlayButtonsComposable(
    modifier: Modifier = Modifier,
    widthFraction: Float = 1f,
    paddingBottom: Dp = 120.dp,
    playbackController: PlaybackController,
    isPlaying: Boolean
) {
    Column(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth(widthFraction)
            .padding(
                start = 28.dp,
                end = 28.dp,
                bottom = paddingBottom
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 5.dp,
                    end = 5.dp
                ),
            verticalArrangement = Arrangement.spacedBy(Constants.Spacing.medium),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    imageVector = Icons.Rounded.SkipPrevious,
                    contentDescription = "",
                    modifier = Modifier
                        .size(Constants.ImageSize.large)
                        .clickable { playbackController.previous() },
                    colorFilter = ColorFilter.tint(color = MusikColorTheme.colorScheme.onSecondary)
                )
                if (isPlaying) {
                    Image(
                        imageVector = Icons.Rounded.Pause,
                        contentDescription = "",
                        modifier = Modifier
                            .size(78.dp)
                            .clickable { playbackController.togglePlayPause() },
                        colorFilter = ColorFilter.tint(color = MusikColorTheme.colorScheme.onSecondary)
                    )
                } else {
                    Image(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = "",
                        modifier = Modifier
                            .size(78.dp)
                            .clickable { playbackController.togglePlayPause() },
                        colorFilter = ColorFilter.tint(color = MusikColorTheme.colorScheme.onSecondary)
                    )
                }
                Image(
                    imageVector = Icons.Rounded.SkipNext,
                    contentDescription = "",
                    modifier = Modifier
                        .size(Constants.ImageSize.large)
                        .clickable { playbackController.next() },
                    colorFilter = ColorFilter.tint(color = MusikColorTheme.colorScheme.onSecondary)
                )
            }
        }
    }
}