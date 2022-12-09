package mx.edu.utez.dealc.provider

class ProviderProvider : FirebaseProvider() {
    companion object {
        private val TAG = ProviderProvider::class.java.toString()
        val COLLECTION_NAME = "Provider"
    }
}