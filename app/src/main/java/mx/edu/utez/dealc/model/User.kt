package mx.edu.utez.dealc.model

import com.google.firebase.firestore.QueryDocumentSnapshot

open class User (
    override var id: String?,
    open var name: String,
    open var lastName: String,
    open var email: String,
    open var phone: String
) : FirebaseObject(id) {
    companion object {
        /**
         * Crea un objeto en base a un documento de Firebase
         * */
        fun fromDocument (element: QueryDocumentSnapshot): User {
            return User(
                element.id,
                element["name"].toString(),
                element["lastName"].toString(),
                element["email"].toString(),
                element["phone"].toString(),
            )
        }
    }

    override fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "name" to this.name,
            "lastName" to this.lastName,
            "email" to this.email,
            "phone" to this.phone,
        )
    }
}