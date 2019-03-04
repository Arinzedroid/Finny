package tech.arinzedroid.finny.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import tech.arinzedroid.finny.R
import tech.arinzedroid.finny.adapters.ExpenseAdapter
import tech.arinzedroid.finny.dataModels.ExpenseModel
import tech.arinzedroid.finny.interfaces.OnItemClickInterface
import tech.arinzedroid.finny.viewModels.AppViewModel

class ExpenseListFragment : Fragment(), OnItemClickInterface {


    private var expenseList: List<ExpenseModel>? = null
    private var appViewModel: AppViewModel? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var noDataTv: TextView
    private var adapter: ExpenseAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            appViewModel = ViewModelProviders.of(it).get(AppViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_expense, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        noDataTv = view.findViewById(R.id.no_expense_tv)

        appViewModel?.getExpense()?.observe(this, Observer {
            adapter = ExpenseAdapter(ArrayList(it),this)
            recyclerView.adapter = adapter
            expenseList = it
            if(it.isNullOrEmpty()){
                noDataTv.visibility = View.VISIBLE
            }else{
                noDataTv.visibility = View.GONE
            }
        })

        return view
    }

    override fun onClick(position: Int) {
        expenseList?.let {
            val frag = AddExpenseFragment.newInstance(it[position],position)
            frag.show(activity?.supportFragmentManager,"")
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = ExpenseListFragment()
    }
}
