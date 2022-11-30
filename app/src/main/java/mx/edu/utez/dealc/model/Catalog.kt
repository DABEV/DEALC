package mx.edu.utez.dealc.model

import com.google.firebase.firestore.QueryDocumentSnapshot

open class Catalog (
    override var id: String?,
    open var name: String,
    open var icon: String?,
) : FirebaseObject(id) {
    companion object {
        /**
         * Crea un objeto en base a un documento de Firebase
         * */
        fun fromDocument (element: QueryDocumentSnapshot): Catalog {
            return Catalog(
                element.id,
                element["name"].toString(),
                element["icon"].toString()
            )
        }
    }

    override fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "name" to this.name,
            "icon" to this.icon,
        )
    }
}