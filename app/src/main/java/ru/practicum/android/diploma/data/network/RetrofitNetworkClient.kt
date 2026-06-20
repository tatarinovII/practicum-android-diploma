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
private const val ERROR_MESSAGE_HTTP = "Ошибка HTTP"

class RetrofitNetworkClient(
    private val context: Context,
    private val vacancyApi: VacancyApi
) : NetworkClient {

    private val connectivityManager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private suspend fun <T> safeApiCall(block: suspend () -> T): NetworkResult<T> {
        if (!isConnected()) {
            return NetworkResult.NoInternet
        }
        return try {
            NetworkResult.Success(block.invoke())
        } catch (e: IOException) {
            NetworkResult.Error(ERROR_CODE_INTERNAL, ERROR_MESSAGE_NETWORK + e.message)
        } catch (e: HttpException) {
            NetworkResult.Error(e.code(), e.message() ?: ERROR_MESSAGE_HTTP)
        }
    }

    override suspend fun searchVacancies(request: VacancyRequest): NetworkResult<VacancyDto> =
        safeApiCall {
            vacancyApi.getVacancies(
                text = request.text,
                area = request.area,
                industry = request.industry,
                salary = request.salary,
                page = request.page,
                onlyWithSalary = request.onlyWithSalary
            )
        }

    override suspend fun getVacancyDetail(vacancyId: String): NetworkResult<VacancyDetailDto> =
        safeApiCall { vacancyApi.getVacancyDetail(vacancyId) }

    override suspend fun getAreas(): NetworkResult<List<FilterAreaDto>> =
        safeApiCall { vacancyApi.getAreas() }

    override suspend fun getIndustries(): NetworkResult<List<FilterIndustryDto>> =
        safeApiCall { vacancyApi.getIndustries() }

    private fun isConnected(): Boolean {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities?.let {
            it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}
