package com.example.harpreetsingh.mycredibleinfo

import android.content.Context
import android.content.SharedPreferences

class Session(internal var ctx: Context) {
    internal var prefs: SharedPreferences
    internal var editor: SharedPreferences.Editor

    var userID: String
        get() = prefs.getString("UserID", "null")
        set(userID) {

            editor.putString("UserID", userID)
            editor.commit()
        }

    var userEmail: String
        get() = prefs.getString("UserEmail", "null")
        set(userEmail) {
            editor.putString("UserEmail", userEmail)
            editor.commit()
        }
    var professionID: String
        get() = prefs.getString("ProID", "null")
        set(professionID) {
            editor.putString("ProID", professionID)
            editor.commit()
        }
    var educationID: String
        get() = prefs.getString("EduID", "null")
        set(educationID) {
            editor.putString("EduID", educationID)
            editor.commit()
        }

    init {
        prefs = ctx.getSharedPreferences("MyCredibleInfo", Context.MODE_PRIVATE)
        editor = prefs.edit()
    }

    fun setLoggedIn(logIn: Boolean) {
        editor.putBoolean("logInMode", logIn)
        editor.commit()
    }

    fun loggedIn(): Boolean {
        return prefs.getBoolean("logInMode", false)
    }

}