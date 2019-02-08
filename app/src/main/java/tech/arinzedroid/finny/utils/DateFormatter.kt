package tech.arinzedroid.finny.utils

import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {

    companion object {
        fun formatTime(date: Date):String {
            val dateFormat = SimpleDateFormat("h:mm a", Locale.ROOT)
            return dateFormat.format(date)
        }

        fun formatDate(date: Date):String{
            val dateFormat = SimpleDateFormat("dd-MM-yyyy ", Locale.ROOT)
            return dateFormat.format(date)
        }

        fun formatDateTime(date: Date):String{
            val dateFormat = SimpleDateFormat("dd-MM-yyyy h:mm a", Locale.ROOT)
            return dateFormat.format(date)
        }
    }
}