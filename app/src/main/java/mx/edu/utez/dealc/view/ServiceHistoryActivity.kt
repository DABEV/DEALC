package mx.edu.utez.dealc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import mx.edu.utez.dealc.MainCoreApplication
import mx.edu.utez.dealc.databinding.ActivityServiceHistoryBinding
import mx.edu.utez.dealc.adapter.ServiceProviderRequestAdapter
import mx.edu.utez.dealc.model.Client
import mx.edu.utez.dealc.model.Location
import mx.edu.utez.dealc.model.ServiceProviderRequest
import mx.edu.utez.dealc.provider.ServiceProviderRequestProvider
import mx.edu.utez.dealc.viewmodel.ClientViewModel
import mx.edu.utez.dealc.viewmodel.ServiceProviderRequestViewModel

class ServiceHistoryActivity : AppCompatActivity(), ServiceProviderRequestAdapter.Eventos {

    lateinit var binding: ActivityServiceHistoryBinding
    lateinit var adapter: ServiceProviderRequestAdapter
    lateinit var viewModel: ServiceProviderRequestViewModel
    private val shared = MainCoreApplication.shared

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityServiceHistoryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ServiceProviderRequestViewModel::class.java)

        if(shared.isLogged()){
            var idUser = shared.getId()
            lifecycleScope.launch{
                viewModel.getAllThatBelongsToMe(idUser)
            }
        }
        initObservers()
    }

    fun initObservers(){
        viewModel.responsesToSend["getAllThatBelongsToMe"]?.first?.observe(this){
            setData(it as List<ServiceProviderRequest>)
        }

        viewModel.responsesToSend["getAllThatBelongsToMe"]?.second?.observe(this){
            Toast.makeText(applicationContext, "Aún no tienes servicios", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setData ( lista: List<ServiceProviderRequest>) {
        binding.recyclerViewServiceHistory.layoutManager = LinearLayoutManager(this)
        adapter = ServiceProviderRequestAdapter(this, this)
        binding.recyclerViewServiceHistory.adapter = adapter

        adapter.submitList(lista)
        adapter.notifyDataSetChanged()
    }

    override fun onItemClick(element: ServiceProviderRequest, position: Int) {
        var intent = Intent(this, DetailServiceActivity::class.java)
        intent.putExtra("serviceId",element.id)
        startActivity(intent)
    }

    override fun onChatClick(element: ServiceProviderRequest) {
        var intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("serviceId",element.id)
        startActivity(intent)
    }

    override fun onAcceptClick(element: ServiceProviderRequest) {
    }

    override fun onRejectClick(element: ServiceProviderRequest) {
    }
}