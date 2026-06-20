package ru.practicum.android.diploma.di

import androidx.room.Room
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.NetworkClient
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.converter.Converters
import ru.practicum.android.diploma.data.db.dao.VacancyDao
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.data.network.VacancyApi

val dataModule = module {
    single {
        val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.API_ACCESS_TOKEN}")
                .build()
            chain.proceed(request)
        }
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    single<VacancyApi> {
        Retrofit.Builder()
            .baseUrl("https://android-diploma.education-services.ru")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VacancyApi::class.java)
    }

    factory { Gson() }

    single<NetworkClient> { RetrofitNetworkClient(androidContext(), get()) }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "diploma_database"
        ).build()
    }

    single<VacancyDao> { get<AppDatabase>().vacancyDao() }

    factory { Converters() }
}
