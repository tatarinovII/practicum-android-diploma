package ru.practicum.android.diploma.data.network

open class Response<T>(
    var resultCode: Int = 0,
    var data: T? = null
)
