package mx.edu.utez.dealc.provider

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.database.Query
import kotlinx.coroutines.CompletableDeferred
import mx.edu.utez.dealc.model.Location
import mx.edu.utez.dealc.model.Message
import mx.edu.utez.dealc.model.ServiceProviderRequest

class ServiceProviderRequestProvider : FirebaseProvider() {
    companion object {
        private val TAG = ServiceProviderRequestProvider::class.java.toString()
        val COLLECTION_NAME = "ServiceProviderRequest"
        val COLLECTION_NAME_CHAT = "ChatRequest"
        val COLLECTION_NAME_LOCATION = "LocationRequest"
        val COLLECTION_NAME_LOCATION_CHILDS = mapOf(
            "client" to "pointsClient",
            "provider" to "pointsProvider",
        )

        /**
         * Registra una nueva petición en Firestore
         * */
        suspend fun addRequest(serviceProviderRequest: ServiceProviderRequest): Boolean? {
            val response = CompletableDeferred<Boolean?>()

            try {
                saveDataFire(ClientProvider.COLLECTION_NAME, serviceProviderRequest.toMap())
                    .addOnCompleteListener {
                        response.complete(it.isSuccessful)
                    }
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
                response.complete(false)
            }

            return response.await()
        }

        /**
         * Obten todos los Services Request que
         * pertenezcan a mi categoría y a un estatus
         * */
        suspend fun getAllThatBelongsToCategoryAndStatus(categoryServiceId: String, serviceStatusId: String): List<ServiceProviderRequest>? {
            val response = CompletableDeferred<List<ServiceProviderRequest>?>()

            try {
                getCollectionRef(COLLECTION_NAME)
                    .whereEqualTo("categoryServiceId", categoryServiceId)
                    .whereEqualTo("serviceStatusId", serviceStatusId)
                    .get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            response.complete(it.result.map { element ->
                                ServiceProviderRequest.fromDocument(element)
                            })
                        } else {
                            response.complete(listOf())
                        }
                    }
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
                response.complete(listOf())
            }

            return response.await()
        }

        /**
         * Guarda la lista de mensajes del chat
         * */
        suspend fun addToChat(requestId: String, messages: MutableList<Message>): Task<Void>? {
            val response = CompletableDeferred<Task<Void>?>()

            try {
                response.complete(saveDataRealDB(COLLECTION_NAME_CHAT, "chat$requestId", messages))
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
                response.complete(null)
            }

            return response.await()
        }

        /**
         * Obtiene los mensajes del chat
         * */
        suspend fun getMessagesFromChat(requestId: String): List<Message>? {
            val response = CompletableDeferred<List<Message>?>()

            try {
                getAllDataFromChildRealDB(COLLECTION_NAME_CHAT, "chat$requestId")
                    .addOnCompleteListener {
                        response.complete(it.result.value as List<Message>?)
                    }
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
                response.complete(listOf())
            }

            return response.await()
        }

        /**
         * Escucha a un chat para obtener los nuevos mensajes
         * */
        suspend fun listenMessages(requestId: String): Query? {
            val response = CompletableDeferred<Query?>()

            try {
                response.complete(getRealtimeRef(COLLECTION_NAME_CHAT, "chat$requestId").limitToLast(1))
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
                response.complete(null)
            }

            return response.await()
        }

        /**
         * Guarda una nueva localización del proveedor
         * */
        suspend fun addToLocationProvider(requestId: String, location: MutableList<Location>): Task<Void>? {
            val response = CompletableDeferred<Task<Void>?>()

            try {
                response.complete(getRealtimeRef(COLLECTION_NAME_LOCATION, "location$requestId").child(COLLECTION_NAME_LOCATION_CHILDS["provider"]!!).setValue(location))
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
                response.complete(null)
            }

            return response.await()
        }

        /**
         * Escucha a una localización para obtener la nueva posición del proveedor
         * */
        suspend fun listenLocationFromProviderChild(requestId: String): Query? {
            val response = CompletableDeferred<Query?>()

            try {
                response.complete(getRealtimeRef(COLLECTION_NAME_LOCATION, "location$requestId").child(COLLECTION_NAME_LOCATION_CHILDS["provider"]!!).limitToLast(1))
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
                response.complete(null)
            }

            return response.await()
        }
    }
}