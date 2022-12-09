package mx.edu.utez.dealc.provider

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CompletableDeferred

class FirebaseLoginProvider {
    companion object {
        private val TAG = FirebaseLoginProvider::class.java.toString()
        private val AUTH = FirebaseAuth.getInstance()
        protected val DB_FIRESTORE = FirebaseFirestore.getInstance()

        /**
         * Realiza la autenticación con correo y contraseña
         * */
        suspend fun signInWithEmail(email: String, password: String, collection: String): Boolean? {
            val response = CompletableDeferred<Boolean?>()

            try {
                AUTH.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            DB_FIRESTORE
                                .collection(collection)
                                .whereEqualTo("email", email)
                                .get()
                                .addOnCompleteListener { itClient ->
                                    if (itClient.isSuccessful) {
                                        response.complete(!itClient.result.isEmpty)
                                    } else {
                                        response.complete(false)
                                    }
                                }
                        } else {
                            response.complete(false)
                        }
                    }
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
                response.complete(false)
            }

            return response.await()
        }

        suspend fun createUserWithEmailAndPassword(email: String, password: String): Boolean?{
            val response = CompletableDeferred<Boolean?>()

            try {
                AUTH.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener {
                        response.complete(it.isSuccessful)
                    }

            }catch (e: Exception){
                Log.e(TAG, e.message!!)
                response.complete(false)
            }

            return response.await()
        }
    }
}