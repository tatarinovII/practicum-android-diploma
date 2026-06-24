package ru.practicum.android.diploma.data.network.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.filterarea.FilterAreaDto
import ru.practicum.android.diploma.data.dto.filterindustry.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.vacancy.VacancyDto
import ru.practicum.android.diploma.data.dto.vacancydetail.VacancyDetailDto

interface VacancyApi {
    @Headers("Content-Type: application/json")
    @GET("vacancies")
    suspend fun searchVacancy(
        @Header("Authorization") token: String,
        @QueryMap options: Map<String, String>
    ): VacancyDto

    @Headers("Content-Type: application/json")
    @GET("areas")
    suspend fun getArea(@Header("Authorization") token: String): List<FilterAreaDto>

    @Headers("Content-Type: application/json")
    @GET("industries")
    suspend fun getIndustry(@Header("Authorization") token: String): List<FilterIndustryDto>

    @Headers("Content-Type: application/json")
    @GET("vacancies/{id}")
    suspend fun getVacancyDetail(
        @Header("Authorization") token: String,
        @Path("id") vacancyId: String
    ): VacancyDetailDto
}
