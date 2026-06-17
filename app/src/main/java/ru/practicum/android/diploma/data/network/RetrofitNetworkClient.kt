package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.filterArea.FilterAreaDto
import ru.practicum.android.diploma.data.dto.filterIndustry.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.vacancyDetail.VacancyDetailDto
import ru.practicum.android.diploma.data.dto.vacancyResponse.VacancyDto

class RetrofitNetworkClient(private val context: Context, private val vacancyApi: VacancyApi) : NetworkClient {

    override suspend fun requestFilterArea(dto: FilterAreaDto): Response {
        TODO("Not yet implemented")
    }

    override suspend fun requestFilterIndustry(dto: FilterIndustryDto): Response {
        TODO("Not yet implemented")
    }

    override suspend fun requestVacancyResponse(dto: VacancyDto): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
        }
        TODO("Not yet implemented")
    }

    override suspend fun requestVacancyDetail(dto: VacancyDetailDto): Response {
        TODO("Not yet implemented")
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
