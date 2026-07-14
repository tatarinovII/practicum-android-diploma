package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.filterindustry.FilterIndustryDto
import ru.practicum.android.diploma.domain.models.FilterIndustry

fun FilterIndustryDto.toFilterIndustry(): FilterIndustry {
    return FilterIndustry(
        id = id,
        name = name
    )
}
