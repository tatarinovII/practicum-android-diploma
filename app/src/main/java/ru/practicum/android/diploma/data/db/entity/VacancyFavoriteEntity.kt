package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.practicum.android.diploma.domain.models.VacancyDetail

@Entity(tableName = "favorite_vacancies")
data class VacancyFavoriteEntity(
    @PrimaryKey
    val id: String,
    val vacancyDetail: VacancyDetail,
    val addedAt: Long = System.currentTimeMillis()
)
