package mx.edu.utez.dealc.model

import java.util.Date

data class Message (
    var userId: String,
    var content: String,
    var dateSent: Date?
)