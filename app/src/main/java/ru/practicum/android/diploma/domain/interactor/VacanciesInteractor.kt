package ru.practicum.android.diploma.domain.interactor

import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.models.SearchVacanciesParams

interface VacanciesInteractor {
    suspend operator fun invoke(params: SearchVacanciesParams): Result<SearchResult>
}
