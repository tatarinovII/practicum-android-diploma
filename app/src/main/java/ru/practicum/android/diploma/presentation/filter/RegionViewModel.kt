package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
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
    private var countriesCache: List<FilterArea>? = null

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
                    countriesCache = listOfCountries

                    val settings = filterSettingsInteractor.getFilterSettings()
                    val country = listOfCountries.find { it.id == settings.countryId }

                    val regionsToShow = if (country != null) {
                        country.areas.sortedBy { it.name }
                    } else {
                        listOfCountries.flatMap { it.areas }.sortedBy { it.name }
                    }

                    _uiRegionState.value = RegionUiState.Content(regions = sortedAndUpMoscow(regionsToShow))
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
            val currentSettings = filterSettingsInteractor.getFilterSettings()
            var countryId = currentSettings.countryId
            var countryName = currentSettings.countryName

            if (countryId == null || countryName == null) {
                val countries = countriesCache
                if (countries != null) {
                    val country = countries.find { it.id == region.parentId }
                    if (country != null) {
                        countryId = country.id
                        countryName = country.name
                    }
                } else {
                    val result = areaInteractor.getAreas().firstOrNull()
                    if (result?.isSuccess == true) {
                        val loadedCountries = result.getOrNull() ?: emptyList()
                        countriesCache = loadedCountries
                        val country = loadedCountries.find { it.id == region.parentId }
                        if (country != null) {
                            countryId = country.id
                            countryName = country.name
                        }
                    }
                }
            }

            val newSettings = currentSettings.copy(
                countryId = countryId,
                countryName = countryName,
                regionId = region.id,
                regionName = region.name
            )
            filterSettingsInteractor.saveFilterSettings(newSettings)
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
