package ru.practicum.android.diploma.presentation.filter

import ru.practicum.android.diploma.domain.models.FilterIndustry

data class IndustryUiState(
    val industries: List<FilterIndustry> = emptyList(),
    val filteredIndustries: List<FilterIndustry> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val selectedIndustryId: Int? = null,
    val error: String? = null
)
