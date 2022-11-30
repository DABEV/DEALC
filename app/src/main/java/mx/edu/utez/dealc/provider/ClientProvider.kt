package mx.edu.utez.dealc.provider

import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import mx.edu.utez.dealc.model.Client

class ClientProvider : FirebaseProvider() {
    companion object {
        private val TAG = ClientProvider::class.java.toString()
        val COLLECTION_NAME = "Client";

        /**
         * Obtiene su información personal por el correo electrónico
         * */
        suspend fun getInfoByEmail(email: String): Client? {
            val response = CompletableDeferred<Client?>()

            try {
                getCollectionRef(COLLECTION_NAME)
                    .whereEqualTo("email", email)
                    .get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val result = it.result.map { element ->
                                Client.fromDocument(element)
                            }
                            response.complete(result[0])
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

        /**
         * Registra a un cliente en Firestore
         * */
        suspend fun addClient(client: Client): Boolean? {
            val response = CompletableDeferred<Boolean?>()

            try {
                saveDataFire(COLLECTION_NAME, client.toMap())
                    .addOnCompleteListener {
                        response.complete(it.isSuccessful)
                    }
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
                response.complete(false)
            }

            return response.await()
        }
    }
}