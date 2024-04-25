package com.github.enteraname74.screens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.github.enteraname74.Constants
import com.github.enteraname74.composable.AllMusicView
import com.github.enteraname74.di.injectElement
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.domain.model.Playlist
import com.github.enteraname74.model.PlaybackController
import com.github.enteraname74.theme.MusikColorTheme
import com.github.enteraname74.type.FetchingState
import com.github.enteraname74.type.PlayerScreenSheetStates
import com.github.enteraname74.viewmodel.HomeScreenModel
import com.github.enteraname74.viewmodel.PlayerScreenModel
import kotlinx.coroutines.launch

class PlaylistTab(
    private val playlist: Playlist
) : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = "Playlist Tab"

            return remember {
                TabOptions(
                    index = 4u,
                    title = title,
                    icon = null
                )
            }
        }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()

        val screenModel = getScreenModel<HomeScreenModel>()

        val playerScreenModel = getScreenModel<PlayerScreenModel>()

        val state by screenModel.state.collectAsState()

        val playbackController = injectElement<PlaybackController>()

        val myId = "inlineContent"
        val musicCountText = buildAnnotatedString {
            append(playlist.musics.size.toString())

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
            Text(
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                color = MusikColorTheme.colorScheme.onPrimary,
                text = playlist.title
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = MusikColorTheme.colorScheme.onPrimary,
                text = musicCountText,
                inlineContent = inlineContent,
            )

            AllMusicView(
                music = playlist.musics,
                onClick = {
                    coroutineScope.launch {
                        playerScreenModel.playerScreenSwipeableState.animateTo(
                            targetValue = PlayerScreenSheetStates.EXPANDED,
                            anim = tween(Constants.AnimationDuration.normal)
                        )
                        playbackController.setPlayerLists((state.allMusicsState as FetchingState.Success<List<Music>>).data)
                        playbackController.setAndPlayMusic(it)
                    }
                }
            )
        }
    }
}
