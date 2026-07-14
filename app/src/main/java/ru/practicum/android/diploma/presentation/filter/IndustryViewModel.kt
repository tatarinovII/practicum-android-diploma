package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.FilterDataInteractor
import ru.practicum.android.diploma.domain.interactor.FilterSettingsInteractor

class IndustryViewModel(
    private val filterDataInteractor: FilterDataInteractor,
    private val filterSettingsInteractor: FilterSettingsInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow(IndustryUiState())
    val uiState: StateFlow<IndustryUiState> = _uiState.asStateFlow()

    init {
        loadIndustries()
        viewModelScope.launch {
            val settings = filterSettingsInteractor.getFilterSettings()
            _uiState.update { it.copy(selectedIndustryId = settings.industryId) }
        }
    }

    private fun loadIndustries() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val result = filterDataInteractor.getIndustries()
            result.fold(
                onSuccess = { industries ->
                    val sorted = industries.sortedBy { it.name }
                    _uiState.update {
                        it.copy(
                            industries = sorted,
                            filteredIndustries = sorted,
                            isLoading = false,
                            error = null
                        )
                    }
                },
                onFailure = { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = e.message ?: "Ошибка загрузки"
                        )
                    }
                }
            )
        }
    }

    fun onSearchQueryChanged(query: String) {
        val state = _uiState.value
        val filtered = if (query.isBlank()) {
            state.industries
        } else {
            state.industries.filter { it.name.contains(query, ignoreCase = true) }
        }
        _uiState.update {
            it.copy(
                searchQuery = query,
                filteredIndustries = filtered
            )
        }
    }

    fun onIndustrySelected(industryId: Int) {
        val state = _uiState.value
        val newSelectedId = if (state.selectedIndustryId == industryId) null else industryId
        _uiState.update { it.copy(selectedIndustryId = newSelectedId) }
    }

    fun confirmSelection() {
        val selectedId = _uiState.value.selectedIndustryId
        if (selectedId != null) {
            val selectedIndustry = _uiState.value.industries.find { it.id == selectedId }
            selectedIndustry?.let {
                viewModelScope.launch {
                    val currentSettings = filterSettingsInteractor.getFilterSettings()
                    val newSettings = currentSettings.copy(
                        industryId = it.id,
                        industryName = it.name
                    )
                    filterSettingsInteractor.saveFilterSettings(newSettings)
                }
            }
        }
    }
}
