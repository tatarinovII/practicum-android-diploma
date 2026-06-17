package ru.practicum.android.diploma.data.dto.vacancyDetail

data class ContactsDto(
    val id: String,
    val name: String,
    val email: String,
    val phones: List<PhoneDto>
)
