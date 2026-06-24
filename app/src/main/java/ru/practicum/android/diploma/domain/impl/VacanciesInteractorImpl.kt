package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.interactor.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.domain.repository.VacancyRepository

class VacanciesInteractorImpl(
    private val repository: VacancyRepository
) : VacanciesInteractor {

    override suspend fun searchVacancies(query: String, page: Int): Result<SearchResult> {
        return repository.searchVacancies(query, page)
    }

    override suspend fun getVacancyDetail(vacancyId: String): Result<VacancyDetail> {
        return repository.getVacancyDetail(vacancyId)
    }
}
