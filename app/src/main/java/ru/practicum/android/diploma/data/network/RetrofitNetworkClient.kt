package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.network.api.VacancyApi

class RetrofitNetworkClient(
    private val context: Context,
    private val vacancyApi: VacancyApi
) : NetworkClient {
    private val token: String =
        "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJwcmFjdGljdW0ucnUiLCJhdWQiOiJwcmFjdGljdW0ucnUiLCJ1c2VybmFtZSI6ItGG0YPRhtC60YPRg9C6In0.jaxpKiIDe0nZZxzLSTVRKibViTN0OAZIUueaVw4LyL8"

    companion object {
        const val ERROR_NO_CONNECTION = -1
        const val NOT_FOUND = 404
        const val EMPTY = 400
        const val SUCCESS = 200
        const val INTERNAL_ERROR_SERVER = 500
    }

    override suspend fun requestFilterArea(): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = ERROR_NO_CONNECTION }
        }
        return withContext(Dispatchers.IO) {
            try {
                val areas = vacancyApi.getArea(token)
                if (areas.isEmpty()) {
                    Response().apply { resultCode = EMPTY }
                } else {
                    Response().apply { resultCode = SUCCESS }
                }
            } catch (e: Throwable) {
                Response().apply { resultCode = INTERNAL_ERROR_SERVER }
            }
        }
    }

    override suspend fun requestFilterIndustry(): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = ERROR_NO_CONNECTION }
        }

        return withContext(Dispatchers.IO) {
            try {
                val industries = vacancyApi.getIndustry(token)
                if (industries.isEmpty()) {
                    Response().apply { resultCode = EMPTY }
                } else {
                    Response().apply { resultCode = SUCCESS }
                }
            } catch (e: Throwable) {
                Response().apply { resultCode = INTERNAL_ERROR_SERVER }
            }
        }
    }

    override suspend fun requestVacancyResponse(dto: VacancyRequest): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = ERROR_NO_CONNECTION }
        }
        return withContext(Dispatchers.IO) {
            try {
                val vacancies = vacancyApi.searchVacancy(token, dto.options)
                if (vacancies.items.isEmpty()) {
                    Response().apply { resultCode = EMPTY }
                } else {
                    Response().apply { resultCode = SUCCESS }
                }
            } catch (e: Throwable) {
                Response().apply { resultCode = INTERNAL_ERROR_SERVER }
            }
        }
    }

    override suspend fun requestVacancyDetail(dto: VacancyDetailRequest): Response {
        return withContext(Dispatchers.IO) {
            try {
                val vacancyDetail = vacancyApi.getVacancyDetail(token, dto.vacancyId)
                Response().apply { resultCode = SUCCESS }
            } catch (e: Throwable) {
                if (e.message.toString() == "HTTP 404 Not Found") {
                    Response().apply { resultCode = NOT_FOUND }
                } else {
                    Response().apply { resultCode = INTERNAL_ERROR_SERVER }
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
