package com.github.enteraname74.composable.playbuttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.enteraname74.Constants
import com.github.enteraname74.model.PlaybackController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandedPlayButtonsComposable(
    modifier: Modifier = Modifier,
    widthFraction: Float = 1f,
    paddingBottom: Dp = 120.dp,
    primaryColor: Color,
    sliderInactiveBarColor: Color,
    playbackController: PlaybackController,
    isPlaying: Boolean,
    currentPosition: Int,
    musicDuration: Int
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
        
        val interactionSource = remember { MutableInteractionSource() }
        val sliderColors = SliderDefaults.colors(
            thumbColor = primaryColor,
            activeTrackColor = primaryColor,
            inactiveTrackColor = sliderInactiveBarColor
        )

        Slider(
            modifier = Modifier
                .fillMaxWidth(),
            value = currentPosition.toFloat(),
            onValueChange = {
                playbackController.seekToPosition(it.toInt())
            },
            colors = sliderColors,
            valueRange = 0f..playbackController.getMusicDuration().toFloat(),
            interactionSource = interactionSource,
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = interactionSource,
                    modifier = Modifier
                        .size(Constants.ImageSize.small)
                        .padding(
                            start = 4.dp,
                            top = 4.dp
                        ),
                    colors = sliderColors
                )
            }
        )
        
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = currentPosition.toMusicDuration(),
                    color = primaryColor,
                    style = MaterialTheme.typography.labelLarge,
                )
                Text(
                    text = musicDuration.toMusicDuration(),
                    color = primaryColor,
                    style = MaterialTheme.typography.labelLarge,
                )
            }

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
                    colorFilter = ColorFilter.tint(color = primaryColor)
                )
                if (isPlaying) {
                    Image(
                        imageVector = Icons.Rounded.Pause,
                        contentDescription = "",
                        modifier = Modifier
                            .size(78.dp)
                            .clickable { playbackController.togglePlayPause() },
                        colorFilter = ColorFilter.tint(color = primaryColor)
                    )
                } else {
                    Image(
                        imageVector = Icons.Rounded.PlayArrow,
                        contentDescription = "",
                        modifier = Modifier
                            .size(78.dp)
                            .clickable { playbackController.togglePlayPause() },
                        colorFilter = ColorFilter.tint(color = primaryColor)
                    )
                }
                Image(
                    imageVector = Icons.Rounded.SkipNext,
                    contentDescription = "",
                    modifier = Modifier
                        .size(Constants.ImageSize.large)
                        .clickable { playbackController.next() },
                    colorFilter = ColorFilter.tint(color = primaryColor)
                )
            }
        }
    }
}

/**
 * Convert a duration to a viewable duration.
 */
private fun Int.toMusicDuration(): String {
    val minutes: Float = this.toFloat() / 1000 / 60
    val seconds: Float = this.toFloat() / 1000 % 60

    val strMinutes: String = minutes.toString().split(".")[0]

    val strSeconds = if (seconds < 10.0) {
        "0" + seconds.toString().split(".")[0]
    } else {
        seconds.toString().split(".")[0]
    }

    return "$strMinutes:$strSeconds"
}