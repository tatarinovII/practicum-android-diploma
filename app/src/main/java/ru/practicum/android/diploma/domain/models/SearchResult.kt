package ru.practicum.android.diploma.domain.models

data class SearchResult(
    val vacancies: List<Vacancy>,
    val currentPage: Int,
    val totalPages: Int,
    val totalFound: Int
)
