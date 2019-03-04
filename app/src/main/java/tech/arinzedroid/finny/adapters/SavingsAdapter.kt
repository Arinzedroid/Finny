package tech.arinzedroid.finny.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import tech.arinzedroid.finny.R
import tech.arinzedroid.finny.dataModels.SavingsModel
import tech.arinzedroid.finny.interfaces.OnSavingsListener
import tech.arinzedroid.finny.utils.CurrencyFormatter
import tech.arinzedroid.finny.utils.DateFormatter
import tech.arinzedroid.finny.viewHolders.SavingsViewHolder

class SavingsAdapter(private val savingsList: List<SavingsModel>, private val listener: OnSavingsListener):
        RecyclerView.Adapter<SavingsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavingsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.savings_items_layout,parent,false)
        return SavingsViewHolder(view,listener)
    }

    override fun getItemCount(): Int {
        return savingsList.size
    }

    override fun onBindViewHolder(holder: SavingsViewHolder, position: Int) {
        val savings = savingsList[position]
        holder.dateTv.text = DateFormatter.formatDate(savings.dateUpdated)
        holder.nameTv.text = savings.title
        holder.totalAmtTv.text = CurrencyFormatter.addSymbol(savings.totalAmt)
    }
}