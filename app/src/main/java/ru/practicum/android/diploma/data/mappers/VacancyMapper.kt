package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.vacancy.VacancyCardDto
import ru.practicum.android.diploma.data.dto.vacancy.VacancyCardSalaryDto
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
