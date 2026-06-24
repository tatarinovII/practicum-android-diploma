package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.network.api.VacancyApi
import ru.practicum.android.diploma.data.network.VacancyDetailRequest
import ru.practicum.android.diploma.data.network.VacancyRequest
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.practicum.android.diploma.ui.navigation.AppNavHost
import ru.practicum.android.diploma.ui.theme.MyAppTheme

class RootActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyAppTheme {
                AppNavHost()
            }
        }

        //region settings
        val vacancyApi = Retrofit.Builder()
            .baseUrl("https://android-diploma.education-services.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VacancyApi::class.java)

        val retrofitNetworkClient = RetrofitNetworkClient(this, vacancyApi)

        testArea(retrofitNetworkClient, vacancyApi)
        testIndustry(retrofitNetworkClient, vacancyApi)
        testVacancy(retrofitNetworkClient, vacancyApi)
        testVacancyDetail(retrofitNetworkClient, vacancyApi)
        //endregion
        //информация о результате из RetrofitNetworkClient в LogCat

    }

    //region testArea
    private fun testArea(retrofitNetworkClient: RetrofitNetworkClient, vacancyApi: VacancyApi) {
        lifecycleScope.launch {
            try {
                val response = retrofitNetworkClient.requestFilterArea()
                Log.d("NetworkRequest", "Response: $response")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    //endregion
    //region testIndustry
    private fun testIndustry(retrofitNetworkClient: RetrofitNetworkClient, vacancyApi: VacancyApi) {
        lifecycleScope.launch {
            try {
                val response = retrofitNetworkClient.requestFilterIndustry()
                Log.d("NetworkRequest", "Response: $response")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    //endregion
    //region testVacancy
    private fun testVacancy(retrofitNetworkClient: RetrofitNetworkClient, vacancyApi: VacancyApi) {

        val options: HashMap<String, String> = HashMap()
        val area = "1"
        val text = "DevOps-инженер"

        options["area"] = area
        options["text"] = text

        lifecycleScope.launch {
            try {
                val response = retrofitNetworkClient.requestVacancyResponse(VacancyRequest(options))
                Log.d("NetworkRequest", "Response: ${response.resultCode}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    //endregion
    //region testVacancyDetail
    private fun testVacancyDetail(retrofitNetworkClient: RetrofitNetworkClient, vacancyApi: VacancyApi) {

        val id = "001cef68-027f-36b2-b7e0-b4e10a2d831f"

        lifecycleScope.launch {
            try {
                val response = retrofitNetworkClient.requestVacancyDetail(VacancyDetailRequest(id))
                Log.d("NetworkRequest", "Response: ${response.resultCode}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    //endregion
}
