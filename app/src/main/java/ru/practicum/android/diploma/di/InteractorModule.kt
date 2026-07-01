package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.impl.AreaInteractorImpl
import ru.practicum.android.diploma.domain.impl.FilterSettingsInteractorImpl
import ru.practicum.android.diploma.domain.impl.VacanciesInteractorImpl
import ru.practicum.android.diploma.domain.interactor.AreaInteractor
import ru.practicum.android.diploma.domain.interactor.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.interactor.VacanciesInteractor

val interactorModule = module {
    factory<VacanciesInteractor> { VacanciesInteractorImpl(get()) }
    factory<FilterSettingsInteractor> { FilterSettingsInteractorImpl(get()) }
    factory<AreaInteractor> { AreaInteractorImpl(get()) }
}
