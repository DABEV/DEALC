package mx.edu.utez.dealc.provider

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CompletableDeferred
import mx.edu.utez.dealc.model.Client

class FirebaseLoginProvider {
    companion object {
        private val TAG = FirebaseLoginProvider::class.java.toString()
        private val AUTH = FirebaseAuth.getInstance()
        protected val DB_FIRESTORE = FirebaseFirestore.getInstance()

        /**
         * Realiza la autenticación con correo y contraseña
         * */
        suspend fun signInWithEmail(email: String, password: String, collection: String): Client? {
            val response = CompletableDeferred<Client?>()

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
                                        val result = itClient.result.map { element ->
                                            Client.fromDocument(element)
                                        }
                                        response.complete(result[0])
                                    } else {
                                        response.complete(null)
                                    }
                                }
                        } else {
                            response.complete(null)
                        }
                    }
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
                response.complete(null)
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

        suspend fun logout(): Boolean?{
            val response = CompletableDeferred<Boolean?>()

            try {
                AUTH.signOut()
                response.complete(true)
            }catch (e: Exception){
                Log.e(TAG, e.message!!)
                response.complete(false)
            }

            return response.await()
        }
    }
}