package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.filterArea.FilterAreaDto
import ru.practicum.android.diploma.data.dto.filterIndustry.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.vacancyDetail.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.vacancyResponse.VacancyDto

interface NetworkClient {
    suspend fun requestFilterArea(dto: FilterAreaDto): Response
    suspend fun requestFilterIndustry(dto: FilterIndustryDto): Response
    suspend fun requestVacancyResponse(dto: VacancyDto): Response
    suspend fun requestVacancyDetail(dto: VacancyDetailDto): Response
}
