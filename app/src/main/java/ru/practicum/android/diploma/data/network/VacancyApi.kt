package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.filterarea.FilterAreaDto
import ru.practicum.android.diploma.data.dto.filterindustry.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.vacancydetail.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.vacancyresponse.VacancyDto

interface VacancyApi {
    @GET("vacancies")
    suspend fun getVacancies(
        @Query("text") text: String? = null,
        @Query("area") area: Int? = null,
        @Query("industry") industry: Int? = null,
        @Query("salary") salary: Int? = null,
        @Query("page") page: Int = 0,
        @Query("only_with_salary") onlyWithSalary: Boolean = false
    ): VacancyDto

    @GET("vacancies/{id}")
    suspend fun getVacancyDetail(@Path("id") id: String): VacancyDetailDto

    @GET("areas")
    suspend fun getAreas(): List<FilterAreaDto>

    @GET("industries")
    suspend fun getIndustries(): List<FilterIndustryDto>
}
