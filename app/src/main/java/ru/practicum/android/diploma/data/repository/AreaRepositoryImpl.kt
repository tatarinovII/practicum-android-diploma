package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.mappers.toDomain
import ru.practicum.android.diploma.data.network.ResponseCode.BAD_REQUEST
import ru.practicum.android.diploma.data.network.ResponseCode.NO_CONNECTION
import ru.practicum.android.diploma.data.network.ResponseCode.SERVER_ERROR
import ru.practicum.android.diploma.data.network.ResponseCode.SUCCESS
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.domain.repository.AreaRepository

class AreaRepositoryImpl(
    private val networkClient: NetworkClient
) : AreaRepository {
    override suspend fun getAreas(): Result<List<FilterArea>> {
        val response = networkClient.requestFilterArea()
        return when (response.resultCode) {
            SUCCESS -> {
                val filterAreasDto = response.data
                if (filterAreasDto?.isNotEmpty() == true) {
                    val filterAreas = filterAreasDto.map { it.toDomain() }
                    val countries = filterAreas.filter { it.parentId == null }
                    Result.success(countries)
                } else {
                    Result.failure(Exception("Не удалось получить список стран"))
                }
            }
            BAD_REQUEST -> {
                Result.failure(Exception("Не удалось получить список стран"))
            }
            SERVER_ERROR -> {
                Result.failure(Exception("Ошибка сервера"))
            }
            NO_CONNECTION -> {
                Result.failure(Exception("Отсутствует подключение к интернету"))
            }
            else -> {
                Result.failure(Exception("Неизвестная ошибка (код ${response.resultCode})"))
            }
        }
    }
}
