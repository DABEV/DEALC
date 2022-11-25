package mx.edu.utez.dealc.model

data class ServiceProviderRequest (
    override var id: String?,
    var shortDescription: String,
    var categoryServiceId: String,
    var serviceStatusId: String,
    var stars: Int = 0,
    var serviceProviderComments: String,
    var chat: MutableList<Message> = mutableListOf(),
    var pathProvider: MutableList<Location> = mutableListOf(),
) : FirebaseObject(id) {
    override fun toMap(): MutableMap<String, Any?> {
        return mutableMapOf(
            "shortDescription" to this.shortDescription,
            "categoryServiceId" to this.categoryServiceId,
            "serviceStatusId" to this.serviceStatusId,
            "stars" to this.stars,
            "serviceProviderComments" to this.serviceProviderComments,
            "chat" to this.chat,
            "pathProvider" to this.pathProvider,
        )
    }
}