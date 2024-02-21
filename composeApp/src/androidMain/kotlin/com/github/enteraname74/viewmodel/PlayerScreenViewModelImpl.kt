package com.github.enteraname74.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.enteraname74.theme.ColorThemeManager
import com.github.enteraname74.viewmodelhandler.PlayerViewModelHandler

/**
 * Implementation of the PlayerScreenViewModel
 */
class PlayerScreenViewModelImpl(
) : ViewModel(), PlayerScreenViewModel {
    override val handler: PlayerViewModelHandler = PlayerViewModelHandler(
        coroutineScope = viewModelScope
    )
}