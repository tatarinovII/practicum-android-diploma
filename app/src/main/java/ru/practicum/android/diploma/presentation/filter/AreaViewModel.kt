package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.AreaInteractor
import ru.practicum.android.diploma.domain.models.FilterArea

//TODO сделать viewModel полностью
class AreaViewModel(
    private val areaInteractor: AreaInteractor
) : ViewModel() {

    private val _uiState = MutableStateFlow<AreaUiState>(AreaUiState.Content())
    val uiState: StateFlow<AreaUiState> = _uiState

    var areas = emptyList<FilterArea>()

    private var _countries = MutableStateFlow<List<FilterArea>>(emptyList())
    val countries: StateFlow<List<FilterArea>> = _countries

    private var country: FilterArea? = null

    init {
        loadAreas()
    }

    fun loadAreas() {
        viewModelScope.launch {
//            areaInteractor.getAreas()
//                .collect {
//                    areas = it
//                    _countries.value = it
//                }
        }
    }

    fun setCountry(filterArea: FilterArea) {
        viewModelScope.launch {
            _uiState.value = AreaUiState.Content(country = filterArea.name)
            country = filterArea
        }
    }

    fun setRegion() {
        viewModelScope.launch {
//            areaInteractor.setRegion
        }
    }

    fun getRegions() {
//        areaInteractor.getRegions()
    }

    fun clearCountry() {
        viewModelScope.launch {
//            areaInteractor.clearCountry
        }
    }

    fun clearRegion() {
        viewModelScope.launch {
//            areaInteractor.clearRegion()
        }
    }
}
