package com.github.enteraname74.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import com.github.enteraname74.domain.datasource.UserDataSource
import com.github.enteraname74.event.UserScreenEvent
import com.github.enteraname74.model.settings.ViewSettingsHandler
import com.github.enteraname74.state.UserScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserScreenModel(
    private val userDataSource: UserDataSource,
    private val viewSettingsHandler: ViewSettingsHandler
): ScreenModel {
    private val _state = MutableStateFlow(UserScreenState())
    val state = _state.asStateFlow()

    fun onEvent(event: UserScreenEvent) {
        when(event) {
            is UserScreenEvent.SetPassword -> setPassword(password = event.password)
            is UserScreenEvent.SetUsername -> setUsername(username = event.username)
            UserScreenEvent.ConnectUser -> connectUser()
        }
    }

    private fun connectUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val isConnected = userDataSource.create(
                username = _state.value.username,
                password = _state.value.password
            )
            println("isConnected: $isConnected")
            if (isConnected) {
                viewSettingsHandler.setUserInformation(
                    username = _state.value.username,
                    password = _state.value.password
                )
            }
        }
    }

    private fun setPassword(password: String) {
        _state.update {
            it.copy(
                password = password
            )
        }
    }

    private fun setUsername(username: String) {
        _state.update {
            it.copy(
                username = username
            )
        }
    }
}