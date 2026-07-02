package ru.practicum.android.diploma.domain.models
data class FilterArea(
    val id: String,
    val name: String,
    val parentId: String?,
    val areas: List<FilterArea>
)
