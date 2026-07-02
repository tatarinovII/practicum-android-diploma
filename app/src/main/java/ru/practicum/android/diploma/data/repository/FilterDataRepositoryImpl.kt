package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.mappers.toFilterIndustry
import ru.practicum.android.diploma.data.network.ResponseCode.NO_CONNECTION
import ru.practicum.android.diploma.data.network.ResponseCode.SUCCESS
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.domain.repository.FilterDataRepository
import java.io.IOException

class FilterDataRepositoryImpl(
    private val networkClient: NetworkClient
) : FilterDataRepository {

    override suspend fun getIndustries(): Result<List<FilterIndustry>> {
        val response = networkClient.requestFilterIndustry()
        return when (response.resultCode) {
            SUCCESS -> {
                val industries = response.data ?: emptyList()
                Result.success(industries.map { it.toFilterIndustry() })
            }
            NO_CONNECTION -> Result.failure(IOException("Нет интернета"))
            else -> Result.failure(Exception("Ошибка загрузки отраслей (${response.resultCode})"))
        }
    }
}
