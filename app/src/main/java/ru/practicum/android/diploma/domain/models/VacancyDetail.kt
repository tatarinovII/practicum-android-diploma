package ru.practicum.android.diploma.domain.models

data class VacancyDetail(
    val id: String,
    val name: String,
    val description: String?,
    val salary: Salary?,
    val address: Address?,
    val experience: Experience?,
    val schedule: Schedule?,
    val employment: Employment?,
    val contacts: Contacts?,
    val employer: Employer?,
    val area: FilterArea?,
    val skills: List<String>?,
    val url: String?,
    val industry: Industry?
)

data class Address(
    val city: String?,
    val street: String?,
    val building: String?,
    val raw: String?
)

data class Experience(
    val name: String?
)

data class Schedule(
    val name: String?
)

data class Employment(
    val name: String?
)

data class Contacts(
    val name: String?,
    val email: String?,
    val phones: List<Phone>?
)

data class Phone(
    val comment: String?,
    val formatted: String?
)

data class Employer(
    val name: String?,
    val logo: String?
)

data class Industry(
    val name: String?
)
