package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.vacancyresponse.VacancyCardDto
import ru.practicum.android.diploma.data.dto.vacancyresponse.VacancyCardSalaryDto
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

fun VacancyCardDto.toDomain(): Vacancy = Vacancy(
    id = id,
    name = name,
    company = company,
    city = city,
    salary = salary?.toDomain(),
    logo = logo
)

fun VacancyCardSalaryDto.toDomain(): Salary = Salary(
    from = from,
    to = to,
    currency = currency
)
