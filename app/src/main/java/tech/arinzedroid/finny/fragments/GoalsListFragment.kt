package tech.arinzedroid.finny.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.content_home.*
import tech.arinzedroid.finny.R
import tech.arinzedroid.finny.adapters.GoalsAdapter
import tech.arinzedroid.finny.dataModels.GoalsModel
import tech.arinzedroid.finny.database.AppDatabase
import tech.arinzedroid.finny.interfaces.OnItemClickInterface
import tech.arinzedroid.finny.utils.ShowUtils
import tech.arinzedroid.finny.viewModels.AppViewModel


class GoalsListFragment : Fragment(), OnItemClickInterface{

    private lateinit var goalsList :List<GoalsModel>
    private var appViewModel :AppViewModel? = null
    private var database: AppDatabase? = null
    private lateinit var adapter: GoalsAdapter

    override fun onClick(position: Int) {
        println("Item at position $position clicked" )
        val frag:Fragment = GoalDetailsFragment.newInstance(goalsList[position],position)
        ShowUtils.showFragment(activity,frag,R.id.frag_container,true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appViewModel = activity?.let { ViewModelProviders.of(it).get(AppViewModel::class.java) }
        database = this.context?.let { AppDatabase.getInstance(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       val view = inflater.inflate(R.layout.content_home,container,false)
        appViewModel?.getGoals()?.observe(this, Observer {
            it?.let { it1 -> displayGoals(ArrayList(it1)) }
        })

        appViewModel?.getGoalUpdate()?.observe(this, Observer {
            adapter.updateAdapter(it?.goalsModel,it?.positon)
        })

        return view
    }

    private fun displayGoals(goals: ArrayList<GoalsModel>){
        goalsList = goals
        adapter = GoalsAdapter(goals,this)
        recycler_view.adapter = adapter
        no_goals_tv.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = GoalsListFragment()

    }
}
