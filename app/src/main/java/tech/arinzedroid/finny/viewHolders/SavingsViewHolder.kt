package tech.arinzedroid.finny.viewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import tech.arinzedroid.finny.R
import tech.arinzedroid.finny.interfaces.OnSavingsListener

class SavingsViewHolder(itemView: View, private val listener: OnSavingsListener):
        RecyclerView.ViewHolder(itemView),View.OnClickListener {

    init {
       itemView.findViewById<ImageButton>(R.id.add_btn).setOnClickListener(this)
        itemView.findViewById<ImageButton>(R.id.sub_btn).setOnClickListener(this)
        itemView.findViewById<View>(R.id.mock_layout).setOnClickListener(this)
    }

    val totalAmtTv: TextView = itemView.findViewById(R.id.item_total_amt)
    val dateTv: TextView = itemView.findViewById(R.id.date_tv)
    val nameTv: TextView = itemView.findViewById(R.id.item_name)

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.add_btn -> listener.onAdd(this.layoutPosition)
            R.id.sub_btn ->  listener.onRemove(this.layoutPosition)
            R.id.mock_layout -> listener.onEdit(this.layoutPosition)
        }
    }
}