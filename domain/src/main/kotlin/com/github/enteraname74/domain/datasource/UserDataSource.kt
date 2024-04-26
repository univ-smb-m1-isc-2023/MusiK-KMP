package com.github.enteraname74.domain.datasource

/**
 * Data source for user.
 */
interface UserDataSource {

    /**
     * Create a user.
     */
    suspend fun create(username: String, password: String): Boolean
}