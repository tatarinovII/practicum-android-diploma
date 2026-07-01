package ru.practicum.android.diploma.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.VacancyDetail

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun map(detail: VacancyDetail): String {
        return gson.toJson(detail)
    }

    @TypeConverter
    fun map(json: String): VacancyDetail {
        return gson.fromJson(json, VacancyDetail::class.java)
    }
}
