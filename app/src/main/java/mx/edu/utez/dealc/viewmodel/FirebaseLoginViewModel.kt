package mx.edu.utez.dealc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.edu.utez.dealc.provider.FirebaseLoginProvider

class FirebaseLoginViewModel: ViewModel() {
    val result: MutableLiveData<Boolean> = MutableLiveData()
    val error: MutableLiveData<Boolean> = MutableLiveData()

    suspend fun signInWithEmail(email: String, password: String) {
        val response = FirebaseLoginProvider.signInWithEmail(email, password)

        if (response!!) {
            result.postValue(true)
        } else {
            error.postValue(false)
        }
    }
}