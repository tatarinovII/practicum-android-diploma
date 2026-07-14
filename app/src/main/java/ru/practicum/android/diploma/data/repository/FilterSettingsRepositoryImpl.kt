package ru.practicum.android.diploma.data.repository

import android.content.Context
import android.content.SharedPreferences
import ru.practicum.android.diploma.domain.models.FilterSettings
import ru.practicum.android.diploma.domain.repository.FilterSettingsRepository

class FilterSettingsRepositoryImpl(
    context: Context
) : FilterSettingsRepository {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    override suspend fun saveFilterSettings(settings: FilterSettings) {
        val editor = prefs.edit()
        saveString(editor, KEY_COUNTRY_ID, settings.countryId)
        saveString(editor, KEY_COUNTRY_NAME, settings.countryName)
        saveString(editor, KEY_REGION_ID, settings.regionId)
        saveString(editor, KEY_REGION_NAME, settings.regionName)
        saveInt(editor, KEY_INDUSTRY_ID, settings.industryId)
        saveString(editor, KEY_INDUSTRY_NAME, settings.industryName)
        saveInt(editor, KEY_SALARY, settings.salary)
        editor.putBoolean(KEY_ONLY_WITH_SALARY, settings.onlyWithSalary)
        editor.apply()
    }

    private fun saveString(editor: SharedPreferences.Editor, key: String, value: String?) {
        if (value != null) {
            editor.putString(key, value)
        } else {
            editor.remove(key)
        }
    }

    private fun saveInt(editor: SharedPreferences.Editor, key: String, value: Int?) {
        if (value != null) {
            editor.putInt(key, value)
        } else {
            editor.remove(key)
        }
    }

    override suspend fun getFilterSettings(): FilterSettings {
        return FilterSettings(
            countryId = prefs.getString(KEY_COUNTRY_ID, null),
            countryName = prefs.getString(KEY_COUNTRY_NAME, null),
            regionId = prefs.getString(KEY_REGION_ID, null),
            regionName = prefs.getString(KEY_REGION_NAME, null),
            industryId = if (prefs.contains(KEY_INDUSTRY_ID)) prefs.getInt(KEY_INDUSTRY_ID, 0) else null,
            industryName = prefs.getString(KEY_INDUSTRY_NAME, null),
            salary = if (prefs.contains(KEY_SALARY)) prefs.getInt(KEY_SALARY, 0) else null,
            onlyWithSalary = prefs.getBoolean(KEY_ONLY_WITH_SALARY, false)
        )
    }

    override suspend fun clearFilterSettings() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "filter_settings"
        private const val KEY_COUNTRY_ID = "country_id"
        private const val KEY_COUNTRY_NAME = "country_name"
        private const val KEY_REGION_ID = "region_id"
        private const val KEY_REGION_NAME = "region_name"
        private const val KEY_INDUSTRY_ID = "industry_id"
        private const val KEY_INDUSTRY_NAME = "industry_name"
        private const val KEY_SALARY = "salary"
        private const val KEY_ONLY_WITH_SALARY = "only_with_salary"
    }

}
