package tech.arinzedroid.finny.viewHolders

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.goal_items_layout.view.*
import tech.arinzedroid.finny.interfaces.OnItemClickInterface

class GoalItemsViewHolder(itemView: View?, listener: OnItemClickInterface) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener{

    val nameView = itemView?.item_name
    val amtView = itemView?.item_amt
    val statusView = itemView?.item_status
    val dateView = itemView?.item_date
    val clockView = itemView?.clock
    val totalAmtView = itemView?.item_total_amt
    val activatedView = itemView?.activated
    val recurrentView = itemView?.loop
    val itemLayout = itemView?.item_layout?.setOnClickListener(this)
    private val mListener = listener


    override fun onClick(v: View?) {
        if(v?.id == itemView?.item_layout?.id){
           mListener.onClick(this.layoutPosition)
        }
    }
}