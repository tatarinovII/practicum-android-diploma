package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.main.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { VacancyViewModel(get(), get()) }
}
