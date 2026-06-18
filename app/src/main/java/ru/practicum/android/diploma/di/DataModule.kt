package ru.practicum.android.diploma.di

import androidx.room.Room
import com.google.gson.Gson
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.converter.Converters
import ru.practicum.android.diploma.data.db.dao.VacancyDao
import ru.practicum.android.diploma.data.network.VacancyApi
import org.koin.android.ext.koin.androidContext

val dataModule = module {
    single<VacancyApi> {
        Retrofit.Builder()
            .baseUrl("https://android-diploma.education-services.ru")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VacancyApi::class.java)
    }

    factory { Gson() }

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
