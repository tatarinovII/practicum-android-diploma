package ru.practicum.android.diploma.domain.repository

import kotlinx.coroutines.flow.Flow

interface AreaRepository {
    suspend fun getCountries(): Flow<List<String>>
    fun setCountry(country: String)
    fun clearCountry()
}
