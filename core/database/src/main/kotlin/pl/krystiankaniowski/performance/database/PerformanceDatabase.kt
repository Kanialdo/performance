package pl.krystiankaniowski.performance.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pl.krystiankaniowski.performance.database.dao.WorkLogDao
import pl.krystiankaniowski.performance.database.model.WorkLogEntity

@Database(entities = [WorkLogEntity::class], version = 1)
abstract class PerformanceDatabase : RoomDatabase() {
    abstract fun workLogDao(): WorkLogDao
}