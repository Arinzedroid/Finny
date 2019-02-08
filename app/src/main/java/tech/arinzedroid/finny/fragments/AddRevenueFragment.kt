package tech.arinzedroid.finny.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.fragment_add_new_item.view.*
import org.parceler.Parcels

import tech.arinzedroid.finny.R
import tech.arinzedroid.finny.dataModels.RevenueModel

private const val REVENUE: String = "REVENUE"

class AddRevenueFragment : DialogFragment() {

    private var listener: OnFragmentInteractionListener? = null
    private var revenueModel: RevenueModel? = null
    private lateinit var nameET: EditText
    private lateinit var amtET: EditText
    private lateinit var dateSp: Spinner
    private lateinit var automateSw: Switch
    private lateinit var activateSw: Switch
    private lateinit var deleteBtn: Button
    private lateinit var saveBtn: Button


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
       val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setCanceledOnTouchOutside(true)

        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            revenueModel = Parcels.unwrap(it.getParcelable(REVENUE))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_revenues, container, false)

        nameET = view.findViewById(R.id.name_et)
        amtET = view.findViewById(R.id.amt_et)
        dateSp = view.findViewById(R.id.due_date_spiner)
        automateSw = view.findViewById(R.id.automate_sw)
        activateSw = view.findViewById(R.id.activate_sw)
        deleteBtn = view.findViewById(R.id.delete_btn)
        saveBtn = view.findViewById(R.id.save_btn)

        val list = ArrayList<Int>()
        for (i in 1..31){
            list.add(i)
        }

        val adapter = ArrayAdapter<Int>(context,R.layout.spinner_items_layout,list)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        dateSp.adapter = adapter

        saveBtn.setOnClickListener {
            validate()
        }

        deleteBtn.setOnClickListener {
            listener?.onDeleteClicked(revenueModel)
            this.dismiss()
        }

        return view
    }

    private fun validate(){
        if(TextUtils.isEmpty(nameET.text)){
            nameET.error = "Revenue name cannot be empty"
            return
        }
        if(TextUtils.isEmpty(amtET.text)){
            amtET.error = "Amount cannot be empty"
            return
        }

        val revenueModel = RevenueModel()
        revenueModel.name = nameET.text.toString()
        revenueModel.amt = amtET.text.toString().toDouble()
        revenueModel.dueDate = dateSp.selectedItem.toString().toInt()
        revenueModel.automate = automateSw.isChecked
        revenueModel.activate = activateSw.isChecked

        this.dismiss()

        listener?.onSaveClicked(revenueModel)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
       fun onSaveClicked(revenue: RevenueModel)
        fun onDeleteClicked(revenue: RevenueModel?)
    }

    companion object {
        @JvmStatic
        fun newInstance(revenue: RevenueModel?) =
                AddRevenueFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(REVENUE, Parcels.wrap(revenue))
                    }
                }
    }
}
