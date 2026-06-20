package ru.practicum.android.diploma.data

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val code: Int, val message: String? = null) : NetworkResult<Nothing>()
    object NoInternet : NetworkResult<Nothing>()
}
