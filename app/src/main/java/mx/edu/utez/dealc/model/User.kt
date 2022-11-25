package mx.edu.utez.dealc.model

open class User (
    override var id: String?,
    open var name: String,
    open var lastName: String,
    open var email: String,
    open var phone: String
) : FirebaseObject(id) {
    override fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "name" to this.name,
            "lastName" to this.lastName,
            "email" to this.email,
            "phone" to this.phone,
        )
    }
}