package ru.practicum.android.diploma.presentation.vacancy

sealed interface VacancyUiState {
    object Loading : VacancyUiState

    data class Content(
        val vacancyDetail: String
    ) : VacancyUiState

    data class Error(
        val errorMessage: String
    ) : VacancyUiState

    data class NotFound(
        val message: String
    ) : VacancyUiState
}
