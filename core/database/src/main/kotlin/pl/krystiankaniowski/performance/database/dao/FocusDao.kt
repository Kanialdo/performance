package pl.krystiankaniowski.performance.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pl.krystiankaniowski.performance.database.model.FocusEntity

@Dao
interface FocusDao {

    @Insert
    suspend fun insert(vararg focus: FocusEntity)

    @Query("SELECT * FROM focus")
    suspend fun getAll(): List<FocusEntity>
}