package ru.practicum.android.diploma.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.FilterArea

interface AreaRepository {
    suspend fun getAreas(): Flow<Result<List<FilterArea>>>
}
