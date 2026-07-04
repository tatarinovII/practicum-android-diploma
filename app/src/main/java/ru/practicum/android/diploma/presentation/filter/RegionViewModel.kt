package ru.practicum.android.diploma.presentation.filter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.interactor.AreaInteractor
import ru.practicum.android.diploma.domain.interactor.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.models.FilterArea

class RegionViewModel(
    private val areaInteractor: AreaInteractor,
    private val filterSettingsInteractor: FilterSettingsInteractor,
) : ViewModel() {
    private var _uiRegionState = MutableStateFlow<RegionUiState>(RegionUiState.Content())
    val uiRegionState: StateFlow<RegionUiState> = _uiRegionState
    lateinit var currentCountry: FilterArea
    private val allRegions = mutableListOf<FilterArea>()

    init {
        loadListOfRegions()
    }
    fun loadListOfRegions() {
        viewModelScope.launch {
            _uiRegionState.value = RegionUiState.Loading
            try {
                val listOfCountries = areaInteractor.getAreas().first()
                val currentArea = filterSettingsInteractor.getFilterSettings().areaName
                if (currentArea == null) {
                    listOfCountries.forEach { country ->
                        allRegions.addAll(country.areas)
                    }
                    _uiRegionState.value = RegionUiState.Content(regions = sortedAndUpMoscow(allRegions))
                } else {
                    val areaId = filterSettingsInteractor.getFilterSettings().areaId
                    currentCountry = listOfCountries.find { it.id == areaId }!!
                    _uiRegionState.value = RegionUiState.Content(regions = sortedAndUpMoscow(currentCountry.areas))
                }
            } catch (e: Exception) {
                _uiRegionState.value = RegionUiState.Error("Ошибка при получении списка")
            }
        }
    }
    fun onRegionSelected(region: FilterArea) {
        viewModelScope.launch {
            val listOfCountries = areaInteractor.getAreas().first()
            val currentArea = filterSettingsInteractor.getFilterSettings().areaName
            var countryNameFromRegion = ""
            if (currentArea == null) {
                val countryId = region.parentId
                listOfCountries.forEach { country ->
                    if (country.id == countryId) {
                        countryNameFromRegion = country.name
                        Log.i("countryNameFromRegion", countryNameFromRegion + ", " + region.name)
                    }
                }
                filterSettingsInteractor.saveFilterSettings(
                    filterSettingsInteractor.getFilterSettings().copy(
                        areaId = countryId,
                        areaName = countryNameFromRegion + ", " + region.name
                    )
                )
            } else {
                filterSettingsInteractor.saveFilterSettings(
                    filterSettingsInteractor.getFilterSettings().copy(
                        areaId = currentCountry.id,
                        areaName = currentCountry.name + ", " + region.name
                    )
                )
            }
        }
    }
    fun sortedAndUpMoscow(regions: List<FilterArea>): List<FilterArea> {
        val mutableSortedRegions = regions
            .sortedBy { it.name }
            .toMutableList()
        val idForMoscow = mutableSortedRegions.indexOfFirst { it.name == "Москва" }
        if (idForMoscow != -1) {
            val regionElementMoscow = mutableSortedRegions.removeAt(idForMoscow)
            mutableSortedRegions.add(0, regionElementMoscow)
        }
        return mutableSortedRegions
    }
}
