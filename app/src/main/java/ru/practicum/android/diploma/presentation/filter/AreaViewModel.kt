package ru.practicum.android.diploma.presentation.filter

import android.util.Log
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
            val areaNames = filterSettingsInteractor.getFilterSettings().areaName?.split(", ")
            if (areaNames?.size == 2) {
                _uiState.value = AreaUiState.Content(
                    country = areaNames[0],
                    region = areaNames[1]
                )
            } else {
                _uiState.value = AreaUiState.Content(
                    country = areaNames?.get(0),
                    region = null
                )
            }
        }
    }
    fun clearCountry() {
        viewModelScope.launch {
            val currentSettings = filterSettingsInteractor.getFilterSettings()
            val newSettings = currentSettings.copy(
                areaId = null,
                areaName = null
            )
            filterSettingsInteractor.saveFilterSettings(newSettings)
            val currentState = _uiState.value
            if (currentState is AreaUiState.Content) {
                _uiState.value = currentState.copy(country = "")
            }
        }
    }

    fun clearRegion() {
        viewModelScope.launch {
            val areaNames = filterSettingsInteractor.getFilterSettings().areaName?.split(", ")
            val currentSettings = filterSettingsInteractor.getFilterSettings()
            val newSettings = currentSettings.copy(
                areaId = null,
                areaName = areaNames?.get(0)
            )
            filterSettingsInteractor.saveFilterSettings(newSettings)
            val currentState = _uiState.value
            if (currentState is AreaUiState.Content) {
                _uiState.value = currentState.copy(region = "")
            }
        }
    }
}
