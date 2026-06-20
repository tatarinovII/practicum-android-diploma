package ru.practicum.android.diploma.domain.repository

import ru.practicum.android.diploma.domain.models.SearchResult

interface VacancyRepository {
    suspend fun searchVacancies(
        text: String?,
        area: Int?,
        industry: Int?,
        salary: Int?,
        page: Int,
        onlyWithSalary: Boolean
    ): Result<SearchResult>
}
