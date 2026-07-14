package ru.practicum.android.diploma.domain.repository

import ru.practicum.android.diploma.domain.models.FilterIndustry

interface FilterDataRepository {
    suspend fun getIndustries(): Result<List<FilterIndustry>>
}
