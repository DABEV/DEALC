package mx.edu.utez.dealc.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.edu.utez.dealc.model.CategoryService
import mx.edu.utez.dealc.provider.CategoryServiceProvider

class CategoryServiceViewModel : ViewModel() {
    val resultMany: MutableLiveData<List<CategoryService>> = MutableLiveData()
    val errorMany: MutableLiveData<List<CategoryService>> = MutableLiveData()

    /**
     * Obten el listado de las categorías de servicio
     * */
    suspend fun getAll() {
        val response = CategoryServiceProvider.getAll()

        if (response!!.isNotEmpty()) {
            resultMany.postValue(response!!)
        } else {
            errorMany.postValue(listOf())
        }
    }
}