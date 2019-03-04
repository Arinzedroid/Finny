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
data class ExpenseModel @ParcelConstructor constructor(
        @ParcelProperty("name") var name: String = "",
        @ParcelProperty("amt") var amt: Double = 0.0,
        @ParcelProperty("desc") var desc: String = "",
        @ParcelProperty("date") var date: Date = Date(),
        @PrimaryKey
        @ParcelProperty("id") var id: String = UUID.randomUUID().toString()
){
    constructor(): this(name = "")
}