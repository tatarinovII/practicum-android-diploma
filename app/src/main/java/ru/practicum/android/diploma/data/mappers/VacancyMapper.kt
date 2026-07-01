package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.vacancy.VacancyCardDto
import ru.practicum.android.diploma.data.dto.vacancy.VacancyCardSalaryDto
import ru.practicum.android.diploma.data.dto.vacancydetail.VacancyDetailDto
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail

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

fun VacancyDetail.toDomain(): Vacancy = Vacancy(
    id = id,
    name = name,
    company = employer?.name,
    city = area?.name,
    salary = salary,
    logo = employer?.logo
)
