package tech.arinzedroid.finny.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import tech.arinzedroid.finny.interfaces.OnDialogButtonClicked

class ShowUtils {

    companion object {
        @JvmStatic
        fun showFragment(fragmentActivity: FragmentActivity?,fragment: Fragment?, container: Int, addToBackStack: Boolean){
            if(addToBackStack){
                fragmentActivity?.supportFragmentManager?.beginTransaction()
                        ?.replace(container,fragment)?.addToBackStack(null)?.commit()
            }else{
                fragmentActivity?.supportFragmentManager?.beginTransaction()
                        ?.replace(container,fragment)?.commit()
            }
        }

        fun showDialog(context: Context, msg:String, title:String, listener: OnDialogButtonClicked){
            val dialog = AlertDialog.Builder(context)
            dialog.setMessage(msg)
                    .setTitle(title)
                    .setPositiveButton("Yes") { _, _ ->
                        listener.onYesClicked()
                    }
                    .setNegativeButton("No") { _, _ ->
                        listener.onNoClicked()
                    }
                    .setNeutralButton("Cancel") { _, _ ->
                        listener.onCancelClicked()
                    }
            dialog.create().show()
        }
    }
}