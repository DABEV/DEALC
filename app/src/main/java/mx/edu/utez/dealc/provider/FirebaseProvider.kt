package mx.edu.utez.dealc.provider

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

open class FirebaseProvider {
    companion object {
        protected val DB_REALTIME = FirebaseDatabase.getInstance()
        protected val DB_FIRESTORE = FirebaseFirestore.getInstance()

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
         * Obtiene la referencia a una colección desde Firestore Database
         * */
        fun getCollectionRef(collection: String): CollectionReference {
            return DB_FIRESTORE.collection(collection)
        }

        /**
         * Obtiene todos los datos de una colección
         * */
        fun getAllDataCollection(collection: String): Task<QuerySnapshot> {
            return getCollectionRef(collection).get()
        }

        /**
         * Obtiene la referencia a un documento desde Firestore Database
         * */
        fun getDocumentRef(collection: String, documentId: String): DocumentReference {
            return getCollectionRef(collection).document(documentId)
        }
    }
}