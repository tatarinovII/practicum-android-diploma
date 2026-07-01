package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.interactor.AreaInteractor

class AreaInteractorImpl(private val repository: AreaRepository) : AreaInteractor {
    override suspend fun getCountries(): Flow<List<String>> {
        return repository.getCountries()
    }

    override fun setCountry() {
        repository.setCountry()
    }

    override fun clearCountry() {
        repository.clearCountry()
    }
}
