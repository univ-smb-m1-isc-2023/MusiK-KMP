package com.github.enteraname74

import com.github.enteraname74.model.PlaybackController
import com.github.enteraname74.model.PlaybackControllerImpl
import com.github.enteraname74.theme.ColorThemeManager
import com.github.enteraname74.viewmodel.MainScreenViewModelImpl
import com.github.enteraname74.viewmodel.PlayerScreenViewModelImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val appModule: Module = module {
    viewModel {
        MainScreenViewModelImpl(
            musicInformationDataSource = get()
        )
    }
    viewModel {
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
}