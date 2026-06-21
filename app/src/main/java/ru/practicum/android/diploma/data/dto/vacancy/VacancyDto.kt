package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.data.network.Response

data class VacancyDto(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<VacancyCardDto>
) : Response()
