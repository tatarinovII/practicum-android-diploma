package ru.practicum.android.diploma.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import ru.practicum.android.diploma.data.dto.vacancydetail.VacancyDetailDto

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromVacancyDetail(detail: VacancyDetailDto): String {
        return gson.toJson(detail)
    }

    @TypeConverter
    fun toVacancyDetail(json: String): VacancyDetailDto {
        return gson.fromJson(json, VacancyDetailDto::class.java)
    }
}
