package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.models.FilterSettings
import ru.practicum.android.diploma.util.FilterEventBus

class FilterViewModel(
    private val filterSettingsInteractor: FilterSettingsInteractor,
    private val filterEventBus: FilterEventBus
) : ViewModel() {

    private val _uiState = MutableStateFlow(FilterUiState())
    val uiState: StateFlow<FilterUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    fun loadSettings() {
        viewModelScope.launch {
            val settings = filterSettingsInteractor.getFilterSettings()
            _uiState.update {
                it.copy(
                    countryId = settings.countryId,
                    countryName = settings.countryName,
                    regionId = settings.regionId,
                    regionName = settings.regionName,
                    industryId = settings.industryId,
                    industryName = settings.industryName,
                    salaryText = settings.salary?.toString() ?: "",
                    onlyWithSalary = settings.onlyWithSalary
                )
            }
        }
    }

    fun clearArea() {
        _uiState.update {
            it.copy(countryId = null, countryName = null, regionId = null, regionName = null)
        }
        saveSettings()
    }

    fun clearIndustry() {
        _uiState.update { it.copy(industryId = null, industryName = null) }
        saveSettings()
    }

    fun updateSalaryText(text: String) {
        val filtered = text.filter { it.isDigit() }
        _uiState.update { it.copy(salaryText = filtered) }
        saveSettings()
    }

    fun toggleOnlyWithSalary() {
        _uiState.update { it.copy(onlyWithSalary = !it.onlyWithSalary) }
        saveSettings()
    }

    private fun saveSettings() {
        viewModelScope.launch {
            val state = _uiState.value
            val settings = FilterSettings(
                countryId = state.countryId,
                countryName = state.countryName,
                regionId = state.regionId,
                regionName = state.regionName,
                industryId = state.industryId,
                industryName = state.industryName,
                salary = state.salaryText.toIntOrNull(),
                onlyWithSalary = state.onlyWithSalary
            )
            filterSettingsInteractor.saveFilterSettings(settings)
        }
    }

    fun applyFilter() {
        viewModelScope.launch {
            saveSettings()
            filterEventBus.emitFilterApplied()
        }
    }

    fun resetFilter() {
        viewModelScope.launch {
            _uiState.update { FilterUiState() }
            saveSettings()
            filterEventBus.emitFilterApplied()
        }
    }

    fun isFilterActive(): Boolean {
        val state = _uiState.value
        return state.countryId != null || state.regionId != null ||
            state.industryId != null || state.salaryText.isNotEmpty() || state.onlyWithSalary
    }
}
