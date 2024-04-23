package com.github.enteraname74.event

/**
 * Events related to the UserScreen.
 */
sealed interface UserScreenEvent {
    data class SetUsername(val username: String): UserScreenEvent
    data class SetPassword(val password: String): UserScreenEvent
    data object ConnectUser: UserScreenEvent
}