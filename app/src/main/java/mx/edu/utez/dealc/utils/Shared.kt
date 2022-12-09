package mx.edu.utez.dealc.utils

import android.content.Context

class Shared(context: Context){

    val NOMBRE_DOCUMENTO = "mx.edu.utez.dealc"
    val EMAIL = "email"

    val storage =  context.getSharedPreferences(NOMBRE_DOCUMENTO,0)

    fun saveEmail(email: String){
        storage.edit().putString(EMAIL, email).commit()
        storage.edit().commit()
    }

    fun get():String{
        return storage.getString(EMAIL, "")!!
    }

    fun isLogged() : Boolean {
        return !get().isNullOrEmpty()
    }

    fun delete(){
        storage.edit().clear().apply()
    }

}