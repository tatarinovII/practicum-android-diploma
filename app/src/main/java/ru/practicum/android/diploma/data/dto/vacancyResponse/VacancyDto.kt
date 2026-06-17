package ru.practicum.android.diploma.data.dto.vacancyResponse

data class VacancyDto(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<VacancyCardDto>
)
