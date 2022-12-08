package mx.edu.utez.dealc.model

import com.google.firebase.firestore.QueryDocumentSnapshot

data class ChatRequest (
    override var id: String?,
    var messages: MutableList<Message>,
) : FirebaseObject(id) {
    companion object {
        /**
         * Crea un objeto en base a un documento de Firebase
         * */
        fun fromDocument (element: QueryDocumentSnapshot): ChatRequest {
            return ChatRequest(
                element.id,
                element["messages"] as MutableList<Message>,
            )
        }
    }

    override fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "messages" to this.messages,
        )
    }
}