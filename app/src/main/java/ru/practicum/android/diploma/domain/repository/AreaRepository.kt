package ru.practicum.android.diploma.domain.repository

import ru.practicum.android.diploma.domain.models.FilterArea

interface AreaRepository {
    suspend fun getCountries(): Result<List<FilterArea>>
    fun setCountry(country: String)
    fun clearCountry()
}
