package com.github.enteraname74.composable

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.github.enteraname74.Constants
import com.github.enteraname74.di.injectElement
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.model.PlaybackController
import com.github.enteraname74.theme.MusikColorTheme
import com.github.enteraname74.type.PlayerScreenSheetStates
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlaylistView(
    title: String,
    musics: List<Music>,
    playerSwipeableState: SwipeableState<PlayerScreenSheetStates>,
    playbackController: PlaybackController = injectElement(),
    onBack: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    BackHandler {
        onBack()
    }

    val myId = "inlineContent"
    val musicCountText = buildAnnotatedString {
        append(musics.size.toString())

        appendInlineContent(myId, "[icon]")
    }

    val inlineContent = mapOf(
        Pair(
            myId,
            InlineTextContent(
                Placeholder(
                    width = 20.sp,
                    height = 20.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                )
            ) {
                Icon(Icons.Rounded.MusicNote, "", tint = MusikColorTheme.colorScheme.onPrimary)
            }
        )
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                contentDescription = null,
                tint = MusikColorTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .size(Constants.ImageSize.medium)
                    .clickable {
                        onBack()
                    }
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center,
                    color = MusikColorTheme.colorScheme.onPrimary,
                    text = title
                )
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MusikColorTheme.colorScheme.onPrimary,
                    text = musicCountText,
                    inlineContent = inlineContent,
                )
            }
            Spacer(
                modifier = Modifier.width(Constants.ImageSize.medium)
            )
        }

        AllMusicView(
            music = musics,
            onClick = {
                coroutineScope.launch {
                    playerSwipeableState.animateTo(
                        targetValue = PlayerScreenSheetStates.EXPANDED,
                        anim = tween(Constants.AnimationDuration.normal)
                    )
                    playbackController.setPlayerLists(musics)
                    playbackController.setAndPlayMusic(it)
                }
            }
        )
    }
}