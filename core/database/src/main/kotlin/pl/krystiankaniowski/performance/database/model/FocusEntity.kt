package pl.krystiankaniowski.performance.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity("focus")
data class FocusEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "date_start") val dateStart: Instant,
    @ColumnInfo(name = "date_end") val dateEnd: Instant,
)