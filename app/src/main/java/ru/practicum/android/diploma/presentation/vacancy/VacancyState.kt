package ru.practicum.android.diploma.presentation.vacancy

import ru.practicum.android.diploma.domain.models.VacancyDetail

sealed interface VacancyState {
    object Loading : VacancyState

    data class Content(
        val vacancyDetail: VacancyDetail, val isFavorite: Boolean
    ) : VacancyState

    object Error : VacancyState
}
