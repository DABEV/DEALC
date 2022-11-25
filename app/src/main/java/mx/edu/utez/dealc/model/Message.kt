package mx.edu.utez.dealc.model

import java.util.Date

data class Message (
    override var id: String?,
    var userId: String,
    var content: String,
    var dateSent: Date?
) : FirebaseObject(id) {
    override fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "userId" to this.userId,
            "content" to this.content,
            "dateSent" to this.dateSent
        )
    }
}