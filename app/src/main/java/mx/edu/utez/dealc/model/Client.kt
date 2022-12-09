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

        /**
         * Crea un objeto en base a un mapa de Firebase
         * */
        fun fromMap (map: Map<String, Any>): Client {
            return Client(
                null,
                map["name"].toString(),
                map["lastName"].toString(),
                map["email"].toString(),
                map["phone"].toString(),
            )
        }

        /**
         * Crea un objeto en base a un mapa de Firebase
         * */
        fun fromMap (id: String, map: Map<String, Any>): Client {
            val obj = fromMap(map)
            obj.id = id
            return obj
        }
    }
}