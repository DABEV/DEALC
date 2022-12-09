package mx.edu.utez.dealc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import mx.edu.utez.dealc.adapter.CategoryServiceAdapter
import mx.edu.utez.dealc.adapter.JobServiceAdapter
import mx.edu.utez.dealc.databinding.ActivityJobServiceBinding
import mx.edu.utez.dealc.model.Job
import mx.edu.utez.dealc.viewmodel.JobViewModel

class JobServiceActivity : AppCompatActivity(), JobServiceAdapter.Eventos {
    lateinit var binding: ActivityJobServiceBinding
    lateinit var viewModel: JobViewModel
    lateinit var adapter: JobServiceAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityJobServiceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(JobViewModel::class.java)

        lifecycleScope.launch {
            viewModel.getAllByCategory(intent.getStringExtra("categoryServiceId")!!)
        }

        initObservers()
    }

    fun initObservers(){

        viewModel.responsesToSend["getAllByCategory"]?.first?.observe(this){
            setData(it as List<Job>)
        }

        viewModel.responsesToSend["getAllByCategory"]?.second?.observe(this){
            Toast.makeText(applicationContext, "Ocurrio un error", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setData (lista: List<Job>) {

        binding.recyclerViewServiceJob.layoutManager = LinearLayoutManager(this)
        adapter = JobServiceAdapter(this, this)
        binding.recyclerViewServiceJob.adapter = adapter
        adapter.submitList(lista)
        adapter.notifyDataSetChanged()

    }

    override fun onItemClick(element: Job, position: Int) {
        startActivity(Intent(applicationContext, MapsActivity::class.java))
    }
}