package com.github.enteraname74

import androidx.compose.animation.core.tween
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.rememberSwipeableState
import com.github.enteraname74.model.PlaybackController
import com.github.enteraname74.model.PlaybackControllerImpl
import com.github.enteraname74.theme.ColorThemeManager
import com.github.enteraname74.type.PlayerScreenSheetStates
import com.github.enteraname74.viewmodel.HomeScreenModel
import com.github.enteraname74.viewmodel.PlayerScreenModel
import com.github.enteraname74.viewmodel.PlayerScreenViewModelImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

//@OptIn(ExperimentalMaterialApi::class)
@OptIn(ExperimentalMaterialApi::class)
actual val appModule: Module = module {
    factory {
        HomeScreenModel(
            musicInformationDataSource = get(),
            musicFileDataSource = get()
        )
    }
    factory {
        PlayerScreenModel(
            playbackController = get()
        )
    }
    factory {
        PlayerScreenViewModelImpl()
    }
    single<ColorThemeManager> {
        ColorThemeManager()
    }
    single<PlaybackControllerImpl> {
        PlaybackControllerImpl(
            context = androidContext()
        )
    }
    single<PlaybackController> {
        PlaybackControllerImpl(
            context = androidContext()
        )
    }
}