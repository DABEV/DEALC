package mx.edu.utez.dealc.model

import com.google.firebase.firestore.QueryDocumentSnapshot

data class ServiceStatus(
    override var id: String?,
    override var name: String,
    override var icon: String?,
) : Catalog(id, name, icon) {
    companion object {
        /**
         * Crea un objeto en base a un documento de Firebase
         * */
        fun fromDocument (element: QueryDocumentSnapshot): ServiceStatus {
            return ServiceStatus(
                element.id,
                element["name"].toString(),
                element["icon"].toString()
            )
        }

        /**
         * Crea un objeto en base a un documento de Firebase
         * */
        fun fromMap (map: Map<String, Any>): ServiceStatus {
            return ServiceStatus(
                null,
                map["name"].toString(),
                map["icon"].toString()
            )
        }

        /**
         * Crea un objeto en base a un documento de Firebase
         * */
        fun fromMap (id: String?, map: Map<String, Any>): ServiceStatus {
            val obj = fromMap(map)
            obj.id = id
            return obj
        }
    }
}