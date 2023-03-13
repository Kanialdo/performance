package pl.krystiankaniowski.performance.database.dao

import androidx.room.Dao
import androidx.room.Insert
import pl.krystiankaniowski.performance.database.model.FocusEntity

@Dao
interface FocusDao {

    @Insert
    fun insert(vararg focus: FocusEntity)
}