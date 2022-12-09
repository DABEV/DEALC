package mx.edu.utez.dealc.provider

import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import mx.edu.utez.dealc.model.Job

class JobProvider : FirebaseProvider() {
    companion object {
        private val TAG = JobProvider::class.java.toString()
        val COLLECTION_NAME = "Job"

        /**
         * Obten el listado de todos los trabajos
         * */
        suspend fun getAll(): List<Job>? {
            val response = CompletableDeferred<List<Job>?>()

            try {
                getAllDataCollection(COLLECTION_NAME)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            response.complete(it.result.map { element ->
                                Job.fromDocument(element)
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
         * Obten el listado de todos los trabajos por una categoría
         * */
        suspend fun getAllByCategory(categoryServiceId: String): List<Job>? {
            val response = CompletableDeferred<List<Job>?>()

            try {
                getCollectionRef(ServiceProviderRequestProvider.COLLECTION_NAME)
                    .whereEqualTo("categoryServiceId", categoryServiceId)
                    .get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            response.complete(it.result.map { element ->
                                Job.fromDocument(element)
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
         * Obtiene toda la info de un trabajo por su id
         * */
        suspend fun getJob(jobId: String): Job? {
            val response = CompletableDeferred<Job?>()

            try {
                getDocumentRef(COLLECTION_NAME, jobId)
                    .get()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            response.complete(Job.fromMap(it.result.id, it.result.data!!))
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

    }
}