package ru.practicum.android.diploma.data.dto.vacancy_response

data class VacancyDto(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<VacancyCardDto>
)
