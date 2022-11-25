package mx.edu.utez.dealc.model

data class ServiceStatus(
    override var id: String?,
    override var name: String,
    override var icon: String?,
) : Catalog(id, name, icon)