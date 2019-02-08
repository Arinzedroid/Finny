package tech.arinzedroid.finny.utils

class CurrencyFormatter {

    companion object {
        fun addSymbol(currency: Double):String{
            return "₦ " + currency.toString()
        }
    }
}