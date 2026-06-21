package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.network.api.VacancyApi
import android.util.Log

class RetrofitNetworkClient(private val context: Context, private val vacancyApi: VacancyApi) : NetworkClient {

    // пока что оставил свой токен
    private val token: String = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJwcmFjdGljdW0ucnUiLCJhdWQiOiJwcmFjdGljdW0ucnUiLCJ1c2VybmFtZSI6ItGG0YPRhtC60YPRg9C6In0.jaxpKiIDe0nZZxzLSTVRKibViTN0OAZIUueaVw4LyL8"

    override suspend fun requestFilterArea(): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = NO_INTERNET }
        }

        return withContext(Dispatchers.IO) {
            try {
                val areas = vacancyApi.getArea(token)

                // Можно поменять параметры вывода в Log
                Log.i("areas", areas[0].areas[0].name)

                if (areas.isEmpty()) {
                    Response().apply { resultCode = BAD_REQUEST }
                } else {
                    Response().apply { resultCode = SUCCESS }
                }
            } catch (e: Throwable) {
                Response().apply { resultCode = SERVER_ERROR }
            }
        }
    }

    override suspend fun requestFilterIndustry(): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = NO_INTERNET }
        }

        return withContext(Dispatchers.IO) {
            try {
                val industries = vacancyApi.getIndustry(token)

                // Можно поменять параметры вывода в Log
                Log.i("industries", industries[0].name)

                if (industries.isEmpty()) {
                    Response().apply { resultCode = BAD_REQUEST }
                } else {
                    Response().apply { resultCode = SUCCESS }
                }
            } catch (e: Throwable) {
                Response().apply { resultCode = SERVER_ERROR }
            }
        }
    }

    override suspend fun requestVacancyResponse(dto: VacancyRequest): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = NO_INTERNET }
        }

        return withContext(Dispatchers.IO) {
            try {
                val vacancies = vacancyApi.searchVacancy(token, dto.options)
                vacancies.items.forEach {
                    // Можно поменять параметры вывода в Log
                    Log.i("vacancies", it.id)
                }

                if (vacancies.items.isEmpty()) {
                    Response().apply { resultCode = BAD_REQUEST }
                } else {
                    vacancies.apply { resultCode = SUCCESS }
                }

            } catch (e: Throwable) {
                Log.i("Throwable", e.message.toString())
                Response().apply { resultCode = SERVER_ERROR }

            }
        }
    }

    override suspend fun requestVacancyDetail(dto: VacancyDetailRequest): Response {
        return withContext(Dispatchers.IO) {
            try {
                val vacancyDetail = vacancyApi.getVacancyDetail(token, dto.vacancyId)

                // Можно поменять параметры вывода в Log
                Log.i("vacancyDetail", vacancyDetail.description)
                Response().apply { resultCode = SUCCESS }

            } catch (e: Throwable) {
                if (e.message.toString() == "HTTP 404 Not Found") {
                    Log.i("Throwable", e.message.toString())
                    Response().apply { resultCode = NOT_FOUND }
                } else {
                    Response().apply { resultCode = SERVER_ERROR }
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

    companion object {
        const val NO_INTERNET = -1
        const val SUCCESS = 200
        const val BAD_REQUEST = 400
        const val NOT_FOUND = 404
        const val SERVER_ERROR = 500
    }
}
