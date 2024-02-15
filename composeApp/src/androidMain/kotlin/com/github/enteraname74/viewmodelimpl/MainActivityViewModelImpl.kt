package com.github.enteraname74.viewmodelimpl

import androidx.lifecycle.ViewModel
import com.github.enteraname74.domain.service.MusicService
import com.github.enteraname74.viewmodel.MainActivityViewModel

class MainActivityViewModelImpl(
    musicService: MusicService
) : ViewModel(), MainActivityViewModel {
}