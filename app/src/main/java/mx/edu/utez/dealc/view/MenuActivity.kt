package mx.edu.utez.dealc.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.messaging.FirebaseMessaging
import mx.edu.utez.dealc.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    lateinit var binding : ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getFirebaseToken()

        binding.btnProfile.setOnClickListener {

        }

        binding.btnHistory.setOnClickListener {  }

        binding.btnService.setOnClickListener {  }
    }

    fun getFirebaseToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            var TAG = "FB:"
            if (it.isSuccessful){
                var token = it.result
                println("$TAG $token")
            }else {
                println("$TAG Error ${it.exception?.message}")
            }
        }
    }
}