package com.github.enteraname74.viewmodel

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.github.enteraname74.Constants
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.event.PlayerScreenEvent
import com.github.enteraname74.state.PlayerScreenState
import com.github.enteraname74.type.PlayerScreenSheetStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
class PlayerScreenModel : ScreenModel {
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