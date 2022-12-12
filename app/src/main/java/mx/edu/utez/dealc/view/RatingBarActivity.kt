package mx.edu.utez.dealc.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import mx.edu.utez.dealc.databinding.ActivityRatingBarBinding
import mx.edu.utez.dealc.viewmodel.FirebaseLoginViewModel
import mx.edu.utez.dealc.viewmodel.ServiceProviderRequestViewModel

class RatingBarActivity : AppCompatActivity() {
    lateinit var binding: ActivityRatingBarBinding
    lateinit var viewModel: ServiceProviderRequestViewModel
    lateinit var serviceId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ServiceProviderRequestViewModel::class.java)
        binding = ActivityRatingBarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        serviceId = intent.getStringExtra("serviceId")!!
        val ratingBar = binding.ratingBarService
        val ratingScale = binding.textViewRatingScale

        val data = FirebaseFirestore
            .getInstance()
            .collection("ServiceProviderRequest")
            .document(serviceId.toString())

        data.get().addOnSuccessListener {
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "No se encontró el dato", Toast.LENGTH_SHORT).show()
        }

        ratingBar.setOnRatingBarChangeListener { rBar, fl, b ->
            ratingScale.text = fl.toString()
            when (rBar.rating.toInt()) {
                1 -> ratingScale.text = "Terrible"
                2 -> ratingScale.text = "Malo"
                3 -> ratingScale.text = "Regular"
                4 -> ratingScale.text = "Bueno"
                5 -> ratingScale.text = "Excelente"
                else -> ratingScale.text = ""
            }
        }
        binding.buttonQualificationService.setOnClickListener {
            qualificationService(
                ratingBar.rating.toInt(),
                binding.editComment.text.toString()
            )
        }
    }

    fun qualificationService(stars: Int, comments: String) {
        val data =
            FirebaseFirestore.getInstance().collection("ServiceProviderRequest").document(serviceId)
                .update(
                    mapOf(
                        "serviceProviderComments" to comments,
                        "stars" to stars
                    )
                ).addOnSuccessListener {
                    startActivity(Intent(this,MenuActivity::class.java))
                    finish()
                    Toast.makeText(this, "Gracias por usar nuestros servicio", Toast.LENGTH_SHORT)
                        .show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Ocurrio un error, intenta mas tarde", Toast.LENGTH_SHORT)
                        .show()

                }
    }

}