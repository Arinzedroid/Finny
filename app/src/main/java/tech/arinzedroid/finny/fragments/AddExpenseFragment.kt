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
import tech.arinzedroid.finny.dataModels.ExpenseModel
import java.util.*

private const val EXPENSE = "EXPENSE"
private const val POSITION = "POSITION"

class AddExpenseFragment : DialogFragment() {
    private var listener: OnFragmentInteractionListener? = null
    private var expense: ExpenseModel? = null
    private var position: Int = 0

    private lateinit var nameEt: EditText
    private lateinit var amtEt: EditText
    private lateinit var descEt: EditText



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        dialog.setCanceledOnTouchOutside(true)

        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            expense = Parcels.unwrap(it.getParcelable(EXPENSE))
            position = Parcels.unwrap(it.getParcelable(POSITION))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_expense, container, false)

        nameEt = view.findViewById(R.id.name_et)
        amtEt = view.findViewById(R.id.amt_et)
        descEt = view.findViewById(R.id.desc_et)

        val deleteBtn = view.findViewById<Button>(R.id.delete_btn)
        val saveBtn = view.findViewById<Button>(R.id.save_btn)

        if(expense == null){
            deleteBtn.isEnabled = false
        }

        nameEt.setText(expense?.name)
        amtEt.setText(expense?.amt?.toString())
        descEt.setText(expense?.desc)


        saveBtn.setOnClickListener {
            validate()
        }

        deleteBtn.setOnClickListener {
            expense?.let { it1 -> listener?.onDeleteExpense(it1)
                dismiss()
            }
        }



        return view
    }

    private fun validate(){
        if(TextUtils.isEmpty(nameEt.text)){
            nameEt.error = "Field cannot be empty"
            return
        }
        if(TextUtils.isEmpty(amtEt.text)){
            amtEt.error = "Field cannot be empty"
            return
        }

        process()
    }

    private fun process(){

        val name = nameEt.text
        val amt = amtEt.text
        val desc = descEt.text

        if(expense == null){
            val expense = ExpenseModel(name.toString(), amt.toString().toDouble(),desc.toString(), Date())
            listener?.onCreateExpense(expense)

        }else{
            expense?.name = name.toString()
            expense?.amt =  amt.toString().toDouble()
            expense?.desc = desc.toString()
            expense?.let {
                listener?.onUpdateExpense(it,position)
            }
        }
        dismiss()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onCreateExpense(expense: ExpenseModel)
        fun onUpdateExpense(expense: ExpenseModel, position: Int)
        fun onDeleteExpense(expense: ExpenseModel)
    }

    companion object {
        @JvmStatic
        fun newInstance(expense: ExpenseModel, position: Int)
                = AddExpenseFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXPENSE, Parcels.wrap(expense))
                putParcelable(POSITION,Parcels.wrap(position))
            }
        }

        @JvmStatic
        fun newInstance() = AddExpenseFragment()
    }

}
