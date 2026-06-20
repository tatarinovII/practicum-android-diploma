package ru.practicum.android.diploma.data.mappers

import android.util.Log
import ru.practicum.android.diploma.data.dto.vacancyresponse.VacancyCardDto
import ru.practicum.android.diploma.data.dto.vacancyresponse.VacancyCardSalaryDto
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

fun VacancyCardDto.toDomain(): Vacancy {
    Log.d("VacancyMapper", "logo for ${id}: $logo")
    return Vacancy(
        id = id,
        name = name,
        company = company,
        city = city,
        salary = salary?.toDomain(),
        logo = logo
    )
}

fun VacancyCardSalaryDto.toDomain(): Salary {
    return Salary(
        from = from,
        to = to,
        currency = currency
    )
}
