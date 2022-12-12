package mx.edu.utez.dealc.model

import java.io.Serializable

data class Location (
    var latitude: Double,
    var longitude: Double,
) : Serializable {
    companion object {
        fun fromMap(map: Map<String, Any?>) : Location {
            return Location(
                map["latitude"].toString().toDouble(),
                map["longitude"].toString().toDouble(),
            )
        }
    }

    fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "latitude" to this.latitude,
            "longitude" to this.longitude,
        )
    }
}