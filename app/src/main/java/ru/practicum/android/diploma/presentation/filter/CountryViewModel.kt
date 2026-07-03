package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.AreaInteractor
import ru.practicum.android.diploma.domain.interactor.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.models.FilterArea

class CountryViewModel(
    private val areaInteractor: AreaInteractor,
    private val filterSettingsInteractor: FilterSettingsInteractor
) : ViewModel() {

    private var _uiCountryState = MutableStateFlow<CountryUiState>(CountryUiState.Content())
    val uiCountryState: StateFlow<CountryUiState> = _uiCountryState

    init {
        loadListOfCountries()
    }

    fun loadListOfCountries() {
        viewModelScope.launch {
            _uiCountryState.value = CountryUiState.Loading
            areaInteractor.getAreas().collect { result ->
                result.fold(
                    onSuccess = { list -> _uiCountryState.value = CountryUiState.Content(countries = list) },
                    onFailure = { error -> CountryUiState.Error(error.message) }
                )

            }
        }
    }

    fun onCountrySelected(country: FilterArea) {
        viewModelScope.launch {
            filterSettingsInteractor.saveFilterSettings(
                filterSettingsInteractor.getFilterSettings().copy(
                    areaId = country.id,
                    areaName = country.name
                )
            )
        }
    }

}
