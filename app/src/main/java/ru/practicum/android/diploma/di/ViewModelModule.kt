package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.presentation.filter.AreaViewModel
import ru.practicum.android.diploma.presentation.filter.CountryViewModel
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.presentation.filter.IndustryViewModel
import ru.practicum.android.diploma.presentation.filter.RegionViewModel
import ru.practicum.android.diploma.presentation.main.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel

val viewModelModule = module {
    viewModel { SearchViewModel(get(), get(), get()) }
    viewModel { VacancyViewModel(get(), get()) }
    viewModel { FavoritesViewModel(get()) }
    viewModel { FilterViewModel(get(), get()) }
    viewModel { AreaViewModel(get()) }
    viewModel { IndustryViewModel(get(), get()) }
    viewModel { CountryViewModel(get(), get()) }
    viewModel { RegionViewModel(get(), get()) }
}
