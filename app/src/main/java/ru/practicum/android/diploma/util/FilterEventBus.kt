package ru.practicum.android.diploma.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FilterEventBus {
    private val _filterApplied = MutableSharedFlow<Unit>()
    val filterApplied: SharedFlow<Unit> = _filterApplied.asSharedFlow()

    suspend fun emitFilterApplied() {
        _filterApplied.emit(Unit)
    }
}
