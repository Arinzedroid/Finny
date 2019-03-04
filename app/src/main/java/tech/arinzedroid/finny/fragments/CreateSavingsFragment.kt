package tech.arinzedroid.finny.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import org.parceler.Parcels

import tech.arinzedroid.finny.R
import tech.arinzedroid.finny.dataModels.SavingsModel
import java.lang.RuntimeException
import java.util.*

class CreateSavingsFragment : DialogFragment() {

    private var savingsModel: SavingsModel? = null
    private var listener: FragmentListener? = null
    private lateinit var titleEt: EditText
    private lateinit var amtET: EditText
    private lateinit var descEt: EditText
    private lateinit var deleteBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            savingsModel = Parcels.unwrap(it.getParcelable(SAVINGSMODEL))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_savings, container, false)

        titleEt = view.findViewById(R.id.name_et)
        amtET = view.findViewById(R.id.amt_et)
        descEt = view.findViewById(R.id.desc_et)
        deleteBtn = view.findViewById(R.id.delete_btn)

        if(savingsModel == null){
            deleteBtn.isEnabled = false
        }

        titleEt.setText(savingsModel?.title)
        amtET.setText(savingsModel?.totalAmt?.toString())
        descEt.setText(savingsModel?.desc)

        view.findViewById<Button>(R.id.save_btn).setOnClickListener{
           validate()
        }

        deleteBtn.setOnClickListener{
            savingsModel?.let {
                listener?.onDeleteSavings(it)
            }
            dismiss()
        }

        return view
    }

    private fun validate(){
        if(TextUtils.isEmpty(titleEt.text)){
            titleEt.error = "Invalid title"
            return
        }
        if(TextUtils.isEmpty(amtET.text)){
            amtET.error = "Invalid amount"
            return
        }

        saveData(savingsModel)
    }

    private fun saveData(savingsModel: SavingsModel?){
        if(savingsModel == null){
            val data = SavingsModel()
            data.desc = descEt.text.toString()
            data.totalAmt = amtET.text.toString().toDouble()
            data.title = titleEt.text.toString()
            listener?.onCreateSavings(data)
        }else{
            savingsModel.desc = descEt.text.toString()
            savingsModel.totalAmt = amtET.text.toString().toDouble()
            savingsModel.title = titleEt.text.toString()
            savingsModel.dateUpdated = Date()
            listener?.onSaveSavings(savingsModel)
        }
        dismiss()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is FragmentListener){
            listener = context
        }else{
            throw RuntimeException("$context Must implement FragmentListener")
        }
    }



    interface FragmentListener{
        fun onCreateSavings(savingsModel: SavingsModel)
        fun onSaveSavings(savingsModel: SavingsModel)
        fun onDeleteSavings(savingsModel: SavingsModel)
    }


    companion object {
        fun newInstance(savingsModel: SavingsModel) = CreateSavingsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(SAVINGSMODEL, Parcels.wrap(savingsModel))
            }
        }

        fun newInstance() = CreateSavingsFragment()
    }

}
