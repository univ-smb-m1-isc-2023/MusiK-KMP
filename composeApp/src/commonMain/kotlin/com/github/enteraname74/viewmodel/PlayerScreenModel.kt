package com.github.enteraname74.viewmodel

import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
import cafe.adriel.voyager.core.model.ScreenModel
import com.github.enteraname74.Constants
import com.github.enteraname74.model.PlaybackController
import com.github.enteraname74.type.PlayerScreenSheetStates

@OptIn(ExperimentalMaterialApi::class)
class PlayerScreenModel constructor(
    val playbackController: PlaybackController, // Mettre dans un view (screen) model player
    //val playerScreenSwipeableState: SwipeableState<PlayerScreenSheetStates>
) : ScreenModel {
    val playerScreenSwipeableState: SwipeableState<PlayerScreenSheetStates> = SwipeableState(
        initialValue = PlayerScreenSheetStates.COLLAPSED,
        animationSpec = tween(Constants.AnimationDuration.normal)
    );
}