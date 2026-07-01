package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.filterarea.FilterAreaDto
import ru.practicum.android.diploma.data.dto.filterindustry.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.vacancy.VacancyDto
import ru.practicum.android.diploma.data.dto.vacancydetail.VacancyDetailDto
import ru.practicum.android.diploma.data.network.ResponseCode.BAD_REQUEST
import ru.practicum.android.diploma.data.network.ResponseCode.NOT_FOUND
import ru.practicum.android.diploma.data.network.ResponseCode.NO_CONNECTION
import ru.practicum.android.diploma.data.network.ResponseCode.SERVER_ERROR
import ru.practicum.android.diploma.data.network.ResponseCode.SUCCESS
import ru.practicum.android.diploma.data.network.api.VacancyApi

class RetrofitNetworkClient(
    private val context: Context,
    private val vacancyApi: VacancyApi
) : NetworkClient {
    private val token: String =
        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJwcmFjdGljdW0ucnUiLCJhdWQiOiJwcmFjdGljdW0ucnUiLCJ1c2VybmFtZSI6ItGG0YPRhtC60YPRg9C6In0.jaxpKiIDe0nZZxzLSTVRKibViTN0OAZIUueaVw4LyL8"

    override suspend fun requestFilterArea(): Response<List<FilterAreaDto>> {
        if (!isConnected()) {
            return Response(resultCode = NO_CONNECTION)
        }
        return withContext(Dispatchers.IO) {
            try {
                val areas = vacancyApi.getArea(token)
                if (areas.isEmpty()) {
                    Response(resultCode = BAD_REQUEST)
                } else {
                    Response(resultCode = SUCCESS, data = areas)
                }
            } catch (e: Throwable) {
                Response(resultCode = SERVER_ERROR)
            }
        }
    }

    override suspend fun requestFilterIndustry(): Response<List<FilterIndustryDto>> {
        if (!isConnected()) {
            return Response(resultCode = NO_CONNECTION)
        }
        return withContext(Dispatchers.IO) {
            try {
                val industries = vacancyApi.getIndustry(token)
                if (industries.isEmpty()) {
                    Response(resultCode = BAD_REQUEST)
                } else {
                    Response(resultCode = SUCCESS, data = industries)
                }
            } catch (e: Throwable) {
                Response(resultCode = SERVER_ERROR)
            }
        }
    }

    override suspend fun requestVacancyResponse(dto: VacancyRequest): Response<VacancyDto> {
        if (!isConnected()) {
            return Response(resultCode = NO_CONNECTION)
        }
        return withContext(Dispatchers.IO) {
            try {
                val vacancies = vacancyApi.searchVacancy(token, dto.options)
                Response(resultCode = SUCCESS, data = vacancies)
            } catch (e: Throwable) {
                Response(resultCode = SERVER_ERROR)
            }
        }
    }

    override suspend fun requestVacancyDetail(dto: VacancyDetailRequest): Response<VacancyDetailDto> {
        if (!isConnected()) {
            return Response(resultCode = NO_CONNECTION)
        }
        return withContext(Dispatchers.IO) {
            try {
                val vacancyDetail = vacancyApi.getVacancyDetail(token, dto.vacancyId)
                Response(resultCode = SUCCESS, data = vacancyDetail)
            } catch (e: Throwable) {
                if (e.message.toString() == "HTTP 404 Not Found") {
                    Response(resultCode = NOT_FOUND)
                } else {
                    Response(resultCode = SERVER_ERROR)
                }
            }
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
