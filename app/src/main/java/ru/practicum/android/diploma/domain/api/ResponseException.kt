package ru.practicum.android.diploma.domain.api

data class ResponseException(
    val responseCode: Int,
    val exception: Exception
) : Exception(exception)
