package com.github.enteraname74.remotedatasource.model

import kotlinx.serialization.Serializable

/**
 * Represent a token to use for authorization header.
 */
@Serializable
data class RemoteToken(
    val token: String,
    val maxDate: String,
)
