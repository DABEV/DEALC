package mx.edu.utez.dealc.model

import com.google.firebase.firestore.QueryDocumentSnapshot

data class Job(
    override var id: String?,
    override var name: String,
    override var icon: String?,
    var categoryServiceId: String,
    var categoryService: Map<String, Any?>? = null,
) : Catalog(id, name, icon) {
    companion object {
        /**
         * Crea un objeto en base a un documento de Firebase
         * */
        fun fromDocument (element: QueryDocumentSnapshot): Job {
            val obj = Job(
                element.id,
                element["name"].toString(),
                element["icon"].toString(),
                element["categoryServiceId"].toString(),
            )

            if (element["categoryService"] != null)
                obj.categoryService = element["categoryService"] as Map<String, Any?>?

            return obj
        }

        /**
         * Crea un objeto en base a un documento de Firebase
         * */
        fun fromMap (map: Map<String, Any>): Job {
            val obj = Job(
                null,
                map["name"].toString(),
                map["icon"].toString(),
                map["categoryServiceId"].toString(),
            )

            if (map["categoryService"] != null)
                obj.categoryService = map["categoryService"] as Map<String, Any?>?

            return obj
        }

        /**
         * Crea un objeto en base a un documento de Firebase
         * */
        fun fromMap (id: String?, map: Map<String, Any>): Job {
            val obj = fromMap(map)
            obj.id = id
            return obj
        }
    }
}