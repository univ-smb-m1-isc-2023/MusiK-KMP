package com.github.enteraname74

import android.content.Context
import com.github.enteraname74.model.PlaybackController
import com.github.enteraname74.model.PlaybackControllerImpl
import com.github.enteraname74.model.settings.MusikSettings
import com.github.enteraname74.model.settings.MusikSettingsImpl
import com.github.enteraname74.model.settings.ViewSettingsHandler
import com.github.enteraname74.theme.ColorThemeManager
import com.github.enteraname74.viewmodel.HomeScreenModel
import com.github.enteraname74.viewmodel.PlayerScreenModel
import com.github.enteraname74.viewmodel.UserScreenModel
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual val appModule: Module = module {
    single {
        HomeScreenModel(
            musicInformationDataSource = get(),
            musicFileDataSource = get(),
            playlistDataSource = get(),
            albumDataSource = get(),
            artistDataSource = get()
        )
    }
    single {
        PlayerScreenModel()
    }
    single {
        UserScreenModel(
            userDataSource = get(),
            viewSettingsHandler = get(),
            authDataSource = get()
        )
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
    single<MusikSettings> {
        MusikSettingsImpl(
            settings = SharedPreferencesSettings(
                delegate = androidApplication().getSharedPreferences(
                    MusikSettings.SETTINGS_KEY,
                    Context.MODE_PRIVATE
                )
            )
        )
    }
    single {
        ViewSettingsHandler(
            settings = get()
        )
    }
}
