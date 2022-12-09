package mx.edu.utez.dealc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.edu.utez.dealc.model.Location
import mx.edu.utez.dealc.model.Message
import mx.edu.utez.dealc.model.ServiceProviderRequest
import mx.edu.utez.dealc.provider.ServiceProviderRequestProvider

class ServiceProviderRequestViewModel: ViewModel() {
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
        "getAllThatBelongsToCategoryAndStatus" to Pair(MutableLiveData(), MutableLiveData()),       // List<mx.edu.utez.dealc.model.ServiceProviderRequest>?
        "addRequest" to Pair(MutableLiveData(), MutableLiveData()),                                 // Boolean
        "addToChat" to Pair(MutableLiveData(), MutableLiveData()),                                  // Task<Void>?
        "getMessagesFromChat" to Pair(MutableLiveData(), MutableLiveData()),                        // List<Message>?
        "listenMessages" to Pair(MutableLiveData(), MutableLiveData()),                             // Query?
        "addToLocationProvider" to Pair(MutableLiveData(), MutableLiveData()),                      // Task<Void>?
        "listenLocationFromProviderChild" to Pair(MutableLiveData(), MutableLiveData()),            // Query?
        "getRequest" to Pair(MutableLiveData(), MutableLiveData()),                                 // mx.edu.utez.dealc.model.ServiceProviderRequest?
        "updateRequest" to Pair(MutableLiveData(), MutableLiveData()),                              // Boolean?
    )

    suspend fun getAllThatBelongsToCategoryAndStatus(categoryServiceId: String, serviceStatusId: String, providerId: String = "") {
        val response = ServiceProviderRequestProvider.getAllThatBelongsToCategoryAndStatus(categoryServiceId, serviceStatusId, providerId)

        if (response != null && response.isNotEmpty()) {
            responsesToSend["getAllThatBelongsToCategoryAndStatus"]?.first?.postValue(response)
        } else {
            responsesToSend["getAllThatBelongsToCategoryAndStatus"]?.second?.postValue(null)
        }
    }

    suspend fun getRequest(requestId: String) {
        val response = ServiceProviderRequestProvider.getRequest(requestId)

        if (response != null) {
            responsesToSend["getRequest"]?.first?.postValue(response)
        } else {
            responsesToSend["getRequest"]?.second?.postValue(null)
        }
    }

    suspend fun addRequest(serviceProviderRequest: ServiceProviderRequest) {
        val response = ServiceProviderRequestProvider.addRequest(serviceProviderRequest)

        if (response!!) {
            responsesToSend["addRequest"]?.first?.postValue(response)
        } else {
            responsesToSend["addRequest"]?.second?.postValue(false)
        }
    }

    suspend fun updateRequest(serviceProviderRequest: ServiceProviderRequest) {
        val response = ServiceProviderRequestProvider.updateRequest(serviceProviderRequest)

        if (response!!) {
            responsesToSend["updateRequest"]?.first?.postValue(response)
        } else {
            responsesToSend["updateRequest"]?.second?.postValue(false)
        }
    }

    suspend fun addToChat(requestId: String, messages: MutableList<Message>) {
        val response = ServiceProviderRequestProvider.addToChat(requestId, messages)

        if (response != null) {
            responsesToSend["addToChat"]?.first?.postValue(response)
        } else {
            responsesToSend["addToChat"]?.second?.postValue(false)
        }
    }

    suspend fun getMessagesFromChat(requestId: String) {
        val response = ServiceProviderRequestProvider.getMessagesFromChat(requestId)

        if (response != null) {
            responsesToSend["getMessagesFromChat"]?.first?.postValue(response)
        } else {
            responsesToSend["getMessagesFromChat"]?.second?.postValue(null)
        }
    }

    suspend fun listenMessages(requestId: String) {
        val response = ServiceProviderRequestProvider.listenMessages(requestId)

        if (response != null) {
            responsesToSend["listenMessages"]?.first?.postValue(response)
        } else {
            responsesToSend["listenMessages"]?.second?.postValue(null)
        }
    }

    suspend fun addToLocationProvider(requestId: String, location: MutableList<Location>) {
        val response = ServiceProviderRequestProvider.addToLocationProvider(requestId, location)

        if (response != null) {
            responsesToSend["addToLocationProvider"]?.first?.postValue(response)
        } else {
            responsesToSend["addToLocationProvider"]?.second?.postValue(null)
        }
    }

    suspend fun listenLocationFromProviderChild(requestId: String) {
        val response = ServiceProviderRequestProvider.listenLocationFromProviderChild(requestId)

        if (response != null) {
            responsesToSend["listenLocationFromProviderChild"]?.first?.postValue(response)
        } else {
            responsesToSend["listenLocationFromProviderChild"]?.second?.postValue(null)
        }
    }
}