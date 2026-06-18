package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.data.db.converter.Converters
import ru.practicum.android.diploma.data.db.dao.VacancyDao
import ru.practicum.android.diploma.data.db.entity.VacancyFavoriteEntity

@Database(
    entities = [VacancyFavoriteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
}
