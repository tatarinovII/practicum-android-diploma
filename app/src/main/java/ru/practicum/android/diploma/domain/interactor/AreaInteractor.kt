package ru.practicum.android.diploma.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.FilterArea

interface AreaInteractor {
    suspend fun getAreas(): Flow<Result<List<FilterArea>>>
}
