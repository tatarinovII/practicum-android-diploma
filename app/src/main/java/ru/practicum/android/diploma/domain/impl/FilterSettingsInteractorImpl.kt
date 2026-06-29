package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.interactor.FilterSettingsInteractor
import ru.practicum.android.diploma.domain.models.FilterSettings
import ru.practicum.android.diploma.domain.repository.FilterSettingsRepository

class FilterSettingsInteractorImpl(
    private val repository: FilterSettingsRepository
) : FilterSettingsInteractor {

    override suspend fun saveFilterSettings(settings: FilterSettings) {
        repository.saveFilterSettings(settings)
    }

    override suspend fun getFilterSettings(): FilterSettings {
        return repository.getFilterSettings()
    }

    override suspend fun clearFilterSettings() {
        repository.clearFilterSettings()
    }
}
