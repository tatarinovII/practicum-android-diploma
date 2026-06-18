package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.entity.VacancyFavoriteEntity

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: VacancyFavoriteEntity)

    @Query("DELETE FROM favorite_vacancies WHERE id = :vacancyId")
    suspend fun deleteById(vacancyId: String)

    @Query("SELECT * FROM favorite_vacancies ORDER BY addedAt DESC")
    fun getAll(): Flow<List<VacancyFavoriteEntity>>

    @Query("SELECT * FROM favorite_vacancies WHERE id = :vacancyId")
    suspend fun getById(vacancyId: String): VacancyFavoriteEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_vacancies WHERE id = :vacancyId)")
    suspend fun isFavorite(vacancyId: String): Boolean
}
