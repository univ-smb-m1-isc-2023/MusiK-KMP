package com.github.enteraname74.domain.datasource

import com.github.enteraname74.domain.model.User

/**
 * Data source for the authentication.
 */
interface AuthDataSource {
    /**
     * Authenticate a user.
     */
    suspend fun authenticate(user: User)
}