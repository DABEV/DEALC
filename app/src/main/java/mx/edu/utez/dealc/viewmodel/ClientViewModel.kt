package mx.edu.utez.dealc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.edu.utez.dealc.model.Client
import mx.edu.utez.dealc.provider.ClientProvider

class ClientViewModel : ViewModel() {
     /**
     * Cada posición del mapa representa
     * String = nombre del método
     * Pair<> = Las posibles respuestas de cada método (correct y fallido)
     *
     * Dentro del Pair, cada posición representa
     * first = correcto
     * second = fallido
     * */
    val responsesToSend : Map<String, Pair<MutableLiveData<Any>, MutableLiveData<Any>>> = mapOf(
        "getInfoByEmail" to Pair(MutableLiveData(), MutableLiveData()),     // mx.edu.utez.dealc.model.Client
        "addClient" to Pair(MutableLiveData(), MutableLiveData()),          // Boolean
        "updateClient" to Pair(MutableLiveData(), MutableLiveData()),       // Boolean
    )

    /**
     * Obten toda la información del cliente por el correo electrónico
     * */
    suspend fun getInfoByEmail(email: String) {
        val response = ClientProvider.getInfoByEmail(email)

        if (response != null && !response.id.isNullOrEmpty()) {
            responsesToSend["getInfoByEmail"]?.first?.postValue(response)
        } else {
            responsesToSend["getInfoByEmail"]?.second?.postValue(null)
        }
    }

    /**
     * Registra a un nuevo cliente
     * */
    suspend fun addClient(client: Client) {
        val response = ClientProvider.addClient(client)

        if (response!!) {
            responsesToSend["addClient"]?.first?.postValue(response!!)
        } else {
            responsesToSend["addClient"]?.second?.postValue(false)
        }
    }

    /**
     * Actualiza la información del cliente
     * */
    suspend fun updateClient(client: Client) {
        val response = ClientProvider.updateClient(client)

        if (response!!) {
            responsesToSend["updateClient"]?.first?.postValue(response!!)
        } else {
            responsesToSend["updateClient"]?.second?.postValue(false)
        }
    }
}