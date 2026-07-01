package ru.practicum.android.diploma.data.dto.filterarea

import ru.practicum.android.diploma.data.network.Response

data class FilterAreaDto(
    val id: String,
    val name: String,
    val parentId: String,
    val areas: List<FilterAreaDto>
) : Response()
