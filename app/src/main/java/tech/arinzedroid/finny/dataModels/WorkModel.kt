package tech.arinzedroid.finny.dataModels

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import tech.arinzedroid.finny.utils.DateConverter
import java.util.*

@Entity
@TypeConverters(DateConverter::class)
data class WorkModel(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        var workId: String = "",
        var isWorkPeriodic: Boolean = true,
        var itemId: String = "",
        var itemType: String = "",
        var createdAt: Date = Date(),
        var updatedAt: Date = Date(),
        var dueDate: Date = Date(),
        var interval: Long = 0
) {
}