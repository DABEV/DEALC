package mx.edu.utez.dealc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.edu.utez.dealc.model.ServiceStatus
import mx.edu.utez.dealc.provider.ServiceStatusProvider

class ServiceStatusViewModel : ViewModel() {
    val resultMany: MutableLiveData<List<ServiceStatus>> = MutableLiveData()
    val errorMany: MutableLiveData<List<ServiceStatus>> = MutableLiveData()

    /**
     * Obten el listado de las estatus del servicio
     * */
    suspend fun getAll() {
        val response = ServiceStatusProvider.getAll()

        if (response!!.isNotEmpty()) {
            resultMany.postValue(response!!)
        } else {
            errorMany.postValue(listOf())
        }
    }
}