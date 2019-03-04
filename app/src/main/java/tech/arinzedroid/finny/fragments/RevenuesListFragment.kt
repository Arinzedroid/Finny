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
import tech.arinzedroid.finny.adapters.RevenueAdapter
import tech.arinzedroid.finny.dataModels.RevenueModel
import tech.arinzedroid.finny.interfaces.OnItemClickInterface
import tech.arinzedroid.finny.viewModels.AppViewModel


class RevenuesListFragment : Fragment(), OnItemClickInterface {

    override fun onClick(position: Int) {
        val aFrag = AddRevenueFragment.newInstance(revenueList?.get(position),position)
        aFrag.show(activity?.supportFragmentManager,"")
    }

    private var appViewModel: AppViewModel? = null
    private var adapter: RevenueAdapter? = null
    private var revenueList: List<RevenueModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appViewModel = activity?.let { ViewModelProviders.of(it).get(AppViewModel::class.java) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_revenues_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val noDataTv = view.findViewById<TextView>(R.id.no_revenue_tv)
        appViewModel?.getRevenue()?.observe(this, Observer {
           it?.let { it1 ->
               adapter = RevenueAdapter(ArrayList(it1),this)
               recyclerView.adapter = adapter
               revenueList = it1
               if(it1.isEmpty()){
                   noDataTv.visibility = View.VISIBLE
               }else{
                   noDataTv.visibility = View.GONE
               }
           }
        })

//        appViewModel?.getRevenueUpdate()?.observe(this, Observer {
//            it?.let {it1 ->
//                revenueList = adapter?.updateRevenue(it1.revenue,it1.position)
//            }
//        })

        return view
    }


    companion object {
        @JvmStatic
        fun newInstance() = RevenuesListFragment()
    }
}
