package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.interactor.AreaInteractor
import ru.practicum.android.diploma.domain.models.FilterArea
import ru.practicum.android.diploma.domain.repository.AreaRepository

class AreaInteractorImpl(
    private val repository: AreaRepository
) : AreaInteractor {

    override suspend fun getAreas(): Flow<Result<List<FilterArea>>> {
        return repository.getAreas()
    }
}
