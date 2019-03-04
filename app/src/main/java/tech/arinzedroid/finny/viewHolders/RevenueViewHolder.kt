package tech.arinzedroid.finny.viewHolders

import android.support.v7.widget.RecyclerView
import android.view.View

import android.widget.TextView
import tech.arinzedroid.finny.R
import tech.arinzedroid.finny.interfaces.OnItemClickInterface

class RevenueViewHolder(itemView: View,private val listener: OnItemClickInterface) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener{

    init {
        itemView.findViewById<View>(R.id.item_layout).setOnClickListener(this)
    }

    val title: TextView = itemView.findViewById(R.id.revenue_tv)
    val amt: TextView = itemView.findViewById(R.id.amt_tv)
    val automate: TextView = itemView.findViewById(R.id.automate_tv)
    val activate: TextView = itemView.findViewById(R.id.active_tv)

    override fun onClick(p0: View?) {
        if(p0?.id == R.id.item_layout){
            listener.onClick(this.layoutPosition)
        }
    }
}