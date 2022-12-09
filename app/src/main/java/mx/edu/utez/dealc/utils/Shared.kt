package mx.edu.utez.dealc.utils

import android.content.Context

class Shared(context: Context){

    val NOMBRE_DOCUMENTO = "mx.edu.utez.dealc"
    val EMAIL = "email"
    val USER_ID = "userId"

    val storage =  context.getSharedPreferences(NOMBRE_DOCUMENTO,0)

    fun saveEmail(email: String, userId: String = ""){
        storage.edit().putString(EMAIL, email).commit()

        if (userId.isNotEmpty())
            storage.edit().putString(USER_ID, userId).commit()

        storage.edit().commit()
    }

    fun get():String{
        return storage.getString(EMAIL, "")!!
    }

    fun getId(): String{
        return storage.getString(USER_ID, "")!!
    }

    fun isLogged() : Boolean {
        return !get().isNullOrEmpty()
    }

    fun delete(){
        storage.edit().clear().apply()
    }

}