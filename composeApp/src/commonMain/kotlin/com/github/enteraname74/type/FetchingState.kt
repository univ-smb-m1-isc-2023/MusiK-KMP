package com.github.enteraname74.type

/**
 * State to represent a fetching operation.
 * @param T the type of a successful result.
 */
sealed class FetchingState<out T> {

    /**
     * Used when an operation succeed.
     */
    data class Success<T>(val data: T): FetchingState<T>()

    /**
     * Used when an operation returned an error.
     */
    data class Error(val message: String): FetchingState<Nothing>()

    /**
     * Used when doing an operation.
     */
    data class Loading(val message: String): FetchingState<Nothing>()
}