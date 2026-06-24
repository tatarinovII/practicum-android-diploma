package ru.practicum.android.diploma.data.dto.vacancydetail

import ru.practicum.android.diploma.data.dto.filterarea.FilterAreaDto
import ru.practicum.android.diploma.data.dto.filterindustry.FilterIndustryDto
import ru.practicum.android.diploma.data.network.Response

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
) : Response()
