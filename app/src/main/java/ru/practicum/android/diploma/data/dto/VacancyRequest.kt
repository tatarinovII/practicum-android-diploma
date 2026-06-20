package ru.practicum.android.diploma.data.dto

data class VacancyRequest(
    val text: String? = null,
    val area: Int? = null,
    val industry: Int? = null,
    val salary: Int? = null,
    val page: Int = 0,
    val onlyWithSalary: Boolean = false
)
