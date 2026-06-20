package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.NetworkResult
import ru.practicum.android.diploma.data.dto.VacancyRequest
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.repository.VacancyRepository

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient
) : VacancyRepository {

    override suspend fun searchVacancies(
        text: String?,
        area: Int?,
        industry: Int?,
        salary: Int?,
        page: Int,
        onlyWithSalary: Boolean
    ): Result<SearchResult> {
        val request = VacancyRequest(text, area, industry, salary, page, onlyWithSalary)
        return when (val result = networkClient.searchVacancies(request)) {
            is NetworkResult.Success -> {
                val vacancyDto = result.data
                val vacancies = vacancyDto.items.map { it.toDomain() }
                Result.success(
                    SearchResult(
                        vacancies = vacancies,
                        currentPage = vacancyDto.page,
                        totalPages = vacancyDto.pages,
                        totalFound = vacancyDto.found
                    )
                )
            }
            is NetworkResult.Error -> {
                Result.failure(Exception("Ошибка загрузки: ${result.message} (код ${result.code})"))
            }
            NetworkResult.NoInternet -> {
                Result.failure(Exception("Нет подключения к интернету"))
            }
        }
    }
}
