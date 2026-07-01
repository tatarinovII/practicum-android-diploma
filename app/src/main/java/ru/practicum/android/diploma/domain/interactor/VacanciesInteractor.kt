package ru.practicum.android.diploma.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail

interface VacanciesInteractor {
    suspend fun searchVacancies(query: String, page: Int): Result<SearchResult>
    suspend fun getVacancyDetail(vacancyId: String): Result<VacancyDetail>
    suspend fun isFavorite(vacancyId: String): Result<Boolean>
    suspend fun addToFavorite(vacancy: VacancyDetail)
    suspend fun deleteFromFavorite(vacancyId: String)
    fun shareVacancy(link: String)
    fun callNumber(number: String)
    fun sendEmail(email: String)
    suspend fun uploadFavoritesVacancies(): Result<Flow<List<Vacancy>>>
}
