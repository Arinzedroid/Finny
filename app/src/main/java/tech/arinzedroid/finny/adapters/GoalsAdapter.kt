package tech.arinzedroid.finny.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import tech.arinzedroid.finny.R
import tech.arinzedroid.finny.dataModels.GoalsModel
import tech.arinzedroid.finny.interfaces.OnItemClickInterface
import tech.arinzedroid.finny.utils.CurrencyFormatter
import tech.arinzedroid.finny.utils.DateFormatter
import tech.arinzedroid.finny.viewHolders.GoalItemsViewHolder

class GoalsAdapter (private val goalsList: ArrayList<GoalsModel>, private val mListener: OnItemClickInterface):
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
        holder.amtView?.text = CurrencyFormatter.addSymbol(goal.amt)
        holder.dateView?.text = DateFormatter.formatDate(goal.expires)
        when(goal.status){
            true -> holder.statusView?.text = "Activated"
            else -> holder.statusView?.text = "Deactivated"
        }
    }
}


//private operator fun <E> List<E>.set(i: Int, value: E)  =
//        mapIndexed { index, e -> if(index == i) value else e }
