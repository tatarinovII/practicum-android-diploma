package ru.practicum.android.diploma.domain.repository

import ru.practicum.android.diploma.domain.models.FilterArea

interface AreaRepository {
    suspend fun getAreas(): Result<List<FilterArea>>
}
