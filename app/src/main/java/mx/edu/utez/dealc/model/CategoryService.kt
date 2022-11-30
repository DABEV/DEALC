package mx.edu.utez.dealc.model

import com.google.firebase.firestore.QueryDocumentSnapshot

data class CategoryService(
    override var id: String?,
    override var name: String,
    override var icon: String?,
) : Catalog(id, name, icon) {
    companion object {
        /**
         * Crea un objeto en base a un documento de Firebase
         * */
        fun fromDocument (element: QueryDocumentSnapshot): CategoryService {
            return CategoryService(
                element.id,
                element["name"].toString(),
                element["icon"].toString()
            )
        }
    }
}