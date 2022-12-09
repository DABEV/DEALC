package mx.edu.utez.dealc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.edu.utez.dealc.provider.JobProvider

class JobViewModel : ViewModel() {
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
        "getAll" to Pair(MutableLiveData(), MutableLiveData()),                     // List<mx.edu.utez.dealc.model.Job>?
        "getAllByCategory" to Pair(MutableLiveData(), MutableLiveData()),           // List<mx.edu.utez.dealc.model.Job>?
        "getJob" to Pair(MutableLiveData(), MutableLiveData()),                     // mx.edu.utez.dealc.model.Job
    )

    suspend fun getAll() {
        val response = JobProvider.getAll()

        if (response!!.isNotEmpty()) {
            responsesToSend["getAll"]?.first?.postValue(response)
        } else {
            responsesToSend["getAll"]?.second?.postValue(response)
        }
    }

    suspend fun getAllByCategory(categoryServiceId: String) {
        val response = JobProvider.getAllByCategory(categoryServiceId)

        if (response!!.isNotEmpty()) {
            responsesToSend["getAllByCategory"]?.first?.postValue(response)
        } else {
            responsesToSend["getAllByCategory"]?.second?.postValue(response)
        }
    }

    suspend fun getJob(jobId: String) {
        val response = JobProvider.getJob(jobId)

        if (response != null) {
            responsesToSend["getJob"]?.first?.postValue(response)
        } else {
            responsesToSend["getJob"]?.second?.postValue(null)
        }
    }

}