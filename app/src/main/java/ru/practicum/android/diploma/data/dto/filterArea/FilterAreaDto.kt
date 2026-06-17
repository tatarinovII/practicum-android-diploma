package ru.practicum.android.diploma.data.dto.filterArea

data class FilterAreaDto(
    val id: Int,
    val name: String,
    val parentId: Int,
    val areas: List<String>
)
