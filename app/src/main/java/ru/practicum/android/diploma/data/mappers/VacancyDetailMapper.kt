package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.filterarea.FilterAreaDto
import ru.practicum.android.diploma.data.dto.filterindustry.FilterIndustryDto
import ru.practicum.android.diploma.data.dto.vacancydetail.AddressDto
import ru.practicum.android.diploma.data.dto.vacancydetail.ContactsDto
import ru.practicum.android.diploma.data.dto.vacancydetail.EmployerDto
import ru.practicum.android.diploma.data.dto.vacancydetail.EmploymentDto
import ru.practicum.android.diploma.data.dto.vacancydetail.ExperienceDto
import ru.practicum.android.diploma.data.dto.vacancydetail.PhoneDto
import ru.practicum.android.diploma.data.dto.vacancydetail.SalaryDto
import ru.practicum.android.diploma.data.dto.vacancydetail.ScheduleDto
import ru.practicum.android.diploma.data.dto.vacancydetail.VacancyDetailDto
import ru.practicum.android.diploma.domain.models.Address
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Currency
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Employment
import ru.practicum.android.diploma.domain.models.Experience
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Schedule
import ru.practicum.android.diploma.domain.models.VacancyDetail


fun VacancyDetailDto.toDomain(): VacancyDetail {
    return VacancyDetail(
        id = id,
        name = name,
        description = description,
        salary = salary?.toDomain(),
        address = address?.toDomain(),
        experience = experience?.toDomain(),
        schedule = schedule?.toDomain(),
        employment = employment?.toDomain(),
        contacts = contacts?.toDomain(),
        employer = employer.toDomain(),
        area = area.toDomain(),
        skills = skills,
        url = url,
        industry = industry.toDomain()
    )
}

fun SalaryDto.toDomain(): Salary {
    return Salary(
        from = from,
        to = to,
        currency = currency?.let { Currency.valueOf(it) }
    )
}

fun AddressDto.toDomain(): Address {
    return Address(
        city = city,
        street = street,
        building = building,
        raw = raw
    )
}

fun ExperienceDto.toDomain(): Experience {
    return Experience(name = name)
}

fun ScheduleDto.toDomain(): Schedule {
    return Schedule(name = name)
}

fun EmploymentDto.toDomain(): Employment {
    return Employment(name = name)
}

fun ContactsDto.toDomain(): Contacts {
    return Contacts(
        name = name,
        email = email,
        phones = phones.map { it.toDomain() }
    )
}

fun PhoneDto.toDomain(): Phone {
    return Phone(
        comment = comment,
        formatted = formatted
    )
}

fun EmployerDto.toDomain(): Employer {
    return Employer(
        name = name,
        logo = logo
    )
}

fun FilterAreaDto.toDomain(): Area {
    return Area(name = name)
}

fun FilterIndustryDto.toDomain(): Industry {
    return Industry(name = name)
}
