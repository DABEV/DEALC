package mx.edu.utez.dealc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import mx.edu.utez.dealc.databinding.ActivityDetailServiceBinding
import mx.edu.utez.dealc.model.ServiceProviderRequest

class DetailServiceActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailServiceBinding
    lateinit var serviceId: String
    lateinit var statusId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailServiceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        serviceId = intent.getStringExtra("serviceId")!!
        statusId= intent.getStringExtra("status")!!
        binding.layoutProviderDetails.isVisible = false
        binding.buttonSeeWay.isVisible = false
        binding.buttonQualificationService.isVisible = false

        val data = FirebaseFirestore
            .getInstance()
            .collection("ServiceProviderRequest")
            .document(serviceId.toString())

        data.get().addOnSuccessListener {
            if(it["clientId"] != null){
                binding.textViewNameService.text = it["shortDescription"].toString()
                binding.textViewStatus.text = it["serviceStatus.name"].toString()
                println("DETAIL: statusId: ${it["serviceStatusId"]}")
                if (it["serviceStatusId"] != "SmiarENn0AyE8omu0T5G"
                    && it["serviceStatusId"] != null && it["providerId"] != null){
                    val dataProvider = FirebaseFirestore
                        .getInstance()
                        .collection("Provider")
                        .document(it["providerId"]!!.toString())
                    dataProvider.get().addOnSuccessListener {
                        binding.layoutProviderDetails.isVisible = true
                        binding.buttonSeeWay.isVisible = true
                        var nombreCompleto = "${it["name"].toString()} ${it["lastName"].toString()}"
                        binding.textViewNameProvider.text = nombreCompleto
                    }
                }

                if (statusId.equals("E23BtUJxow19URbNkaOF") && it["stars"].toString().toInt() == 0) {
                    binding.buttonQualificationService.isVisible = true
                }
            }else{
                Toast.makeText(this, "Dato inexistente", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "No se encontró el dato", Toast.LENGTH_SHORT).show()
        }


        binding.buttonSeeWay.setOnClickListener {
            var intent = Intent(this, RouteServiceActivity::class.java)
            intent.putExtra("serviceId", serviceId)
            startActivity(intent)
        }

        binding.buttonCancelService.setOnClickListener {
            updateStatusMap("Cancelado", "s9QpNDaHjjqJsMJF2wh9")
        }
        binding.buttonQualificationService.setOnClickListener {
            var intent = Intent(this, RatingBarActivity::class.java)
            intent.putExtra("serviceId", serviceId)
            startActivity(intent)
        }
    }

    fun updateStatusMap(statusName: String, statusId: String){
        val data = FirebaseFirestore.getInstance().collection("ServiceProviderRequest").document(serviceId)
            .update(mapOf("serviceStatus.name" to statusName,
                "serviceStatusId" to statusId))
        data.addOnSuccessListener {
            Toast.makeText(this, "Cancelado con éxito", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,ServiceHistoryActivity::class.java))
            finish()
        }.addOnFailureListener{
        }
    }
}