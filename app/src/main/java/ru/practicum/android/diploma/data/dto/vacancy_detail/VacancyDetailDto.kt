package ru.practicum.android.diploma.data.dto.vacancy_detail

import ru.practicum.android.diploma.data.dto.filter_area.FilterAreaDto
import ru.practicum.android.diploma.data.dto.filter_industry.FilterIndustryDto

data class VacancyDetailDto(
    val id: String,
    val name: String,
    val description: String,
    val salary: SalaryDto?,
    val address: AddressDto?,
    val experience: ExperienceDto?,
    val schedule: ScheduleDto?,
    val employment: EmploymentDto?,
    val contacts: ContactsDto?,
    val employer: EmployerDto,
    val area: FilterAreaDto,
    val skills: List<String>,
    val url: String,
    val industry: FilterIndustryDto
)
