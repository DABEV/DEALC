package mx.edu.utez.dealc.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import mx.edu.utez.dealc.databinding.ActivityServiceHistoryBinding
import mx.edu.utez.dealc.adapter.ServiceProviderRequestAdapter
import mx.edu.utez.dealc.model.ServiceProviderRequest

class ServiceHistoryActivity : AppCompatActivity(), ServiceProviderRequestAdapter.Eventos {

    lateinit var binding: ActivityServiceHistoryBinding
    lateinit var adapter: ServiceProviderRequestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityServiceHistoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setData()
    }

    private fun setData () {
        binding.recyclerViewServiceHistory.layoutManager = LinearLayoutManager(this)
        adapter = ServiceProviderRequestAdapter(this, this)
        binding.recyclerViewServiceHistory.adapter = adapter

        val list: List<ServiceProviderRequest> = listOf(
            ServiceProviderRequest("", "Servicio 1", "1", "1", 2, "Pendiente"),
            ServiceProviderRequest("", "Servicio 2", "2", "2", 2, "Pendiente"),
            ServiceProviderRequest("", "Servicio 3", "3", "3", 2, "Pendiente"),
            ServiceProviderRequest("", "Servicio 4", "4", "4", 2, "Pendiente"),
        )

        adapter.submitList(list)
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(element: ServiceProviderRequest, position: Int) {
    }

    override fun onChatClick(element: ServiceProviderRequest) {
    }

    override fun onAcceptClick(element: ServiceProviderRequest) {
    }

    override fun onRejectClick(element: ServiceProviderRequest) {
    }
}