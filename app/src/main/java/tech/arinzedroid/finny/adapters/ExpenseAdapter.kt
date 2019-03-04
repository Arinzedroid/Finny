package tech.arinzedroid.finny.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import tech.arinzedroid.finny.R
import tech.arinzedroid.finny.dataModels.ExpenseModel
import tech.arinzedroid.finny.interfaces.OnItemClickInterface
import tech.arinzedroid.finny.utils.CurrencyFormatter
import tech.arinzedroid.finny.utils.DateFormatter
import tech.arinzedroid.finny.viewHolders.ExpensesViewHolder

class ExpenseAdapter(private val expenseList: ArrayList<ExpenseModel>,
                     private val listener: OnItemClickInterface):
        RecyclerView.Adapter<ExpensesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_item,parent,false)
        return ExpensesViewHolder(view,listener)
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        val data = expenseList[position]
        holder.nameTv.text = data.name
        holder.amtTv.text = CurrencyFormatter.addSymbol(data.amt)
        holder.descTv.text = data.desc
        holder.dateTv.text = DateFormatter.formatDate(data.date)
    }
}