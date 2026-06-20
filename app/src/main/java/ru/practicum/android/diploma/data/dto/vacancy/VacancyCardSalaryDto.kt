package ru.practicum.android.diploma.data.dto.vacancy

import ru.practicum.android.diploma.domain.models.Currency

data class VacancyCardSalaryDto(
    val from: Int?,
    val to: Int?,
    val currency: Currency?
)
