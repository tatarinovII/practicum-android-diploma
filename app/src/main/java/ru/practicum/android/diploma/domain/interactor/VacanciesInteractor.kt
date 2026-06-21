package ru.practicum.android.diploma.domain.interactor

import ru.practicum.android.diploma.domain.models.SearchResult

interface VacanciesInteractor {
    suspend fun searchVacancies(query: String, page: Int): Result<SearchResult>
}
