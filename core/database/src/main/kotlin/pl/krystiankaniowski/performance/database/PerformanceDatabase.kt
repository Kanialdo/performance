package pl.krystiankaniowski.performance.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pl.krystiankaniowski.performance.database.dao.FocusDao
import pl.krystiankaniowski.performance.database.model.FocusEntity
import pl.krystiankaniowski.performance.database.utils.InstantConverter

@Database(entities = [FocusEntity::class], version = 1)
@TypeConverters(
    InstantConverter::class,
)
abstract class PerformanceDatabase : RoomDatabase() {
    abstract fun focusDao(): FocusDao
}