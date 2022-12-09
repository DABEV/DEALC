package mx.edu.utez.dealc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import mx.edu.utez.dealc.databinding.ActivityServiceHistoryBinding
import mx.edu.utez.dealc.adapter.ServiceProviderRequestAdapter
import mx.edu.utez.dealc.model.Location
import mx.edu.utez.dealc.model.ServiceProviderRequest
import java.io.Serializable

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
            ServiceProviderRequest("", "Servicio 1", "1", "1", 2, "Pendiente", Location(18.8692852, -99.2080311), "1", "1"),
            ServiceProviderRequest("", "Servicio 2", "2", "2", 2, "Pendiente", Location(18.8692852, -99.2080311), "1", "1"),
            ServiceProviderRequest("", "Servicio 3", "3", "3", 2, "Pendiente", Location(18.8692852, -99.2080311), "1", "1"),
            ServiceProviderRequest("", "Servicio 4", "4", "4", 2, "Pendiente", Location(18.8692852, -99.2080311), "1", "1"),
        )

        adapter.submitList(list)
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(element: ServiceProviderRequest, position: Int) {
        startActivity(Intent(this, DetailServiceActivity::class.java).putExtra("serviceId", "2h6cgdAgTzIdEr2ixUFQ"))
    }

    override fun onChatClick(element: ServiceProviderRequest) {
        startActivity(Intent(this, ChatActivity::class.java))
    }

    override fun onAcceptClick(element: ServiceProviderRequest) {
    }

    override fun onRejectClick(element: ServiceProviderRequest) {
    }
}