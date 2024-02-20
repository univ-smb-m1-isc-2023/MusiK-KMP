package com.github.enteraname74.viewmodel

import com.github.enteraname74.viewmodelhandler.MainScreenViewModelHandler
import com.github.enteraname74.viewmodelhandler.ViewModelHandler

/**
 * A ViewModel is represented by an Interface.
 * The benefits of it is that it can be used anywhere (Android, desktop) instead of
 * an abstract class (on android, we need to inherit from the ViewModel class, making it
 * inherit also from an abstract class).
 *
 * A ViewModel possess only an handler that is used for handling the logic of the ViewModel.
 * This handler can be an abstract class that implementations will need to specialize.
 * If the class is not abstract, it will make the ViewModel usable on any platform without specific implementation.
 */
interface MusikViewModel<Handler: ViewModelHandler> {
    val handler: Handler
}

/**
 * ViewModel for the main screen.
 */
typealias MainScreenViewModel = MusikViewModel<MainScreenViewModelHandler>