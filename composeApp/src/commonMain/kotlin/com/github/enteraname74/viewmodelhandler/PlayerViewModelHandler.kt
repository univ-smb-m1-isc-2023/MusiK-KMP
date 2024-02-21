package com.github.enteraname74.viewmodelhandler

import android.util.Log
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.event.PlayerScreenEvent
import com.github.enteraname74.state.PlayerScreenState
import com.github.enteraname74.theme.ColorThemeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Handler for the player view model.
 */
open class PlayerViewModelHandler(
    private val coroutineScope: CoroutineScope
): ViewModelHandler {
    private val _state = MutableStateFlow(PlayerScreenState())
    val state = _state.asStateFlow()

    /**
     * Manage events on the player sreen.
     */
    fun onEvent(event: PlayerScreenEvent) {
        coroutineScope.launch {
            when(event) {
                is PlayerScreenEvent.UpdatePlayedMusic -> updatePlayerMusic(event.music)
                is PlayerScreenEvent.UpdateIsPlaying -> updatePlayingState(event.isPlaying)
            }
        }
    }

    /**
     * Update the currently played music to show on the player screen.
     */
    protected open fun updatePlayerMusic(currentMusic: Music?) {
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
    protected open fun updatePlayingState(playing: Boolean) {
        Log.d("VM HANDLER", "IS PLAYING: $playing")
        _state.update {
            it.copy(
                isPlaying = playing
            )
        }
    }
}