package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.network.Response
import ru.practicum.android.diploma.data.network.VacancyDetailRequest
import ru.practicum.android.diploma.data.network.VacancyRequest
import ru.practicum.android.diploma.data.network.api.AreaResponse

interface NetworkClient {
    suspend fun requestFilterArea(): AreaResponse
    suspend fun requestFilterIndustry(): Response
    suspend fun requestVacancyResponse(dto: VacancyRequest): Response
    suspend fun requestVacancyDetail(dto: VacancyDetailRequest): Response
}
