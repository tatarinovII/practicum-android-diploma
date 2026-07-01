package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.repository.AreaRepository

class AreaRepositoryImpl() : AreaRepository {
    override suspend fun getCountries(): Flow<List<String>> {
        TODO("Not yet implemented")
    }

    override fun setCountry(country: String) {
        TODO("Not yet implemented")
    }

    override fun clearCountry() {
        TODO("Not yet implemented")
    }
}
