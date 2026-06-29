package ru.practicum.android.diploma.data.repository

import android.content.Context
import android.content.SharedPreferences
import ru.practicum.android.diploma.domain.models.FilterSettings
import ru.practicum.android.diploma.domain.repository.FilterSettingsRepository

class FilterSettingsRepositoryImpl(
    context: Context
) : FilterSettingsRepository {
    private val prefs: SharedPreferences = context.getSharedPreferences("filter_settings", Context.MODE_PRIVATE)

    override suspend fun saveFilterSettings(settings: FilterSettings) {
        prefs.edit().apply {
            settings.areaId?.let { putString("area_id", it) } ?: remove("area_id")
            settings.areaName?.let { putString("area_name", it) } ?: remove("area_name")
            settings.industryId?.let { putInt("industry_id", it) } ?: remove("industry_id")
            settings.industryName?.let { putString("industry_name", it) } ?: remove("industry_name")
            settings.salary?.let { putInt("salary", it) } ?: remove("salary")
            putBoolean("only_with_salary", settings.onlyWithSalary)
            apply()
        }
    }

    override suspend fun getFilterSettings(): FilterSettings {
        return FilterSettings(
            areaId = prefs.getString("area_id", null),
            areaName = prefs.getString("area_name", null),
            industryId = if (prefs.contains("industry_id")) prefs.getInt("industry_id", 0) else null,
            industryName = prefs.getString("industry_name", null),
            salary = if (prefs.contains("salary")) prefs.getInt("salary", 0) else null,
            onlyWithSalary = prefs.getBoolean("only_with_salary", false)
        )
    }

    override suspend fun clearFilterSettings() {
        prefs.edit().clear().apply()
    }
}
