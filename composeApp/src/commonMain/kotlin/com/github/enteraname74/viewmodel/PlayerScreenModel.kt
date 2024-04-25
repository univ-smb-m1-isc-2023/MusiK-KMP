package com.github.enteraname74.viewmodel

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.github.enteraname74.Constants
import com.github.enteraname74.domain.datasource.LyricsDataSource
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.event.PlayerScreenEvent
import com.github.enteraname74.state.PlayerScreenState
import com.github.enteraname74.strings.appStrings
import com.github.enteraname74.type.FetchingState
import com.github.enteraname74.type.PlayerScreenSheetStates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
class PlayerScreenModel(
    private val lyricsDataSource: LyricsDataSource
) : ScreenModel {
    val playerScreenSwipeableState: SwipeableState<PlayerScreenSheetStates> =
        SwipeableState(
            initialValue = PlayerScreenSheetStates.COLLAPSED,
            animationSpec = tween(Constants.AnimationDuration.normal)
        )

    private val _state = MutableStateFlow(PlayerScreenState())
    val state = _state.asStateFlow()

    /**
     * Manage events on the player sreen.
     */
    fun onEvent(event: PlayerScreenEvent) {
        screenModelScope.launch {
            when(event) {
                is PlayerScreenEvent.UpdatePlayedMusic -> updatePlayerMusic(event.music)
                is PlayerScreenEvent.UpdateIsPlaying -> updatePlayingState(event.isPlaying)
                PlayerScreenEvent.FetchLyrics -> fetchLyrics()
            }
        }
    }

    /**
     * Fetch the lyrics of the current song.
     */
    private fun fetchLyrics() {
        _state.value.currentMusic?.let { currentMusic ->
            CoroutineScope(Dispatchers.IO).launch {
                _state.update {
                    it.copy(
                        lyrics = FetchingState.Loading(message = appStrings.fetchingLyrics)
                    )
                }
                val lyrics = lyricsDataSource.fetchLyrics(musicId = currentMusic.id)

                _state.update {
                    it.copy(
                        lyrics = if (lyrics.isBlank()) FetchingState.Error(message = appStrings.noLyricsFound) else FetchingState.Success(lyrics)
                    )
                }
            }
        }

    }

    /**
     * Update the currently played music to show on the player screen.
     */
    private fun updatePlayerMusic(currentMusic: Music?) {
        Log.d("VM HANDLER", "MUSIC: $currentMusic")
        _state.update {
            it.copy(
                currentMusic = currentMusic
            )
        }
    }

    /**
     * Update the state of playing.
     */
    private fun updatePlayingState(playing: Boolean) {
        Log.d("VM HANDLER", "IS PLAYING: $playing")
        _state.update {
            it.copy(
                isPlaying = playing
            )
        }
    }
}