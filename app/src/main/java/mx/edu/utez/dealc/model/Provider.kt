package mx.edu.utez.dealc.model

import com.google.firebase.firestore.QueryDocumentSnapshot

data class Provider(
    override var id: String?,
    override var name: String,
    override var lastName: String,
    override var email: String,
    override var phone: String,
    var services: MutableList<String>
) : User(id, name, lastName, email, phone) {
    companion object {
        /**
         * Crea un objeto en base a un documento de Firebase
         * */
        fun fromDocument (element: QueryDocumentSnapshot): Provider {
            return Provider(
                element.id,
                element["name"].toString(),
                element["lastName"].toString(),
                element["email"].toString(),
                element["phone"].toString(),
                element["services"] as MutableList<String>
            )
        }
    }

    override fun toMap(): MutableMap<String, Any?> {
        val mutableMap : MutableMap<String, Any?> = super.toMap()
        mutableMap["services"] = this.services
        return mutableMap
    }
}