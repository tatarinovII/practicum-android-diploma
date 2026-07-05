package ru.practicum.android.diploma.presentation.filter

import ru.practicum.android.diploma.domain.models.FilterArea

sealed interface RegionUiState {
    object Loading : RegionUiState
    data class Content(val regions: List<FilterArea?> = emptyList()) : RegionUiState
    data class Error(val errorMessage: String) : RegionUiState
}
