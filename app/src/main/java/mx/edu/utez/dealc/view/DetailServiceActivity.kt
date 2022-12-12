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

        val data = FirebaseFirestore
            .getInstance()
            .collection("ServiceProviderRequest")
            .document(serviceId.toString())

        data.get().addOnSuccessListener {
            if(it["clientId"] != null){
                binding.textViewNameService.text = it["shortDescription"].toString()
                binding.textViewStatus.text = it["serviceStatus.name"].toString()

                if (it["providerId"] != null){
                    val dataProvider = FirebaseFirestore
                        .getInstance()
                        .collection("Provider")
                        .document(it["providerId"]!!.toString())
                    dataProvider.get().addOnSuccessListener {
                        binding.layoutProviderDetails.isVisible = true
                        var nombreCompleto = "${it["name"].toString()} ${it["lastName"].toString()}"
                        binding.textViewNameProvider.text = nombreCompleto
                    }
                }
            }else{
                Toast.makeText(this, "Dato inexistente", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "No se encontró el dato", Toast.LENGTH_SHORT).show()
        }


        binding.buttonSeeWay.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        binding.buttonCancelService.setOnClickListener {
            updateStatusMap("Cancelado", "s9QpNDaHjjqJsMJF2wh9")
        }
        binding.buttonQualificationService.setOnClickListener {
            if (statusId.equals("E23BtUJxow19URbNkaOF")) {
                var intent = Intent(this, RatingBarActivity::class.java)
                intent.putExtra("serviceId", serviceId)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Error no puedes evaluar un servicio que esta en proceso", Toast.LENGTH_SHORT).show()
            }
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