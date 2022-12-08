package mx.edu.utez.dealc.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import mx.edu.utez.dealc.adapter.CategoryServiceAdapter
import mx.edu.utez.dealc.adapter.ServiceProviderRequestAdapter
import mx.edu.utez.dealc.databinding.ActivityCategoryServiceBinding
import mx.edu.utez.dealc.model.CategoryService
import mx.edu.utez.dealc.model.ServiceProviderRequest

class CategoryServiceActivity : AppCompatActivity(), CategoryServiceAdapter.Eventos {
    lateinit var binding: ActivityCategoryServiceBinding
    lateinit var adapter: CategoryServiceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCategoryServiceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setData()
    }

    private fun setData () {
        binding.recyclerViewCategoryService.layoutManager = LinearLayoutManager(this)
        adapter = CategoryServiceAdapter(this, this)
        binding.recyclerViewCategoryService.adapter = adapter

        val list: List<CategoryService> = listOf(
            CategoryService("", "Categoria 1", "icono 1"),
            CategoryService("", "Categoria 2", "icono 2"),
            CategoryService("", "Categoria 3", "icono 3"),
        )
        adapter.submitList(list)
        adapter.notifyDataSetChanged()
    }


    override fun onItemClick(element: CategoryService, position: Int) {
    }
}