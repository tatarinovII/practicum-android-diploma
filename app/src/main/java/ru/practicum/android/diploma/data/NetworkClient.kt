package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.dto.VacancyRequest
import ru.practicum.android.diploma.data.dto.filterarea.FilterAreaDto
import ru.practicum.android.diploma.data.dto.filterindustry.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.vacancydetail.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.vacancyresponse.VacancyDto

interface NetworkClient {
    suspend fun searchVacancies(request: VacancyRequest): NetworkResult<VacancyDto>
    suspend fun getVacancyDetail(vacancyId: String): NetworkResult<VacancyDetailDto>
    suspend fun getAreas(): NetworkResult<List<FilterAreaDto>>
    suspend fun getIndustries(): NetworkResult<List<FilterIndustryDto>>
}
