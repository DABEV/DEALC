package mx.edu.utez.dealc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import mx.edu.utez.dealc.adapter.CategoryServiceAdapter
import mx.edu.utez.dealc.databinding.ActivityCategoryServiceBinding
import mx.edu.utez.dealc.model.CategoryService
import mx.edu.utez.dealc.viewmodel.CategoryServiceViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mx.edu.utez.dealc.MainCoreApplication
import mx.edu.utez.dealc.model.ServiceProviderRequest


class CategoryServiceActivity : AppCompatActivity(), CategoryServiceAdapter.Eventos {
    lateinit var binding: ActivityCategoryServiceBinding
    lateinit var adapter: CategoryServiceAdapter
    lateinit var viewModel: CategoryServiceViewModel
    private val shared = MainCoreApplication.shared

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCategoryServiceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(CategoryServiceViewModel::class.java)
        var list = ArrayList<CategoryService>()


        lifecycleScope.launch {
            viewModel.getAll()
        }

        initObservers()

    }

    fun initObservers(){
        viewModel.resultMany.observe(this){
            setData(it)
        }

        viewModel.errorMany.observe(this){
            Toast.makeText(applicationContext, "Ocurrio un error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setData (lista: List<CategoryService>) {

        binding.recyclerViewCategoryService.layoutManager = LinearLayoutManager(this)
        adapter = CategoryServiceAdapter(this, this)
        binding.recyclerViewCategoryService.adapter = adapter
        adapter.submitList(lista)
        adapter.notifyDataSetChanged()

    }

    override fun onItemClick(element: CategoryService, position: Int) {
        val serviceProviderRequest = ServiceProviderRequest.fromMap(mapOf(
            "categoryServiceId" to element.id,
            "categoryService" to element.toMap(),
            "serviceStatusId" to "E23BtUJxow19URbNkaOF",
            "serviceStatus" to mapOf<String, Any>( "name" to "Disponible"),
            "stars" to 0L,
            "serviceProviderComments" to "",
            "clientId" to shared.getId(),
        ))

        println("1.- Camino service $serviceProviderRequest")

        var intent = Intent(this, JobServiceActivity::class.java).putExtra("serviceProviderRequest", HashMap(serviceProviderRequest.toMap()))
        startActivity(intent)
    }
}