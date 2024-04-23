package com.github.enteraname74.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.github.enteraname74.domain.datasource.MusicFileDataSource
import com.github.enteraname74.domain.datasource.MusicInformationDataSource
import com.github.enteraname74.domain.model.File
import com.github.enteraname74.event.MainScreenEvent
import com.github.enteraname74.state.MainScreenState
import com.github.enteraname74.strings.appStrings
import com.github.enteraname74.type.FetchingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class HomeScreenModel(
    private val musicInformationDataSource: MusicInformationDataSource,
    private val musicFileDataSource: MusicFileDataSource
) : ScreenModel {
    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    /**
     * Manage events of the main screen.
     */
    fun onEvent(event: MainScreenEvent) {
        screenModelScope.launch {
            when (event) {
                MainScreenEvent.FetchMusics -> fetchAllMusics()
                is MainScreenEvent.UploadMusic -> uploadFile(event.file)
            }
        }
    }

    /**
     * Fetch all musics.
     */
    protected open suspend fun fetchAllMusics() {
        _state.update {
            it.copy(
                allMusicsState = FetchingState.Loading(
                    message = appStrings.fetchingAllMusics
                )
            )
        }
        _state.update {
            it.copy(
                allMusicsState = FetchingState.Success(
                    musicInformationDataSource.getAll()
                )
            )
        }
    }

    protected open suspend fun uploadFile(file: File) {
        musicFileDataSource.uploadFile(file)
    }

    // Optional
    override fun onDispose() {
        // ...
    }
}