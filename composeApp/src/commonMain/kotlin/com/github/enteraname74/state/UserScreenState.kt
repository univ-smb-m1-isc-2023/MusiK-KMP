package com.github.enteraname74.state

/**
 * UI state for the user screen.
 */
data class UserScreenState(
    val username: String = "",
    val password: String = "",
    val shouldGoToMainScreen: Boolean = false
)
