package mx.edu.utez.dealc.model

import com.google.firebase.firestore.QueryDocumentSnapshot

open class FirebaseObject(open var id : String?) {
    companion object {
        /**
         * Crea un objeto en base a un documento de Firebase
         * */
        fun fromDocument (element: QueryDocumentSnapshot): FirebaseObject {
            return FirebaseObject(element.id)
        }
    }

    open fun toMap() : MutableMap<String, Any?> {
        return mutableMapOf()
    }
}