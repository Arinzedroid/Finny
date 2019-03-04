package tech.arinzedroid.finny.utils

class CurrencyFormatter {

    companion object {
        fun addSymbol(currency: Double):String{
            val moneyString  = String.format("%,.2f",currency)
            return "₦ $moneyString"
        }
    }
}