package ru.practicum.android.diploma.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancies")
data class VacancyFavoriteEntity(
    @PrimaryKey
    val id: String,
    val vacancyJson: String,
    val addedAt: Long = System.currentTimeMillis()
)
