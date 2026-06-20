package ru.practicum.android.diploma.domain.interactor

import ru.practicum.android.diploma.domain.models.SearchResult

interface VacanciesInteractor {
    suspend operator fun invoke(
        text: String?,
        area: Int? = null,
        industry: Int? = null,
        salary: Int? = null,
        page: Int = 0,
        onlyWithSalary: Boolean = false
    ): Result<SearchResult>
}
