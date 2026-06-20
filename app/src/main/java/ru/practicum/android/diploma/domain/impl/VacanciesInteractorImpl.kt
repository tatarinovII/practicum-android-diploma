package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.interactor.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.repository.VacancyRepository

class VacanciesInteractorImpl(
    private val repository: VacancyRepository
) : VacanciesInteractor {
    override suspend operator fun invoke(
        text: String?,
        area: Int?,
        industry: Int?,
        salary: Int?,
        page: Int,
        onlyWithSalary: Boolean
    ): Result<SearchResult> {
        return repository.searchVacancies(text, area, industry, salary, page, onlyWithSalary)
    }
}
