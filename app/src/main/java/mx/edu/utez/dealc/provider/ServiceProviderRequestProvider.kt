package mx.edu.utez.dealc.provider

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.database.Query
import kotlinx.coroutines.CompletableDeferred
import mx.edu.utez.dealc.model.*

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
         * Obtiene toda la info de una petición
         * */
        suspend fun getRequest(requestId: String): ServiceProviderRequest? {
            val response = CompletableDeferred<ServiceProviderRequest?>()

            try {
                getDocumentRef(COLLECTION_NAME, requestId)
                    .get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            response.complete(ServiceProviderRequest.fromMap(it.result.id, it.result.data!!))
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
         * Registra una nueva petición en Firestore
         * */
        suspend fun addRequest(serviceProviderRequest: ServiceProviderRequest): Boolean? {
            val response = CompletableDeferred<Boolean?>()

            try {
                saveDataFire(COLLECTION_NAME, serviceProviderRequest.toMap())
                    .addOnCompleteListener {
                        println("IdChido ${it.isSuccessful}")
                        response.complete(it.isSuccessful)
                    }
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
                response.complete(false)
            }

            return response.await()
        }

        /**
         * Actualiza una petición en Firestore
         * */
        suspend fun updateRequest(serviceProviderRequest: ServiceProviderRequest): Boolean? {
            val response = CompletableDeferred<Boolean?>()

            try {
                updateDataFire(ClientProvider.COLLECTION_NAME, serviceProviderRequest.id!!, serviceProviderRequest.toMap())
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
        suspend fun getAllThatBelongsToCategoryAndStatus(categoryServiceId: String, serviceStatusId: String, providerId: String = ""): List<ServiceProviderRequest>? {
            val response = CompletableDeferred<List<ServiceProviderRequest>?>()

            var query = getCollectionRef(COLLECTION_NAME)
                .whereEqualTo("categoryServiceId", categoryServiceId)
                .whereEqualTo("serviceStatusId", serviceStatusId)

            /**
             * En caso de que hayan mandado un provider, entonces lo consideramos para
             * la consulta
             * */
            if (!providerId.isNullOrEmpty()) {
                query = query.whereEqualTo("providerId", providerId)
            }

            try {
                query.get()
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
        suspend fun getMessagesFromChat(requestId: String): MutableList<Message>? {
            val response = CompletableDeferred<MutableList<Message>?>()

            try {
                getAllDataFromChildRealDB(COLLECTION_NAME_CHAT, "chat$requestId")
                    .addOnCompleteListener {
                        response.complete(it.result.children.map { element ->
                            Message.fromDocument(element)
                        } as MutableList<Message>?)
                    }
            } catch (e: Exception) {
                Log.e(TAG, e.message!!)
                response.complete(mutableListOf())
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
        /**
         * Obten todos los Services Request que
         * pertenezcan a mi
         * */
        suspend fun getAllThatBelongsToMe(userId: String, field: String = "clientId"): List<ServiceProviderRequest>? {
            val response = CompletableDeferred<List<ServiceProviderRequest>?>()

            var query = getCollectionRef(COLLECTION_NAME)
                .whereEqualTo(field, userId)

            try {
                query.get()
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

    }
}