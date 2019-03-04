package tech.arinzedroid.finny.viewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import tech.arinzedroid.finny.R
import tech.arinzedroid.finny.interfaces.OnItemClickInterface

class ExpensesViewHolder(itemView: View, private val listener: OnItemClickInterface):
        RecyclerView.ViewHolder(itemView),View.OnClickListener {

    init {
        itemView.findViewById<View>(R.id.item_layout).setOnClickListener(this)
    }

    val nameTv: TextView = itemView.findViewById(R.id.expense_tv)
    val descTv: TextView = itemView.findViewById(R.id.desc_tv)
    val amtTv: TextView = itemView.findViewById(R.id.amt_tv)
    val dateTv: TextView = itemView.findViewById(R.id.date_tv)

    override fun onClick(view: View?) {
        if(view?.id == R.id.item_layout){
            listener.onClick(this.layoutPosition)
        }
    }


}