package mx.edu.utez.dealc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import mx.edu.utez.dealc.databinding.ActivityDetailServiceBinding
import mx.edu.utez.dealc.model.ServiceProviderRequest

class DetailServiceActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailServiceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val dataService = intent.getStringExtra("serviceId")

        val data = FirebaseFirestore.getInstance().collection("ServiceProviderRequest").document(dataService.toString())
        data.get().addOnSuccessListener {
            if(it["clientId"] != null){
                binding.textViewName.text = it["shortDescription"].toString()
                binding.textViewDetails.text = it["serviceProviderComments"].toString()
            }else{
                Toast.makeText(this, "Dato inexistente", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "No se encontró el dato", Toast.LENGTH_SHORT).show()
        }

        binding.imageViewChat.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }

        binding.buttonSeeWay.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        binding.buttonCancelService.setOnClickListener {  }
    }
}