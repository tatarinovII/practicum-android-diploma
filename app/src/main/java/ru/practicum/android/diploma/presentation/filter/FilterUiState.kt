package ru.practicum.android.diploma.presentation.filter

data class FilterUiState(
    val areaName: String? = null,
    val areaId: String? = null,
    val industryName: String? = null,
    val industryId: Int? = null,
    val salaryText: String = "",
    val onlyWithSalary: Boolean = false
)
