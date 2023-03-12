package pl.krystiankaniowski.performance.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pl.krystiankaniowski.performance.database.model.FocusEntity

@Dao
interface FocusDao {

    @Insert
    fun insert(vararg focus: FocusEntity)
}