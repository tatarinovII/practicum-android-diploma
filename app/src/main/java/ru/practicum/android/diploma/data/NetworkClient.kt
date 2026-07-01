package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.filterarea.FilterAreaDto
import ru.practicum.android.diploma.data.dto.filterindustry.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.vacancy.VacancyDto
import ru.practicum.android.diploma.data.dto.vacancydetail.VacancyDetailDto
import ru.practicum.android.diploma.data.network.Response
import ru.practicum.android.diploma.data.network.VacancyDetailRequest
import ru.practicum.android.diploma.data.network.VacancyRequest

interface NetworkClient {
    suspend fun requestFilterArea(): Response<List<FilterAreaDto>>
    suspend fun requestFilterIndustry(): Response<List<FilterIndustryDto>>
    suspend fun requestVacancyResponse(dto: VacancyRequest): Response<VacancyDto>
    suspend fun requestVacancyDetail(dto: VacancyDetailRequest): Response<VacancyDetailDto>
}
