package tech.arinzedroid.finny.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tech.arinzedroid.finny.R
import tech.arinzedroid.finny.dataModels.GoalsModel
import tech.arinzedroid.finny.interfaces.OnItemClickInterface
import tech.arinzedroid.finny.utils.CurrencyFormatter
import tech.arinzedroid.finny.utils.DateFormatter
import tech.arinzedroid.finny.viewHolders.GoalItemsViewHolder

class GoalsAdapter (private val context: Context, private val goalsList: ArrayList<GoalsModel>, private val mListener: OnItemClickInterface):
        RecyclerView.Adapter<GoalItemsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalItemsViewHolder {
        return GoalItemsViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.goal_items_layout,parent,false),mListener)
    }

    override fun getItemCount(): Int {
       return goalsList.size
    }

    fun updateAdapter(goal: GoalsModel?, position: Int?){
        if(position != null && goal != null){
            goalsList[position] = goal
            notifyItemChanged(position)
        }
    }

    override fun onBindViewHolder(holder: GoalItemsViewHolder, position: Int) {
       val goal : GoalsModel = goalsList[position]
        holder.nameView?.text = goal.goalName
        holder.totalAmtView?.text = CurrencyFormatter.addSymbol(goal.totalAmt)
        holder.amtView?.text = CurrencyFormatter.addSymbol(goal.currAmt)
        holder.dateView?.text = DateFormatter.formatDate(goal.expires)
        if(goal.currAmt >= goal.totalAmt){
            holder.clockView?.visibility = View.INVISIBLE
            holder.amtView?.setTextColor(context.resources.getColor(R.color.orangeText))
        }
        when(goal.status){
            true -> holder.activatedView?.setImageDrawable(context.resources?.getDrawable(R.drawable.ic_activated))
            else -> holder.activatedView?.setImageDrawable(context.resources?.getDrawable(R.drawable.ic_deactivated))
        }
        when(goal.recurrent){
            true -> holder.recurrentView?.setImageDrawable(context.resources?.getDrawable(R.drawable.ic_loop))
            else -> holder.recurrentView?.setImageDrawable(context.resources?.getDrawable(R.drawable.ic_no_loop))
        }
    }
}


//private operator fun <E> List<E>.set(i: Int, value: E)  =
//        mapIndexed { index, e -> if(index == i) value else e }
