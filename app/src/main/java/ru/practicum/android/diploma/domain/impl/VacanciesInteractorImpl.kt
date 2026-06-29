package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.externalNavigator.ExternalNavigator
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.interactor.VacanciesInteractor
import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.domain.repository.VacancyRepository

class VacanciesInteractorImpl(
    private val repository: VacancyRepository, private val externalNavigator: ExternalNavigator
) : VacanciesInteractor {

    override suspend fun searchVacancies(
        query: String,
        page: Int,
        areaId: String?,
        industryId: Int?,
        salary: Int?,
        onlyWithSalary: Boolean
    ): Result<SearchResult> {
        return repository.searchVacancies(query, page, areaId = areaId, industryId = industryId, salary = salary, onlyWithSalary = onlyWithSalary)
    }

    override suspend fun getVacancyDetail(vacancyId: String): Result<VacancyDetail> {
        return repository.getVacancyDetail(vacancyId)
    }

    override suspend fun isFavorite(vacancyId: String): Result<Boolean> {
        return repository.isFavorite(vacancyId)
    }

    override suspend fun addToFavorite(vacancy: VacancyDetail) {
        return repository.addToFavorite(vacancy)
    }

    override suspend fun deleteFromFavorite(vacancyId: String) {
        return repository.deleteFromFavorite(vacancyId)
    }

    override fun shareVacancy(link: String) {
        repository.shareVacancy(link)
    }

    override fun callNumber(number: String) {
        repository.callNumber(number)
    }

    override fun sendEmail(email: String) {
        repository.sendEmail(email)
    }

    override suspend fun uploadFavoritesVacancies(): Result<Flow<List<Vacancy>>> {
        return repository.uploadFavoritesVacancies()
    }
}
