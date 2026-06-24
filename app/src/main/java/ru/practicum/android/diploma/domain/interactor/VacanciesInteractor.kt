package ru.practicum.android.diploma.domain.interactor

import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.models.VacancyDetail

interface VacanciesInteractor {
    suspend fun searchVacancies(query: String, page: Int): Result<SearchResult>
    suspend fun getVacancyDetail(vacancyId: String): Result<VacancyDetail>
}
