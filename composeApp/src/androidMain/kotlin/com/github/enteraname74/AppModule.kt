package com.github.enteraname74

import com.github.enteraname74.viewmodelimpl.MainActivityViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

actual val appModule: Module = module {
    viewModel {
        MainActivityViewModelImpl(
            musicInformationDataSource = get()
        )
    }
}