package mx.edu.utez.dealc.view

import android.content.Context

class Shared(context: Context){

    val NOMBRE_DOCUMENTO = "userSession"

    val EMAIL = "email"

    val storage =  context.getSharedPreferences(NOMBRE_DOCUMENTO,0)
    var edit = storage.edit()

    fun guardarUsuario(email: String){

        edit.putString(EMAIL, email).commit()

        edit.commit()

    }

    fun get():String{
        return storage.getString(EMAIL, "")!!
    }

    fun delete(){
        storage.edit().clear().apply()
    }

}