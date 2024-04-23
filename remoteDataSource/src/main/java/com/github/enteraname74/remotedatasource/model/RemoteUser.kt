package com.github.enteraname74.remotedatasource.model

import com.github.enteraname74.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
data class RemoteUser(
    val name: String,
    val password: String
)

fun RemoteUser.toUser() = User(name, password)

fun User.toRemoteUser() = RemoteUser(name, password)