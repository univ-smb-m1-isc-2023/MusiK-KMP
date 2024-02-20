package com.github.enteraname74.viewmodelimpl

import androidx.lifecycle.ViewModel
import com.github.enteraname74.domain.datasource.MusicInformationDataSource
import com.github.enteraname74.viewmodel.MainActivityViewModel

class MainActivityViewModelImpl(
    musicInformationDataSource: MusicInformationDataSource
) : ViewModel(), MainActivityViewModel {
}