package ru.practicum.android.diploma.di

import androidx.lifecycle.SavedStateHandle
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.presentation.filter.IndustryViewModel
import ru.practicum.android.diploma.presentation.main.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel

val viewModelModule = module {
    viewModel { SearchViewModel(get(), get(), get()) }
    viewModel { VacancyViewModel(get(), get()) }
    viewModel { FavoritesViewModel(get()) }
    single { FilterViewModel(get(), get()) }
    viewModel { (handle: SavedStateHandle) -> IndustryViewModel(get(), get()) }
}
