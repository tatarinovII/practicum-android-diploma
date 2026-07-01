package ru.practicum.android.diploma.domain.repository

import ru.practicum.android.diploma.domain.models.FilterSettings

interface FilterSettingsRepository {
    suspend fun saveFilterSettings(settings: FilterSettings)
    suspend fun getFilterSettings(): FilterSettings
    suspend fun clearFilterSettings()
}
