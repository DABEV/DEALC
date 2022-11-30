package mx.edu.utez.dealc.model

import com.google.firebase.firestore.QueryDocumentSnapshot

data class Client (
    override var id: String?,
    override var name: String,
    override var lastName: String,
    override var email: String,
    override var phone: String,
) : User(id, name, lastName, email, phone) {
    companion object {
        /**
         * Crea un objeto en base a un documento de Firebase
         * */
        fun fromDocument (element: QueryDocumentSnapshot): Client {
            return Client(
                element.id,
                element["name"].toString(),
                element["lastName"].toString(),
                element["email"].toString(),
                element["phone"].toString(),
            )
        }
    }
}