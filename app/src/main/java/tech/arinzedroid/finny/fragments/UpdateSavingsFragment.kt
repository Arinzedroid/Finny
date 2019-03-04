package tech.arinzedroid.finny.fragments


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
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

const val SAVINGSMODEL: String = "savingsModel"
const val ISADD: String = "isAdd"

class UpdateSavingsFragment : DialogFragment() {

    private lateinit var amtEt: EditText

    private lateinit var savingsModel: SavingsModel
    private  var isAdd: Boolean = false
    private var listener: FragmentListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            savingsModel = Parcels.unwrap(it.getParcelable(SAVINGSMODEL))
            isAdd = Parcels.unwrap(it.getParcelable(ISADD))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_add_savings, container, false)

        amtEt = view.findViewById(R.id.amt_et)
        view.findViewById<Button>(R.id.save_btn).setOnClickListener{
            if(!TextUtils.isEmpty(amtEt.text)){
                val amt = amtEt.text.toString().toDouble()
                if(isAdd){
                    listener?.onAddSavings(amt,savingsModel)
                    dismiss()
                }else{
                    listener?.onRemoveSavings(amt,savingsModel)
                    dismiss()
                }
            }else{
                amtEt.error = "Invalid amount"
            }
        }

        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is FragmentListener){
            listener = context
        }else{
            throw RuntimeException("$context Must implement fragmentListener interface")
        }
    }


    interface FragmentListener{
        fun onAddSavings(amt: Double, savingsModel: SavingsModel)
        fun onRemoveSavings(amt: Double, savingsModel: SavingsModel)
    }

    companion object {
        @JvmStatic
        fun newInstance(savingsModel: SavingsModel,isAdd: Boolean) = UpdateSavingsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(SAVINGSMODEL, Parcels.wrap(savingsModel))
                putParcelable(ISADD,Parcels.wrap(isAdd))
            }
        }
    }


}
