package mx.edu.utez.dealc.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.firestore.*

open class FirebaseProvider {
    companion object {
        protected val DB_REALTIME = FirebaseDatabase.getInstance()
        protected val DB_FIRESTORE = FirebaseFirestore.getInstance()

        /***********************
         * FIRESTORE FUNCTIONS *
         ***********************/

        /**
         * Obtiene la referencia a una colección desde Firestore Database
         * */
        fun getCollectionRef(collection: String): CollectionReference {
            return DB_FIRESTORE.collection(collection)
        }

        /**
         * Obtiene la referencia a un documento desde Firestore Database
         * */
        fun getDocumentRef(collection: String, documentId: String): DocumentReference {
            return getCollectionRef(collection).document(documentId)
        }

        /**
         * Obtiene toda la info de un documento desde Firestore Database
         * */
        fun getDataFromRef(collection: String, documentId: String): Task<DocumentSnapshot> {
            return getCollectionRef(collection).document(documentId).get()
        }

        /**
         * Registra un nuevo record en la colección especificada
         * */
        fun saveDataFire(collection: String, data: Map<String, Any?>): Task<DocumentReference> {
            return getCollectionRef(collection).add(data)
        }

        /**
         * Actualiza un documento dado su id
         * */
        fun updateDataFire(collection: String, documentId: String, data: Map<String, Any?>): Task<Void> {
            return getDocumentRef(collection, documentId).set(data)
        }

        /**
         * Obtiene todos los datos de una colección
         * */
        fun getAllDataCollection(collection: String): Task<QuerySnapshot> {
            return getCollectionRef(collection).get()
        }

        /*************************
         * REALTIME DB FUNCTIONS *
         *************************/

        /**
         * Obtiene la referencia a una colección y
         * dato específico (si este es necesario) desde Realtime Database
         * */
        fun getRealtimeRef(collection: String, childId: String? = ""): DatabaseReference {
            var dbRef = DB_REALTIME.getReference(collection)

            if (!childId.isNullOrEmpty())
                return dbRef.child(childId)
            return dbRef
        }

        /**
         * Obten todos los datos del child
         * */
        fun getAllDataFromChildRealDB(collection: String, child: String): Task<DataSnapshot> {
            return getRealtimeRef(collection, child).get()
        }

        /**
         * Guarda datos en realtime (Any Class).
         * */
        fun saveDataRealDB(collection: String, childId: String, data: Any?): Task<Void> {
            return getRealtimeRef(collection, childId).setValue(data)
        }

        /**
         * Guarda datos en realtime (MutableList).
         * */
        fun saveDataRealDB(collection: String, childId: String, data: MutableList<Any?>): Task<Void> {
            return getRealtimeRef(collection, childId).setValue(data)
        }
    }
}