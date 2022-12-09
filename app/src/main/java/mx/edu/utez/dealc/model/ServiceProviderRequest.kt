package mx.edu.utez.dealc.model

import com.google.firebase.firestore.QueryDocumentSnapshot

data class ServiceProviderRequest (
    override var id: String?,
    var shortDescription: String,
    var categoryServiceId: String,
    var serviceStatusId: String,
    var stars: Long = 0,
    var serviceProviderComments: String,
    var locationClient: Location? = null,
    var clientId: String,
    var providerId: String? = null,
    var categoryService: Map<String, Any?>? = null,
    var serviceStatus: Map<String, Any?>? = null,
    var client: Map<String, Any?>? = null,
    var provider: Map<String, Any?>? = null
) : FirebaseObject(id) {
    companion object {
        /**
         * Crea un objeto en base a un documento de Firebase
         * */
        fun fromDocument (element: QueryDocumentSnapshot): ServiceProviderRequest {
            val obj = ServiceProviderRequest(
                element.id,
                element["shortDescription"].toString(),
                element["categoryServiceId"].toString(),
                element["serviceStatusId"].toString(),
                element["stars"] as Long,
                element["serviceProviderComments"].toString(),
                element["locationClient"] as Location?,
                element["clientId"].toString(),
                element["providerId"].toString(),
            )

            if (element["client"] != null)
                obj.client = element["client"] as Map<String, Any?>?

            if (element["provider"] != null)
                obj.provider = element["provider"] as Map<String, Any?>?

            if (element["categoryService"] != null)
                obj.categoryService = element["categoryService"] as Map<String, Any?>?

            if (element["serviceStatus"] != null)
                obj.serviceStatus = element["serviceStatus"] as Map<String, Any?>?

            return obj
        }

        /**
         * Crea un objeto en base a un mapa
         * */
        fun fromMap (map: Map<String, Any>): ServiceProviderRequest {
            val obj = ServiceProviderRequest(
                null,
                map["shortDescription"].toString(),
                map["categoryServiceId"].toString(),
                map["serviceStatusId"].toString(),
                map["stars"] as Long,
                map["serviceProviderComments"].toString(),
                map["locationClient"] as Location?,
                map["clientId"].toString(),
                map["providerId"].toString(),
            )

            if (map["client"] != null)
                obj.client = map["client"] as Map<String, Any?>?

            if (map["provider"] != null)
                obj.provider = map["provider"] as Map<String, Any?>?

            if (map["categoryService"] != null)
                obj.categoryService = map["categoryService"] as Map<String, Any?>?

            if (map["serviceStatus"] != null)
                obj.serviceStatus = map["serviceStatus"] as Map<String, Any?>?

            return obj
        }

        /**
         * Crea un objeto en base a un mapa
         * */
        fun fromMap (id: String?, map: Map<String, Any>): ServiceProviderRequest {
            val obj = fromMap(map)
            obj.id = id
            return obj
        }
    }

    override fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "shortDescription" to this.shortDescription,
            "categoryServiceId" to this.categoryServiceId,
            "serviceStatusId" to this.serviceStatusId,
            "stars" to this.stars,
            "serviceProviderComments" to this.serviceProviderComments,
            "locationClient" to this.locationClient,
            "clientId" to this.clientId,
            "providerId" to this.providerId,
            "client" to this.client,
            "provider" to this.provider,
            "categoryService" to this.categoryService,
            "serviceStatus" to this.serviceStatus,
        )
    }
}