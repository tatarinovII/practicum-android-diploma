package ru.practicum.android.diploma.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail

interface VacancyRepository {
    suspend fun searchVacancies(query: String, page: Int, perPage: Int = 20): Result<SearchResult>
    suspend fun getVacancyDetail(vacancyId: String): Result<VacancyDetail>
    suspend fun isFavorite(vacancyId: String): Result<Boolean>
    suspend fun addToFavorite(vacancy: VacancyDetail)
    suspend fun deleteFromFavorite(vacancyId: String)
    fun shareVacancy(link: String)
    fun callNumber(number: String)
    fun sendEmail(email: String)
    suspend fun uploadFavoritesVacancies(): Result<Flow<List<Vacancy>>>
}
