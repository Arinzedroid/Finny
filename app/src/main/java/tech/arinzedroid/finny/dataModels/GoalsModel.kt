package tech.arinzedroid.finny.dataModels

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.ParcelProperty
import tech.arinzedroid.finny.utils.DateConverter
import java.time.DayOfWeek
import java.time.Month
import java.util.*

@Parcel
@Entity
@TypeConverters(DateConverter::class)
data class GoalsModel @ParcelConstructor constructor(
        @ParcelProperty("goalName") var goalName: String = "",
        @ParcelProperty("dateCreated") var dateCreated: Date = Date(),
        @ParcelProperty("status") var status: Boolean = false,
        @ParcelProperty("amt") var amt: Double = 0.0,
        @ParcelProperty("expires") var expires: Date = Date(),
        @ParcelProperty("type") var type: String = "",
        @ParcelProperty("source") var source: String = "",
        @ParcelProperty("priority") var priority: String = "",
        @ParcelProperty("recurrent") var recurrent: Boolean = false,
        @ParcelProperty("occurrence") var occurrence: String = "",
        @ParcelProperty("day") var day: String = "",
        @ParcelProperty("month") var month: String = "January",
        @ParcelProperty("time") var time: Int = 12,
        @ParcelProperty("am") var am: String = "AM",
        @ParcelProperty("expiration") var expiration: Date = Date(),
        @PrimaryKey()
        @ParcelProperty("id") var id: String = "" )
{
    constructor(): this(goalName = "")

}
