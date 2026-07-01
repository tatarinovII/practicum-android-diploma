package ru.practicum.android.diploma.presentation.filter

sealed interface AreaUiState {
    data class Content(
        val country: String? = null,
        val region: String? = null,
    ) : AreaUiState

    data class Error(
        val errorMessage: String
    ) : AreaUiState
}

