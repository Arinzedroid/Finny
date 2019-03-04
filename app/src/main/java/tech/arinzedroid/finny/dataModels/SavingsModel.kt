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
data class SavingsModel @ParcelConstructor constructor(
        @PrimaryKey
        @ParcelProperty("id") var id: String = UUID.randomUUID().toString(),
        @ParcelProperty("title") var title: String = "",
        @ParcelProperty("dateCreated") var dateCreated: Date = Date(),
        @ParcelProperty("dateUpdated") var dateUpdated: Date = Date(),
        @ParcelProperty("totalAmt") var totalAmt: Double = 0.0,
        @ParcelProperty("currAmt") var currAmt: Double = 0.0,
        @ParcelProperty("desc") var desc: String = ""
) {
    constructor(): this(title = "")
}