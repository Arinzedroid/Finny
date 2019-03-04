package tech.arinzedroid.finny.dataModels

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.ParcelProperty
import tech.arinzedroid.finny.utils.DateConverter
import java.util.*

@Parcel
@Entity
@TypeConverters(DateConverter::class)
data class TasksData @ParcelConstructor constructor(
        @PrimaryKey
        @ParcelProperty("id") var id:String = UUID.randomUUID().toString(),
        @ParcelProperty("workId") var workId: String = "",
        @ParcelProperty("itemId") var itemId: String = "",
        @ParcelProperty("itemType") var itemType: String = "",
        @ParcelProperty("status") var status: String = "",
        @ParcelProperty("isSuccess") var isSuccess: Boolean = false,
        @ParcelProperty("dateCreated") var dateCreated: Date = Date(),
        @ParcelProperty("dateUpdated") var dateUpdated: Date = Date()

){
    constructor(): this(status = "")
}