package ru.practicum.android.diploma.data.network.api

import ru.practicum.android.diploma.data.dto.filterarea.FilterAreaDto

class AreaResponse(
    var resultCode: Int = 0,
    val results: List<FilterAreaDto> = emptyList()
)
