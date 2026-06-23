package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.VacancyResponse

interface VacancyApi {
    @GET("search/vacancies")
    suspend fun searchVacancy(@QueryMap options: Map<String, String>): List<VacancyResponse>
}
