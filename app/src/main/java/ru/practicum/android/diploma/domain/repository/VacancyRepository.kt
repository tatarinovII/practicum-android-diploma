package ru.practicum.android.diploma.domain.repository

import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.models.SearchVacanciesParams

interface VacancyRepository {
    suspend fun searchVacancies(params: SearchVacanciesParams): Result<SearchResult>
}
