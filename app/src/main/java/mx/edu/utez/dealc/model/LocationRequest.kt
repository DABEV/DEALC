package mx.edu.utez.dealc.model

import com.google.firebase.firestore.QueryDocumentSnapshot

class LocationRequest (
    override var id: String?,
    var pointsProvider: MutableList<Location>,
    var pointsClient: MutableList<Location>,
) : FirebaseObject(id) {
    companion object {
        /**
         * Crea un objeto en base a un documento de Firebase
         * */
        fun fromDocument (element: QueryDocumentSnapshot): LocationRequest {
            return LocationRequest(
                element.id,
                element["pointsProvider"] as MutableList<Location>,
                element["pointsClient"] as MutableList<Location>,
            )
        }
    }

    override fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "pointsProvider" to this.pointsProvider,
            "pointsClient" to this.pointsClient,
        )
    }
}