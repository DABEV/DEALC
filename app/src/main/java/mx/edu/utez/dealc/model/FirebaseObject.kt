package mx.edu.utez.dealc.model

open class FirebaseObject(open var id : String?) {
    open fun toMap() : MutableMap<String, Any?> {
        return mutableMapOf()
    }
}