package mx.edu.utez.dealc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.edu.utez.dealc.adapter.CategoryServiceAdapter
import mx.edu.utez.dealc.databinding.ActivityCategoryServiceBinding
import mx.edu.utez.dealc.model.CategoryService
import mx.edu.utez.dealc.viewmodel.CategoryServiceViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch


class CategoryServiceActivity : AppCompatActivity(), CategoryServiceAdapter.Eventos {
    lateinit var binding: ActivityCategoryServiceBinding
    lateinit var adapter: CategoryServiceAdapter
    lateinit var viewModel: CategoryServiceViewModel
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
        var intent = Intent(this, JobServiceActivity::class.java)
        intent.putExtra("categoryServiceId", element.id)
        intent.putExtra("categoryServiceName", element.name)
        intent.putExtra("categoryServiceIcon", element.icon)
        startActivity(intent)
    }
}