package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.interactor.FilterDataInteractor
import ru.practicum.android.diploma.domain.models.FilterIndustry
import ru.practicum.android.diploma.domain.repository.FilterDataRepository

class FilterDataInteractorImpl(
    private val repository: FilterDataRepository
) : FilterDataInteractor {
    override suspend fun getIndustries(): Result<List<FilterIndustry>> {
        return repository.getIndustries()
    }
}
