package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.dto.vacancy_response.VacancyDto

data class VacancyResponse(
    val resultCount: Int,
    val results: List<VacancyDto>
) : Response()
