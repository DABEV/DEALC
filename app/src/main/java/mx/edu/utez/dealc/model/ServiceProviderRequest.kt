package mx.edu.utez.dealc.model

import com.google.firebase.firestore.QueryDocumentSnapshot

data class ServiceProviderRequest (
    override var id: String?,
    var shortDescription: String,
    var categoryServiceId: String,
    var serviceStatusId: String,
    var stars: Int = 0,
    var serviceProviderComments: String
) : FirebaseObject(id) {
    companion object {
        /**
         * Crea un objeto en base a un documento de Firebase
         * */
        fun fromDocument (element: QueryDocumentSnapshot): ServiceProviderRequest {
            return ServiceProviderRequest(
                element.id,
                element["shortDescription"].toString(),
                element["categoryServiceId"].toString(),
                element["serviceStatusId"].toString(),
                element["stars"] as Int,
                element["serviceProviderComments"].toString(),
            )
        }
    }

    override fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "shortDescription" to this.shortDescription,
            "categoryServiceId" to this.categoryServiceId,
            "serviceStatusId" to this.serviceStatusId,
            "stars" to this.stars,
            "serviceProviderComments" to this.serviceProviderComments,
        )
    }
}