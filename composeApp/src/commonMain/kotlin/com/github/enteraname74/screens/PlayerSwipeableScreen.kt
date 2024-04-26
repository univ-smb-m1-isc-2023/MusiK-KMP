package com.github.enteraname74.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Lyrics
import androidx.compose.material.swipeable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.github.enteraname74.Constants
import com.github.enteraname74.MusikContext
import com.github.enteraname74.composable.AppImage
import com.github.enteraname74.composable.MusikBackHandler
import com.github.enteraname74.composable.playbuttons.ExpandedPlayButtonsComposable
import com.github.enteraname74.composable.playbuttons.MinimisedPlayButtonsComposable
import com.github.enteraname74.di.injectElement
import com.github.enteraname74.event.PlayerScreenEvent
import com.github.enteraname74.model.PlaybackController
import com.github.enteraname74.strings.appStrings
import com.github.enteraname74.theme.MusikColorTheme
import com.github.enteraname74.type.FetchingState
import com.github.enteraname74.type.PlayerScreenSheetStates
import com.github.enteraname74.type.ScreenOrientation
import com.github.enteraname74.viewmodel.PlayerScreenModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun PlayerSwipeableScreen(
    maxHeight: Float,
    playerScreenModel: PlayerScreenModel,
    playbackController: PlaybackController = injectElement()
) {
    val coroutineScope = rememberCoroutineScope()
    val state by playerScreenModel.state.collectAsState()
    val swipeableState = playerScreenModel.playerScreenSwipeableState
    var shouldShowDialog by rememberSaveable {
        mutableStateOf(false)
    }

    MusikBackHandler(
        enabled = swipeableState.currentValue == PlayerScreenSheetStates.EXPANDED
    ) {
        coroutineScope.launch {
            swipeableState.animateTo(
                PlayerScreenSheetStates.MINIMISED,
                tween(Constants.AnimationDuration.normal)
            )
        }
    }

    if (swipeableState.currentValue == PlayerScreenSheetStates.COLLAPSED
        && !swipeableState.isAnimationRunning
    ) {
        playbackController.stopPlayback()
    }

    val orientation = MusikContext.orientation
    val alphaTransition =
        if (swipeableState.currentValue == PlayerScreenSheetStates.MINIMISED && swipeableState.offset.value == 0f) {
            0f
        } else {
            when (orientation) {
                ScreenOrientation.HORIZONTAL -> {
                    if ((1f / (abs(swipeableState.offset.value) / 70)) > 0.1) {
                        (1f / (abs(swipeableState.offset.value) / 70)).coerceAtMost(1f)
                    } else {
                        0f
                    }
                }

                else -> {
                    if ((1f / (abs(
                            max(
                                swipeableState.offset.value.roundToInt(),
                                0
                            )
                        ) / 100)) > 0.1
                    ) {
                        (1f / (abs(max(swipeableState.offset.value.roundToInt(), 0)) / 100))
                            .coerceAtMost(1f)
                    } else {
                        0f
                    }
                }
            }
        }

    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    x = 0,
                    y = max(swipeableState.offset.value.roundToInt(), 0)
                )
            }
            .swipeable(
                state = swipeableState,
                orientation = Orientation.Vertical,
                anchors = mapOf(
                    (maxHeight - 200f) to PlayerScreenSheetStates.MINIMISED,
                    maxHeight to PlayerScreenSheetStates.COLLAPSED,
                    0f to PlayerScreenSheetStates.EXPANDED
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MusikColorTheme.colorScheme.secondary)
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .padding(Constants.Spacing.small)
                    .align(Alignment.TopStart)
            ) {
                val constraintsScope = this
                val maxWidth = with(LocalDensity.current) {
                    constraintsScope.maxWidth.toPx()
                }

                val imagePaddingStart =
                    when (MusikContext.orientation) {
                        ScreenOrientation.HORIZONTAL -> max(
                            (((maxWidth * 1.5) / 100) - (max(
                                swipeableState.offset.value.roundToInt(),
                                0
                            ) / 15)).roundToInt().dp,
                            Constants.Spacing.small
                        )

                        else -> max(
                            (((maxWidth * 3.5) / 100) - (max(
                                swipeableState.offset.value.roundToInt(),
                                0
                            ) / 40)).roundToInt().dp,
                            Constants.Spacing.small
                        )
                    }

                val imagePaddingTop =
                    when (MusikContext.orientation) {
                        ScreenOrientation.HORIZONTAL -> max(
                            (((maxHeight * 7) / 100) - (max(
                                swipeableState.offset.value.roundToInt(),
                                0
                            ) / 5)).roundToInt().dp,
                            Constants.Spacing.small
                        )

                        else -> max(
                            (((maxHeight * 5) / 100) - (max(
                                swipeableState.offset.value.roundToInt(),
                                0
                            ) / 15)).roundToInt().dp,
                            Constants.Spacing.small
                        )
                    }


                val imageSize =
                    when (MusikContext.orientation) {
                        ScreenOrientation.HORIZONTAL -> max(
                            (((maxWidth * 10) / 100) - (max(
                                swipeableState.offset.value.roundToInt(),
                                0
                            ) / 2)).dp,
                            55.dp
                        )

                        else -> max(
                            (((maxWidth * 30) / 100) - (max(
                                swipeableState.offset.value.roundToInt(),
                                0
                            ) / 7)).dp,
                            55.dp
                        )
                    }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = imagePaddingStart,
                            top = imagePaddingTop,
                            end = imagePaddingStart
                        )
                ) {
                    val url = state.currentMusic?.albumArtworkUrl ?: ""
                    if (url.isNotEmpty()) {
                        KamelImage(
                            modifier = Modifier.size(imageSize),
                            resource = asyncPainterResource(data = url),
                            contentDescription = null
                        )
                    } else {
                        AppImage(
                            bitmap = null,
                            size = imageSize,
                            roundedPercent = (swipeableState.offset.value / 100).roundToInt()
                                .coerceIn(3, 10)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(alphaTransition),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Image(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = "",
                            modifier = Modifier
                                .size(Constants.ImageSize.medium),
                            colorFilter = ColorFilter.tint(MusikColorTheme.colorScheme.onSecondary),
                            alpha = alphaTransition
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(
                                    start = Constants.Spacing.small,
                                    end = Constants.Spacing.small
                                ),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = state.currentMusic?.name ?: "",
                                color = MusikColorTheme.colorScheme.onSecondary,
                                maxLines = 1,
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .basicMarquee()
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = formatTextForEllipsis(
                                        state.currentMusic?.artist ?: "",
                                        MusikContext.orientation
                                    ),
                                    color = MusikColorTheme.colorScheme.subText,
                                    fontSize = 15.sp,
                                    maxLines = 1,
                                    textAlign = TextAlign.Center,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = " | ",
                                    color = MusikColorTheme.colorScheme.subText,
                                    fontSize = 15.sp,
                                )
                                Text(
                                    text = formatTextForEllipsis(
                                        state.currentMusic?.album ?: "",
                                        MusikContext.orientation
                                    ),
                                    color = MusikColorTheme.colorScheme.subText,
                                    fontSize = 15.sp,
                                    maxLines = 1,
                                    textAlign = TextAlign.Center,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                        Image(
                            imageVector = Icons.Rounded.Lyrics,
                            contentDescription = "",
                            modifier = Modifier
                                .size(Constants.ImageSize.medium)
                                .clickable {
                                    playerScreenModel.onEvent(
                                        PlayerScreenEvent.FetchLyrics
                                    )
                                    shouldShowDialog = true
                                },
                            colorFilter = ColorFilter.tint(MusikColorTheme.colorScheme.onSecondary),
                            alpha = alphaTransition
                        )
                    }

                    when (MusikContext.orientation) {
                        ScreenOrientation.HORIZONTAL -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.End
                            ) {
                                ExpandedPlayButtonsComposable(
                                    widthFraction = 0.45f,
                                    paddingBottom = 0.dp,
                                    playbackController = playbackController,
                                    isPlaying = state.isPlaying
                                )
                            }
                        }

                        else -> {
                            ExpandedPlayButtonsComposable(
                                playbackController = playbackController,
                                isPlaying = state.isPlaying
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .height(imageSize + Constants.Spacing.small)
                        .fillMaxWidth()
                        .padding(
                            start = imageSize + Constants.Spacing.large,
                            end = Constants.Spacing.small
                        )
                        .alpha((swipeableState.offset.value / maxHeight).coerceIn(0.0F, 1.0F)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(150.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = state.currentMusic?.name ?: "",
                            color = MusikColorTheme.colorScheme.onSecondary,
                            maxLines = 1,
                            textAlign = TextAlign.Start,
                            fontSize = 15.sp,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = state.currentMusic?.artist ?: "",
                            color = MusikColorTheme.colorScheme.onSecondary,
                            fontSize = 12.sp,
                            maxLines = 1,
                            textAlign = TextAlign.Start,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    MinimisedPlayButtonsComposable(
                        playerViewDraggableState = swipeableState,
                        playbackController = playbackController,
                        isPlaying = state.isPlaying
                    )
                }
            }
        }

        if (shouldShowDialog) {
            Dialog(
                onDismissRequest = { shouldShowDialog = false }
            ) {
                Column(
                    modifier = Modifier
                        .width(500.dp)
                        .background(
                            color = MusikColorTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(Constants.Spacing.medium),
                    verticalArrangement = Arrangement.spacedBy(Constants.Spacing.medium)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = appStrings.lyrics,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MusikColorTheme.colorScheme.onSecondary,
                    )
                    when (state.lyrics) {
                        is FetchingState.Error -> Text(
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .verticalScroll(rememberScrollState()),
                            text = (state.lyrics as FetchingState.Error).message,
                            color = MusikColorTheme.colorScheme.onSecondary,
                        )

                        is FetchingState.Loading -> Text(
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .verticalScroll(rememberScrollState()),
                            text = (state.lyrics as FetchingState.Loading).message,
                            color = MusikColorTheme.colorScheme.onSecondary,
                        )

                        is FetchingState.Success -> Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .verticalScroll(rememberScrollState()),
                            text = (state.lyrics as FetchingState.Success<String>).data,
                            color = MusikColorTheme.colorScheme.onSecondary,
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = {
                                shouldShowDialog = false
                            }
                        ) {
                            Text(
                                text = appStrings.close,
                                color = MusikColorTheme.colorScheme.onSecondary,
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun formatTextForEllipsis(text: String, orientation: ScreenOrientation): String {
    if (orientation == ScreenOrientation.HORIZONTAL) {
        return text
    }
    return if (text.length > 16) {
        "${text.subSequence(0, 16)}…"
    } else {
        text
    }
}
