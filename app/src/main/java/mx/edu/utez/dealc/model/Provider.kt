package mx.edu.utez.dealc.model

import com.google.firebase.firestore.QueryDocumentSnapshot

data class Provider(
    override var id: String?,
    override var name: String,
    override var lastName: String,
    override var email: String,
    override var phone: String,
    var jobs: MutableList<Map<String, Any?>?>
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
                element["jobs"] as MutableList<Map<String, Any?>?>
            )
        }

        /**
         * Crea un objeto en base a un mapa de Firebase
         * */
        fun fromMap (map: Map<String, Any>): Provider {
            return Provider(
                null,
                map["name"].toString(),
                map["lastName"].toString(),
                map["email"].toString(),
                map["phone"].toString(),
                map["jobs"] as MutableList<Map<String, Any?>?>
            )
        }

        /**
         * Crea un objeto en base a un mapa de Firebase
         * */
        fun fromMap (id: String, map: Map<String, Any>): Provider {
            val obj = fromMap(map)
            obj.id = id
            return obj
        }
    }

    override fun toMap(): MutableMap<String, Any?> {
        val mutableMap : MutableMap<String, Any?> = super.toMap()
        mutableMap["jobs"] = this.jobs
        return mutableMap
    }
}