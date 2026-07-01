package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.filterarea.FilterAreaDto
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
    override suspend fun getAreas(): Flow<List<FilterArea>> = flow {
        val response = networkClient.requestFilterArea()
        when (response.resultCode) {
            SUCCESS -> {
                val filterAreasDto = response as? List<FilterAreaDto>
                if (filterAreasDto?.isNotEmpty() == true) {
                    val filterAreas = filterAreasDto.map { it.toDomain() }
                    val countries = filterAreas.filter { it.parentId == null }
                    emit(countries)
                } else {
                    Result.failure<FilterArea>(Exception("Не удалось получить список стран"))
                }
            }
            BAD_REQUEST -> {
                Result.failure<FilterArea>(Exception("Не удалось получить список стран"))
            }
            SERVER_ERROR -> {
                Result.failure<FilterArea>(Exception("Ошибка сервера"))
            }
            NO_CONNECTION -> {
                Result.failure<FilterArea>(Exception("Отсутствует подключение к интернету"))
            }
        }
    }
}
