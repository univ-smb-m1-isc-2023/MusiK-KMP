package com.github.enteraname74.viewmodelhandler

import com.github.enteraname74.event.PlayerScreenEvent
import com.github.enteraname74.state.PlayerScreenState
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
                is PlayerScreenEvent.UpdatePlayedMusic -> updatePlayerMusic(event)
                is PlayerScreenEvent.UpdateIsPlaying -> updatePlayingState(event.isPlaying)
                is PlayerScreenEvent.UpdatePositionInCurrentMusic -> updateCurrentPositionInMusic(event.position)
            }
        }
    }

    /**
     * Update the currently played music to show on the player screen.
     */
    protected open fun updatePlayerMusic(data: PlayerScreenEvent.UpdatePlayedMusic) {
        _state.update {
            it.copy(
                currentMusic = data.music,
                currentMusicDuration = data.duration
            )
        }
    }

    /**
     * Update the state of playing.
     */
    protected open fun updatePlayingState(playing: Boolean) {
        _state.update {
            it.copy(
                isPlaying = playing
            )
        }
    }

    /**
     * Update the current position in the music.
     */
    protected open fun updateCurrentPositionInMusic(position: Int) {
        _state.update {
            it.copy(
                currentPositionInMusic = position
            )
        }
    }
}