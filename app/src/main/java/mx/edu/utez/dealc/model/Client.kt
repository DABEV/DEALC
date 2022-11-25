package mx.edu.utez.dealc.model

data class Client (
    override var id: String?,
    override var name: String,
    override var lastName: String,
    override var email: String,
    override var phone: String,
) : User(id, name, lastName, email, phone)