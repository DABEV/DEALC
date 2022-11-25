package mx.edu.utez.dealc.model

data class Provider(
    override var id: String?,
    override var name: String,
    override var lastName: String,
    override var email: String,
    override var phone: String,
    var services: MutableList<String>
) : User(id, name, lastName, email, phone) {
    override fun toMap(): MutableMap<String, Any?> {
        val mutableMap : MutableMap<String, Any?> = super.toMap()
        mutableMap["services"] = this.services
        return mutableMap
    }
}