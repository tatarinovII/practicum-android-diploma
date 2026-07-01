package ru.practicum.android.diploma.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.data.repository.FilterDataRepositoryImpl
import ru.practicum.android.diploma.data.repository.FilterSettingsRepositoryImpl
import ru.practicum.android.diploma.data.repository.VacancyRepositoryImpl
import ru.practicum.android.diploma.domain.repository.FilterDataRepository
import ru.practicum.android.diploma.domain.repository.FilterSettingsRepository
import ru.practicum.android.diploma.domain.repository.VacancyRepository

val repositoryModule = module {
    single<VacancyRepository> { VacancyRepositoryImpl(get(), get(), get()) }
    single<FilterSettingsRepository> { FilterSettingsRepositoryImpl(androidContext()) }
    single<FilterDataRepository> { FilterDataRepositoryImpl(get()) }
}
