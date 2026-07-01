package ru.practicum.android.diploma.domain.interactor

import ru.practicum.android.diploma.domain.models.FilterSettings

interface FilterSettingsInteractor {
    suspend fun saveFilterSettings(settings: FilterSettings)
    suspend fun getFilterSettings(): FilterSettings
    suspend fun clearFilterSettings()
}
