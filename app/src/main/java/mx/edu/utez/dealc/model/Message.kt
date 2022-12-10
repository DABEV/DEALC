package mx.edu.utez.dealc.model

import com.google.firebase.database.DataSnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import java.util.Date

data class Message (
    var userId: String,
    var username: String,
    var content: String,
){
    companion object{
        fun fromDocument (element: DataSnapshot): Message {
            val obj = Message(
                element.child("userId").value.toString(),
                element.child("username").value.toString(),
                element.child("content").value.toString(),
            )
            return obj
        }
    }

     fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "userId" to this.userId,
            "username" to this.username,
            "content" to this.content,
        )
    }
}
