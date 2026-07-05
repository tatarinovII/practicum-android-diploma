package ru.practicum.android.diploma.presentation.filter

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
    private val filterSettingsInteractor: FilterSettingsInteractor
) : ViewModel() {
    private var _uiRegionState = MutableStateFlow<RegionUiState>(RegionUiState.Content())
    val uiRegionState: StateFlow<RegionUiState> = _uiRegionState
    private var currentCountry: FilterArea? = null
    private val allRegions = mutableListOf<FilterArea>()

    init {
        loadListOfRegions()
    }

    fun loadListOfRegions() {
        viewModelScope.launch {
            _uiRegionState.value = RegionUiState.Loading
            try {
                val result = areaInteractor.getAreas().first()
                if (result.isSuccess) {
                    val listOfCountries = result.getOrNull() ?: emptyList()
                    val settings = filterSettingsInteractor.getFilterSettings()
                    val currentAreaName = settings.areaName
                    val currentAreaId = settings.areaId

                    if (currentAreaName == null) {
                        listOfCountries.forEach { country ->
                            allRegions.addAll(country.areas)
                        }
                        _uiRegionState.value = RegionUiState.Content(regions = sortedAndUpMoscow(allRegions))
                    } else {
                        val countryName = currentAreaName.split(",").firstOrNull()?.trim()
                        val country = if (currentAreaId != null) {
                            listOfCountries.find { it.id == currentAreaId }
                                ?: listOfCountries.find { it.name == countryName }
                        } else {
                            listOfCountries.find { it.name == countryName }
                        }
                        if (country != null) {
                            currentCountry = country
                            _uiRegionState.value = RegionUiState.Content(regions = sortedAndUpMoscow(country.areas))
                        } else {
                            listOfCountries.forEach { country ->
                                allRegions.addAll(country.areas)
                            }
                            _uiRegionState.value = RegionUiState.Content(regions = sortedAndUpMoscow(allRegions))
                        }
                    }
                } else {
                    _uiRegionState.value = RegionUiState.Error("Ошибка при получении списка")
                }
            } catch (e: Exception) {
                _uiRegionState.value = RegionUiState.Error("Ошибка при получении списка")
            }
        }
    }

    fun onRegionSelected(region: FilterArea) {
        viewModelScope.launch {
            try {
                val result = areaInteractor.getAreas().first()
                if (!result.isSuccess) {
                    return@launch
                }
                val listOfCountries = result.getOrNull() ?: emptyList()
                val currentArea = filterSettingsInteractor.getFilterSettings().areaName

                var countryName = ""
                if (currentArea == null) {
                    val country = listOfCountries.find { it.id == region.parentId }
                    countryName = country?.name ?: ""
                } else {
                    countryName = currentCountry?.name ?: ""
                }
                val newAreaName = if (countryName.isNotEmpty()) {
                    "$countryName, ${region.name}"
                } else {
                    region.name
                }

                filterSettingsInteractor.saveFilterSettings(
                    filterSettingsInteractor.getFilterSettings().copy(
                        areaId = region.id,
                        areaName = newAreaName
                    )
                )
            } catch (e: Exception) {
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
