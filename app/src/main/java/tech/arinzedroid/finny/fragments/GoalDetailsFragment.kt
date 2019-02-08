package tech.arinzedroid.finny.fragments

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.arch.lifecycle.Observer
import android.util.Log
import org.parceler.Parcels

import tech.arinzedroid.finny.R
import tech.arinzedroid.finny.dataModels.GoalsModel
import tech.arinzedroid.finny.dataModels.RevenueModel
import tech.arinzedroid.finny.viewModels.AppViewModel
import java.util.*
import kotlin.collections.ArrayList

private const val GOAL_MODEL = "GOAL_MODEL"
private const val POSITION = "POSITION"

class GoalDetailsFragment : Fragment() {

    private lateinit var goalModel: GoalsModel
    private var position: Int = 0
    private var listener: OnFragmentInteractionListener? = null
    private var appViewModel: AppViewModel? = null


    private lateinit var goalNameTv: TextView
    private lateinit var amtTv: TextView
    private lateinit var saveBtn: Button
    private lateinit var editBtn: Button
    private lateinit var deleteBtn: Button
    private lateinit var typeSp: Spinner
    private lateinit var sourceSp: Spinner
    private lateinit var prioritySp: Spinner
    private lateinit var recurrent:Switch
    private lateinit var occurrenceSp:Spinner
    private lateinit var daySp: Spinner
    private lateinit var monthSp: Spinner
    private lateinit var timeSp: Spinner
    private lateinit var amSp: Spinner
    private lateinit var expirationCal: CalendarView
    private lateinit var activated: Switch
    private lateinit var occurenceText: TextView
    private lateinit var layout4: View
    private lateinit var layout5: View
    private lateinit var view3: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appViewModel = activity?.let { ViewModelProviders.of(it).get(AppViewModel::class.java) }
        arguments?.let {
            goalModel = Parcels.unwrap(it.getParcelable(GOAL_MODEL))
            position = Parcels.unwrap(it.getParcelable(POSITION))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view : View =  inflater.inflate(R.layout.fragment_goal_details, container, false)
        goalNameTv = view.findViewById(R.id.goal_name_tv)
        amtTv = view.findViewById(R.id.amt_et)
        saveBtn = view.findViewById(R.id.save_btn)
        //editBtn = view.findViewById(R.id.edit_btn)
        deleteBtn = view.findViewById(R.id.delete_btn)
        typeSp = view.findViewById(R.id.type_spinner)
        sourceSp = view.findViewById(R.id.source_spinner)
        prioritySp = view.findViewById(R.id.priority_spinner)
        recurrent = view.findViewById(R.id.recurrent_switch)
        occurrenceSp = view.findViewById(R.id.occurrence_spinner)
        daySp = view.findViewById(R.id.day_spinner)
        monthSp = view.findViewById(R.id.month_spinner)
        timeSp = view.findViewById(R.id.time_spinner)
        amSp = view.findViewById(R.id.am_spinner)
        expirationCal = view.findViewById(R.id.expiration_cal)
        activated = view.findViewById(R.id.activate_switch)
        occurenceText = view.findViewById(R.id.occurrence_text)
        layout4 = view.findViewById(R.id.spinner_layout4)
        layout5 = view.findViewById(R.id.layout5)
        view3 = view.findViewById(R.id.view2)

        appViewModel?.getRevenue()?.observe(this, Observer {
            val list = ArrayList<String>()
            it?.forEach{itm ->
                list.add(itm.name)
            }
            val adapter = ArrayAdapter<String>(activity,R.layout.spinner_items_layout,list)
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
            sourceSp.adapter = adapter
        })


        goalNameTv.text = goalModel.goalName
        amtTv.text = goalModel.amt.toString()
        setSpinnerItem(typeSp,goalModel.type)
        setSpinnerItem(sourceSp,goalModel.source)
        setSpinnerItem(prioritySp,goalModel.priority)
        recurrent.isChecked = goalModel.recurrent
        setSpinnerItem(occurrenceSp,goalModel.occurrence)
        setSpinnerItem(daySp,goalModel.day)
        setSpinnerItem(monthSp,goalModel.month)
        setSpinnerItem(amSp,goalModel.am)
        expirationCal.date = goalModel.expiration.time
        activated.isChecked = goalModel.status

        recurrent.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                occurenceText.visibility = View.VISIBLE
                layout4.visibility = View.VISIBLE
                layout5.visibility = View.VISIBLE
                view3.visibility = View.VISIBLE

            }else{
                occurenceText.visibility = View.GONE
                layout4.visibility = View.GONE
                layout5.visibility = View.GONE
                view3.visibility = View.GONE
            }
        }

        saveBtn.setOnClickListener{
            validate()
        }

        deleteBtn.setOnClickListener{
            listener?.onDeleteGoal(goalModel)
        }

        return view
    }

    private fun validate(){
        Log.e(this.tag,Date().toString())
        Log.e(this.tag,Date(expirationCal.date).toString())
        if(TextUtils.isEmpty(amtTv.text)){
           amtTv.error = "Value cannot be empty"
            return
        }
//        if(Date().after(Date(expirationCal.date))){
//            Toast.makeText(activity,"Expiration date is less than today. Please adjust and try again",
//                    Toast.LENGTH_SHORT).show()
//            return
//        }

        process()
    }

    private fun process(){
        goalModel.amt = amtTv.text.toString().toDouble()
        //goalModel.source = sourceSp.selectedItem as String
        //goalModel.priority = prioritySp.selectedItem as String
        goalModel.recurrent = recurrent.isChecked
        //goalModel.occurrence = occurrenceSp.selectedItem as String
//        goalModel.day = daySp.selectedItem as String
//        goalModel.month = monthSp.selectedItem as String
//        goalModel.am = amSp.selectedItem as String
        goalModel.expiration = Date(expirationCal.date)
        goalModel.status = activated.isChecked

        listener?.onSaveGoal(goalModel,position)

    }

    private fun setSpinnerItem(spinner: Spinner, value: String){
        val adapter: ArrayAdapter<String>? = spinner.adapter as ArrayAdapter<String>?
        adapter?.getPosition(value)?.let { spinner.setSelection(it) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnGoalAddedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        fun onSaveGoal(goal: GoalsModel, position: Int)
        fun onDeleteGoal(goal: GoalsModel)
    }

    companion object {
        @JvmStatic
        fun newInstance(goalModel :GoalsModel, position: Int) =
                GoalDetailsFragment().apply {
                    arguments = Bundle().apply{
                        putParcelable(GOAL_MODEL,Parcels.wrap(goalModel))
                        putParcelable(POSITION,Parcels.wrap(position))
                    }
                }
    }
}
