package ru.practicum.android.diploma.domain.repository

import ru.practicum.android.diploma.domain.models.SearchResult

interface VacancyRepository {
    suspend fun searchVacancies(query: String, page: Int, perPage: Int = 20): Result<SearchResult>
}
