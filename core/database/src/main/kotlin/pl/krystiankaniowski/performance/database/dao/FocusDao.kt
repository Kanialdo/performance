package pl.krystiankaniowski.performance.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pl.krystiankaniowski.performance.database.model.FocusEntity

@Dao
internal interface FocusDao {

    @Insert
    suspend fun insert(vararg focus: FocusEntity)

    @Update
    suspend fun update(focus: FocusEntity)

    @Query("SELECT * FROM focus WHERE uid = :id")
    suspend fun get(id: Long): FocusEntity

    @Query("SELECT * FROM focus")
    fun getAll(): Flow<List<FocusEntity>>

    @Query("DELETE FROM focus WHERE uid = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM focus")
    suspend fun deleteAll()
}