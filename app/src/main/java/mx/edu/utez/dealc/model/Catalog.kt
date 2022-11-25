package mx.edu.utez.dealc.model

open class Catalog (
    override var id: String?,
    open var name: String,
    open var icon: String?,
) : FirebaseObject(id) {
    override fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "name" to this.name,
            "icon" to this.icon,
        )
    }
}