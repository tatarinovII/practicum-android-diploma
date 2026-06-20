package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.interactor.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.models.SearchVacanciesParams
import ru.practicum.android.diploma.domain.repository.VacancyRepository

class VacanciesInteractorImpl(
    private val repository: VacancyRepository
) : VacanciesInteractor {
    override suspend operator fun invoke(params: SearchVacanciesParams): Result<SearchResult> {
        return repository.searchVacancies(params)
    }
}
