package com.example.dailydiet

import android.content.Context
import android.content.SharedPreferences

class SaveSharedPrefHelper {
    val PREFS_NAME = "Diet"

    /* save item in shared preference
      key as String
      context as Context
     */

    public fun saveStringItem(key:String,value:String, context:Context) {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
        editor.commit()
    }

    /* get item from shared preference
      key as String
      context as Context
      @Return value as String
     */
    public fun getStringItem(key: String, context: Context): String? {
        val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(key, null)
    }
}