package tech.arinzedroid.finny.fragments


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tech.arinzedroid.finny.R
import tech.arinzedroid.finny.adapters.SavingsAdapter
import tech.arinzedroid.finny.dataModels.SavingsModel
import tech.arinzedroid.finny.interfaces.OnSavingsListener
import tech.arinzedroid.finny.viewModels.AppViewModel

class SavingsListFragment : Fragment(),OnSavingsListener {

    private lateinit var savingsList: List<SavingsModel>
    private lateinit var appViewModel: AppViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appViewModel = ViewModelProviders.of(requireActivity()).get(AppViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_savings, container, false)
        val noData = view.findViewById<View>(R.id.no_savings_tv)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        appViewModel.getSavings()?.observe(this, Observer {
            if(it.isNullOrEmpty()){
                noData.visibility = View.VISIBLE
            }else{
                savingsList = it
                noData.visibility = View.GONE
                val adapter = SavingsAdapter(it,this)
                recyclerView.adapter = adapter
            }
        })

        return view
    }

    override fun onAdd(position: Int) {
        val data = savingsList[position]
        val frag = UpdateSavingsFragment.newInstance(data,true)
        frag.show(requireFragmentManager(),"dialog")
    }

    override fun onRemove(position: Int) {
        val data = savingsList[position]
        val frag = UpdateSavingsFragment.newInstance(data,false)
        frag.show(requireFragmentManager(),"dialog")
    }

    override fun onEdit(position: Int) {
        val data = savingsList[position]
        val frag = CreateSavingsFragment.newInstance(data)
        frag.show(requireFragmentManager(),"dialog")
    }

    companion object {
        @JvmStatic
        fun newInstance() = SavingsListFragment()
    }

}
