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
import mx.edu.utez.dealc.adapter.JobServiceAdapter
import mx.edu.utez.dealc.databinding.ActivityProfileBinding
import mx.edu.utez.dealc.model.Client
import mx.edu.utez.dealc.model.Job
import mx.edu.utez.dealc.viewmodel.ClientViewModel
import mx.edu.utez.dealc.viewmodel.JobViewModel

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    lateinit var viewModel: ClientViewModel
    private val shared = MainCoreApplication.shared

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ClientViewModel::class.java)

        if(shared.isLogged()){
            var email = shared.get()
            lifecycleScope.launch{
                viewModel.getInfoByEmail(email)
            }
        }
        initObservers()

    }

    fun initObservers(){
        viewModel.responsesToSend["getInfoByEmail"]?.first?.observe(this){
            setData(it as Client)
        }

        viewModel.responsesToSend["getInfoByEmail"]?.second?.observe(this){
            Toast.makeText(applicationContext, "Ocurrio un error", Toast.LENGTH_SHORT).show()
        }

    }

    private fun setData (cliente: Client) {
        binding.textViewName.setText(cliente.name)
        binding.textViewEmail.setText(cliente.email)
        binding.textViewLastName.setText(cliente.lastName)
        binding.textViewPhone.setText(cliente.phone)
    }


}