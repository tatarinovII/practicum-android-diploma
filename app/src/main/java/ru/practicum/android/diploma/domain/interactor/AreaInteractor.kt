package ru.practicum.android.diploma.domain.interactor

import kotlinx.coroutines.flow.Flow

interface AreaInteractor {
    suspend fun getCountries(): Flow<List<FilterArea>>
}
