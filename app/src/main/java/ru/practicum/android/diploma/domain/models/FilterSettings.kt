package ru.practicum.android.diploma.domain.models

data class FilterSettings(
    val areaId: String? = null,
    val areaName: String? = null,
    val industryId: Int? = null,
    val industryName: String? = null,
    val salary: Int? = null,
    val onlyWithSalary: Boolean = false
)
