package com.github.enteraname74

import com.github.enteraname74.viewmodel.MainScreenViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val appModule: Module = module {
    viewModel {
        MainScreenViewModelImpl(
            musicInformationDataSource = get()
        )
    }
}