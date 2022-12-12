package mx.edu.utez.dealc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import mx.edu.utez.dealc.databinding.ActivityConfirmationBinding
import mx.edu.utez.dealc.model.ServiceProviderRequest
import mx.edu.utez.dealc.viewmodel.ServiceProviderRequestViewModel

class ConfirmationActivity : AppCompatActivity() {
    lateinit var binding : ActivityConfirmationBinding
    lateinit var serviceProviderRequest: ServiceProviderRequest
    lateinit var viewModel: ServiceProviderRequestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ServiceProviderRequestViewModel::class.java)

        serviceProviderRequest = ServiceProviderRequest.fromMap((intent.getSerializableExtra("serviceProviderRequest") as HashMap<String, Any?>).toMap())

        binding.btnRequire.setOnClickListener {
            if (!binding.editTextDetails.text.toString().isNullOrEmpty()){
                lifecycleScope.launch {

                    serviceProviderRequest.shortDescription = binding.editTextDetails.text.toString()

                    // var locationClient = Location(18.8611731, -99.21192)
                    viewModel.addRequest(serviceProviderRequest)
                }
            }
        }

        initObservers()
    }

    fun initObservers(){

        viewModel.responsesToSend["addRequest"]?.first?.observe(this){
            println("4.- Camino service $serviceProviderRequest")
            Toast.makeText(applicationContext, "Todo correcto", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

        viewModel.responsesToSend["addRequest"]?.second?.observe(this){
            Toast.makeText(applicationContext, "Ocurrio un error", Toast.LENGTH_SHORT).show()
        }

    }
}