package com.github.enteraname74.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.enteraname74.domain.datasource.MusicInformationDataSource
import com.github.enteraname74.viewmodelhandler.MainScreenViewModelHandler

/**
 * Implementation of the MainScreenViewModel for Android.
 */
class MainScreenViewModelImpl(
    musicInformationDataSource: MusicInformationDataSource
) : ViewModel(), MainScreenViewModel {
    override val handler: MainScreenViewModelHandler = MainScreenViewModelHandler(
        coroutineScope = viewModelScope,
        musicInformationDataSource = musicInformationDataSource
    )
}