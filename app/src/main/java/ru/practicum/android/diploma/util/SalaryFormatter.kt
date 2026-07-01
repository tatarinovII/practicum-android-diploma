package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.domain.models.Salary

fun Salary.formatSalary(): String {
    val fromFormatted = from?.toString()?.replace(Regex("(\\d)(?=(\\d{3})+$)"), "$1 ") ?: ""
    val toFormatted = to?.toString()?.replace(Regex("(\\d)(?=(\\d{3})+$)"), "$1 ") ?: ""
    val currencySymbol = currency?.name ?: ""

    return when {
        from != null && to != null -> "от $fromFormatted до $toFormatted $currencySymbol"
        from != null -> "от $fromFormatted $currencySymbol"
        to != null -> "до $toFormatted $currencySymbol"
        else -> "Зарплата не указана"
    }
}
