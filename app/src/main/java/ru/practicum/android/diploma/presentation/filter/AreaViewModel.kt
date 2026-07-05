package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.FilterSettingsInteractor

class AreaViewModel(
    private val filterSettingsInteractor: FilterSettingsInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow<AreaUiState>(AreaUiState.Content())
    val uiState: StateFlow<AreaUiState> = _uiState

    init {
        loadAreas()
    }

    fun loadAreas() {
        viewModelScope.launch {
            val settings = filterSettingsInteractor.getFilterSettings()
            _uiState.value = AreaUiState.Content(
                country = settings.countryName,
                region = settings.regionName
            )
        }
    }

    fun clearCountry() {
        viewModelScope.launch {
            val currentSettings = filterSettingsInteractor.getFilterSettings()
            val newSettings = currentSettings.copy(
                countryId = null,
                countryName = null,
                regionId = null,
                regionName = null
            )
            filterSettingsInteractor.saveFilterSettings(newSettings)
            _uiState.value = AreaUiState.Content(country = null, region = null)
        }
    }

    fun clearRegion() {
        viewModelScope.launch {
            val currentSettings = filterSettingsInteractor.getFilterSettings()
            val newSettings = currentSettings.copy(
                regionId = null,
                regionName = null
            )
            filterSettingsInteractor.saveFilterSettings(newSettings)
            _uiState.value = AreaUiState.Content(
                country = currentSettings.countryName,
                region = null
            )
        }
    }
}
