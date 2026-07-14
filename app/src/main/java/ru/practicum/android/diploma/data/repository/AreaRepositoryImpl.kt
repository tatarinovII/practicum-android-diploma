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
    override suspend fun getAreas(): Flow<Result<List<FilterArea>>> = flow {
        val response = networkClient.requestFilterArea()
        when (response.resultCode) {
            SUCCESS -> {
                val filterAreasDto = response.data
                if (filterAreasDto != null) {
                    val filterAreas = filterAreasDto.map { it.toDomain() }

                    // Элемент "Другие регионы" перемещаю в конец
                    val mutableFilterAreas = filterAreas.toMutableList()
                    val anotherRegionElement = mutableFilterAreas.removeAt(6)
                    mutableFilterAreas.add(anotherRegionElement)
                    emit(Result.success(mutableFilterAreas))
                } else {
                    emit(Result.success(emptyList()))
                }
            }
            BAD_REQUEST -> {
                emit(Result.failure(Exception("Не удалось получить список стран")))
            }
            SERVER_ERROR -> {
                emit(Result.failure(Exception("Ошибка сервера")))
            }
            NO_CONNECTION -> {
                emit(Result.failure(Exception("Отсутствует подключение к интернету")))
            }
            else -> {
                emit(Result.failure(Exception("Неизвестная ошибка (код ${response.resultCode})")))
            }
        }
    }
}
