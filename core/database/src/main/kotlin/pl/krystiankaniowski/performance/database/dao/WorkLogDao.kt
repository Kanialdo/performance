package pl.krystiankaniowski.performance.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import pl.krystiankaniowski.performance.database.model.WorkLogEntity

@Dao
interface WorkLogDao {

    @Query("SELECT * FROM worklog")
    fun getAll(): List<WorkLogEntity>

    @Insert
    fun insertAll(vararg workLogs: WorkLogEntity)

    @Delete
    fun delete(workLog: WorkLogEntity)
}