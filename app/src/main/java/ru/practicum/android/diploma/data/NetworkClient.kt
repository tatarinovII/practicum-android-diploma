package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.filterarea.FilterAreaDto
import ru.practicum.android.diploma.data.dto.filterindustry.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.vacancydetail.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.vacancyresponse.VacancyDto

interface NetworkClient {
    suspend fun requestFilterArea(dto: FilterAreaDto): Response
    suspend fun requestFilterIndustry(dto: FilterIndustryDto): Response
    suspend fun requestVacancyResponse(dto: VacancyDto): Response
    suspend fun requestVacancyDetail(dto: VacancyDetailDto): Response
}
