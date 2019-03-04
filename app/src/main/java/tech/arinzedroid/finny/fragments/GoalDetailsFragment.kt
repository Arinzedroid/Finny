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
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.util.Log
import org.parceler.Parcels

import tech.arinzedroid.finny.R
import tech.arinzedroid.finny.dataModels.GoalsModel
import tech.arinzedroid.finny.dataModels.RevenueModel
import tech.arinzedroid.finny.utils.DateFormatter
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
    private var revenueList: List<RevenueModel>? = null
    private var date: Long = 0


    private lateinit var goalNameTv: TextView
    private lateinit var amtTv: EditText
    private lateinit var debitAmtTv: EditText
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
    private lateinit var calenderBtn: Button
    private lateinit var dialogView: AlertDialog

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
        debitAmtTv = view.findViewById(R.id.debit_amt_et)
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
        //expirationCal = view.findViewById(R.id.expiration_cal)
        activated = view.findViewById(R.id.activate_switch)
        occurenceText = view.findViewById(R.id.occurrence_text)
        layout4 = view.findViewById(R.id.spinner_layout4)
        layout5 = view.findViewById(R.id.layout5)
        view3 = view.findViewById(R.id.view2)
        calenderBtn = view.findViewById(R.id.expiry_button)

        appViewModel?.getRevenue()?.observe(this, Observer {
            revenueList = it
            val list = ArrayList<String>()
            it?.forEach{itm ->
                list.add(itm.name)
            }
            val adapter = ArrayAdapter<String>(activity,android.R.layout.simple_spinner_item,list)
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
            sourceSp.adapter = adapter
        })


        goalNameTv.text = goalModel.goalName
        amtTv.setText(goalModel.totalAmt.toString())
        debitAmtTv.setText(goalModel.debitAmt.toString())
        setSpinnerItem(typeSp,goalModel.type)
        setSpinnerItem(sourceSp,goalModel.source)
        setSpinnerItem(prioritySp,goalModel.priority)
        recurrent.isChecked = goalModel.recurrent
        setSpinnerItem(occurrenceSp,goalModel.occurrence)
        setSpinnerItem(daySp,goalModel.day)
        setSpinnerItem(monthSp,goalModel.month)
        setSpinnerItem(amSp,goalModel.am)
        setSpinnerItem(timeSp,goalModel.time.toString())
        activated.isChecked = goalModel.status

        activity?.let { it1 ->
            val builder = AlertDialog.Builder(it1)
            val nInflater = it1.layoutInflater
            val nView = nInflater.inflate(R.layout.calender_dialog, null)
            builder.setView(nView)
            val calenderView = nView.findViewById<CalendarView>(R.id.calender_view)
            calenderView.date = goalModel.expires.time
            val calendar = Calendar.getInstance()
            calendar.time = goalModel.expires

            calenderView.setOnDateChangeListener { _, year, month, day ->
                calendar.set(year,month,day)
            }

            builder.setPositiveButton("OK") { _, _ ->
                goalModel.expires = Date(calendar.timeInMillis)
            }
            builder.setNegativeButton("Cancel"
            ) { dialog, _ ->
                dialog.cancel()
            }
            dialogView = builder.create()
        }

        if(goalModel.recurrent){
            occurenceText.visibility = View.VISIBLE
            layout4.visibility = View.VISIBLE
            //layout5.visibility = View.VISIBLE
            view3.visibility = View.VISIBLE

        }else{
            occurenceText.visibility = View.GONE
            layout4.visibility = View.GONE
            //layout5.visibility = View.GONE
            view3.visibility = View.GONE

        }

        recurrent.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                occurenceText.visibility = View.VISIBLE
                layout4.visibility = View.VISIBLE
                //layout5.visibility = View.VISIBLE
                view3.visibility = View.VISIBLE

            }else{
                occurenceText.visibility = View.GONE
                layout4.visibility = View.GONE
                //layout5.visibility = View.GONE
                view3.visibility = View.GONE
            }
        }

        occurrenceSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val item = parent.getItemAtPosition(position) as String
                checkOccurrence(item)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        saveBtn.setOnClickListener{
            validate()
        }

        deleteBtn.setOnClickListener{
            listener?.onDeleteGoal(goalModel)
        }

        calenderBtn.setOnClickListener {
            dialogView.show()
        }

        return view
    }

    private fun checkOccurrence(occurrence: String){
       when(occurrence.toUpperCase()){
           "DAILY" -> {
               monthSp.isEnabled = false
               daySp.isEnabled = false
           }
           "WEEKLY" -> {
               monthSp.isEnabled = false
               daySp.isEnabled = true
           }
           "MONTHLY" -> {
               monthSp.isEnabled = true
               daySp.isEnabled = false
           }
       }
    }

    private fun validate(){
        if(TextUtils.isEmpty(amtTv.text)){
           amtTv.error = "Field cannot be empty"
            return
        }
        if(TextUtils.isEmpty(debitAmtTv.text)){
            debitAmtTv.error = "Field cannot be empty"
            return
        }
        if(!isSpinnerSelected(sourceSp)){
            toast("Please select a valid revenue source for this goal")
            return
        }
        if(Date() >= goalModel.expires){
            toast("Expiration date is not valid. Please adjust and try again")
            return
        }
        process()
    }

    private fun toast(msg: String){
        Toast.makeText(activity,msg,Toast.LENGTH_SHORT).show()
    }

    private fun process(){
        goalModel.totalAmt = amtTv.text.toString().toDouble()
        goalModel.debitAmt = debitAmtTv.text.toString().toDouble()
        goalModel.source = sourceSp.selectedItem as String?
        goalModel.sourceId = getIdFromSelected(goalModel.source,revenueList)
        goalModel.priority = prioritySp.selectedItem as String?
        goalModel.recurrent = recurrent.isChecked
        goalModel.occurrence = occurrenceSp.selectedItem as String?
        goalModel.day = daySp.selectedItem as String?
        goalModel.month = monthSp.selectedItem as String?
        goalModel.am = amSp.selectedItem as String?
        val time = timeSp.selectedItem as String?
        goalModel.time = Integer.parseInt(time)
        goalModel.status = activated.isChecked
        listener?.onUpdateGoal(goalModel,position)
    }

    private fun getIdFromSelected(item: String?, revenueList: List<RevenueModel>?): String{
        revenueList?.forEach {
            if(it.name == item)
                return it.id
        }
        return ""
    }

    private fun setSpinnerItem(spinner: Spinner, value: String?){
        val adapter: ArrayAdapter<String>? = spinner.adapter as ArrayAdapter<String>?
        adapter?.getPosition(value)?.let { spinner.setSelection(it) }
    }

    private fun isSpinnerSelected(spinner: Spinner): Boolean{
        val adapter: ArrayAdapter<*>? = spinner.adapter as ArrayAdapter<*>?
        if(adapter != null)
           return adapter.count > 0

        return false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnGoalAddedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onUpdateGoal(goal: GoalsModel, position: Int)
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
