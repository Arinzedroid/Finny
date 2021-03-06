package tech.arinzedroid.finny.dataModels

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import org.parceler.Parcel
import org.parceler.ParcelConstructor
import org.parceler.ParcelProperty
import tech.arinzedroid.finny.utils.DateConverter
import java.util.*

@Entity
@Parcel
data class RevenueModel @ParcelConstructor constructor(
        @ParcelProperty("name") var name: String,
        @ParcelProperty("amt") var amt: Double = 0.0,
        @ParcelProperty("recurrentAmt") var recurrentAmt: Double = 0.0,
        @ParcelProperty("dueDate") var dueDate: Int = 0,
        @ParcelProperty("automate") var automate: Boolean = false,
        @ParcelProperty("activate") var activate: Boolean = false,
        @PrimaryKey
        @ParcelProperty("id") var id: String = UUID.randomUUID().toString()
        )
{
    constructor(): this(name = "")

    override fun toString(): String {
        return name
    }

}

