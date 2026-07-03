package ru.practicum.android.diploma.presentation.filter

import ru.practicum.android.diploma.domain.models.FilterArea

sealed interface CountryUiState {
    object Loading : CountryUiState
    data class Content(val countries: List<FilterArea?> = emptyList()) : CountryUiState
    data class Error(val errorMessage: String) : CountryUiState
}
