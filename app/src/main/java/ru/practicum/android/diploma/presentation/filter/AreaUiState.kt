package ru.practicum.android.diploma.presentation.filter

sealed interface AreaUiState {
    data class Content(
        var country: String? = null,
        var region: String? = null,
    ) : AreaUiState

    object Loading : AreaUiState

    data class Error(
        val errorMessage: String
    ) : AreaUiState
}

