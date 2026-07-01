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

    private fun loadSettings() {
        viewModelScope.launch {
            val settings = filterSettingsInteractor.getFilterSettings()
            _uiState.update {
                it.copy(
                    areaId = settings.areaId,
                    areaName = settings.areaName,
                    industryId = settings.industryId,
                    industryName = settings.industryName,
                    salaryText = settings.salary?.toString() ?: "",
                    onlyWithSalary = settings.onlyWithSalary
                )
            }
        }
    }

    fun updateArea(areaId: String, areaName: String) {
        _uiState.update {
            it.copy(
                areaId = if (areaName.isEmpty()) null else areaId,
                areaName = if (areaName.isEmpty()) null else areaName
            )
        }
    }

    fun updateIndustry(industryId: Int, industryName: String) {
        _uiState.update {
            it.copy(
                industryId = if (industryName.isEmpty()) null else industryId,
                industryName = if (industryName.isEmpty()) null else industryName
            )
        }
    }

    fun updateSalaryText(text: String) {
        val filtered = text.filter { it.isDigit() }
        _uiState.update { it.copy(salaryText = filtered) }
    }

    fun toggleOnlyWithSalary() {
        _uiState.update { it.copy(onlyWithSalary = !it.onlyWithSalary) }
    }

    private fun saveSettings() {
        viewModelScope.launch {
            val state = _uiState.value
            val settings = FilterSettings(
                areaId = state.areaId,
                areaName = state.areaName,
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
        return state.areaId != null || state.industryId != null || state.salaryText.isNotEmpty() || state.onlyWithSalary
    }
}
