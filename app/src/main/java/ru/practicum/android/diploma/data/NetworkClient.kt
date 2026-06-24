package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.network.Response
import ru.practicum.android.diploma.data.network.VacancyDetailRequest
import ru.practicum.android.diploma.data.network.VacancyRequest

interface NetworkClient {
    suspend fun requestFilterArea(): Response
    suspend fun requestFilterIndustry(): Response
    suspend fun requestVacancyResponse(dto: VacancyRequest): Response
    suspend fun requestVacancyDetail(dto: VacancyDetailRequest): Response
}
