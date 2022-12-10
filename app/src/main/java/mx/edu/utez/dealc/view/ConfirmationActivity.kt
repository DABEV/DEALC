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
import mx.edu.utez.dealc.databinding.ActivityConfirmationBinding
import mx.edu.utez.dealc.model.Job
import mx.edu.utez.dealc.model.Location
import mx.edu.utez.dealc.model.ServiceProviderRequest
import mx.edu.utez.dealc.viewmodel.ServiceProviderRequestViewModel

class ConfirmationActivity : AppCompatActivity() {
    lateinit var binding : ActivityConfirmationBinding
    lateinit var  categoryServiceId: String
    lateinit var categoryServiceName: String
    lateinit var categoryServiceIcon: String
    lateinit var viewModel: ServiceProviderRequestViewModel
    private val shared = MainCoreApplication.shared

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ServiceProviderRequestViewModel::class.java)

        categoryServiceId = intent.getStringExtra("categoryServiceId")!!
        categoryServiceIcon = intent.getStringExtra("categoryServiceIcon")!!
        categoryServiceName = intent.getStringExtra("categoryServiceName")!!

        binding.btnRequire.setOnClickListener {
            if (!binding.editTextDetails.text.toString().isNullOrEmpty()){
                lifecycleScope.launch {
                    // var locationClient = Location(18.8611731, -99.21192)
                    val service = ServiceProviderRequest(
                        null,
                        binding.editTextDetails.text.toString(),
                        categoryServiceId,
                        "E23BtUJxow19URbNkaOF",
                        0,
                        "",
                        null,
                        shared.getId(),
                        null,
                        null,
                        null,
                        null,
                        null)

                    viewModel.addRequest(service)
                }
            }
        }

        initObservers()
    }

    fun initObservers(){

        viewModel.responsesToSend["addRequest"]?.first?.observe(this){
            Toast.makeText(applicationContext, "Todo correcto", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MenuActivity::class.java))
            finish()
        }

        viewModel.responsesToSend["addRequest"]?.second?.observe(this){
            Toast.makeText(applicationContext, "Ocurrio un error", Toast.LENGTH_SHORT).show()
        }

    }
}