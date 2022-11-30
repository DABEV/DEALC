package mx.edu.utez.dealc.provider

import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import mx.edu.utez.dealc.model.ServiceStatus

class ServiceStatusProvider : FirebaseProvider() {
    companion object {
        private val TAG = ServiceStatus::class.java.toString()
        val COLLECTION_NAME = "ServiceStatus";

        /**
         * Obten el listado de las estatus del servicio
         * */
        suspend fun getAll(): List<ServiceStatus>? {
            val response = CompletableDeferred<List<ServiceStatus>?>()

            try {
                getAllDataCollection(COLLECTION_NAME)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            response.complete(it.result.map { element ->
                                ServiceStatus.fromDocument(element)
                            })
                        } else {
                            response.complete(listOf())
                        }
                    }
            } catch (e: Exception) {
                Log.d(TAG, e.message!!)
                response.complete(listOf())
            }

            return response.await()
        }
    }
}