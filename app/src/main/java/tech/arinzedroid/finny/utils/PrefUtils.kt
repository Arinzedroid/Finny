package tech.arinzedroid.finny.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

const val USER_ID: String = "USER_ID"
const val USER_FIRST_TIME: String = "FIRST_TIME"
const val LOGGED_IN: String = "LOGGED_IN"

class PrefUtils(context: Context) {

    private var prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private lateinit var editor: SharedPreferences.Editor

    fun setUserId(userId: String){
        editor = prefs.edit()
        editor.putString(USER_ID,userId)
        editor.apply()
    }

    fun getUserId(): String{
        return prefs.getString(USER_ID,"")
    }

    fun setFirstTime(firstTime: Boolean){
        editor = prefs.edit()
        editor.putBoolean(USER_FIRST_TIME,firstTime)
        editor.apply()
    }

    fun isFirstTime(): Boolean{
        return prefs.getBoolean(USER_FIRST_TIME,false)
    }

    fun anonLogIn(login: Boolean){
        editor = prefs.edit()
        editor.putBoolean(LOGGED_IN,login)
        editor.apply()
    }

    fun isAnonLogIn(): Boolean{
        return prefs.getBoolean(LOGGED_IN,false)
    }


}