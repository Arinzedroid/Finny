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
import tech.arinzedroid.finny.R

class AddNewGoalFragment : DialogFragment() {

    private var listener: OnGoalAddedListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog : Dialog = super.onCreateDialog(savedInstanceState)

        dialog.setCanceledOnTouchOutside(true)

        return dialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_add_new_item, container, false)

        val ok : Button = view.findViewById(R.id.ok_btn)
        val goalNameEt = view.findViewById<EditText>(R.id.goal_name_et)
        ok.setOnClickListener {
            if(!TextUtils.isEmpty(goalNameEt.text)){
                dismiss()
                onButtonPressed(goalNameEt.text.toString())
            }
        }

        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(goalName: String) {
        listener?.onCreateGoal(goalName)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnGoalAddedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnGoalAddedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnGoalAddedListener {
        fun onCreateGoal(goalName: String)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddNewGoalFragment()
    }
}
