package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.filter_area.FilterAreaDto
import ru.practicum.android.diploma.data.dto.filter_industry.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.vacancy_detail.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.vacancy_response.VacancyDto

interface NetworkClient {
    suspend fun requestFilterArea(dto: FilterAreaDto): Response
    suspend fun requestFilterIndustry(dto: FilterIndustryDto): Response
    suspend fun requestVacancyResponse(dto: VacancyDto): Response
    suspend fun requestVacancyDetail(dto: VacancyDetailDto): Response
}
