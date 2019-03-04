package tech.arinzedroid.finny.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import tech.arinzedroid.finny.R
import tech.arinzedroid.finny.dataModels.RevenueModel
import tech.arinzedroid.finny.interfaces.OnItemClickInterface
import tech.arinzedroid.finny.utils.CurrencyFormatter
import tech.arinzedroid.finny.viewHolders.RevenueViewHolder

class RevenueAdapter(private val revenues: ArrayList<RevenueModel>,private val listener: OnItemClickInterface) :
        RecyclerView.Adapter<RevenueViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RevenueViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.revenue_items_layout,parent,false)
        return RevenueViewHolder(view,listener)
    }

    override fun getItemCount(): Int {
        return revenues.size
    }

    fun updateRevenue(revenue : RevenueModel?, position: Int): List<RevenueModel>{
        if(revenue != null){
            revenues[position] = revenue
            notifyItemChanged(position)
        }
        return revenues
    }

    override fun onBindViewHolder(holder: RevenueViewHolder, position: Int) {
        val data = revenues[position]
        holder.title.text = data.name
        holder.amt.text = CurrencyFormatter.addSymbol(data.amt)
        holder.automate.text = when(data.automate){
            true -> "Automated"
            false -> "Not Automated"
        }
        holder.activate.text = when(data.activate){
            true -> "Active"
            false -> "Inactive"
        }
    }
}