package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import retrofit2.HttpException
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.NetworkResult
import ru.practicum.android.diploma.data.dto.VacancyRequest
import ru.practicum.android.diploma.data.dto.filterarea.FilterAreaDto
import ru.practicum.android.diploma.data.dto.filterindustry.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.vacancydetail.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.vacancyresponse.VacancyDto
import java.io.IOException

private const val ERROR_CODE_INTERNAL = 500
private const val ERROR_MESSAGE_NETWORK = "Ошибка сети: "
private const val ERROR_MESSAGE_UNKNOWN = "Неизвестная ошибка: "

class RetrofitNetworkClient(
    private val context: Context,
    private val vacancyApi: VacancyApi
) : NetworkClient {

    override suspend fun searchVacancies(request: VacancyRequest): NetworkResult<VacancyDto> {
        if (!isConnected()) {
            return NetworkResult.NoInternet
        }
        return try {
            val response = vacancyApi.getVacancies(
                text = request.text,
                area = request.area,
                industry = request.industry,
                salary = request.salary,
                page = request.page,
                onlyWithSalary = request.onlyWithSalary
            )
            NetworkResult.Success(response)
        } catch (e: IOException) {
            NetworkResult.Error(ERROR_CODE_INTERNAL, ERROR_MESSAGE_NETWORK + e.message)
        } catch (e: HttpException) {
            NetworkResult.Error(e.code(), e.message() ?: "Ошибка HTTP")
        } catch (e: Exception) {
            NetworkResult.Error(ERROR_CODE_INTERNAL, ERROR_MESSAGE_UNKNOWN + e.message)
        }
    }

    override suspend fun getVacancyDetail(vacancyId: String): NetworkResult<VacancyDetailDto> {
        if (!isConnected()) {
            return NetworkResult.NoInternet
        }
        return try {
            val response = vacancyApi.getVacancyDetail(vacancyId)
            NetworkResult.Success(response)
        } catch (e: IOException) {
            NetworkResult.Error(ERROR_CODE_INTERNAL, ERROR_MESSAGE_NETWORK + e.message)
        } catch (e: HttpException) {
            NetworkResult.Error(e.code(), e.message() ?: "Ошибка HTTP")
        } catch (e: Exception) {
            NetworkResult.Error(ERROR_CODE_INTERNAL, ERROR_MESSAGE_UNKNOWN + e.message)
        }
    }

    override suspend fun getAreas(): NetworkResult<List<FilterAreaDto>> {
        if (!isConnected()) {
            return NetworkResult.NoInternet
        }
        return try {
            val response = vacancyApi.getAreas()
            NetworkResult.Success(response)
        } catch (e: IOException) {
            NetworkResult.Error(ERROR_CODE_INTERNAL, ERROR_MESSAGE_NETWORK + e.message)
        } catch (e: HttpException) {
            NetworkResult.Error(e.code(), e.message() ?: "Ошибка HTTP")
        } catch (e: Exception) {
            NetworkResult.Error(ERROR_CODE_INTERNAL, ERROR_MESSAGE_UNKNOWN + e.message)
        }
    }

    override suspend fun getIndustries(): NetworkResult<List<FilterIndustryDto>> {
        if (!isConnected()) {
            return NetworkResult.NoInternet
        }
        return try {
            val response = vacancyApi.getIndustries()
            NetworkResult.Success(response)
        } catch (e: IOException) {
            NetworkResult.Error(ERROR_CODE_INTERNAL, ERROR_MESSAGE_NETWORK + e.message)
        } catch (e: HttpException) {
            NetworkResult.Error(e.code(), e.message() ?: "Ошибка HTTP")
        } catch (e: Exception) {
            NetworkResult.Error(ERROR_CODE_INTERNAL, ERROR_MESSAGE_UNKNOWN + e.message)
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}
