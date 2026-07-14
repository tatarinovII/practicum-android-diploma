package ru.practicum.android.diploma.domain.interactor

import ru.practicum.android.diploma.domain.models.FilterIndustry

interface FilterDataInteractor {
    suspend fun getIndustries(): Result<List<FilterIndustry>>
}
