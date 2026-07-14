package ru.practicum.android.diploma.presentation.filter

data class FilterUiState(
    val countryName: String? = null,
    val countryId: String? = null,
    val regionName: String? = null,
    val regionId: String? = null,
    val industryName: String? = null,
    val industryId: Int? = null,
    val salaryText: String = "",
    val onlyWithSalary: Boolean = false
)
