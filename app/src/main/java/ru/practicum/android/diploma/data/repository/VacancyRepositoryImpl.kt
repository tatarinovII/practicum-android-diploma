package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.db.converter.Converters
import ru.practicum.android.diploma.data.db.dao.VacancyDao
import ru.practicum.android.diploma.data.db.entity.VacancyFavoriteEntity
import ru.practicum.android.diploma.data.dto.vacancy.VacancyDto
import ru.practicum.android.diploma.data.dto.vacancydetail.VacancyDetailDto
import ru.practicum.android.diploma.data.externalNavigator.ExternalNavigator
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.network.ResponseCode.NOT_FOUND
import ru.practicum.android.diploma.data.network.ResponseCode.NO_CONNECTION
import ru.practicum.android.diploma.data.network.ResponseCode.SERVER_ERROR
import ru.practicum.android.diploma.data.network.ResponseCode.SUCCESS
import ru.practicum.android.diploma.data.network.VacancyDetailRequest
import ru.practicum.android.diploma.domain.api.ResponseException
import ru.practicum.android.diploma.data.network.VacancyRequest
import ru.practicum.android.diploma.domain.models.SearchResult
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.domain.repository.VacancyRepository
import java.io.IOException

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val dao: VacancyDao,
    private val converter: Converters,
    private val externalNavigator: ExternalNavigator
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
            SERVER_ERROR -> Result.failure(Exception("Ошибка сервера"))
            else -> Result.failure(Exception("Неизвестная ошибка ${response.resultCode}"))
        }
    }

    override suspend fun getVacancyDetail(vacancyId: String): Result<VacancyDetail> {
        val request = VacancyDetailRequest(vacancyId)
        val response = networkClient.requestVacancyDetail(request)
        return when (response.resultCode) {
            SUCCESS -> {
                val detailDto = response as? VacancyDetailDto
                if (detailDto == null) {
                    Result.failure(Exception("Некорректный тип ответа"))
                } else {
                    Result.success(detailDto.toDomain())
                }
            }
            NO_CONNECTION -> Result.failure(ResponseException(NO_CONNECTION,IOException("Нет подключения к интернету")))
            NOT_FOUND -> Result.failure(ResponseException(NOT_FOUND, Exception("Вакансия не найдена")))
            SERVER_ERROR -> Result.failure(ResponseException(SERVER_ERROR, Exception("Ошибка сервера")))
            else -> Result.failure(ResponseException(response.resultCode, Exception("Ошибка загрузки деталей (код ${response.resultCode})")))
        }
    }

    override suspend fun isFavorite(vacancyId: String): Result<Boolean> {
        return try {
            Result.success(dao.isFavorite(vacancyId))
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }

    override suspend fun addToFavorite(vacancy: VacancyDetail) {
        dao.insert(
            VacancyFavoriteEntity(
                id = vacancy.id, vacancyJson = converter.map(vacancy)
            )
        )
    }

    override suspend fun deleteFromFavorite(vacancyId: String) {
        dao.deleteById(vacancyId)
    }

    override fun shareVacancy(link: String) {
        externalNavigator.shareLink(link)
    }

    override fun callNumber(number: String) {
        externalNavigator.openPhone(number)
    }

    override fun sendEmail(email: String) {
        externalNavigator.openEmail(email)
    }
}
