package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.interactor.AreaInteractor
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.domain.repository.AreaRepository

class AreaInteractorImpl(
    private val repository: AreaRepository
) : AreaInteractor {

    override suspend fun getAreas(): Result<List<FilterArea>> {
        return repository.getAreas()
    }
}
