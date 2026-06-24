package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.vacancy.VacancyDto
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.network.ResponseCode.BAD_REQUEST
import ru.practicum.android.diploma.data.network.ResponseCode.NO_CONNECTION
import ru.practicum.android.diploma.data.network.ResponseCode.SERVER_ERROR
import ru.practicum.android.diploma.data.network.ResponseCode.SUCCESS
import ru.practicum.android.diploma.data.network.VacancyRequest
import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.repository.VacancyRepository
import java.io.IOException

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient
) : VacancyRepository {

    override suspend fun searchVacancies(
        query: String,
        page: Int,
        perPage: Int
    ): Result<SearchResult> {
        val options = hashMapOf(
            "text" to query,
            "page" to page.toString(),
            "per_page" to perPage.toString()
        )
        val request = VacancyRequest(options)
        val response = networkClient.requestVacancyResponse(request)

        return when (response.resultCode) {
            SUCCESS -> {
                val vacancyDto = response as? VacancyDto
                if (vacancyDto == null) {
                    Result.failure(Exception("Некорректный тип ответа"))
                } else {
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
            }
            NO_CONNECTION -> Result.failure(IOException("Отсутствует подключение к интернету"))
            BAD_REQUEST -> Result.failure(Exception("Ничего не найдено"))
            SERVER_ERROR -> Result.failure(Exception("Ошибка сервера"))
            else -> Result.failure(Exception("Неизвестная ошибка ${response.resultCode}"))
        }
    }
}
