package ru.practicum.android.diploma.presentation.filter

import android.util.Log
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

    //var country: FilterArea? = null

    init {
        Log.i("1_1", "AreaViewModel created: $this")
        loadAreas()
    }

    fun loadAreas() {
        viewModelScope.launch {
            areaInteractor.getAreas()
                .collect {
                    areas = it
                    _countries.value = it
                }
        }
    }

    fun setCountryFromArea(filterArea: FilterArea?) {

        val currentState = _uiState.value
        if (currentState is AreaUiState.Content) {
            _uiState.value = currentState.copy(country = filterArea?.name)
            Log.i("2_2", "new state: ${_uiState.value}")
        }


        //_uiState.value = AreaUiState.Content(country = filterArea?.name)
        //Log.i("atut", "${uiState.value}")
        //country = filterArea
    }

    fun setRegionFromArea() {
        viewModelScope.launch {
//            areaInteractor.setRegion
        }
    }

    fun getRegions() {
//        areaInteractor.getRegions()
    }

    fun clearCountry() {
        val currentState = _uiState.value
        if (currentState is AreaUiState.Content) {
            _uiState.value = currentState.copy(country = "")
        }
    }

    fun clearRegion() {
        viewModelScope.launch {
//            areaInteractor.clearRegion()
        }
    }
}
