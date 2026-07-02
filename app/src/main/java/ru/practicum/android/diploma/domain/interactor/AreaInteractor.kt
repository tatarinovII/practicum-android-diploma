package ru.practicum.android.diploma.domain.interactor

import ru.practicum.android.diploma.domain.models.FilterArea

interface AreaInteractor {
    suspend fun getAreas(): Result<List<FilterArea>>
}
