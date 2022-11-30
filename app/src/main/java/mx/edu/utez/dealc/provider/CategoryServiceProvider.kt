package mx.edu.utez.dealc.provider

import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import mx.edu.utez.dealc.model.CategoryService

class CategoryServiceProvider : FirebaseProvider() {
    companion object {
        private val TAG = CategoryService::class.java.toString()
        val COLLECTION_NAME = "CategoryService";

        /**
         * Obten el listado de las categorías de servicio
         * */
        suspend fun getAll(): List<CategoryService>? {
            val response = CompletableDeferred<List<CategoryService>?>()

            try {
                getAllDataCollection(COLLECTION_NAME)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            response.complete(it.result.map { element ->
                                CategoryService.fromDocument(element)
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